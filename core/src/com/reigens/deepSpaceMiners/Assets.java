package com.reigens.deepSpaceMiners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


/**
 * Created by Rich on 9/6/2014.
 */
public class Assets {
    public static Texture texture_background;
    public static Sprite sprite_background;
    public static Texture texture_wormHole;
    public static Texture texture_meteorRegular;
    public static Texture texture_scorePanel;
    public static Sprite sprite_scorePanelLeft;
    public static Sprite sprite_scorePanelRight;


    public static void load() {
        texture_background = new Texture(Gdx.files.internal("background.png"));
        texture_background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite_background = new Sprite(texture_background);
        sprite_background.flip(false, true);

        texture_scorePanel = new Texture(Gdx.files.internal("ui/buttonRight.png"));
        texture_scorePanel.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite_scorePanelLeft = new Sprite(texture_scorePanel);
        sprite_scorePanelLeft.flip(true, false);
        sprite_scorePanelRight = new Sprite(texture_scorePanel);
        sprite_scorePanelRight.flip(false, false);

        texture_wormHole = new Texture(Gdx.files.internal("wormHole.png"));

        texture_meteorRegular = new Texture(Gdx.files.internal("Meteors/RegularMeteor.png"));

    }

    public static void dispose() {
        texture_background.dispose();
        texture_wormHole.dispose();
        texture_meteorRegular.dispose();
        texture_scorePanel.dispose();

    }
}