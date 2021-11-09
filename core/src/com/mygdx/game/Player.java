package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.*;

public class Player extends Players {

    Animator idle;
    Animator moving;
    TriggerUI triggerUI;
    Inventory inventory;

    Weapons currentWeapon;

    float oldX;
    float oldY;

    float stateTime;

    //Movement System
    float moveSpeed = 150f;
    float runningSpeed = 100f;

    public Player(Sprite sprite, float x, float y) {
        super(sprite, x, y);
        this.idle = Animator.initializeAnimation(this.idle, "heroes/knight/knight_idle_spritesheet.png", 6, 1, 0.07f);
        this.moving = Animator.initializeAnimation(this.idle, "heroes/knight/knight_run_spritesheet.png", 6, 1, 0.07f);
        this.triggerUI = new TriggerUI(new Texture("ui (new)/keyboard_input.png"), 50, 50, "F", "Press F to use !", 0.2f);
        this.inventory = new Inventory(new Texture("ui (new)/Inventory_notSelected.png"), 0, 0, 400, 400);
    }

    public Player(Sprite sprite, Vector2 position) {
        super(sprite, position);
        this.idle = Animator.initializeAnimation(this.idle, "heroes/knight/knight_idle_spritesheet.png", 6, 1, 0.07f);
        this.moving = Animator.initializeAnimation(this.idle, "heroes/knight/knight_run_spritesheet.png", 6, 1, 0.07f);
        this.triggerUI = new TriggerUI(new Texture("ui (new)/keyboard_input.png"), 50, 50, "F", "Press F to use !", 0.2f);
        this.inventory = new Inventory(new Texture("ui (new)/Inventory_notSelected.png"), 0, 0, 100, 100);
    }

    //Main method

    public void flipPlayerWithMouse(Cursor cursor)
    {
        float mouseX = cursor.getCursorX();
        float positionX = this.position.x;
        float characterWidth = this.getWidth();
        boolean isFlipped = this.sprite.isFlipX();

        if (mouseX < positionX + (characterWidth / 2))
        {
            if (!isFlipped) this.sprite.flip(true, false);
        } else {
            if (isFlipped) this.sprite.flip(false, false);
        }
    }

    public void flipPlayerWithKeyboard()
    {
        if (!this.qwertyCheck) {
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                if (!this.sprite.isFlipX())
                    this.sprite.flip(true, false);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                if (this.sprite.isFlipX())
                    this.sprite.flip(true, false);
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                if (!this.sprite.isFlipX())
                    this.sprite.flip(true, false);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                if (this.sprite.isFlipX())
                    this.sprite.flip(false, false);
            }
        }
    }

    public void animationController()
    {
        boolean isMoving = this.isMoving();

        if (isMoving)
            this.moving.playAnimationToSprite(this.sprite, this.stateTime, true);
        else
            this.idle.playAnimationToSprite(this.sprite, this.stateTime, true);
    }

    public void collisionCheckPoint(LayerData layerData)
    {
        layerData.setLayerCoordinate((int)this.getPositionX() / Dungeon.tileSize, (int)(this.sprite.getY() + (this.getHeight() / 2)) / Dungeon.tileSize);
        this.collider.onCollidingSetOldEntityPosition(this.sprite, layerData, this.oldX + 3f, this.oldY, this.position);
        layerData.setLayerCoordinate((int)(this.getPositionX() + this.getWidth()) / Dungeon.tileSize, (int)(this.getPositionY() + (this.getHeight() / 2)) / Dungeon.tileSize);
        this.collider.onCollidingSetOldEntityPosition(this.sprite, layerData, this.oldX - 3f, this.oldY, this.position);
        layerData.setLayerCoordinate((int)(this.getPositionX() + (this.getWidth() / 2)) / Dungeon.tileSize, (int)(this.getPositionY() + (this.getHeight())) / Dungeon.tileSize);
        this.collider.onCollidingSetOldEntityPosition(this.sprite, layerData, this.oldX, this.oldY - 3f, this.position);
        layerData.setLayerCoordinate((int)(this.getPositionX() + (this.getWidth() / 2)) / Dungeon.tileSize, (int)(this.getPositionY()) / Dungeon.tileSize);
        this.collider.onCollidingSetOldEntityPosition(this.sprite, layerData, this.oldX, this.oldY + 3f, this.position);
    }

    public void checkWeapons(SpriteBatch batch, Cursor cursor, LayerData layerData)
    {
        this.currentWeapon = this.inventory.getCurrentWeapons();
        if (this.currentWeapon == null) return;
        this.currentWeapon.setWeaponX((this.sprite.getX() + this.getWidth() / 2));
        this.currentWeapon.setWeaponY((this.sprite.getY() + (this.getHeight() / 2)) - 10f);

        if (this.currentWeapon instanceof Sword) {
            this.currentWeapon.lookAtCursor(cursor, 45f);
            ((Sword) this.currentWeapon).update(batch);
        } else if (this.currentWeapon instanceof BasicGun) {
            this.currentWeapon.lookAtCursor(cursor, 0f);
            ((BasicGun) this.currentWeapon).update(batch, cursor, layerData);
        }
    }

    public Weapons getCurrentWeapon()
    {
        return this.inventory.getCurrentWeapons();
    }

    public void getDamaged(float dmg)
    {
        System.out.println("Hit");
    }

    public void update(SpriteBatch batch, float deltaTime, Cursor cursor, LayerData layerData)
    {
        this.stateTime += Game.deltaTime;

        if (!this.collider.isColliding(layerData)) {
            this.oldX = this.position.x;
            this.oldY = this.position.y;
        }
        this.animationController();
        this.Move(false, this.moveSpeed, runningSpeed, deltaTime);
        this.collisionCheckPoint(layerData);
        this.flipPlayerWithMouse(cursor);
        this.flipPlayerWithKeyboard();
        this.draw(batch);
        this.checkWeapons(batch, cursor, layerData);
        this.inventory.update(batch, this.getPositionVector(), cursor);
    }

    public void dispose()
    {
        this.triggerUI.dispose();
        this.idle.dispose();
        this.moving.dispose();
    }
}
