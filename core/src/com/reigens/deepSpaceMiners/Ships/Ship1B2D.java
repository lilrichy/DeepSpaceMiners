package com.reigens.deepSpaceMiners.Ships;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Richard Reigens on 9/13/2014.
 */
public class Ship1B2D {
    private Body shipBody;

    public Ship1B2D(World world, FixtureDef shipFixtureDef, FixtureDef wormholeFixtureDef,
                    float x, float y, float width, float height) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.angle = 1.57f;
        bodyDef.angularDamping = 1.5f;

        PolygonShape shipShape = new PolygonShape();
        shipShape.setAsBox(height, width);

        shipFixtureDef.shape = shipShape;
        shipFixtureDef.filter.groupIndex = 1;

        shipBody = world.createBody(bodyDef);
        shipBody.createFixture(shipFixtureDef);

        CircleShape wormholeShape = new CircleShape();
        wormholeShape.setRadius(width / 2);
        wormholeShape.setPosition(new Vector2(x+height *2f, y-width*3.3f));

        wormholeFixtureDef.shape = wormholeShape;
        wormholeFixtureDef.filter.groupIndex = 1;
        shipBody.createFixture(wormholeFixtureDef);
        shipBody.setGravityScale(0.01f);
    }
}
