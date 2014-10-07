package com.reigens.deepSpaceMiners.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
 * Created by Richard Reigens on 10/6/2014.
 */
public class SettingsScreen implements Screen {
    GameMain game;
    private Stage stage;
    private Table table;
    Sound buttonChirp;

    public SettingsScreen(GameMain game) {
        this.game = game;
    }

    @Override public void render(float delta) {
        delta = MathUtils.clamp(delta, 0, 1 / 30f);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        //    screenBackground.setSize(width, height);
        table.invalidateHierarchy();
    }

    @Override public void show() {
        Gdx.input.setCatchBackKey(true);
        stage = new Stage(new StretchViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        final Preferences prefs = Gdx.app.getPreferences("levelLocks");
        buttonChirp = Assets.manager.get(Assets.buttonChirp, Sound.class);
        Skin skin = new Skin(Gdx.files.internal("ui/Skin.json"), Assets.manager.get(Assets.uiAtlas, TextureAtlas.class));
        table = new Table(skin);
        table.setFillParent(true);

        //Back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonChirp.play();
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new LevelSelectScreen(game));
                    }
                })));
            }
        });

        table.add(new Label("Settings", skin, "bigWhite")).top().padTop(20).expandX().spaceBottom(50).row();

        table.add(backButton).spaceBottom(30).row();
        stage.addActor(table);

    }

    @Override public void hide() {

    }

    @Override public void pause() {

    }

    @Override public void resume() {

    }

    @Override public void dispose() {
        stage.dispose();

    }
}
