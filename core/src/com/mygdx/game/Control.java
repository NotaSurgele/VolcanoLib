package com.mygdx.game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

public class Control {

    String data;

    public static int FORWARD;
    public static int BACKWARD;
    public static int LEFT;
    public static int RIGHT;
    public static int RUN;
    public static int ATTACK1;
    public static int ATTACK2;

    public Control() throws IOException {
        this.data = this.readFile(".config/input.ini");
        this.retrieveInput();
    }

    public String getRegex()
    {
        String currentRunningOs = System.getProperty("os.name");

        if (currentRunningOs.contains("Win")) {
            return "\r\n";
        } else if (currentRunningOs.contains("Lin")) {
            return "\n";
        } else if (currentRunningOs.contains("Mac")) {
            return "\n";
        }
        return "\n";
    }

    private void retrieveInput() {
        String regex = this.getRegex();
        String[] arrayData = this.data.split(regex);

        for (int i = 2; !arrayData[i].contains(","); i++) {
            String[] input = arrayData[i].split("=");

            if (arrayData[i].contains("FORWARD")) FORWARD = Integer.parseInt(input[1]);
            if (arrayData[i].contains("BACKWARD")) BACKWARD = Integer.parseInt(input[1]);
            if (arrayData[i].contains("LEFT")) LEFT = Integer.parseInt(input[1]);
            if (arrayData[i].contains("RIGHT")) RIGHT = Integer.parseInt(input[1]);
            if (arrayData[i].contains("RUN")) RUN = Integer.parseInt(input[1]);
            if (arrayData[i].contains("ATTACK1")) ATTACK1 = Integer.parseInt(input[1]);
            if (arrayData[i].contains("ATTACK2")) ATTACK2 = Integer.parseInt(input[1]);
        }
    }

    public String readFile(String fileName) throws IOException {
        String data = "";
        FileReader r = null;

        System.out.println("\033[93m" + "Loading " + fileName + " !" + "\033[0m");
        try {
            r = new FileReader(fileName);
            for (int ch = 0; (ch = r.read()) != -1; data += (char)ch);
            System.out.println("\033[93m" + "Successful !" + "\033[0m\n");
        } catch (FileNotFoundException e) {
            System.err.println("An error has occured\nCouldn't read: " + fileName);
            e.printStackTrace();
        }
        r.close();
        return data;
    }

}
