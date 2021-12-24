package com.volcano.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;

import java.util.ArrayList;

public class Room {

    ArrayList<int[][]> layers;
    RoomRetriever roomRetriever;

    int roomX = 0;
    int roomY = 0;

    final int ROOMWIDTH = 50;
    final int ROOMHEIGHT = 50;

    Vector2 spawnPoint;

    public int isRoomAdded = 0;

    PropsLoader pL;

    public Room(PropsLoader pL) {
        this.roomRetriever = new RoomRetriever();
        this.layers = this.roomRetriever.getRoomLayers();
        this.pL = pL;
    }

    private boolean isNotOverTheEdges(int x, int y, int roomWidth, int roomHeight, int width, int height)
    {
        int maxX = x + roomWidth;
        int maxY = y + roomHeight;

        return maxX > width || maxY > height;
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

    private void setRoomSpawnPoint()
    {
        float worldX = (this.roomX + this.ROOMWIDTH / 2f)  * Dungeon.tileSize;
        float worldY = (this.roomY + this.ROOMHEIGHT / 2f) * Dungeon.tileSize;

        this.spawnPoint = new Vector2(worldX, worldY);
    }

    private void writeRoomArray(int[][] array, int get)
    {
        int localX = 0;
        int localY = 0;

        int[][] room = this.layers.get(get);

        for (int i = this.roomY; i != (this.roomY + this.ROOMHEIGHT); i++) {

            for (int j = this.roomX; j != (this.roomX + this.ROOMWIDTH); j++) {

                array[i][j] = room[localY][localX];
                localX++;
            }
            localX = 0;
            localY++;
        }
    }

    public void addFloorAndWall(int[][] floor, int[][] wall, int worldWidth, int worldHeight)
    {
        int x = MathUtils.random(0, worldWidth - 1);
        int y = MathUtils.random(0, worldHeight - 1);
        int roomWidth = this.ROOMWIDTH;
        int roomHeight = this.ROOMHEIGHT;

        if (this.isNotOverTheEdges(x, y, roomWidth, roomHeight, worldWidth, worldHeight)
                || this.isNotOverlapping(floor, x, y, roomWidth, roomHeight))
            return;
        this.roomX = x;
        this.roomY = y;

        this.setRoomSpawnPoint();
        this.writeRoomArray(floor, 1);
        this.writeRoomArray(wall, 0);
        this.isRoomAdded = 1;
    }

    public void addProp(int[][] propsLayer, Props[][] propsData)
    {
        this.writeRoomArray(propsLayer, 2);
        int[][] tmp = this.layers.get(2);

        int localX = 0;
        int localY = 0;

        for (int i = this.roomY; i != (this.roomY + this.ROOMHEIGHT); i++) {

            for (int j = this.roomX; j != (this.roomX + this.ROOMWIDTH); j++) {
                Props p = this.pL.getPropByValue(tmp[localY][localX]);

                if (p != null) {
                    propsData[i][j] = p;
                }
                localX++;
            }
            localX = 0;
            localY++;
        }
    }

    public int getRoomX() { return this.roomX; }

    public int getRoomY()
    {
        return this.roomY;
    }

    public int getWidth()
    {
        return this.ROOMWIDTH;
    }

    public int getHeight()
    {
        return this.ROOMHEIGHT;
    }

    public Vector2 getSpawnPoint()
    {
        return this.spawnPoint;
    }
}
