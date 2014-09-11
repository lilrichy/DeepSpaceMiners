package com.reigens.deepSpaceMiners.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.reigens.deepSpaceMiners.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Deep Space Miners Pre-Dev";
        config.width=960; // sets window width
        config.height=540;  // sets window height

        new LwjglApplication(new GameMain(), config);
	}
}
