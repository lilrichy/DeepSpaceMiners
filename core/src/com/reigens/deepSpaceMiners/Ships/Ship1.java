package com.reigens.deepSpaceMiners.Ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Richard Reigens on 9/8/2014.
 */
public class Ship1 {
    //public static Rectangle bounds;
    public static TextureRegion up;
    public static TextureRegion down;
    public static TextureRegion left;
    public static TextureRegion right;
    public static TextureRegion current_frame;
    public static TextureRegion[] ship_frames;
    public static Animation ship_Animation;
    public static TextureAtlas atlas;
    public static Sprite image;


    public Ship1(){
      //  bounds = new Rectangle(960, 540, 180, 120);


        atlas = new TextureAtlas(Gdx.files.internal("Ships/ship1.pack"));
        up = atlas.findRegion("ship1");
        down = atlas.findRegion("ship3");
        right = atlas.findRegion("ship2");
        left = atlas.findRegion("ship4");

        image = new Sprite(up);

    }
}
