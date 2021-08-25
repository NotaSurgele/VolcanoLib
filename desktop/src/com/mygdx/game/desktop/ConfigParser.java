package com.mygdx.game.desktop;

import org.lwjgl.Sys;

import java.util.HashMap;
import java.util.Map;

public class ConfigParser {

    public ConfigParser() {}

    public HashMap<String, String> getKeyData(String[] data, String toGet)
    {
        for (int each = 0; each != data.length; each++) {
            if (data[each].equals(toGet)) {
                HashMap<String, String> keyData = new HashMap<String, String>();

                for (int i = each += 2; !data[i].equals("],"); i++) {
                    String[] valArray = data[i].split("=");
                    valArray[0] = valArray[0].replace(" ", "");
                    valArray[1] = valArray[1].replace(" ", "");
                    keyData.put(valArray[0], valArray[1]);
                }
                return keyData;
            }
        }
        return null;
    }

    public HashMap<String, String> getWindowData(String[] data)
    {
        HashMap<String, String> windowData = getKeyData(data, "[Game Window]");

        if (windowData == null) {
            System.err.println("Error cannot get the current asked key !");
        }
        return windowData;
    }

    public HashMap<String, String> getSysData(String[] data)
    {
        HashMap<String, String> sysData = getKeyData(data, "[System Info]");
        return sysData;
    }
}
