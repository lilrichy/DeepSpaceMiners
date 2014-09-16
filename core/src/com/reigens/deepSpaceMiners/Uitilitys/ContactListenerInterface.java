package com.reigens.deepSpaceMiners.Uitilitys;

import com.badlogic.gdx.physics.box2d.*;
import com.reigens.deepSpaceMiners.Assets.Strings;
import com.reigens.deepSpaceMiners.Screens.Levels.Ui.Hud;

/**
 * Created by Richard Reigens on 9/16/2014.
 */
public class ContactListenerInterface implements ContactListener {

    public static int HULLINTEGRITY;
    public static int ASTEROIDSMISSED;
    public static int ASTEROIDSGATHERED;

    public static void destroyAsteroid(Fixture F, Boolean missed) {
        F.getBody().setUserData(Strings.DELETE);
        if (missed) {
            ASTEROIDSMISSED++;
            Hud.updateMissed(ASTEROIDSMISSED);
        }
    }

    public static void shipHit() {
        HULLINTEGRITY -= 10;
        Hud.updateHull(HULLINTEGRITY);
    }

    public static void wormHoleHit(Fixture F) {
        ASTEROIDSGATHERED++;
        destroyAsteroid(F, false);
        Hud.updateGathered(ASTEROIDSGATHERED);
    }

    public static void resetValues(int hullMax) {
        ASTEROIDSGATHERED = 0;
        ASTEROIDSMISSED = 0;
        HULLINTEGRITY = hullMax;
    }

    @Override public void beginContact(Contact contact) {

        Fixture FA = contact.getFixtureA();
        Fixture FB = contact.getFixtureB();

        if (FA.getUserData() != null && FA.getUserData() == Strings.ASTEROID) {
            if (FB.getUserData() != null && FB.getUserData() == Strings.GROUND) {
                destroyAsteroid(FA, true);
            }
            if (FB.getUserData() != null && FB.getUserData() == Strings.SHIP) {
                shipHit();
            }
            if (FB.getUserData() != null && FB.getUserData() == Strings.WORMHOLE) {
                wormHoleHit(FA);
            }
        }
        if (FB.getUserData() != null && FB.getUserData() == Strings.ASTEROID) {
            if (FA.getUserData() != null && FA.getUserData() == Strings.GROUND) {
                destroyAsteroid(FB, true);
            }
            if (FA.getUserData() != null && FA.getUserData().equals(Strings.SHIP)) {
                shipHit();
            }
            if (FA.getUserData() != null && FA.getUserData().equals(Strings.WORMHOLE)) {
                wormHoleHit(FB);
            }
        }
    }

    @Override public void endContact(Contact contact) {
        //  System.out.println("Hull: " + HULLINTEGRITY + " Missed: " + ASTEROIDSMISSED + " Gathered: " + ASTEROIDSGATHERED);

    }

    @Override public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
