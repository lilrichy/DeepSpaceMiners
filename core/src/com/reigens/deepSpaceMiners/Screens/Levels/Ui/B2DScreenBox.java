package com.reigens.deepSpaceMiners.Screens.Levels.Ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.reigens.deepSpaceMiners.Uitilitys.B2DHelper;

/**
 * Created by Richard Reigens on 9/15/2014.
 */
public class B2DScreenBox {

    public B2DScreenBox() {

    }

    public static void setupScreenBox(World world, int screenWidth, int screenHeight) {
        BodyDef bodyDef;
        FixtureDef fixtureDef = new FixtureDef();

        // top
        bodyDef = B2DHelper.CreateBody(BodyDef.BodyType.StaticBody, 0, 0);
        EdgeShape topShape = new EdgeShape();
        topShape.set(new Vector2(- screenWidth / 100 * .5f, screenHeight / 100 * .5f),
                new Vector2(screenWidth / 100 * .5f, screenHeight / 100 * .5f));
        fixtureDef.filter.groupIndex = 5;
        fixtureDef = B2DHelper.CreateFixture(topShape, 0f, 0f, .05f);
        Body top = world.createBody(bodyDef);
        top.createFixture(fixtureDef);
        topShape.dispose();

        // Left
        bodyDef = B2DHelper.CreateBody(BodyDef.BodyType.StaticBody, 0, 0);
        EdgeShape leftShape = new EdgeShape();
        leftShape.set(new Vector2(- screenWidth / 100 * .5f, - screenHeight / 100 * .5f),
                new Vector2(- screenWidth / 100 * .5f, screenHeight / 100 * .5f));
        fixtureDef.filter.groupIndex = 5;
        fixtureDef = B2DHelper.CreateFixture(leftShape, 0f, 0f, .05f);
        Body left = world.createBody(bodyDef);
        left.createFixture(fixtureDef);
        leftShape.dispose();

        // Right
        bodyDef = B2DHelper.CreateBody(BodyDef.BodyType.StaticBody, 0, 0);
        EdgeShape rightShape = new EdgeShape();
        rightShape.set(new Vector2(screenWidth / 100 * .5f, - screenHeight / 100 * .5f),
                new Vector2(screenWidth / 100 * .5f, screenHeight / 100 * .5f));
        fixtureDef.filter.groupIndex = 5;
        fixtureDef = B2DHelper.CreateFixture(rightShape, 0f, 0f, .05f);
        Body right = world.createBody(bodyDef);
        right.createFixture(fixtureDef);
        rightShape.dispose();

        // Ground
        bodyDef = B2DHelper.CreateBody(BodyDef.BodyType.StaticBody, 0, 0);
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(- screenWidth / 100 * .5f, - screenHeight / 100 * .5f),
                new Vector2(screenWidth / 100 * .5f, - screenHeight / 100 * .5f));
        fixtureDef.filter.groupIndex = 5;
        fixtureDef = B2DHelper.CreateFixture(groundShape, 0f, .5f, 0f);
        Body ground = world.createBody(bodyDef);
        ground.createFixture(fixtureDef);
        groundShape.dispose();
    }
}
