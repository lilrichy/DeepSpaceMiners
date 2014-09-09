package com.reigens.deepSpaceMiners.Ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Richard Reigens on 9/8/2014.
 */
public class Ship1 {
   // public static TextureAtlas atlas;
    public static Texture shipTexture;

    public static Sprite image;


    public Ship1(){

       // atlas = new TextureAtlas(Gdx.files.internal("Ships/ship1.pack"));
       // image = new Sprite(atlas.findRegion("ship1"));
        shipTexture = new Texture(Gdx.files.internal("Ships/ship1Single.png"));
        image = new Sprite(shipTexture);
        image.setOriginCenter();

    }
}
