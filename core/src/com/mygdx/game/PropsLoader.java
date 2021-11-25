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
            String texturePath = val[1];
            int value = Integer.parseInt(val[2]);
            PropsType type = this.getPropsType(val[3]);
            this.propsArray[i] = new Props(new Texture(texturePath), value, type);
        }
    }

}
