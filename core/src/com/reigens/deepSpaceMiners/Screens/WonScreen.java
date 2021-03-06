package com.reigens.deepSpaceMiners.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.GameMain;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Richard Reigens on 9/9/2014.
 */
public class WonScreen implements Screen {
    GameMain game;
    private Stage stage;
    private Table table;

    public WonScreen(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        stage = new Stage(new StretchViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/Skin.json"), Assets.manager.get(Assets.uiAtlas, TextureAtlas.class));
        table = new Table(skin);
        table.setFillParent(true);

        //Menu button
        TextButton menuButton = new TextButton("Menu", skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new LevelSelectScreen(game));
                    }
                })));
            }
        });

        //NextLevel button
        TextButton nextButton = new TextButton(" Next Level ", skin);
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        //Todo
                        game.setScreen(new LevelSelectScreen(game));
                    }
                })));
            }
        });

        //Set up Table
        table.add(new Label("Congratulations", skin, "bigWhite")).top().padTop(20).expandX().spaceBottom(50).row();
        table.add().row();
        table.add(new Label("You have Completed Level 1", skin, "smallWhite")).top().padTop(20).expandX().spaceBottom(50).row();
        table.add(menuButton).spaceBottom(30).row();
        table.add(nextButton).row();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        delta = MathUtils.clamp(delta, 0, 1 / 30f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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