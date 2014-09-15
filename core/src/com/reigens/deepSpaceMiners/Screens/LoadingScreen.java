package com.reigens.deepSpaceMiners.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.GameMain;

/**
 * Created by Richard Reigens on 9/10/2014.
 */
public class LoadingScreen implements Screen {
    GameMain game;
    BitmapFont font;
    SpriteBatch batch;
    Texture emptyT, fullT;
    NinePatch empty, full;
    Stage stage;
    Image splash;

    public LoadingScreen(GameMain game) {
        this.game = game;
        Assets.load();
        stage = new Stage();
        Texture splashTexture = new Texture(Gdx.files.internal("splash.png"));
        splash = new Image(splashTexture);
    }

    @Override
    public void show() {
        stage.addActor(splash);
        splash.setFillParent(true);
        font = new BitmapFont(Gdx.files.internal(Assets.white32));
        batch = new SpriteBatch();
        emptyT = new Texture(Gdx.files.internal("Loading/empty.png"));
        fullT = new Texture(Gdx.files.internal("Loading/full.png"));
        empty = new NinePatch(new TextureRegion(emptyT, 24, 24), 8, 8, 8, 8);
        full = new NinePatch(new TextureRegion(fullT, 24, 24), 8, 8, 8, 8);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        batch.begin();

        empty.draw(batch, Gdx.graphics.getWidth() / 4, 50, Gdx.graphics.getHeight(), 30);
        full.draw(batch, Gdx.graphics.getWidth() / 4, 50, Assets.manager.getProgress() * Gdx.graphics.getWidth() / 2, 30);
        font.drawMultiLine(batch, (int) (Assets.manager.getProgress() * 100) + "% loaded", Gdx.graphics.getWidth() / 2, 75, 0, BitmapFont.HAlignment.CENTER);
        batch.end();

        if (Assets.manager.update()) {
            // all the assets are loaded
            game.setScreen(new LevelSelectScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
