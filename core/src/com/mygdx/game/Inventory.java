package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.volcano.game.Cursor;
import com.volcano.game.Weapons;

import java.util.ArrayList;

public class Inventory {

    Sprite sprite;
    Vector2 position;
    Vector2 scale;

    Vector3 screenPosition;

    Texture topLeft;
    Texture topRight;
    Texture botLeft;
    Texture botRight;
    Texture basic;

    Sword sword;

    public static int inventorySize = 4;
    public static ArrayList<Weapons> inventory;
    public static int currentWeapon = 0;
    public static boolean inventoryIsOpen = false;

    String uiPath = "ui (new)/";

    public Inventory(Texture t, float x, float y, float w, float h) {
        this.position = new Vector2(x, y);
        this.scale = new Vector2(w, h);
        this.sprite = new Sprite();
        this.sprite.setRegion(t);
        this.sprite.setBounds(this.position.x, this.position.y, this.scale.x, this.scale.y);
        this.topLeft = new Texture(this.uiPath + "Inventory_select_top_left.png");
        this.topRight = new Texture(this.uiPath + "Inventory_select_top_right.png");
        this.botLeft = new Texture(this.uiPath + "Inventory_select_bottom_left.png");
        this.botRight = new Texture(this.uiPath + "Inventory_select_bottom_right.png");
        this.screenPosition = new Vector3();
        this.sword = new Sword(new Texture("heroes/knight/weapon_sword_1.png"), 70, 70, 0, 0);
        Inventory.inventory = new ArrayList<>();
        Inventory.inventory.add(this.sword);
        this.basic = t;
    }

    public void render(SpriteBatch batch, Cursor cursor)
    {
        if (Inventory.inventoryIsOpen) {
            this.sprite.draw(batch);
        }
    }

    public void checkCursorPosition(Cursor cursor)
    {
        float cursorX = cursor.getCursorX();
        float cursorY = cursor.getCursorY();
        Vector3 screenCoordinate = Game.camera.project(new Vector3(this.sprite.getX() - 500f, this.sprite.getY() - 200f, 0));
        float middleX = screenCoordinate.x;
        float middleY = screenCoordinate.y;

        if (cursorX < middleX && cursorY > middleY) {
            this.sprite.setRegion(this.topLeft);
        } if (cursorX < middleX && cursorY < middleY) {
            this.sprite.setRegion(this.botLeft);
        } if (cursorX > middleX && cursorY < middleY) {
            this.sprite.setRegion(this.botRight);
        } if (cursorX > middleX && cursorY > middleY) {
            this.sprite.setRegion(this.topRight);
        }
    }

    public void update(SpriteBatch batch, Vector2 position, Cursor cursor)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB))
            Inventory.inventoryIsOpen = !Inventory.inventoryIsOpen;
        this.sprite.setPosition(position.x, position.y - 100f);
        this.checkCursorPosition(cursor);
        this.render(batch, cursor);
    }

    public void dispose()
    {

    }
}
