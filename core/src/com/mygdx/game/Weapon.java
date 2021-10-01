package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.Cursor;
import com.volcano.game.Weapons;

public class Weapon extends Weapons {

    double angle;

    public Weapon(Texture t, float w, float h, float x, float y) {
        super(t, w, h, x, y);
    }

    //Set method

    // Main method
    public void rotateAroundMouse(Cursor cursor)
    {
        Vector2 lookAt = cursor.getCursorPosition();
        lookAt.x = lookAt.x - cursor.sprite.getWidth();

        angle = lookAt.sub(this.position).angleDeg() - 45f;
        this.sprite.setRotation((float)angle);
    }

    public void update(SpriteBatch batch)
    {
        this.draw(batch);
    }

    public void draw(SpriteBatch batch)
    {
        this.sprite.draw(batch);
    }

    public void dispose()
    {

    }
}
