package com.volcano.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Dungeon;
import com.mygdx.game.Props;
import com.mygdx.game.PropsLoader;
import com.mygdx.game.PropsType;

import java.util.ArrayList;

public class Room {

    int width;
    int height;

    final int minWidth = 42;
    final int minHeight = 42;

    final int maxWidth = 56;
    final int maxHeight = 56;

    final int chamberMinWidth = 16;
    final int chamberMinHeight = 16;

    final int chamberMaxWidth = 26;
    final int chamberMaxHeight = 26;

    Vector2 spawnPoint;

    public int isRoomAdded = 0;

    int roomX = 0;
    int roomY = 0;

    int[][] layer;

    //Props
    PropsLoader propsLoader;
    Props[] props;

    public Room(PropsLoader pL) {
        this.setRoomSize();
        this.layer = new int[this.height][this.width];
        this.loadLayer();
        this.propsLoader = pL;
        this.loadProps();
    }

    private void setTilesId(int[][] layer, int line, int cell, int w, int h)
    {
        if (line == 0)      layer[line][cell] = -2;
        if (line == h - 1)  layer[line][cell] = -4;
        if (cell == 0)      layer[line][cell] = -5;
        if (cell == w - 1)  layer[line][cell] = -3;
        if (line > 0 && line < (h - 1) &&  cell > 0 && cell < (w - 1))  layer[line][cell] = 1;
        if (line == 0 && cell == 0)         layer[line][cell] = -5;
        if (line == 0 && cell == w - 1)     layer[line][cell] = -6;
        if (line == h - 1 && cell == w - 1) layer[line][cell] = -7;
        if (line == h - 1 && cell == 0)     layer[line][cell] = -8;
        if (line == 0 && cell == 0)         layer[line][cell] = -9;
        if (line == h - 2 && cell > 0 && cell < w - 1)      layer[line][cell] = -10;
    }

    private void loadLayer()
    {
        int w = this.width;
        int h = this.height;

        this.spawnPoint = new Vector2();
        for (int line = 0; line != h; line++) {
            for (int cell = 0; cell != w; cell++) {
                this.setTilesId(this.layer, line, cell, w, h);
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

    private void setChamberTiles(int[][] chamber, int width, int height)
    {
        for (int line = 0; line != height; line++) {
            for (int cell = 0; cell != width; cell++) {
                this.setTilesId(chamber, line, cell, width, height);
            }
        }
    }

    private void addChamberInPropsLayer(int[][] chamber, int width, int height, int[][] propsLayer)
    {
        int x = this.roomX;
        int y = MathUtils.random(this.roomY, (this.roomY + this.height) - height);
        int line = 0;
        int each = 0;

        for (int i = y; i != y + height; i++) {
            for (int j = x; j != x + width; j++) {
                propsLayer[i][j] = chamber[line][each];
                each++;
            }
            each = 0;
            line++;
        }
    }

    private void chamberGenerator(int[][] propsLayer)
    {
        int width = 0;
        int height = 0;
        int[][] chamber = null;

        width = MathUtils.random(this.chamberMinWidth, this.chamberMaxWidth);
        height = MathUtils.random(this.chamberMinHeight, this.chamberMaxHeight);
        chamber = new int[height][width];
        this.setChamberTiles(chamber, width, height);
        this.addChamberInPropsLayer(chamber, width, height, propsLayer);
    }

    private void loadProps()
    {
        int propsNumber = MathUtils.random(1);

        this.props = new Props[propsNumber];
        for (int index = 0; index != propsNumber; index++) {

            int size = this.propsLoader.getPropsArraySize();
            int r = MathUtils.random(1, size) - 1;
            Props p = this.propsLoader.getProp(r);

            this.props[index] = p;
        }
    }

    public Vector2 getSpawnPoint()
    {
        return this.spawnPoint;
    }

    public void printRooms()
    {
        for (int i = 0; i != this.layer.length; i++) {
            for (int j = 0; j != this.layer[i].length; j++) {
                System.out.print(this.layer[i][j]);
            }
            System.out.println();
        }
        System.out.println("_____________________________________________________________________");
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

        return maxX > width || maxY > height;
    }

    public int getRoomX() { return this.roomX; }

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

    private void writeRoomArray(int[][] array)
    {
        int localX = 0;
        int localY = 0;

        for (int i = this.roomY; i != (this.roomY + this.height); i++) {

            for (int j = this.roomX; j != (this.roomX + this.width); j++) {
                array[i][j] = this.layer[localY][localX];
                localX++;
            }
            localX = 0;
            localY++;
        }
    }

    public int[][] addRoomInMap(int[][] map, int worldWidth, int worldHeight)
    {
        int x = MathUtils.random(0, worldWidth - 1);
        int y = MathUtils.random(0, worldHeight - 1);
        int roomWidth = this.width;
        int roomHeight = this.height;

        if (this.isNotOverTheEdges(x, y, roomWidth, roomHeight, worldWidth, worldHeight)
            || this.isNotOverlapping(map, x, y, roomWidth, roomHeight))
            return map;
        this.roomX = x;
        this.roomY = y;

        this.setRoomSpawnPoint();
        this.writeRoomArray(map);
        this.isRoomAdded = 1;
        return map;
    }

    private boolean isPropsOverlapping(int[][] propsLayer, int x, int y, PropsType type)
    {
        if (type == PropsType.FLOOR)
            return !(propsLayer[y][x] == 1);
        else
            return !(propsLayer[y][x] == -10);
    }

    public int[][] addPropsInRoom(int[][] propsLayer, Props[][] propsArray)
    {
        this.writeRoomArray(propsLayer);
        this.chamberGenerator(propsLayer);
        int size = this.props.length;

        for (int each = 0; each != size; each++) {
            Props p = this.props[each];
            int x = 0;
            int y = 0;

            int minX = this.roomX;
            int maxX = this.roomX + this.width - 1;

            int minY = this.roomY;
            int maxY = this.roomY + this.height - 1;

            switch (p.getType()) {
                case FLOOR:
                    x = MathUtils.random(minX, maxX);
                    y = MathUtils.random(minY, maxY);

                    break;
                case WALL:
                    x = MathUtils.random(minX, maxX);
                    y = maxY - 1;
                    break;
                default: break;
            }
            //BOucle infini si props > 75
            /*while (isPropsOverlapping(propsLayer, x, y, p.getType())) {
                x = MathUtils.random(minX, maxX);
                y = MathUtils.random(minY, maxY);
            }*/
            propsLayer[y][x] = p.getValue();
            propsArray[y][x] = p;
        }
        //this.displayLayer(propsLayer, propsLayer.length, propsLayer[0].length);
        return propsLayer;
    }

    public int[][] getRoomLayer()
    {
        return this.layer;
    }

    private void displayLayer(int[][] layer, int width, int height)
    {
        for (int each = 0; each != height; each++) {
            for (int cell = 0; cell !=  width; cell++) {
                System.out.print(layer[each][cell] + " ");
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
