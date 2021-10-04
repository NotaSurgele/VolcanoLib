package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.Animator;
import com.volcano.game.Cursor;
import com.volcano.game.Players;

public class Player extends Players {

    Animator idle;
    Animator moving;
    Sword sword;

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

    public void test(Players player)
    {
        return;
    }

    public void update(SpriteBatch batch, Cursor cursor)
    {
        this.stateTime += Gdx.graphics.getDeltaTime();
        this.deltaTime = Gdx.graphics.getDeltaTime();

        this.animationController();
        this.Move(false, this.moveSpeed, runningSpeed, deltaTime);
        this.flipPlayerWithMouse(cursor);
        this.flipPlayerWithKeyboard();
        this.sprite.draw(batch);
        this.sword.update(batch);
        this.sword.lookAtCursor(cursor);
        this.sword.setWeaponPosition(new Vector2((this.getPositionX() + this.getWidth() / 2), (this.getPositionY() + this.getHeight() / 2) - 10f));
    }
}
