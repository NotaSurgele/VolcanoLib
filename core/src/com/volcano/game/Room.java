package com.volcano.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Dungeon;

import java.util.ArrayList;

public class Room {

    int width;
    int height;

    int minWidth = 26;
    int minHeight = 26;

    int maxWidth = 56;
    int maxHeight = 56;

    float startX = 0f;
    float startY = 0f;

    Vector2 spawnPoint;

    public int isRoomAdded = 0;

    float x = startX;
    float y = startY;

    int roomX = 0;
    int roomY = 0;

    int[][] layer;

    public Room() {
        this.setRoomSize();
        this.layer = new int[this.height][this.width];
        this.loadLayer();
    }

    public Room(int startX, int startY)
    {
        this.setRoomSize();
        this.layer = new int[this.height][this.width];
        this.startX = startX;
        this.startY = startY;
        this.loadLayer();
    }

    private void setTilesId(int line, int cell, int w, int h)
    {
        if (line == 0)      this.layer[line][cell] = -2;
        if (line == h - 1)  this.layer[line][cell] = -4;
        if (cell == 0)      this.layer[line][cell] = -5;
        if (cell == w - 1)  this.layer[line][cell] = -3;
        if (line > 0 && line < (h - 1) &&  cell > 0 && cell < (w - 1))  this.layer[line][cell] = 1;
        if (line == 0 && cell == 0)         this.layer[line][cell] = -5;
        if (line == 0 && cell == w - 1)     this.layer[line][cell] = -6;
        if (line == h - 1 && cell == w - 1) this.layer[line][cell] = -7;
        if (line == h - 1 && cell == 0)     this.layer[line][cell] = -8;
    }

    private void loadLayer()
    {
        int w = this.width;
        int h = this.height;

        this.spawnPoint = new Vector2();
        for (int line = 0; line != h; line++) {
            for (int cell = 0; cell != w; cell++) {
                this.setTilesId(line, cell, w, h);
            }
        }
        this.setExtract();
    }

    private void setExtract()
    {
        int x = MathUtils.random(3, this.width - 2) - 1;
        int y = MathUtils.random(3, this.height - 2) - 1;

        this.layer[y][x] = 2;
    }

    public Vector2 getSpawnPoint()
    {
        return this.spawnPoint;
    }

    public void setRoomSpawnPoint()
    {
        float worldX = (this.roomX + this.width / 2f)  * Dungeon.tileSize;
        float worldY = (this.roomY + this.height / 2f) * Dungeon.tileSize;

        this.spawnPoint = new Vector2(worldX, worldY);
    }

    private boolean isNotOverlapping(int[][] map, int startX, int startY, int roomWidth, int roomHeight)
    {
        int width = startX + roomWidth;
        int height = startY + roomHeight;

        for (int i = startY; i != height; i++) {
            for (int j = startX; j != width; j++) {
                if (map[i][j] != -1) return true;
            }
        }
        return false;
    }

    private boolean isNotOverTheEdges(int x, int y, int roomWidth, int roomHeight, int width, int height)
    {
        int maxX = x + roomWidth;
        int maxY = y + roomHeight;

        if (maxX > width || maxY > height) return true;
        return false;
    }

    public int getRoomX()
    {
        return this.roomX;
    }

    public int getRoomY()
    {
        return this.roomY;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int[][] addRoomInMap(int[][] map, int width, int height)
    {
        int x = MathUtils.random(0, width - 1);
        int y = MathUtils.random(0, height - 1);
        int roomWidth = this.width;
        int roomHeight = this.height;
        int localX = 0;
        int localY = 0;

        if (this.isNotOverTheEdges(x, y, roomWidth, roomHeight, width, height)
            || this.isNotOverlapping(map, x, y, roomWidth, roomHeight))
            return map;
        this.roomX = x;
        this.roomY = y;
        this.setRoomSpawnPoint();
        for (int i = y; i != (y + roomHeight); i++) {
            for (int j = x; j != (x + roomWidth); j++) {
                map[i][j] = this.layer[localY][localX];
                localX++;
            }
            localX = 0;
            localY++;
        }
        this.isRoomAdded = 1;
        return map;
    }

    private void displayLayer()
    {
        for (int each = 0; each != this.height; each++) {
            for (int cell = 0; cell !=  this.width; cell++) {
                System.out.print(this.layer[each][cell] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("______________________________________________________________________________");
    }

    private void setRoomSize()
    {
        this.width = MathUtils.random(this.minWidth, this.maxWidth);
        this.height = MathUtils.random(this.minHeight, this.maxHeight);
    }

}
