package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.Animator;
import com.volcano.game.Players;

public class Player extends Players {

    Animator idle;
    Animator moving;
    float stateTime;
    float deltaTime;

    public Player(Sprite sprite, float x, float y) {
        super(sprite, x, y);
        this.idle = Animator.initializeAnimation(this.idle, "heroes/knight/knight_idle_spritesheet.png", 6, 1, 0.07f);
        this.moving = Animator.initializeAnimation(this.idle, "heroes/knight/knight_run_spritesheet.png", 6, 1, 0.07f);
    }

    public Player(Sprite sprite, Vector2 position) {
        super(sprite, position);
        this.idle = Animator.initializeAnimation(this.idle, "heroes/knight/knight_idle_spritesheet.png", 6, 1, 0.07f);
        this.moving = Animator.initializeAnimation(this.idle, "heroes/knight/knight_run_spritesheet.png", 6, 1, 0.07f);
    }

    //Main method

    public void flipPlayerWithMouse()
    {
        float mouseX = Gdx.input.getX();
        float positionX = this.sprite.getX();
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

    public void update(SpriteBatch batch)
    {
        this.stateTime += Gdx.graphics.getDeltaTime();
        deltaTime = Gdx.graphics.getDeltaTime();

        this.animationController();
        this.Move(false, 100f, deltaTime);
        this.flipPlayerWithMouse();
        this.flipPlayerWithKeyboard();
        this.sprite.draw(batch);
    }
}
