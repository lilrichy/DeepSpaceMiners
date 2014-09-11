package com.reigens.deepSpaceMiners.Ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.reigens.deepSpaceMiners.Assets.Assets;

/**
 * Created by Rich on 9/6/2014.
 */
public class WormHole {
    public static Rectangle bounds;
    public static TextureRegion image;
    public static TextureRegion[] wormHole_frames;
    public static TextureRegion current_frame;
    public static Animation wormHole_animation;

    public WormHole() {
        bounds = new Rectangle(960, 500, 128, 128);
        image = current_frame;
        TextureRegion[][] Temp = TextureRegion.split(Assets.manager.get(Assets.wormhole, Texture.class), 32, 32);
        wormHole_frames = new TextureRegion[20];
        int index = 0;
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                wormHole_frames[index++] = Temp[i][j];
            }
        }
        wormHole_animation = new Animation(0.05f, wormHole_frames);
    }
}



