package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.*;
import java.util.HashMap;

public class GameConfig {

    final static String configPath = "../../.config/";

    public GameConfig() {}

    public static String readDefaultConfigData() throws IOException {
        String data = new String();
        FileReader r = null;

        try {
            r = new FileReader(configPath + "default.ini");
        } catch (FileNotFoundException e) {
            System.err.println("An error has occured !\nCouldn't read: default.ini");
            e.printStackTrace();
        }
        for (int ch; (ch = r.read()) != -1; data += (char)ch);
        r.close();
        return data;
    }

    public static String readConfigFile() throws IOException {
        String data = "";
        FileReader r = null;

        try {
            r = new FileReader(configPath + "config.ini");
            for (int ch = 0; (ch = r.read()) != -1; data += (char)ch);

        } catch (FileNotFoundException e) {
            System.err.println("An error has occured\nCouldn't read: config.ini");
            e.printStackTrace();
        }
        r.close();
        return data;
    }

    public static String loadConfigFile(String file) throws IOException {
        File config = new File(configPath + file);

        System.out.println("\033[95m" + "Creating " + file + " !" + "\033[0m");
        try {
            if (config.createNewFile()) {
                String data = readDefaultConfigData();
                FileWriter w = new FileWriter(configPath + file);

                System.out.println("A new " + file + " has been created !");
                for (int each = 0; each != data.length(); w.write(data.charAt(each++)));
                w.close();
            } else {
                System.out.println("\033[93m" + file + " already exist !" + "\033[0m");
            }
        } catch (IOException e) {
            System.err.println("An error occured");
            e.printStackTrace();
        }
        System.out.println("\033[93m" + "Loading " + file + " !\033[0m");
        return readConfigFile();
    }



    public static LwjglApplicationConfiguration loadWindowConfiguration(LwjglApplicationConfiguration config, String data)
    {
        OSName os = new OSName();
        String regex = os.getCurrentRunningOSName();
        String[] configArray = data.split(regex);

        HashMap<String, String> windowData = ConfigParser.getWindowData(configArray);

        if (windowData != null) {
            config.title = windowData.get("Window_name");
            config.height = Integer.parseInt(windowData.get("Window_y"));
            config.width = Integer.parseInt(windowData.get("Window_x"));
            config.fullscreen = Boolean.parseBoolean(windowData.get("Fullscreen"));
            config.pauseWhenBackground = Boolean.parseBoolean(windowData.get("PauseWhenBackground"));
            config.pauseWhenMinimized = Boolean.parseBoolean(windowData.get("PauseWhenMinimized"));
        }
        return config;
    }
}
