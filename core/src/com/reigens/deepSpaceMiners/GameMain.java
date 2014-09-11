package com.reigens.deepSpaceMiners;

import com.badlogic.gdx.Game;
import com.reigens.deepSpaceMiners.Screens.Splash;

public class GameMain extends Game {

    public Splash splash;

    @Override
    public void create() {
        splash = new Splash(this);
        setScreen(splash);
    }
}