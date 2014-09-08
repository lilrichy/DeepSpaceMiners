package com.reigens.deepSpaceMiners.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


/**
 * Created by Richard Reigens on 9/6/2014.
 */
public class Graphics {
    // Level Select Screen Graphics
 //   public static Texture texture_background;

    // Level 1 Graphics
    public static Texture texture_background;
    public static Sprite sprite_background;
    public static Texture texture_scorePanel;
    public static Sprite sprite_scorePanelLeft;
    public static Sprite sprite_scorePanelRight;

    // Ship Graphics
    public static Texture texture_wormHole;

    // Meteor Graphics
    public static Texture texture_meteorRegular;

    public static void loadLevelSelectScreen(){
        texture_background = new Texture(Gdx.files.internal("LevelSelectScreen/background.png"));
    }

    public static void loadLevel1() {
        texture_background = new Texture(Gdx.files.internal("Level1/background.png"));
        texture_background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite_background = new Sprite(texture_background);
        sprite_background.flip(false, true);

        texture_scorePanel = new Texture(Gdx.files.internal("Level1/buttonRight.png"));
        texture_scorePanel.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite_scorePanelLeft = new Sprite(texture_scorePanel);
        sprite_scorePanelLeft.flip(true, false);
        sprite_scorePanelRight = new Sprite(texture_scorePanel);
        sprite_scorePanelRight.flip(false, false);
    }

    public  static void loadShip(){
        texture_wormHole = new Texture(Gdx.files.internal("Ships/wormHole.png"));
    }

    public static void loadMeteor(){
        texture_meteorRegular = new Texture(Gdx.files.internal("Meteors/RegularMeteor.png"));
    }
    public static void disposeLevel1() {
        texture_background.dispose();
        texture_scorePanel.dispose();
    }
    public static void disposeShip(){
        texture_wormHole.dispose();
    }
    public static void disposeMeteor(){
        texture_meteorRegular.dispose();
    }
}