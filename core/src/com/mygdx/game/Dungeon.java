package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.LayerData;
import com.volcano.game.MazeGenerator;
import com.volcano.game.Room;
import com.volcano.game.TriggerUI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Dungeon {

    int[][] map;
    int[][] propsLayer;

    public LayerData layerData;

    PropsLoader propsLoader;
    Texture[][] propsTextureArray;

    Texture floor;
    Texture wallLeft;
    Texture wallTop;
    Texture wallRight;
    Texture wallDown;
    Texture extract;
    Texture[][] mapTextureArray;

    public static final int tileSize = 32;
    int width = 250;
    int height = 250;

    ArrayList<Room> rooms;
    int currentRoom = 0;
    int howMuchRoom = 12;

    final String floorPath = "tiles/floor/";
    final String wallPath = "tiles/wall/";

    public Dungeon() {
        this.map = new int[this.height][this.width];
        this.mapTextureArray = new Texture[this.height][this.width];
        this.propsLayer = new int[this.height][this.width];
        this.propsTextureArray = new Texture[this.height][this.width];
        this.floor = new Texture(this.floorPath + "floor_2.png");
        this.wallTop = new Texture(this.wallPath + "wall_top_1.png");
        this.wallRight = new Texture(this.wallPath + "wall_bottom_inner_right.png");
        this.wallDown = new Texture(this.wallPath + "wall_bottom_1.png");
        this.wallLeft = new Texture(this.wallPath + "wall_bottom_inner_left.png");
        this.extract = new Texture(this.floorPath + "stair_nextlevel.png");
        this.rooms = new ArrayList<Room>();
        this.propsLoader = new PropsLoader();
        this.load();
    }

    public void load()
    {
        for (int i = 0; i != this.height; i++) {
            for (int j = 0; j != this.width; j++) {
                this.map[i][j] = -1;
                this.propsLayer[i][j] = -1;
            }
        }
        for (int i = 0; i != this.howMuchRoom; ) {
            Room r = new Room(this.propsLoader);

            this.map = r.addRoomInMap(this.map, this.width, this.height);

            if (r.isRoomAdded == 1) {
                this.propsLayer = r.addPropsInRoom(this.propsLayer, this.propsTextureArray);
                i += r.isRoomAdded;
                this.rooms.add(r);

            }
        }
        this.loadTextureMapArray();
        this.layerData = new LayerData(this.map);
    }

    private void loadTextureMapArray()
    {
        int h = this.height;
        int w = this.width;

        for (int line = 0; line != h; line++) {

            for (int each = 0; each != w; each++) {

                if (this.map[line][each] != -1) {

                    if (this.map[line][each] == -2) this.mapTextureArray[line][each] = this.wallDown;
                    else if (this.map[line][each] == -3) this.mapTextureArray[line][each] = this.wallRight;
                    else if (this.map[line][each] == -4) this.mapTextureArray[line][each] = this.wallTop;
                    else if (this.map[line][each] == -5) this.mapTextureArray[line][each] = this.wallLeft;
                    else if (this.map[line][each] == 1) this.mapTextureArray[line][each] = this.floor;
                    else if (this.map[line][each] == 2) this.mapTextureArray[line][each] = this.extract;
                } else
                    this.mapTextureArray[line][each] = null;
            }
        }
    }

    private boolean stairsCheck(int line, int each, int[][]layer)
    {
        if (layer[line][each] == 2) {
            return true;
        }
        return false;
    }

    private void checkStairs(Player player, SpriteBatch batch, TriggerUI triggerUI)
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
            if (this.currentRoom >= this.rooms.size()) return;
            Room r = this.rooms.get(this.currentRoom);
            if (r != null) {
                Vector2 v = r.getSpawnPoint();
                player.setPosition(v);
                Game.STATE = Game.State.CHANGE;
            }
        }
    }

    public Vector2 getChoosenRoomSpawnPoint(int get)
    {
        return this.rooms.get(get).getSpawnPoint();
    }

    public Room getCurrentRoom()
    {
        return this.rooms.get(this.currentRoom);
    }

    public void draw(SpriteBatch batch)
    {
        int h = this.height;
        int w = this.width;
        int x = 0;
        int y = 0;

        Room r = this.getCurrentRoom();

        for (int line = 0; line != h; line++) {
            int xMin = r.getRoomX();
            int yMin = r.getRoomY();

            int xMax = (r.getRoomX() + r.getWidth());
            int yMax = (r.getRoomY() + r.getHeight());

            for (int each = 0; each != w; each++) {

                if (this.mapTextureArray[line][each] != null) {
                    if ((line >= yMin && line <= yMax) && (each >= xMin && each <= xMax)) {
                        batch.draw(this.mapTextureArray[line][each], x, y, tileSize, tileSize);
                        if (this.propsTextureArray[line][each] != null) {
                            batch.draw(this.propsTextureArray[line][each], x, y, tileSize, tileSize);
                        }
                    }
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

    public void dispose()
    {
        this.wallDown.dispose();
        this.wallRight.dispose();
        this.wallLeft.dispose();
        this.floor.dispose();
        this.extract.dispose();
    }
}
