package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.volcano.game.Utils;

public class PropsLoader {

    Props[] propsArray;

    final String propsDir = "props_itens/";

    public PropsLoader()  {
        this.loadArray();
    }

    private PropsType getPropsType(String d)
    {
        if (d.equals("Wall"))
            return PropsType.WALL;
        else return PropsType.FLOOR;
    }

    private void loadArray() {
        String data = Utils.readFile(Utils.configDir + "props.data");
        String[] dataArray = data.split(";");

        this.propsArray = new Props[dataArray.length];
        for (int i = 0; i != dataArray.length; i++) {
            String[] val = dataArray[i].split(":");

            String name = val[0];
            String texturePath = val[1];
            int value = Integer.parseInt(val[2]);
            PropsType type = this.getPropsType(val[3]);
            boolean isAnimate = Boolean.parseBoolean(val[4]);
            if (isAnimate) {
                int cols = Integer.parseInt(val[5]);
                int rows = Integer.parseInt(val[6]);
                float frameDuration = Float.parseFloat(val[7]);
                this.propsArray[i] = new Props(name, new Texture(propsDir + texturePath), value, type, cols, rows, frameDuration);
            } else
                this.propsArray[i] = new Props(name, new Texture(propsDir + texturePath), value, type);
        }
    }

    public Props getProp(int index)
    {
        return this.propsArray[index];
    }

    public int getPropsArraySize()
    {
        return this.propsArray.length;
    }

}
