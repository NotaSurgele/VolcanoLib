package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.volcano.game.Cursor;
import com.volcano.game.Weapons;

public class BasicGun extends Weapons {

    float originY;

    public BasicGun(Texture t, float w, float h, float x, float y) {
        super(t, w, h, x, y);
        this.originY = this.sprite.getOriginY() + 0.5f;
    }

    public void flipWeaponDraw(Cursor cursor)
    {
        float mouseX = cursor.getCursorX();
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

    public void update(SpriteBatch batch, Cursor cursor)
    {
        this.render(batch, cursor);
    }

    public void render(SpriteBatch batch, Cursor cursor)
    {
        this.flipWeaponDraw(cursor);
        this.sprite.draw(batch);
    }

    public void dispose()
    {
        this.sprite.getTexture().dispose();
    }
}
