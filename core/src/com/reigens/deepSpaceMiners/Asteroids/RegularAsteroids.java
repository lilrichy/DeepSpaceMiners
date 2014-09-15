package com.reigens.deepSpaceMiners.Asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.GameMain;
import com.reigens.deepSpaceMiners.Screens.Levels.Level1Screen;

/**
 * Created by Richard Reigens on 9/6/2014.
 */
public class RegularAsteroids {
    public static Rectangle bounds;
    public static TextureRegion image;
    public static TextureRegion[] frames;
    public static TextureRegion current_frame;
    public static Animation animation;
    static GameMain game;

    public RegularAsteroids(GameMain game) {
        this.game = game;
    }

    public static void spawnAsteroid() {
        bounds = new Rectangle();
        bounds.x = MathUtils.random(50, Gdx.graphics.getWidth() - 128);
        bounds.y = Gdx.graphics.getHeight();
        bounds.width = 96;
        bounds.height = 96;
        Level1Screen.smallRegularAsteroids.add(bounds);
        Level1Screen.lastDropTime = TimeUtils.millis();

        image = current_frame;
        TextureRegion[][] Temp = TextureRegion.split(Assets.manager.get(Assets.smallRegAsteroid, Texture.class), 64, 64);
        frames = new TextureRegion[ 16 ];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                frames[ index++ ] = Temp[ i ][ j ];
            }
        }
        animation = new Animation(0.1f, frames);
    }
}