package com.reigens.deepSpaceMiners.Screens.Levels.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.reigens.deepSpaceMiners.Assets.Assets;

/**
 * Created by Richard Reigens on 9/14/2014.
 */
public class Hud {
    static Label integrityLabel, collectedLabel, missedLabel, shipFuel;
    static Texture barEmpty, barFull;
    static NinePatch empty, full;
    static Image barFullImage;

    public static void createHud(Stage stage, int missed, int gathered, int hull, int fuel) {
        Skin skin = new Skin(Gdx.files.internal("ui/Skin.json"), Assets.manager.get(Assets.uiAtlas, TextureAtlas.class));

        //Left Panel
        Image leftPanel = new Image(Assets.manager.get(Assets.hudLeftPanel, Texture.class));
        leftPanel.setPosition(0, 880);
        leftPanel.setSize(650, 200);
        stage.addActor(leftPanel);
        collectedLabel = new Label("Collected: " + gathered, skin);
        collectedLabel.setPosition(110, 1000);
        collectedLabel.setFontScale(1.5f, 2.5f);
        stage.addActor(collectedLabel);
        missedLabel = new Label("Missed: " + missed, skin);
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
        integrityLabel = new Label("Hull Integrity: " + hull, skin);
        integrityLabel.setPosition(1300, 1000);
        integrityLabel.setFontScale(1.5f, 2.5f);
        stage.addActor(integrityLabel);

        // Begin Fuel Bar
        barFull = Assets.manager.get(Assets.hudStatusBarFull, Texture.class);
        barEmpty = Assets.manager.get(Assets.hudStatusBarEmpty, Texture.class);
        full = new NinePatch(new TextureRegion(barFull, 24, 24), 8, 8, 8, 8);
        empty = new NinePatch(new TextureRegion(barEmpty, 24, 24), 8, 8, 8, 8);
        barFullImage = new Image(full);
        Image barEmptyImage = new Image(empty);

        barFullImage.setPosition(1460, 920);
        barEmptyImage.setPosition(1460, 920);
        barFullImage.setWidth(280);
        barFullImage.setHeight(50);
        barEmptyImage.setWidth(280);
        barEmptyImage.setHeight(50);

        stage.addActor(barEmptyImage);
        stage.addActor(barFullImage);

        // End Fuel Bar

        shipFuel = new Label("Fuel:", skin);
        shipFuel.setPosition(1300, 930);
        shipFuel.setFontScale(1.5f, 2.5f);
        stage.addActor(shipFuel);
    }

    public static void updateGathered(int gathered) {
        collectedLabel.setText("Collected: " + gathered);
    }

    public static void updateMissed(int missed) {
        missedLabel.setText("Missed: " + missed);
    }

    public static void updateFuel(int fuel) {
        barFullImage.setWidth(fuel);
        if (barFullImage.getWidth() > 280) {
            barFullImage.setWidth(280);
        }
        if (barFullImage.getWidth() <= 8) {
            barFullImage.setWidth(8);
        }
    }

    public static void updateHull(int hull) {
        integrityLabel.setText("Hull Integrity: " + hull);
    }
}
