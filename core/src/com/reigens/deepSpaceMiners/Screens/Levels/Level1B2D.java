package com.reigens.deepSpaceMiners.Screens.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.reigens.deepSpaceMiners.GameMain;
import com.reigens.deepSpaceMiners.Screens.LevelSelectScreen;
import com.reigens.deepSpaceMiners.Uitilitys.B2DHelper;
import com.reigens.deepSpaceMiners.Uitilitys.InputProcessorInterface;

/**
 * Created by Richard Reigens on 9/12/2014.
 */
public class Level1B2D implements Screen {
    GameMain game;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

    public Level1B2D(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, - 9.81f/*Gravity*/), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();

        Gdx.input.setInputProcessor(new InputProcessorInterface() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE)
                    game.setScreen(new LevelSelectScreen(game));
                return true;
            }
        });

        BodyDef bodyDef;
        FixtureDef fixtureDef;

        bodyDef = B2DHelper.CreateBody(BodyDef.BodyType.DynamicBody, 0, 1);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(.25f);
        fixtureDef = B2DHelper.CreateFixture(circleShape, 2.5f, .25f, .75f);
        B2DHelper.DrawBody(world, bodyDef, fixtureDef);
        circleShape.dispose();

        bodyDef = B2DHelper.CreateBody(BodyDef.BodyType.DynamicBody, 1, 1);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(.5f, .8f);
        fixtureDef = B2DHelper.CreateFixture(polygonShape, 2f, .5f, .5f);
        B2DHelper.DrawBody(world, bodyDef, fixtureDef);
        polygonShape.dispose();

        bodyDef = B2DHelper.CreateBody(BodyDef.BodyType.StaticBody, 0, 0);
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] { new Vector2(- 500, 0), new Vector2(500, 0) });
        fixtureDef = B2DHelper.CreateFixture(groundShape, 0f, .5f, 0f);
        B2DHelper.DrawBody(world, bodyDef, fixtureDef);
        groundShape.dispose();



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, camera.combined);

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
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
