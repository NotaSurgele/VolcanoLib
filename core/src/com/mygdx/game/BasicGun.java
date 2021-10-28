package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.volcano.game.Cursor;
import com.volcano.game.Weapons;

import java.util.ArrayList;

public class BasicGun extends Weapons {

    ArrayList<Bullet> test;

    public BasicGun(Texture t, float w, float h, float x, float y) {
        super(t, w, h, x, y);
        this.test = new ArrayList<>();
    }

    public void update(SpriteBatch batch, Cursor cursor)
    {
        this.render(batch, cursor);
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Bullet b = new Bullet(new Texture("Weapons/Bullet.png"), 16, 16, cursor);
            this.test.add(b);
        }
    }

    public void render(SpriteBatch batch, Cursor cursor)
    {
        this.flipWeapon(cursor);
        for (int i = test.size(); i != 0; i--) {
            test.get(i - 1).update(test.get(i - 1), batch);
            if (test.get(i - 1).isDead) this.test.remove(i - 1);
        }
        this.sprite.draw(batch);
    }

    public void dispose()
    {
        this.sprite.getTexture().dispose();
    }
}
