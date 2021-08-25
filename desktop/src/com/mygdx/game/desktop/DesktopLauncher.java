package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import org.lwjgl.Sys;

import java.io.*;

public class DesktopLauncher {

	public static void main (String[] arg) throws IOException {
		GameConfig gameConfig = new GameConfig();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config = gameConfig.loadWindowConfiguration(config, gameConfig.loadConfigFile("config.ini"));
		new LwjglApplication(new MyGdxGame(), config);
	}
}