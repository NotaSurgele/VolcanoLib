package com.volcano.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Weapons {

    public Sprite sprite;
    public Vector2 position;

    boolean rotateAround = false;

    public int weaponsID;

    public Weapons(Texture t, float w, float h, float x, float y) {
        this.setWeaponsData(t, w, h, x, y);
    }

    public Weapons() {}

    //Main method
    public void setWeaponsData(Texture t, float w, float h, float x, float y)
    {
        this.sprite = new Sprite();
        this.position = new Vector2(x, y);
        this.sprite.setBounds(x, y, w, h);
        this.sprite.setRegion(t);
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    //Get method
    public Vector2 getWeaponPosition()
    {
        return this.position;
    }

    public float getWeaponX()
    {
        return this.position.x;
    }

    public float getWeaponY()
    {
        return this.position.y;
    }

    public float getWeaponWidth()
    {
        return this.sprite.getWidth();
    }

    public float getWeaponHeight()
    {
        return this.sprite.getHeight();
    }

    public Vector2 getWeaponScale()
    {
        return new Vector2(this.sprite.getWidth(), this.sprite.getHeight());
    }

    public Sprite getSprite()
    {
        return this.sprite;
    }

    //Set method

    public void setWeaponPosition(Vector2 newPosition)
    {
        this.position = newPosition;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setWeaponX(float newX)
    {
        this.position.set(newX, this.position.y);
        this.position.x = newX;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setWeaponY(float newY)
    {
        this.position.set(this.position.x, newY);
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setWeaponScale(Vector2 newScale)
    {
        this.sprite.setScale(newScale.x, newScale.y);
    }

    public void setWeaponScale(float scale)
    {
        this.sprite.setScale(scale);
    }

    public void setWeaponScale(float newWidth, float newHeight)
    {
        this.sprite.setScale(newWidth, newHeight);
    }

    public void setSprite(Sprite newSprite)
    {
        this.sprite = newSprite;
    }

    //Main method

    public void lookAtCursor(Cursor cursor, float adjust)
    {
        Vector2 lookAt = cursor.getCursorPosition();
        lookAt.x = lookAt.x - cursor.sprite.getWidth();

        float angle = lookAt.sub(this.position).angleDeg() - adjust;
        this.sprite.setRotation(angle);
    }

    public void lookAtCursor(Cursor cursor)
    {
        Vector2 lookAt = cursor.getCursorPosition();
        lookAt.x = lookAt.x - cursor.sprite.getWidth();

        float angle = lookAt.sub(this.position).angleDeg();
        this.sprite.setRotation(angle);
    }
}
