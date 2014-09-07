package com.reigens.deepSpaceMiners.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.reigens.deepSpaceMiners.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Testing Game Pre-Dev";

        new LwjglApplication(new GameMain(), config);
	}
}
