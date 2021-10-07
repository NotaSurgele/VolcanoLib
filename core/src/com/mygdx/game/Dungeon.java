package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Room;

import java.util.ArrayList;

public class Dungeon {

    ArrayList<Room> map;
    int roomsNumber = 10;

    public Dungeon() {
        this.map = new ArrayList<Room>();
        this.load();
    }

    public void load()
    {
        for (int each = 0; each != roomsNumber; each++) {
            Room r = new Room();
            this.map.add(r);
        }
    }

    public void update(SpriteBatch batch)
    {
        for (Room r : this.map) {
            r.update(batch);
        }
    }
}
