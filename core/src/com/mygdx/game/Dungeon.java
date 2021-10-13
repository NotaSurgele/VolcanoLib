package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Room;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Dungeon {

    int[][] map;

    Texture floor;
    Texture wallLeft;
    Texture wallTop;
    Texture wallRight;
    Texture wallDown;

    final int tileSize = 16;
    int width = 200;
    int height = 200;

    int howMuchRoom = 10;

    final String floorPath = "tiles/floor/";
    final String wallPath = "tiles/wall/";

    public Dungeon() {
        this.map = new int[this.height][this.width];
        this.floor = new Texture(this.floorPath + "floor_2.png");
        this.wallTop = new Texture(this.wallPath + "wall_top_1.png");
        this.wallRight = new Texture(this.wallPath + "wall_bottom_inner_right.png");
        this.wallDown = new Texture(this.wallPath + "wall_bottom_1.png");
        this.wallLeft = new Texture(this.wallPath + "wall_bottom_inner_left.png");
        this.load();
    }

    public void load()
    {
        for (int i = 0; i != this.height; i++) {
            for (int j = 0; j != this.width; j++) {
                this.map[i][j] = -1;
            }
        }
        for (int i = 0; i != this.howMuchRoom; ) {
            Room r = new Room();
            this.map = r.addRoomInMap(this.map, this.width, this.height);
            i += r.isRoomAdded;
        }
    }

    public void draw(SpriteBatch batch)
    {
        int h = this.height;
        int w = this.width;
        int x = 0;
        int y = 0;

        for (int line = 0; line != h; line++) {
            for (int each = 0; each != w; each++) {
                switch (this.map[line][each]) {
                    case -2: batch.draw(this.wallDown, x, y);     break;
                    case -3: batch.draw(this.wallRight, x, y);    break;
                    case -4: batch.draw(this.wallTop, x, y);      break;
                    case -5: batch.draw(this.wallLeft, x, y);     break;
                    case  1: batch.draw(this.floor, x, y);        break;
                    default:                                      break;
                }
                x += this.tileSize;
            }
            y += this.tileSize;
            x = 0;
        }
    }

    public void update(SpriteBatch batch)
    {
        this.draw(batch);
    }
}
