package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.LayerData;
import com.volcano.game.Room;
import com.volcano.game.TriggerUI;

import java.util.ArrayList;

public class Dungeon {

    int[][] floorLayer;
    int[][] propsLayer;
    int[][] wallLayer;
    int[][] globalLayer;

    Props[][] propsArray;
    Texture[][] floorTextureArray;
    Texture[][] wallTextureArray;

    PropsLoader propsLoader;

    public LayerData layerData;

    Texture floor;
    Texture wall;
    Texture wallLeft;
    Texture wallTop;
    Texture wallRight;
    Texture wallDown;
    Texture extract;

    public static final int tileSize = 32;
    int width = 250;
    int height = 250;

    ArrayList<Room> rooms;
    int currentRoom = 0;
    int howMuchRoom = 12;

    final String floorPath = "tiles/floor/";
    final String wallPath = "tiles/wall/";

    public Dungeon() {
        this.floorLayer = new int[this.height][this.width];
        this.wallLayer  =  new int[this.height][this.width];
        this.propsLayer = new int[this.height][this.width];

        this.floorTextureArray = new Texture[this.height][this.width];
        this.wallTextureArray = new Texture[this.height][this.width];
        this.propsArray = new Props[this.height][this.width];

        this.propsLoader = new PropsLoader();

        this.floor = new Texture(this.floorPath + "floor_2.png");
        this.wallTop = new Texture(this.wallPath + "wall_top_1.png");
        this.wallRight = new Texture(this.wallPath + "wall_bottom_inner_right.png");
        this.wallDown = new Texture(this.wallPath + "wall_bottom_1.png");
        this.wallLeft = new Texture(this.wallPath + "wall_bottom_inner_left.png");
        this.extract = new Texture(this.floorPath + "stair_nextlevel.png");
        this.wall = new Texture(this.wallPath + "wall_1.png");
        this.rooms = new ArrayList<Room>();
        this.load();
    }

    public void load()
    {
        for (int i = 0; i != this.height; i++) {
            for (int j = 0; j != this.width; j++) {
                this.floorLayer[i][j] = -1;
                this.wallLayer[i][j] = -1;
                this.propsLayer[i][j] = -1;
            }
        }
        for (int i = 0; i != this.howMuchRoom; ) {
            Room r = new Room(this.propsLoader);

            r.addFloorAndWall(this.floorLayer, this.wallLayer, this.width, this.height);
            if (r.isRoomAdded == 1) {
                r.addProp(this.propsLayer, this.propsArray);
                i += r.isRoomAdded;
                this.rooms.add(r);
            }
        }
        this.loadFloorTextureArray();
        this.loadWallTextureArray();
        this.loadGlobalLayer();
        this.layerData = new LayerData(this.globalLayer);
    }

    private void loadGlobalLayer()
    {
        this.globalLayer = new int[this.propsLayer.length][this.propsLayer[0].length];

        for (int line = this.propsArray.length - 1; line != 0; line--) {
            for (int cell = this.propsArray[line].length - 1; cell != 0; cell--) {
                this.globalLayer[line][cell] = this.floorLayer[line][cell];
                if (this.wallLayer[line][cell] != -1)
                    this.globalLayer[line][cell] = this.wallLayer[line][cell];
                if (this.propsLayer[line][cell] < -1)
                    this.globalLayer[line][cell] = this.propsLayer[line][cell];
            }
        }
    }

    private void loadFloorTextureArray()
    {
        int h = this.height;
        int w = this.width;

        for (int line = 0; line != h; line++) {

            for (int each = 0; each != w; each++) {
                if (this.floorLayer[line][each] != -1) {
                    if (this.floorLayer[line][each] == 1)
                        this.floorTextureArray[line][each] = this.floor;
                    if (this.floorLayer[line][each] == 2)
                        this.floorTextureArray[line][each] = this.extract;
                } else
                    this.floorTextureArray[line][each] = null;
            }
        }
    }

    private void loadWallTextureArray()
    {
        int h = this.height;
        int w = this.width;

        for (int line = 0; line != h; line++) {
            for (int each = 0; each != w; each++) {
                if (this.wallLayer[line][each] != -1) {
                   if (this.wallLayer[line][each] == -2) this.wallTextureArray[line][each] = this.wallDown;
                    else if (this.wallLayer[line][each] == -3) this.wallTextureArray[line][each] = this.wallRight;
                    else if (this.wallLayer[line][each] == -4) this.wallTextureArray[line][each] = this.wallTop;
                    else if (this.wallLayer[line][each] == -5) this.wallTextureArray[line][each] = this.wallLeft;
                    else if (this.wallLayer[line][each] == -10) this.wallTextureArray[line][each] = this.wall;
                } else
                    this.wallTextureArray[line][each] = null;
            }
        }
    }

    private boolean stairsCheck(int line, int each, int[][]layer)
    {
        return layer[line][each] == 2;
    }

    private void checkStairs(Player player, SpriteBatch batch, TriggerUI triggerUI)
    {
        float TriggerUIX = player.getPositionX() + player.getWidth() + 10f;
        float TriggerUIY = player.getPositionY() + (player.getHeight() / 2);

        if (this.stairsCheck(
                (int)(player.getPositionY() + (player.getHeight() / 2) - 16f) / Dungeon.tileSize,
                (int)(player.getPositionX() + (player.getWidth() / 2)) / Dungeon.tileSize,
                    this.propsLayer
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

                if ((line >= yMin && line <= yMax) && (each >= xMin && each <= xMax)) {
                    if (this.floorTextureArray[line][each] != null)
                        batch.draw(this.floorTextureArray[line][each], x, y, tileSize, tileSize);
                    if (this.wallTextureArray[line][each] != null)
                        batch.draw(this.wallTextureArray[line][each], x, y, tileSize, tileSize);
                    if (this.propsArray[line][each] != null) {
                        if (this.propsArray[line][each].isAnimate)
                            this.propsArray[line][each].animate(batch, true, x, y, tileSize, tileSize);
                        else
                            batch.draw(this.propsArray[line][each].getTexture(), x, y, tileSize, tileSize);
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
