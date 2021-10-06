package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import org.lwjgl.Sys;

import java.io.*;

public class DesktopLauncher {

	public static void main (String[] arg) throws IOException {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		GameConfig.loadWindowConfiguration(config, GameConfig.loadConfigFile("config.ini"));
		config.backgroundFPS = 100000;
		config.foregroundFPS = 100000;
		config.vSyncEnabled = false;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
