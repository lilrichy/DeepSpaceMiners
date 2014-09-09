package com.reigens.deepSpaceMiners;

import com.badlogic.gdx.Game;
import com.reigens.deepSpaceMiners.Screens.*;

public class GameMain extends Game {

    public LevelSelectScreen levelSelectScreen;
    public Level1Screen level1Screen;
    public PauseScreen pauseScreen;
    public GameOverScreen gameOverScreen;
    public WonScreen wonScreen;

    @Override
    public void create() {
        levelSelectScreen = new LevelSelectScreen(this);
        level1Screen = new Level1Screen(this);
        pauseScreen = new PauseScreen(this);
        gameOverScreen = new GameOverScreen(this);
        wonScreen = new WonScreen(this);
        setScreen(levelSelectScreen);
    }
}
