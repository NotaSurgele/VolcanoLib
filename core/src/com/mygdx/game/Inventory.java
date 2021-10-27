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
    BasicGun basicGun;

    public static int inventoryMaxSize = 4;
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
        this.basicGun = new BasicGun(new Texture("Weapons/BasicGun.png"), 50, 50, 0, 0);
        this.addGame();
        this.basic = t;
    }

    public void addGame()
    {
        Inventory.inventory = new ArrayList<>();
        Inventory.inventory.add(this.sword);
        Inventory.inventory.add(this.basicGun);
        Inventory.inventory.add(this.sword);
        Inventory.inventory.add(this.basicGun);
    }

    public void checkCursorPosition(Cursor cursor)
    {
        float cursorX = cursor.getWorldCursorX();
        float cursorY = cursor.getWorldCursorY();
        float middleX = this.sprite.getX() + (this.sprite.getWidth() / 2);
        float middleY = this.sprite.getY() + (this.sprite.getHeight() / 2);

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

    public Weapons getCurrentWeapons()
    {
        return Inventory.inventory.get(Inventory.currentWeapon);
    }

    private void setCurrentWeapon()
    {
        Texture t = this.sprite.getTexture();
        int oldValue = Inventory.currentWeapon;

        if (t == this.topRight)      Inventory.currentWeapon = 0;
        else if (t == this.botRight) Inventory.currentWeapon = 1;
        else if (t == this.botLeft)  Inventory.currentWeapon = 2;
        else if (t == this.topLeft)  Inventory.currentWeapon = 3;

        try {
            Inventory.inventory.get(Inventory.currentWeapon);
        } catch ( IndexOutOfBoundsException e ) {
            Inventory.currentWeapon = oldValue;
        }
    }

    public void renderInventoryItems(SpriteBatch batch)
    {
        for (int i = Inventory.inventory.size(); i != 0; i--) {
            float middleX = this.sprite.getX() + (this.sprite.getWidth() / 2);
            float middleY = this.sprite.getY() + (this.sprite.getHeight() / 2);

            if (i == 4) {
                float x = middleX - (this.sprite.getWidth() / 2) / 2;
                float y = middleY + (this.sprite.getHeight() / 2) / 2 - 60f;
                Weapons w = Inventory.inventory.get(i - 1);

                batch.draw(w.getInventoryShow(), x, y, w.getWeaponWidth(), w.getWeaponHeight());
            } else if (i == 3) {
                float x = middleX - (this.sprite.getWidth() / 2) / 2 ;
                float y = middleY - (this.sprite.getHeight() / 2) / 2;

                Weapons w = Inventory.inventory.get(i - 1);
                batch.draw(w.getInventoryShow(), x, y, w.getWeaponWidth(), w.getWeaponHeight());
            } else if (i == 2) {
                float x = middleX + (this.sprite.getWidth() / 2) / 2 - 60f;
                float y = middleY - (this.sprite.getHeight() / 2) / 2;

                Weapons w = Inventory.inventory.get(i - 1);
                batch.draw(w.getInventoryShow(), x, y, w.getWeaponWidth(), w.getWeaponHeight());
            } else if (i == 1) {
                float x = middleX + (this.sprite.getWidth() / 2) / 2 - 60f;
                float y = middleY + (this.sprite.getHeight() / 2) / 2 - 60f;

                Weapons w = Inventory.inventory.get(0);
                batch.draw(w.getInventoryShow(), x, y, w.getWeaponWidth(), w.getWeaponHeight());
            }
        }
    }

    public void render(SpriteBatch batch)
    {
        this.sprite.draw(batch);
        this.renderInventoryItems(batch);
    }

    public void update(SpriteBatch batch, Vector2 position, Cursor cursor)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) Inventory.inventoryIsOpen = !Inventory.inventoryIsOpen;
        if (Inventory.inventoryIsOpen) {
            this.setCurrentWeapon();
            this.sprite.setPosition(position.x, position.y - 100f);
            this.checkCursorPosition(cursor);
            this.render(batch);
        }
    }

    public void dispose()
    {

    }
}
