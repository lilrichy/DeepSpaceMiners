package com.reigens.deepSpaceMiners.Screens.Levels.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.reigens.deepSpaceMiners.Assets.Assets;

/**
 * Created by Richard Reigens on 9/14/2014.
 */
public class Hud {

    public static void createHud(Stage stage, int asteroidsGathered, int asteroidsMissed, int shipHull, int fallSpeed) {
        Skin skin = new Skin(Gdx.files.internal("ui/Skin.json"), Assets.manager.get(Assets.uiAtlas, TextureAtlas.class));

        //Left Panel
        Image leftPanel = new Image(Assets.manager.get(Assets.hudLeftPanel, Texture.class));
        leftPanel.setPosition(0, 880);
        leftPanel.setSize(650, 200);
        stage.addActor(leftPanel);
        Label collectedLabel = new Label("Collected: " + asteroidsGathered, skin);
        collectedLabel.setPosition(110, 1000);
        collectedLabel.setFontScale(1.5f, 2.5f);
        stage.addActor(collectedLabel);
        Label missedLabel = new Label("Missed: " + asteroidsMissed, skin);
        missedLabel.setPosition(230, 930);
        missedLabel.setFontScale(1.5f, 2.5f);
        stage.addActor(missedLabel);

        //TopBar
        Image topBar = new Image(Assets.manager.get(Assets.hudTopBar, Texture.class));
        topBar.setPosition(650, 880);
        topBar.setSize(620, 200);
        stage.addActor(topBar);

        //Right panel
        Image rightPanel = new Image(Assets.manager.get(Assets.hudRightPanel, Texture.class));
        rightPanel.setPosition(1270, 880);
        rightPanel.setSize(650, 200);
        stage.addActor(rightPanel);
        Label integrityLabel = new Label("Hull Integrity: " + shipHull, skin);
        integrityLabel.setPosition(1300, 1000);
        integrityLabel.setFontScale(1.5f, 2.5f);
        stage.addActor(integrityLabel);
        Label speedLabel = new Label("Speed: " + fallSpeed, skin);
        speedLabel.setPosition(1300, 930);
        speedLabel.setFontScale(1.5f, 2.5f);
        stage.addActor(speedLabel);
    }

}
