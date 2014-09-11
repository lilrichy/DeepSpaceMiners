package com.reigens.deepSpaceMiners.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Richard Reigens on 9/10/2014.
 */
public class Assets {

    public static final AssetManager manager = new AssetManager();

    //Font Strings
    public static final String black14 = "font/black14.fnt";
    public static final String black16 = "font/black16.fnt";
    public static final String black24 = "font/black24.fnt";
    public static final String black32 = "font/black32.fnt";
    public static final String black64 = "font/black64.fnt";
    public static final String red8 = "font/red8.fnt";
    public static final String red14 = "font/red14.fnt";
    public static final String red32 = "font/red32.fnt";
    public static final String white14 = "font/white14.fnt";
    public static final String white16 = "font/white16.fnt";
    public static final String white32 = "font/white32.fnt";
    public static final String white64 = "font/white64.fnt";

    //Skin Strings
    public static final String skin = "ui/Skin.json";

    //Texture Strings
    public static final String smallRegAsteroid = "Asteroids/RegularAsteroid.png";
    public static final String ship1 = "Ships/ship1.png";
    public static final String wormhole = "Ships/wormHole.png";
    public static final String levelSelectScreenBackground = "LevelSelectScreen/background.png";
    public static final String level1Background = "Level1/background.png";
    public static final String hudLeftPanel = "Level1/hudLeftPanel.png";
    public static final String hudRightPanel = "Level1/hudRightPanel.png";
    public static final String hudTopBar = "Level1/topBar.png";
    public static final String uiAtlas = "ui/ui.pack";

    public static void load() {
        //    Texture.setAssetManager(manager);


        // Load Textures
        //Asteroids
        manager.load(smallRegAsteroid, Texture.class);
        // Ship Textures
        manager.load(ship1, Texture.class);
        manager.load(wormhole, Texture.class);
        //Level SelectScreen Textures
        manager.load(levelSelectScreenBackground, Texture.class);
        //Level 1 Textures
        manager.load(level1Background, Texture.class);
        manager.load(hudLeftPanel, Texture.class);
        manager.load(hudRightPanel, Texture.class);
        manager.load(hudTopBar, Texture.class);

        // Load Fonts
        manager.load(black14, BitmapFont.class);
        manager.load(black16, BitmapFont.class);
        manager.load(black24, BitmapFont.class);
        manager.load(black32, BitmapFont.class);
        manager.load(black64, BitmapFont.class);
        manager.load(red8, BitmapFont.class);
        manager.load(red14, BitmapFont.class);
        manager.load(red32, BitmapFont.class);
        manager.load(white14, BitmapFont.class);
        manager.load(white16, BitmapFont.class);
        manager.load(white32, BitmapFont.class);
        manager.load(white64, BitmapFont.class);

        // Load Skin
        // manager.load(skin, Skin.class);

        // Load Sounds

        // Load Atlas
        manager.load(uiAtlas, TextureAtlas.class);

    }

    public static void dispose() {
        manager.dispose();
    }
}
