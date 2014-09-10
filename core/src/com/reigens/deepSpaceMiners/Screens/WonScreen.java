package com.reigens.deepSpaceMiners.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.reigens.deepSpaceMiners.Assets.Graphics;
import com.reigens.deepSpaceMiners.GameMain;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Richard Reigens on 9/9/2014.
 */
public class WonScreen implements Screen {
    GameMain game;


    private TextureAtlas uiAtlas;
    private Stage stage;
    private Table table;
    private Skin skin;
    private Image screenBackground;

    public WonScreen(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Graphics.loadLevelSelectScreen();

        uiAtlas = new TextureAtlas(Gdx.files.internal("ui/ui.pack"));
        skin = new Skin(Gdx.files.internal("ui/Skin.json"), uiAtlas);
        screenBackground = new Image(Graphics.texture_background);
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
                        game.setScreen(game.levelSelectScreen);
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
                        game.setScreen(game.levelSelectScreen);
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
