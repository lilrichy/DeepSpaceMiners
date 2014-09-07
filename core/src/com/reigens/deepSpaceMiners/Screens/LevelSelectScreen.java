package com.reigens.deepSpaceMiners.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.awt.*;


/**
 * Created by Richard Reigens on 9/7/2014.
 */
public class LevelSelectScreen implements Screen {

    public Stage stage;
    public Table table;
    public Skin skin;
    private Image screenBackground;



    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

    //    TextureAtlas uiAtlas = Gdx.files.internal("WIP.pack", TextureAtlas.class);

    }




    @Override
    public void render(float delta) {
        delta = MathUtils.clamp(delta, 0, 1/30f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
    //    screenBackground.setSize(width, height);
        table.invalidateHierarchy();

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

    @Override
    public void dispose() {

    }
}
