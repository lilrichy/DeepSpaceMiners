package com.reigens.deepSpaceMiners.Uitilitys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Richard Reigens on 9/12/2014.
 */
public class B2DHelper {

    public static BodyDef CreateBody(BodyDef.BodyType type, int positionX, int positionY) {
        BodyDef bodyDef = new BodyDef();
        Vector2 vector2 = new Vector2(positionX, positionY);
        bodyDef.type = type;
        bodyDef.position.set(vector2);
        return bodyDef;
    }

    public static FixtureDef CreateFixture(Shape shape, Float density, Float friction, Float restitution) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        return fixtureDef;
    }

    public static void DrawBody(World world, BodyDef bodyDef, FixtureDef fixtureDef) {
        world.createBody(bodyDef).createFixture(fixtureDef);
    }
}
