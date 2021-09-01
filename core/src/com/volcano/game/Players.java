package com.volcano.game;

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
    }

    public void setPosition(float newX, float newY)
    {
        this.position.x = newX;
        this.position.y = newY;
    }

    public void setPositionX(float newX)
    {
        this.position.x = newX;
    }

    public void setPositionY(float newY)
    {
        this.position.y = newY;
    }
}
