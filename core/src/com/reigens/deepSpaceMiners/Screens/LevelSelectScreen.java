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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.Assets.Strings;
import com.reigens.deepSpaceMiners.GameMain;
import com.reigens.deepSpaceMiners.Screens.Levels.Level1;
import com.reigens.deepSpaceMiners.Screens.Levels.Level2;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Richard Reigens on 9/7/2014.
 */
public class LevelSelectScreen implements Screen {
    GameMain game;

    private Stage stage;
    private Table table;
    private String levelText = Strings.level1;
    private String levelGoal = Strings.levelGoal1;
    Sound buttonChirp;

    public LevelSelectScreen(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        final Preferences prefs = Gdx.app.getPreferences("levelLocks");
        buttonChirp = Assets.manager.get(Assets.buttonChirp, Sound.class);

        Skin skin = new Skin(Gdx.files.internal("ui/Skin.json"), Assets.manager.get(Assets.uiAtlas, TextureAtlas.class));

        table = new Table(skin);
        table.setFillParent(true);

        final Label missionBriefing = new Label(levelText, skin, "field");
        missionBriefing.setWrap(true);
        final Label missionGoal = new Label(levelGoal, skin, "fieldSmall");
        missionGoal.setWrap(true);
        final List<String> levelList = new List<String>(skin);
        levelList.setItems("Level 1", "Level 2", "Level 3", "Level 4", "Level 5");
        ScrollPane levelPane = new ScrollPane(levelList, skin);
        ScrollPane missionBriefingPane = new ScrollPane(missionBriefing, skin);
        missionBriefingPane.setFadeScrollBars(false);
        ScrollPane goalPane = new ScrollPane(missionGoal, skin);

        levelList.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (levelList.getSelectedIndex()) {
                    case 0:
                        missionBriefing.setText(Strings.level1);
                        missionGoal.setText(Strings.levelGoal1);
                        break;
                    case 1:
                        if (prefs.getBoolean("Level 2") != true) {
                            missionBriefing.setText(Strings.locked);
                            missionGoal.setText(Strings.locked);
                        }
                        else {
                            missionBriefing.setText(Strings.level2);
                            missionGoal.setText(Strings.levelGoal2);
                        }
                        break;
                    case 2:
                        if (prefs.getBoolean("Level 3") != true) {
                            missionBriefing.setText(Strings.locked);
                            missionGoal.setText(Strings.locked);
                        }
                        else {
                            missionBriefing.setText(Strings.level3);
                            missionGoal.setText(Strings.levelGoal3);
                        }
                        break;
                    case 3:
                        if (prefs.getBoolean("Level 4") != true) {
                            missionBriefing.setText(Strings.locked);
                            missionGoal.setText(Strings.locked);
                        }
                        else {
                            missionBriefing.setText(Strings.level4);
                            missionGoal.setText(Strings.levelGoal4);
                        }
                        break;
                    case 4:
                        if (prefs.getBoolean("Level 5") != true) {
                            missionBriefing.setText(Strings.locked);
                            missionGoal.setText(Strings.locked);
                        }
                        else {
                            missionBriefing.setText(Strings.level5);
                            missionGoal.setText(Strings.levelGoal5);
                        }
                        break;
                }
            }
        });

        //Play button
        TextButton play = new TextButton("PLAY", skin);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonChirp.play();
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {

                        switch (levelList.getSelectedIndex()) {
                            case 0:
                                game.setScreen(new Level1(game));
                                break;
                            case 1:
                                if (prefs.getBoolean("Level 2")) {
                                    game.setScreen(new Level2(game));
                                }
                                else {
                                    game.setScreen(new LevelSelectScreen(game));
                                }
                                break;
                            case 2:
                                if (prefs.getBoolean("Level 3")) {
                                    // game.setScreen(new Level3(game));
                                }
                                else {
                                    game.setScreen(new LevelSelectScreen(game));
                                }
                                break;
                            case 3:
                                if (prefs.getBoolean("Level 4")) {
                                    // game.setScreen(new Level4(game));
                                }
                                else {
                                    game.setScreen(new LevelSelectScreen(game));
                                }
                                break;
                            case 4:
                                if (prefs.getBoolean("Level 5")) {
                                    // game.setScreen(new Level5(game));
                                }
                                else {
                                    game.setScreen(new LevelSelectScreen(game));
                                }
                                break;
                        }
                    }
                })));
            }
        });

        //Settings Button
        ImageButton settingsGear = new ImageButton(skin.getDrawable("gear"));
        settingsGear.setSize(48, 48);
        settingsGear.setPosition(Gdx.graphics.getWidth() - settingsGear.getWidth(), Gdx.graphics.getHeight() - settingsGear.getHeight());
        stage.addActor(settingsGear);
        settingsGear.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonChirp.play();
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new SettingsScreen(game));

                    }
                })));
            }
        });

        //Set up Table
        table.add(new Label("SELECT LEVEL", skin, "bigWhite")).colspan(3)
                .expandX().spaceBottom(50).row();

        Table nest1 = new Table(skin);
        nest1.add();
        nest1.add(new Label("Mission Briefing: ", skin, "smallWhite")).left().bottom().row();
        nest1.add(levelPane).width(200).spaceLeft(100).pad(10).maxHeight(Gdx.graphics.getHeight() * .65f);
        nest1.add(missionBriefingPane).expandX().fillX().width(Gdx.graphics.getWidth() - 300)
                .height(Gdx.graphics.getHeight() / 2).maxHeight(Gdx.graphics.getHeight() * .65f).row();

        table.add(nest1).colspan(3).row();
        table.add().expandX().spaceBottom(10).row();

        Table nest2 = new Table(skin);
        nest2.add(new Label("Mission Goals: ", skin, "smallWhite")).left().bottom().row();
        nest2.add(goalPane).width(Gdx.graphics.getWidth() - 300)
                .height(100).spaceRight(10);
        nest2.add(play).height(100);

        table.add(nest2).colspan(3).row();

        stage.addActor(table);

        stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f))); // coming in from top animation
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
        stage.getViewport().apply();
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
        stage.dispose();
    }
}

