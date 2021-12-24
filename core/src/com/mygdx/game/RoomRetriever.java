package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class RoomRetriever {

    public RoomRetriever() {}

    public int[][] convertJSONArray(JSONArray arr)
    {
        int[][] layer = new int[50][50];
        int size = arr.size();
        int x = 0;

        for (int line = size - 1; line != 0; line--) {

            String s = arr.get(line).toString();
            String clear1 = s.replace("[", "");
            String clear2 = clear1.replace("]", "");
            String[] sArray = clear2.split(",");

            for (int each = 0; each != sArray.length; each++) {
                layer[x][each] = Integer.parseInt(sArray[each]);
            }
            x++;
        }
        return layer;
    }

    public ArrayList<int[][]> getRoomLayers() {
        JSONObject globalObj = null;
        File f = new File(".config/data/Rooms.json");
        ArrayList<int[][]> layersArrayList = new ArrayList<>();

        try {
            Object tmp = new JSONParser().parse(new FileReader(f));
            JSONObject room = null;
            globalObj = (JSONObject) tmp;
            int where = MathUtils.random(1, globalObj.size());

            room = (JSONObject) globalObj.get(String.valueOf(where));
            JSONArray wallLayer = (JSONArray) room.get("Wall");
            JSONArray floor = (JSONArray) room.get("Floor");
            JSONArray prop = (JSONArray) room.get("Prop");
            layersArrayList.add(this.convertJSONArray(wallLayer));
            layersArrayList.add(this.convertJSONArray(floor));
            layersArrayList.add(this.convertJSONArray(prop));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return layersArrayList;
    }
}
