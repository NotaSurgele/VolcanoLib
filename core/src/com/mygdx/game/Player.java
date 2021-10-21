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
    Sword sword;
    TriggerUI triggerUI;

    float oldX;
    float oldY;

    float stateTime;
    float deltaTime;

    //Movement System
    float moveSpeed = 150f;
    float runningSpeed = 100f;

    public Player(Sprite sprite, float x, float y) {
        super(sprite, x, y);
        this.idle = Animator.initializeAnimation(this.idle, "heroes/knight/knight_idle_spritesheet.png", 6, 1, 0.07f);
        this.moving = Animator.initializeAnimation(this.idle, "heroes/knight/knight_run_spritesheet.png", 6, 1, 0.07f);
        this.sword = new Sword(new Texture("heroes/knight/weapon_sword_1.png"), 70, 70, (this.getPositionX() + this.getWidth() / 2), (this.getPositionY() + this.getHeight() / 2) - 10f);
        this.triggerUI = new TriggerUI(new Texture("ui (new)/keyboard_input.png"), 50, 50, "F", "Press F to use !", 0.2f);
    }

    public Player(Sprite sprite, Vector2 position) {
        super(sprite, position);
        this.idle = Animator.initializeAnimation(this.idle, "heroes/knight/knight_idle_spritesheet.png", 6, 1, 0.07f);
        this.moving = Animator.initializeAnimation(this.idle, "heroes/knight/knight_run_spritesheet.png", 6, 1, 0.07f);
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

    public void update(SpriteBatch batch, Cursor cursor, LayerData layerData)
    {
        this.stateTime += Gdx.graphics.getDeltaTime();
        this.deltaTime = Gdx.graphics.getDeltaTime();

        if (!this.collider.isColliding(layerData)) {
            this.oldX = this.position.x;
            this.oldY = this.position.y;
        }
        this.animationController();
        this.Move(false, this.moveSpeed, runningSpeed, deltaTime);
        this.collisionCheckPoint(layerData);
        this.flipPlayerWithMouse(cursor);
        this.flipPlayerWithKeyboard();
        this.sprite.draw(batch);
        this.sword.lookAtCursor(cursor, 45f);
        this.sword.setWeaponPosition(new Vector2((this.sprite.getX() + this.getWidth() / 2), (this.sprite.getY() + this.getHeight() / 2) - 10f));
        this.sword.update(batch);
    }

    public void dispose()
    {
        this.triggerUI.dispose();
        this.dispose();
    }
}
