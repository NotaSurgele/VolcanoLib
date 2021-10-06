package com.volcano.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Room {

    int width;
    int height;

    int minWidth = 16;
    int minHeight = 16;

    int maxWidth = 56;
    int maxHeight = 56;

    int tileSize = 16;

    float startX = 300f;
    float startY = 100f;

    float x = startX;
    float y = startY;

    Texture floor;
    Texture wallLeft;
    Texture wallTop;
    Texture wallRight;
    Texture wallDown;

    SpriteBatch batch;

    int[][] layer;

    String floorPath = "tiles/floor/";
    String wallPath = "tiles/wall/";

    public Room() {
        this.setRoomSize();
        this.layer = new int[this.height][this.width];
        this.batch = new SpriteBatch();
        this.loadLayer();
        this.floor = new Texture(this.floorPath + "floor_2.png");
        this.wallTop = new Texture(this.wallPath + "wall_top_1.png");
        this.wallRight = new Texture(this.wallPath + "wall_bottom_inner_right.png");
        this.wallDown = new Texture(this.wallPath + "wall_bottom_1.png");
        this.wallLeft = new Texture(this.wallPath + "wall_bottom_inner_left.png");
    }

    private void setTilesId(int line, int cell, int w, int h)
    {
        if (line == 0)      this.layer[line][cell] = -1;
        if (line == h - 1)  this.layer[line][cell] = -3;
        if (cell == 0)      this.layer[line][cell] = -4;
        if (cell == w - 1)  this.layer[line][cell] = -2;
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

        for (int line = 0; line != h; line++) {
            for (int cell = 0; cell != w; cell++) {
                this.setTilesId(line, cell, w, h);
            }
        }
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

    public void drawRoom(SpriteBatch batch)
    {
        int h = this.height;
        int w = this.width;

        for (int line = 0; line != h; line++) {
            for (int each = 0; each != w; each++) {
                switch (this.layer[line][each]) {
                    case -1: batch.draw(this.wallDown, this.x, this.y);     break;
                    case -2: batch.draw(this.wallRight, this.x, this.y);    break;
                    case -3: batch.draw(this.wallTop, this.x, this.y);      break;
                    case -4: batch.draw(this.wallLeft, this.x, this.y);     break;
                    case  1: batch.draw(this.floor, this.x, this.y);        break;
                    default:                                                break;
                }
                this.x += this.tileSize;
            }
            this.y += this.tileSize;
            this.x = this.startX;
        }
        this.x = this.startX;
        this.y = this.startY;
    }

    private void setRoomSize()
    {
        this.width = MathUtils.random(this.minWidth, this.maxWidth);
        this.height = MathUtils.random(this.minHeight, this.maxHeight);
    }

    public void update(SpriteBatch batch)
    {
        //this.displayLayer();
        this.drawRoom(batch);
    }
}
