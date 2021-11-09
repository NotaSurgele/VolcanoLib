package com.volcano.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Weapons {

    public Sprite sprite;
    public Vector2 position;
    public float knockBackForce;

    Texture inventoryShow;

    boolean rotateAround = false;

    public Weapons(Texture t, float w, float h, float x, float y, float f) {
        this.setWeaponsData(t, w, h, x, y, f);
    }

    public Weapons() {}

    //Main method
    public void setWeaponsData(Texture t, float w, float h, float x, float y, float f)
    {
        this.sprite = new Sprite();
        this.position = new Vector2(x, y);
        this.sprite.setBounds(x, y, w, h);
        this.sprite.setRegion(t);
        this.sprite.setPosition(this.position.x, this.position.y);
        this.inventoryShow = t;
        this.knockBackForce = f;
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

    public Texture getInventoryShow()
    {
        return this.inventoryShow;
    }

    public float getWeaponRotation()
    {
        return this.sprite.getRotation();
    }

    public float getWeaponOriginX()
    {
        return this.sprite.getOriginX();
    }

    public float getWeaponOriginY()
    {
        return this.sprite.getOriginY();
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
        lookAt.x = lookAt.x - (cursor.sprite.getWidth() / 2);

        float angle = lookAt.sub(this.position).angleDeg() - adjust;
        this.sprite.setRotation(angle);
    }

    public void lookAtCursor(Cursor cursor)
    {
        Vector2 lookAt = cursor.getCursorPosition();
        lookAt.x = lookAt.x - (cursor.sprite.getWidth() / 2);

        float angle = lookAt.sub(this.position).angleDeg();
        this.sprite.setRotation(angle);
    }

    public boolean isFlipX()
    {
        return this.sprite.isFlipX();
    }

    public boolean isFlipY()
    {
        return this.sprite.isFlipY();
    }

    public void flipWeapon(Cursor cursor)
    {
        float mouseX = cursor.getWorldCursorX();
        float positionX = this.sprite.getX();
        boolean isFlippedY = this.sprite.isFlipY();

        if (mouseX < positionX)
        {
            this.setWeaponY(this.getWeaponY() + (this.sprite.getHeight() / 2));
            if (!isFlippedY)
                this.sprite.flip(false, true);
        } else {
            if (isFlippedY) {
                this.sprite.flip(false, true);
            }
        }
    }
}
