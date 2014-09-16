package com.reigens.deepSpaceMiners.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.reigens.deepSpaceMiners.GameMain;

/**
 * Created by Richard Reigens on 9/10/2014.
 */

public class Splash implements Screen {
    GameMain game;
    private Image splash;
    private Stage stage;

    public Splash(GameMain game) {
        this.game = game;
        stage = new Stage(new StretchViewport(1920, 1080));
        Texture splashTexture = new Texture(Gdx.files.internal("splash.png"));
        splash = new Image(splashTexture);
    }

    @Override
    public void show() {
        //Load Preferences
        // Gdx.graphics.setVSync(SettingsScreen.vSync());

        stage.addActor(splash);
        splash.setFillParent(true);

        splash.addAction(Actions.sequence(Actions.alpha(0)
                , Actions.fadeIn(0.75f), Actions.delay(1.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new LoadingScreen(game));
            }
        })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        stage.dispose();
    }
}