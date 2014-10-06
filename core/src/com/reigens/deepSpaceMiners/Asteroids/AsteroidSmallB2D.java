package com.reigens.deepSpaceMiners.Asteroids;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.Assets.Strings;
import com.reigens.deepSpaceMiners.Screens.Levels.Level1;
import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;
import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

/**
 * Created by Richard Reigens on 9/15/2014.
 */
public class AsteroidSmallB2D {


    public static void spawnAsteroid(World world, int screenWidth, int screenHeight, float gravity) {


        float rand = MathUtils.random(- screenWidth / 100 * .5f, screenWidth / 100 * .5f);

        float width = .9f, height = .9f;
        float y = screenHeight / 100 * .5f;

        BodyDef bodyDef = new BodyDef();
        FixtureDef asteroidFixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(rand, y);
        bodyDef.angle = 1.57f;
        bodyDef.angularDamping = 1.5f;

        PolygonShape asteroidShape = new PolygonShape();
        asteroidShape.setAsBox(height / 2, width / 2, new Vector2(.5f, 0), 0);

        asteroidFixtureDef.shape = asteroidShape;
        asteroidFixtureDef.density = 5f;
        asteroidFixtureDef.restitution = 0f;
        asteroidFixtureDef.friction = 2f;

        TextureAtlas frames = Assets.manager.get(Assets.smallRegAsteroidAtlas, TextureAtlas.class);
        Animation animation = new Animation(1 / 16f, frames.getRegions());
        animation.setPlayMode(Animation.PlayMode.valueOf("LOOP"));
        animation.setFrameDuration(.09f);
        AnimatedBox2DSprite asteroidAnimation = new AnimatedBox2DSprite(new AnimatedSprite(animation));

        Body asteroidBody = world.createBody(bodyDef);
        Fixture asteroidFixture = asteroidBody.createFixture(asteroidFixtureDef);
        asteroidFixture.setUserData(asteroidAnimation);

        asteroidBody.setGravityScale(gravity);

        //Asteroid Sensor
        PolygonShape asteroidSensorShape = new PolygonShape();
        asteroidSensorShape.setAsBox(height / 2 + .01f, width / 2 + .01f, new Vector2(.5f, 0), 0);

        asteroidFixtureDef.shape = asteroidSensorShape;
        Fixture asteroidSensorFixture = asteroidBody.createFixture(asteroidFixtureDef);
        asteroidSensorFixture.setUserData(Strings.ASTEROID);

    }
}
