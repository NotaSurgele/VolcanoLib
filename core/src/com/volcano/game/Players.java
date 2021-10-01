package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Players {

    public Sprite sprite;
    public Vector2 position;
    public boolean qwertyCheck;

    ShapeRenderer hitboxShape;

    public Players(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        this.position = new Vector2(x, y);
        this.hitboxShape = new ShapeRenderer();
    }

    public Players(Sprite sprite, Vector2 position) {
        this.sprite = sprite;
        this.position = new Vector2(position);
        this.hitboxShape = new ShapeRenderer();
    }

    //Main method

    public void Move(boolean qwert, float moveSpeed, float deltaTime)
    {
        Vector2 direction = new Vector2(this.position);
        this.qwertyCheck = qwert;

        if (!this.qwertyCheck) {
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.Z)) ? moveSpeed * deltaTime: 0;
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.S)) ? -moveSpeed * deltaTime: 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.Q)) ? -moveSpeed * deltaTime: 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.D)) ? moveSpeed * deltaTime: 0;
        } else {
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.W)) ? moveSpeed * deltaTime: 0;
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.S)) ? -moveSpeed * deltaTime: 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.A)) ? -moveSpeed * deltaTime: 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.D)) ? moveSpeed * deltaTime: 0;
        }
        this.setPositionVector(direction);
    }
    public void Move(boolean qwert, float moveSpeed, float runningSpeed, float deltaTime)
    {
        Vector2 direction = new Vector2(this.position);
        this.qwertyCheck = qwert;
        boolean isRunning = this.isRunning();

        moveSpeed = isRunning ? moveSpeed += runningSpeed : moveSpeed;
        if (!this.qwertyCheck) {
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.Z)) ? moveSpeed * deltaTime: 0;
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.S)) ? -moveSpeed * deltaTime: 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.Q)) ? -moveSpeed * deltaTime: 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.D)) ? moveSpeed * deltaTime: 0;
        } else {
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.W)) ? moveSpeed * deltaTime: 0;
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.S)) ? -moveSpeed * deltaTime: 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.A)) ? -moveSpeed * deltaTime: 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.D)) ? moveSpeed * deltaTime: 0;
        }
        this.setPositionVector(direction);
    }

    public void drawHitbox(boolean toShow, Color hitboxColor)
    {
        if (toShow) {
            this.hitboxShape.setColor(hitboxColor);
            this.hitboxShape.setAutoShapeType(true);
            this.hitboxShape.begin();
            this.hitboxShape.rect(this.getPositionX(), this.getPositionY(), this.getWidth(), this.getHeight());
            this.hitboxShape.end();
        }
    }

    // Get Method
    public Vector2 getPositionVector()
    {
        return this.position;
    }

    public float getPositionX()
    {
        return this.position.x;
    }

    public float getPositionY()
    {
        return this.position.y;
    }

    public float getWidth()
    {
        return this.sprite.getWidth();
    }

    public float getHeight()
    {
        return this.sprite.getHeight();
    }

    // Set Method
    public void setPositionVector(Vector2 newPosition)
    {
        this.position = newPosition;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public void setPosition(float newX, float newY)
    {
        this.position.x = newX;
        this.position.y = newY;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setPositionX(float newX)
    {
        this.position.x = newX;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setPositionY(float newY)
    {
        this.position.y = newY;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    // Boolean method
    public boolean isMoving()
    {
        if (!this.qwertyCheck) {
            return  Gdx.input.isKeyPressed(Input.Keys.Z) || Gdx.input.isKeyPressed(Input.Keys.Q) ||
                    Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D);
        } else {
            return  Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.A) ||
                    Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D);
        }
    }

    public boolean isRunning()
    {
        boolean isMoving = this.isMoving();

        if (isMoving) {
            return (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT));
        }
        return false;
    }
}
