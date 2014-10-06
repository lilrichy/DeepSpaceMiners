package com.reigens.deepSpaceMiners.Screens.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.Assets.Strings;
import com.reigens.deepSpaceMiners.Asteroids.AsteroidSmallB2D;
import com.reigens.deepSpaceMiners.GameMain;
import com.reigens.deepSpaceMiners.Screens.LevelSelectScreen;
import com.reigens.deepSpaceMiners.Screens.Levels.Ui.B2DScreenBox;
import com.reigens.deepSpaceMiners.Screens.Levels.Ui.Hud;
import com.reigens.deepSpaceMiners.Ships.Ship1B2D;
import com.reigens.deepSpaceMiners.Uitilitys.ContactListenerInterface;
import com.reigens.deepSpaceMiners.Uitilitys.InputProcessorInterface;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

/**
 * Created by Richard Reigens on 9/12/2014.
 */
public class Level2 extends InputProcessorInterface implements Screen {

    public static long lastDropTime;
    private SpriteBatch batch;
    GameMain game;
    private int ASTEROIDSMISSED = 0, ASTEROIDSGATHERED = 0, HULL = 100;
    private World world;
    private Stage stage;
    private Skin skin;
    private int screenWidth = Gdx.graphics.getWidth();
    private int screenHeight = Gdx.graphics.getHeight();
    private float currentBgY = 0;
    private long lastSpawn = TimeUtils.millis();
    private long lastTimeBg = TimeUtils.millis();
    private Array<Body> worldBodies = new Array<Body>();
    private Texture background;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private MouseJointDef mouseJointDef;
    private MouseJoint joint;
    private QueryCallback queryCallback = new QueryCallback() {
        @Override public boolean reportFixture(Fixture fixture) {
            if (! fixture.testPoint(tmp.x, tmp.y))
                return true;
            if (fixture.getBody().getUserData() != null && fixture.getBody().getUserData() == Strings.SHIP) {
                mouseJointDef.bodyB = fixture.getBody();
                mouseJointDef.target.set(fixture.getBody().getWorldCenter());//tmp.x, tmp.y);
                joint = (MouseJoint) world.createJoint(mouseJointDef);
            }
            return false;
        }
    };
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();
    private int shipFuel = 280;

    //Changeable Level Variables
    private boolean running = true;

    private int fuelRate = 50;
    private int startingHull = 100;// Default Hull integrity to reset to
    private int ateroidSpawnTime = 750;//Asteroid Spawn rate
    private float asteroidGravity = .5f;
    private int asteroidsToWin = 50;// Number of asteroids required to win

    public Level2(GameMain game) {
        this.game = game;
    }

