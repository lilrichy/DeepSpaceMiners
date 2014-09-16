package com.reigens.deepSpaceMiners.Ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.reigens.deepSpaceMiners.Assets.Assets;
import com.reigens.deepSpaceMiners.Assets.Strings;
import net.dermetfan.utils.libgdx.graphics.AnimatedBox2DSprite;
import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

/**
 * Created by Richard Reigens on 9/13/2014.
 */
public class Ship1B2D {

    public Ship1B2D(World world, FixtureDef shipFixtureDef, FixtureDef wormholeFixtureDef,
                    float x, float y, float width, float height) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.angle = 1.57f;
        bodyDef.angularDamping = 1f;

        //Ship
        PolygonShape shipShape = new PolygonShape();
        shipShape.setAsBox(height, width);

        shipFixtureDef.shape = shipShape;
        shipFixtureDef.density = 1f;
        shipFixtureDef.friction = 2f;
        shipFixtureDef.restitution = .3f;

        Box2DSprite shipSprite = new Box2DSprite(new Sprite(Assets.manager.get(Assets.ship1, Texture.class)));
        shipSprite.setRotation(270);
        Body shipBody = world.createBody(bodyDef);
        Fixture shipFixture = shipBody.createFixture(shipFixtureDef);
        shipFixture.setUserData(shipSprite);

        // Wormhole
        PolygonShape wormholeShape = new PolygonShape();
        wormholeShape.setAsBox(height / 2, width, new Vector2(.5f, 0), 0);

        wormholeFixtureDef.shape = wormholeShape;
        wormholeFixtureDef.isSensor = true;
        wormholeFixtureDef.density = 0f;
        wormholeFixtureDef.friction = 1;
        wormholeFixtureDef.restitution = .4f;

        TextureAtlas frames = Assets.manager.get(Assets.wormholeAtlas, TextureAtlas.class);
        Animation animation = new Animation(1 / 10f, frames.getRegions());
        animation.setPlayMode(Animation.PlayMode.valueOf("LOOP_PINGPONG"));
        animation.setFrameDuration(.03f);
        AnimatedBox2DSprite wormHoleAnimation = new AnimatedBox2DSprite(new AnimatedSprite(animation));

        Fixture wormholeFixture = shipBody.createFixture(wormholeFixtureDef);
        wormholeFixture.setUserData(wormHoleAnimation);

        shipBody.setGravityScale(0.01f);
        shipBody.setUserData(Strings.SHIP);

        //Ship Sensor
        PolygonShape shipSensorShape = new PolygonShape();
        shipSensorShape.setAsBox(height, width);

        shipFixtureDef.shape = shipSensorShape;
        Fixture shipSensorFixture = shipBody.createFixture(shipFixtureDef);
        shipSensorFixture.setUserData(Strings.SHIP);

        //WormHole Sensor
        PolygonShape wHSensorShape = new PolygonShape();
        wHSensorShape.setAsBox(height / 2, width, new Vector2(.5f, 0), 0);

        shipFixtureDef.shape = wHSensorShape;
        Fixture wHSensorFixture = shipBody.createFixture(shipFixtureDef);
        wHSensorFixture.setUserData(Strings.WORMHOLE);
    }
}
