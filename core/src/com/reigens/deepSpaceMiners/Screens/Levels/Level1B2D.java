package com.reigens.deepSpaceMiners.Screens.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.GameMain;
import com.reigens.deepSpaceMiners.Screens.LevelSelectScreen;
import com.reigens.deepSpaceMiners.Screens.Levels.Ui.B2DScreenBox;
import com.reigens.deepSpaceMiners.Screens.Levels.Ui.Hud;
import com.reigens.deepSpaceMiners.Screens.PauseScreen;
import com.reigens.deepSpaceMiners.Ships.Ship1B2D;
import com.reigens.deepSpaceMiners.Uitilitys.InputProcessorInterface;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

/**
 * Created by Richard Reigens on 9/12/2014.
 */
public class Level1B2D extends InputProcessorInterface implements Screen {
    final int shipHull = 100;// Hull integrity Total - set same as startingHull but this value changes while playing
    public SpriteBatch batch;
    GameMain game;
    int asteroidsGathered;
    int asteroidsMissed;
    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();
    //Changeable Level Variables
    int startingHull = 100;// Default Hull integrity to reset to
    int startingSpeed = 50;// Default Starting speed to reset to "same as fall speed"
    int fallSpeed = 50;// Starting Speed - Set same as startingSpeed, but this value changes while playing.
    int maxSpeed = 500;// Cap on asteroid speed - higher is faster
    int speedRate = 500;// Rate of speed increase - higher is slower increase over time
    int asteroidRate = 500;// Time in between asteroids - higher is less asteroids
    int asteroidsToWin = 50;// Number of asteroids required to win
    private World world;
    private Stage stage;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private MouseJointDef mouseJointDef;
    private MouseJoint joint;
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();
    Texture background;
    float currentBgY;
    long lastTimeBg;

    private QueryCallback queryCallback = new QueryCallback() {
        @Override public boolean reportFixture(Fixture fixture) {
            if (! fixture.testPoint(tmp.x, tmp.y))
                return true;
            mouseJointDef.bodyB = fixture.getBody();
            mouseJointDef.target.set(fixture.getBody().getWorldCenter());//tmp.x, tmp.y);
            joint = (MouseJoint) world.createJoint(mouseJointDef);
            return false;
        }
    };

    public Level1B2D(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, - 9.81f/*Gravity*/), true);
        debugRenderer = new Box2DDebugRenderer();
        stage = new Stage(new StretchViewport(1920, 1080));
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        Hud.createHud(stage, asteroidsGathered, asteroidsMissed, shipHull, fallSpeed);
        background = Assets.manager.get(Assets.level1ScrollingBG, Texture.class);
        currentBgY = 0;
        lastTimeBg = TimeUtils.nanoTime();

        BodyDef bodyDef = new BodyDef();
        FixtureDef wormholeFixtureDef = new FixtureDef(), shipFixtureDef = new FixtureDef();
        Body blankBody = world.createBody(bodyDef);

        //Ship properties
        shipFixtureDef.density = 1f;
        shipFixtureDef.friction = 2f;
        shipFixtureDef.restitution = .3f;
        wormholeFixtureDef.density = 0f;
        wormholeFixtureDef.friction = 1;
        wormholeFixtureDef.restitution = .4f;
        Ship1B2D ship = new Ship1B2D(world, shipFixtureDef, wormholeFixtureDef, 0f, 1f, .45f, .5f);

        world.createBody(bodyDef);
        //Mouse joint
        mouseJointDef = new MouseJointDef();
        mouseJointDef.bodyA = blankBody;
        mouseJointDef.collideConnected = true;
        mouseJointDef.maxForce = 500;

        B2DScreenBox.setupScreenBox(world, screenWidth, screenHeight);
    }

    @Override
    public void render(float delta) {
        delta = MathUtils.clamp(delta, 0, 1 / 30f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

        float TIMESTEP = 1 / 60f;
        int VELOCITYITERATIONS = 8;
        int POSITIONITERATIONS = 3;
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
        // debugRenderer.render(world, camera.combined);

        // move the separator each 1s
        if (TimeUtils.nanoTime() - lastTimeBg > 100000000) {
            // move the separator
            currentBgY -= .01f;
            // set the current time to lastTimeBg
            lastTimeBg = TimeUtils.nanoTime();
        }

        // if the seprator reaches the screen edge, move it back to the first position
        if (currentBgY <= - screenHeight / 50 * .5f) {
            currentBgY = screenHeight / 50 * .5f;
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // draw the first background
        batch.draw(background, - screenWidth / 100 * .5f, currentBgY, screenWidth / 100, screenHeight / 50);
        // draw the second background
        batch.draw(background, - screenWidth / 100 * .5f, currentBgY - screenHeight / 50, screenWidth / 100, screenHeight / 50);
        Box2DSprite.draw(batch, world);
        batch.end();
        stage.draw();
        camera.update();
    }

    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        camera.unproject(tmp.set(screenX, screenY, 0));
        world.QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);
        return true;
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (joint == null)
            return false;
        camera.unproject(tmp.set(screenX, screenY, 0));
        joint.setTarget(tmp2.set(tmp.x, tmp.y));
        Vector2 tmp3 = tmp2.sub(mouseJointDef.bodyB.getPosition());
        float angleTarget = (float) (Math.atan2(tmp3.y, tmp3.x));
        float bodyAngle = mouseJointDef.bodyB.getAngle();
        float angle = bodyAngle + (angleTarget - bodyAngle) * .3f;
        mouseJointDef.bodyB.setTransform(mouseJointDef.bodyB.getPosition(), angle);
        return true;
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (joint == null)
            return false;
        world.destroyJoint(joint);
        mouseJointDef.bodyB.setTransform(mouseJointDef.bodyB.getPosition(), 1.57f);
        joint = null;
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE:
            case Input.Keys.BACK:
                game.setScreen(new LevelSelectScreen(game));
                break;
            case Input.Keys.MENU:
                game.setScreen(new PauseScreen(game));
        }
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.zoom += amount / 25f;
        return true;
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 100;
        camera.viewportHeight = height / 100;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        camera.update();
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        stage.dispose();
        background.dispose();
        batch.dispose();
    }
}