    public void gameState() {
        if (running) {
            float TIMESTEP = 1 / 60f;
            int VELOCITYITERATIONS = 8;
            int POSITIONITERATIONS = 3;

            // Burn Fuel

            if (TimeUtils.millis() - lastTimeBg > fuelRate) {
                shipFuel--;
                Hud.updateFuel(shipFuel);
            }

            // Spawn Random Asteroids
            if (TimeUtils.millis() - lastSpawn > ateroidSpawnTime) {

                AsteroidSmallB2D.spawnAsteroid(world, screenWidth, screenHeight, asteroidGravity);
                // set the current time to lastTimeBg
                lastSpawn = TimeUtils.millis();
            }

            world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

            // move the separator each 1s
            if (TimeUtils.millis() - lastTimeBg > 100) {
                // move the separator
                currentBgY -= .01f;
                // set the current time to lastTimeBg
                lastTimeBg = TimeUtils.millis();
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

            sweepDeadBodies();
        }
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, - 9.81f/*Gravity*/), true);
        world.setContactListener(new ContactListenerInterface());
        debugRenderer = new Box2DDebugRenderer();
        stage = new Stage(new StretchViewport(1920, 1080));
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("ui/Skin.json"), Assets.manager.get(Assets.uiAtlas, TextureAtlas.class));
        InputMultiplexer multiplexer = new InputMultiplexer(this, stage);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);
        // Gdx.input.setCatchMenuKey(true);
        ContactListenerInterface.resetValues(startingHull);

        Hud.createHud(stage, ASTEROIDSMISSED, ASTEROIDSGATHERED, HULL, shipFuel);
        B2DScreenBox.setupScreenBox(world, screenWidth, screenHeight);
        background = Assets.manager.get(Assets.level1ScrollingBG, Texture.class);

        BodyDef bodyDef = new BodyDef();
        FixtureDef wormholeFixtureDef = new FixtureDef(), shipFixtureDef = new FixtureDef();
        Ship1B2D.createShip(world, shipFixtureDef, wormholeFixtureDef, 0f, 1f, .7f, .7f);

        //Mouse joint
        Body blankBody = world.createBody(bodyDef);
        mouseJointDef = new MouseJointDef();
        mouseJointDef.bodyA = blankBody;
        mouseJointDef.collideConnected = true;
        mouseJointDef.maxForce = 500;
    }

    @Override
    public void render(float delta) {
        delta = MathUtils.clamp(delta, 0, 1 / 30f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ASTEROIDSGATHERED = ContactListenerInterface.ASTEROIDSGATHERED;
        ASTEROIDSMISSED = ContactListenerInterface.ASTEROIDSMISSED;
        HULL = ContactListenerInterface.HULLINTEGRITY;

        if (HULL <= 0) {
            lose();
        }

        if (ASTEROIDSGATHERED >= asteroidsToWin) {
            win();
        }

        if (shipFuel<=10){
            if (joint != null){
            world.destroyJoint(joint);
            mouseJointDef.bodyB.setTransform(mouseJointDef.bodyB.getPosition(), 1.57f);
            joint = null;}
        }

        gameState();

        stage.act(delta);
        stage.draw();
        camera.update();
        // debugRenderer.render(world, camera.combined);
    }

    public void win() {
        running = false;
        new Dialog("", skin) {
            {
                text("Congratulations! \n\n You have completed this Mission!" +
                        "\n\n Press back to go to main menu");
            }
        }.show(stage);
    }

    public void lose() {
        running = false;
        new Dialog("", skin) {
            {
                text("You just blew up your ship! \n\n Press back to go to main menu.");
            }
        }.show(stage);
    }

    public void sweepDeadBodies() {
        world.getBodies(worldBodies);
        for (Body body : worldBodies) {
            if (body.getUserData() != null && body.getUserData() == Strings.DELETE) {
                if (! world.isLocked())
                    world.destroyBody(body);
            }
        }
    }

    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        camera.unproject(tmp.set(screenX, screenY, 0));
        world.QueryAABB(queryCallback, tmp.x, tmp.y, tmp.x, tmp.y);
        return false;
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (joint == null)
            return false;
        camera.unproject(tmp.set(screenX, screenY, 0));
        joint.setTarget(tmp2.set(tmp.x, tmp.y));
        Vector2 tmp3 = tmp2.sub(mouseJointDef.bodyB.getPosition());
        float angleTarget = (float) (Math.atan2(tmp3.y, tmp3.x));
        float bodyAngle = mouseJointDef.bodyB.getAngle();
        float angle = bodyAngle + (angleTarget - bodyAngle);
        mouseJointDef.bodyB.setTransform(mouseJointDef.bodyB.getPosition(), angle);
        return true;
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (joint == null)
            return false;
        world.destroyJoint(joint);
        mouseJointDef.bodyB.setTransform(mouseJointDef.bodyB.getPosition(), 1.57f);
        joint = null;
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE:
            case Input.Keys.BACK:
                game.setScreen(new LevelSelectScreen(game));
                break;
            /*case Input.Keys.MENU:
                game.setScreen(new PauseScreen(game));
                break;*/
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
        //  background.dispose();
        batch.dispose();
    }
}