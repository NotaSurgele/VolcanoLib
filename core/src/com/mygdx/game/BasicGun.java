package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Cursor;
import com.volcano.game.LayerData;
import com.volcano.game.Weapons;

import java.util.ArrayList;

public class BasicGun extends Weapons {

    ArrayList<Bullet> bulletLoader;

    public BasicGun(Texture t, float w, float h, float x, float y) {
        super(t, w, h, x, y);
        this.bulletLoader = new ArrayList<>();
    }

    public void update(SpriteBatch batch, Cursor cursor, LayerData layerData)
    {
        this.render(batch, cursor, layerData);
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Bullet b = new Bullet(new Texture("Weapons/Bullet.png"), 16, 16, cursor);
            this.bulletLoader.add(b);
            b = null;
        }
    }

    public void render(SpriteBatch batch, Cursor cursor, LayerData layerData)
    {
        this.flipWeapon(cursor);
        for (int i = this.bulletLoader.size(); i != 0; i--) {
            Bullet b = this.bulletLoader.get(i - 1);
            b.update(batch, layerData);
            if (b.isDead)
                this.bulletLoader.remove(i - 1);
        }
        this.sprite.draw(batch);
    }

    public void dispose()
    {
        this.sprite.getTexture().dispose();
    }
}
