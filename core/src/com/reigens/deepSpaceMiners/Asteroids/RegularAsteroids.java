package com.reigens.deepSpaceMiners.Asteroids;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.reigens.deepSpaceMiners.Assets.Graphics;
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


    public static void spawnasteroid() {

        bounds = new Rectangle();
        bounds.x = MathUtils.random(50, 1920 - 128);
        bounds.y = 1080;
        bounds.width = 96;
        bounds.height = 96;
        Level1Screen.smallRegularAsteroids.add(bounds);
        Level1Screen.lastDropTime = TimeUtils.millis();

        image = current_frame;
        TextureRegion[][] Temp = TextureRegion.split(Graphics.texture_asteroidRegular, 64, 64);
        frames = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                frames[index++] = Temp[i][j];
            }
        }
        animation = new Animation(0.1f, frames);
    }
}


