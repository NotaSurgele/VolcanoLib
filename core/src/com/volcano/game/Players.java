package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Players {

    public Sprite sprite;
    public Vector2 position;

    public Players(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        this.position = new Vector2(x, y);
    }

    public Players(Sprite sprite, Vector2 position) {
        this.sprite = sprite;
        this.position = new Vector2(position);
    }

    //Main method

    public void Move(boolean qwert, float moveSpeed)
    {
        Vector2 direction = new Vector2(this.position);

        if (!qwert) {
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.Z)) ? moveSpeed * Gdx.graphics.getDeltaTime(): 0;
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.S)) ? -moveSpeed * Gdx.graphics.getDeltaTime(): 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.Q)) ? -moveSpeed * Gdx.graphics.getDeltaTime(): 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.D)) ? moveSpeed * Gdx.graphics.getDeltaTime(): 0;
        } else {
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.W)) ? moveSpeed * Gdx.graphics.getDeltaTime(): 0;
            direction.y += (Gdx.input.isKeyPressed(Input.Keys.S)) ? -moveSpeed * Gdx.graphics.getDeltaTime(): 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.A)) ? -moveSpeed * Gdx.graphics.getDeltaTime(): 0;
            direction.x += (Gdx.input.isKeyPressed(Input.Keys.D)) ? moveSpeed * Gdx.graphics.getDeltaTime(): 0;
        }
        this.setPositionVector(direction);
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

    // Set Method
    public void setPositionVector(Vector2 newPosition)
    {
        this.position = newPosition;
        this.sprite.setPosition(this.position.x, this.position.y);
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
}
