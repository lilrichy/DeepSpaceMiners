package com.reigens.deepSpaceMiners.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.GameMain;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Richard Reigens on 9/9/2014.
 */
public class LostScreen implements Screen {
    GameMain game;
    private Stage stage;
    private Table table;

    public LostScreen(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/Skin.json"), Assets.manager.get(Assets.uiAtlas, TextureAtlas.class));
        Image screenBackground = new Image(Assets.manager.get(Assets.levelSelectScreenBackground, Texture.class));
        screenBackground.setFillParent(true);
        stage.addActor(screenBackground);
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

        //Retry Level button
        TextButton retryButton = new TextButton("Retry", skin);
        retryButton.addListener(new ClickListener() {
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
        table.add(new Label("Try Again!", skin, "bigWhite")).top().padTop(20).expandX().spaceBottom(50).row();
        table.add().row();
        table.add(new Label("You just blew up your ship!", skin, "smallWhite")).top().padTop(20).expandX().spaceBottom(50).row();
        table.add(menuButton).spaceBottom(30).row();
        table.add(retryButton).row();
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
