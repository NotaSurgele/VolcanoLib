package com.mygdx.game.desktop;

import static java.lang.System.out;

public class OSName {

    public OSName() {}

    enum Name {
        WINDOWS,
        MAC,
        LINUX
    }

    static Name osName = Name.WINDOWS;

    public String getCurrentRunningOSName()
    {
        String currentRunningOs = System.getProperty("os.name");

        if (currentRunningOs.contains("Win")) {
            osName = Name.WINDOWS;
            return "\r\n";
        } else if (currentRunningOs.contains("Lin")) {
            osName = Name.LINUX;
            return "\n";
        } else if (currentRunningOs.contains("Mac")) {
            osName = Name.MAC;
            return "\n";
        }
        return null;
    }
}
