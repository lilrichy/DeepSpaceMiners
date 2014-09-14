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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.GameMain;
import com.reigens.deepSpaceMiners.Screens.LevelSelectScreen;
import com.reigens.deepSpaceMiners.Ships.Ship1B2D;
import com.reigens.deepSpaceMiners.Uitilitys.B2DHelper;
import com.reigens.deepSpaceMiners.Uitilitys.InputProcessorInterface;
import com.reigens.deepSpaceMiners.Uitilitys.LogHelper;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

/**
 * Created by Richard Reigens on 9/12/2014.
 */
public class Level1B2D extends InputProcessorInterface implements Screen {
    GameMain game;
    public Vector3 touch;
    private World world;
    private Stage stage;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
    private Ship1B2D ship;
    public SpriteBatch batch;
    private Box2DSprite shipSprite;
    private MouseJointDef mouseJointDef;
    private Body ground;
    private MouseJoint joint;

    public Level1B2D(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, - 9.81f/*Gravity*/), true);
        debugRenderer = new Box2DDebugRenderer();
        stage = new Stage();
        camera = new OrthographicCamera();
        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(this);

        Image screenBackground = new Image(Assets.manager.get(Assets.level1Background, Texture.class));
        screenBackground.setFillParent(true);
        stage.addActor(screenBackground);

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef(), wormholeFixtureDef = new FixtureDef(),
                shipFixtureDef = new FixtureDef();

        //Ship
        shipFixtureDef.density = 5f;
        shipFixtureDef.friction = .4f;
        shipFixtureDef.restitution = .3f;
        wormholeFixtureDef.density = fixtureDef.density - .5f;
        wormholeFixtureDef.friction = 1;
        wormholeFixtureDef.restitution = .4f;
        ship = new Ship1B2D(world, fixtureDef, wormholeFixtureDef, 0f, 1f, .3f, .4f);

        bodyDef = B2DHelper.CreateBody(BodyDef.BodyType.StaticBody, 0, - 2);
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] { new Vector2(- 500, 0), new Vector2(500, 0) });
        fixtureDef = B2DHelper.CreateFixture(groundShape, 0f, .5f, 0f);
        ground = world.createBody(bodyDef);
        ground.createFixture(fixtureDef);
        groundShape.dispose();

        //Mouse joint
        mouseJointDef = new MouseJointDef();
        mouseJointDef.bodyA = ground;
        mouseJointDef.collideConnected = true;
        mouseJointDef.maxForce = 500;
    }

    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();
    private Vector2 tmp3 = new Vector2();

    private float degrees;
    private float currentDegrees;
    private QueryCallback queryCallback = new QueryCallback() {
        @Override public boolean reportFixture(Fixture fixture) {
            if (! fixture.testPoint(tmp.x, tmp.y))
                return true;
            mouseJointDef.bodyB = fixture.getBody();
            mouseJointDef.target.set(fixture.getBody().getWorldCenter());//tmp.x, tmp.y);

            joint = (MouseJoint) world.createJoint(mouseJointDef);
            LogHelper.Log("Clicked " + tmp.toString());
            return false;
        }
    };

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

        tmp3 = tmp2.sub(mouseJointDef.bodyB.getPosition());
        float angleTarget = (float) (Math.atan2(tmp3.y, tmp3.x));
        float bodyAngle = mouseJointDef.bodyB.getAngle();
        float angle = bodyAngle + (angleTarget - bodyAngle) * .3f;
        mouseJointDef.bodyB.setTransform(mouseJointDef.bodyB.getPosition(), angle);
        System.out.println("angle: " + angle);
        System.out.println("target: " + mouseJointDef.bodyB.getAngle());

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
        if (keycode == Input.Keys.ESCAPE)
            game.setScreen(new LevelSelectScreen(game));
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.zoom += amount / 25f;
        return true;
    }

    @Override
    public void render(float delta) {
        delta = MathUtils.clamp(delta, 0, 1 / 30f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
        debugRenderer.render(world, camera.combined);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Box2DSprite.draw(batch, world);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 100;
        camera.viewportHeight = height / 100;
        camera.update();
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
    }
}
