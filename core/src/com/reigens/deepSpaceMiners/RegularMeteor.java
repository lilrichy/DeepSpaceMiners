package com.reigens.deepSpaceMiners;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Rich on 9/6/2014.
 */
public class RegularMeteor {

    public static Rectangle bounds;
    public static TextureRegion image;
    public static TextureRegion[] frames;
    public static TextureRegion current_frame;
    public static Animation animation;


    public static void spawnMeteor() {


        bounds = new Rectangle();
        bounds.x = MathUtils.random(50, 1920 - 128);
        bounds.y = 1080;
        bounds.width = 128;
        bounds.height = 128;
        GameScreen.smallRegMeteors.add(bounds);
        GameScreen.lastDropTime = TimeUtils.nanoTime();


        image = current_frame;
        TextureRegion[][] Temp = TextureRegion.split(Assets.texture_meteorRegular, 64, 64);
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


