package com.mygdx.game;

import com.volcano.game.Utils;

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
        this.data = Utils.readFile(".config/input.ini");
        this.retrieveInput();
    }

    private void retrieveInput() {
        String[] arrayData = this.data.split(";");

        for (int i = 0; !arrayData[i].contains(","); i++) {
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

}
