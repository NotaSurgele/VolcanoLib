package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Cursor;
import com.volcano.game.LayerData;
import com.volcano.game.Weapons;

import java.util.ArrayList;

public class BasicGun extends Weapons {

    ArrayList<Bullet> bulletLoader;

    public BasicGun(Texture t, float w, float h, float x, float y, float f) {
        super(t, w, h, x, y, f);
        this.bulletLoader = new ArrayList<>();
    }

    public void update(SpriteBatch batch, Cursor cursor, LayerData layerData)
    {
        this.render(batch, cursor, layerData);
        if (Gdx.input.isButtonPressed(Control.ATTACK1)) {

            Bullet b = new Bullet(new Texture("Weapons/Bullet.png"), 16, 16, cursor) {
                @Override
                public void update(SpriteBatch batch, Player player, float x, float y) {}
            };
            this.bulletLoader.add(b);
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
        this.draw(batch);
    }

    public void dispose()
    {
        this.sprite.getTexture().dispose();
    }
}
