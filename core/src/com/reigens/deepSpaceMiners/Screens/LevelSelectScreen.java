package com.reigens.deepSpaceMiners.Screens;

import com.badlogic.gdx.Game;
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
import com.reigens.deepSpaceMiners.Assets.Strings;
import com.reigens.deepSpaceMiners.GameMain;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


/**
 * Created by Richard Reigens on 9/7/2014.
 */
public class LevelSelectScreen implements Screen {
    GameMain game;

    private TextureAtlas uiAtlas;
    private Stage stage;
    private Table table;
    private Skin skin;
    private Image screenBackground;
    private String levelText = Strings.level1;


    public LevelSelectScreen(GameMain game) {
        this.game = game;

    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Graphics.loadLevelSelectScreen();

        uiAtlas = new TextureAtlas(Gdx.files.internal("ui/ui.pack"));
        skin = new Skin(Gdx.files.internal("ui/Skin.json"), uiAtlas);
        screenBackground = new Image(Graphics.texture_background);
        stage.addActor(screenBackground);

        table = new Table(skin);
        //Table debug
        table.debug();
        table.setFillParent(true);

        final Label missionBriefing = new Label(levelText,skin, "field");
        missionBriefing.setWrap(true);
        final List levelList = new List(skin);
        levelList.setItems(new String[]{"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"});
        ScrollPane scrollPane = new ScrollPane(levelList, skin);
        ScrollPane textPane = new ScrollPane(missionBriefing, skin);



        levelList.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (levelList.getSelectedIndex()){
                    case 0:
                        missionBriefing.setText(Strings.level1);
                        break;
                    case 1:
                        missionBriefing.setText(Strings.level2);
                        break;
                    case 2:
                        missionBriefing.setText(Strings.level3);
                        break;
                    case 3:
                        missionBriefing.setText(Strings.level4);
                        break;
                    case 4:
                        missionBriefing.setText(Strings.level5);
                        break;

                }


            }
        });


        //Play button
        TextButton play = new TextButton("PLAY", skin);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
                    @Override
                    public void run() {
                        switch (levelList.getSelectedIndex())
                        {
                            case 0:
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1Screen());
                                break;
                            case 1:
                               // ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen());
                                break;
                            case 2:
                               // ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen());
                                break;
                            case 3:
                                // ((Game) Gdx.app.getApplicationListener()).setScreen(new Level4Screen());
                                break;
                            case 4:
                                // ((Game) Gdx.app.getApplicationListener()).setScreen(new Level5Screen());
                                break;


                        }
                    }
                })));
            }
        });
        play.pad(10);

//Set up Table

        table.add(new Label("SELECT LEVEL", skin, "bigWhite")).padTop(20).colspan(3).expandX().spaceBottom(50).row();
        table.add(scrollPane).width(200).padLeft(25).left();
        table.add(textPane).right().width(350);
        table.add().row();
        table.add(play).colspan(3).expandX().padBottom(10).padTop(50).padRight(50).bottom().right();

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

