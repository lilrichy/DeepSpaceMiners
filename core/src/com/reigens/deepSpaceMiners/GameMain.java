package com.reigens.deepSpaceMiners;

import com.badlogic.gdx.Game;
import com.reigens.deepSpaceMiners.Screens.LevelSelectScreen;

public class GameMain extends Game {

    public LevelSelectScreen menuScreen;

    @Override
    public void create() {
        menuScreen = new LevelSelectScreen(this);
        setScreen(menuScreen);
    }
}
