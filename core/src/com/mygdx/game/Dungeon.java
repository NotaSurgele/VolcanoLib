package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.MazeGenerator;
import com.volcano.game.Room;
import com.volcano.game.TriggerUI;

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
    Texture extract;

    public static final int tileSize = 32;
    int width = 300;
    int height = 300;

    ArrayList<Room> rooms;
    int currentRoom = 0;
    int howMuchRoom = 15;

    final String floorPath = "tiles/floor/";
    final String wallPath = "tiles/wall/";

    public Dungeon() {
        this.map = new int[this.height][this.width];
        this.floor = new Texture(this.floorPath + "floor_2.png");
        this.wallTop = new Texture(this.wallPath + "wall_top_1.png");
        this.wallRight = new Texture(this.wallPath + "wall_bottom_inner_right.png");
        this.wallDown = new Texture(this.wallPath + "wall_bottom_1.png");
        this.wallLeft = new Texture(this.wallPath + "wall_bottom_inner_left.png");
        this.extract = new Texture(this.floorPath + "stair_nextlevel.png");
        this.rooms = new ArrayList<Room>();
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
            if (r.isRoomAdded == 1) {
                i += r.isRoomAdded;
                this.rooms.add(r);
            }
        }
    }

    public boolean stairsCheck(int line, int each, int[][]layer)
    {
        if (layer[line][each] == 2) {
            return true;
        }
        return false;
    }

    public void checkStairs(Player player, SpriteBatch batch, TriggerUI triggerUI)
    {
        float TriggerUIX = player.getPositionX() + player.getWidth() + 10f;
        float TriggerUIY = player.getPositionY() + (player.getHeight() / 2);

        if (this.stairsCheck(
                (int)(player.getPositionY() + (player.getHeight() / 2) - 16f) / Dungeon.tileSize,
                (int)(player.getPositionX() + (player.getWidth() / 2)) / Dungeon.tileSize,
                    this.map
        )) {
            triggerUI.draw(batch, TriggerUIX, TriggerUIY);
            this.stairsInput(player);
        }
    }

    public void stairsInput(Player player)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            this.currentRoom += 1;
            if (this.currentRoom >= this.rooms.size())
                return;
            Room r = this.rooms.get(this.currentRoom);
            if (r != null) {
                Vector2 v = r.getSpawnPoint();
                player.setPosition(v);
            }
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
                    case -2: batch.draw(this.wallDown, x, y, tileSize, tileSize);     break;
                    case -3: batch.draw(this.wallRight, x, y, tileSize, tileSize);    break;
                    case -4: batch.draw(this.wallTop, x, y, tileSize, tileSize);      break;
                    case -5: batch.draw(this.wallLeft, x, y, tileSize, tileSize);     break;
                    case  1: batch.draw(this.floor, x, y, tileSize, tileSize);        break;
                    case  2: batch.draw(this.extract, x, y, tileSize, tileSize);      break;
                    default:                                                          break;
                }
                x += tileSize;
            }
            y += tileSize;
            x = 0;
        }
    }

    public void update(SpriteBatch batch, Player player, TriggerUI triggerUI)
    {
        this.draw(batch);
        this.checkStairs(player, batch, triggerUI);
    }
}
