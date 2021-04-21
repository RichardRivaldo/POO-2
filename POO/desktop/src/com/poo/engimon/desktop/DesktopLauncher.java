package com.poo.engimon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.poo.engimon.POO;

public class DesktopLauncher {
	public static void main (String[] arg) {
		// Init Config
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Set title
		config.title = "POO Engimon";
		config.useGL30 = true;
		// Set screen size
		config.width = 800;
		config.height = 800;
		// Set screen open window position
		config.x = 325;
		config.y = 0;
		new LwjglApplication(new POO(), config);
	}
}
