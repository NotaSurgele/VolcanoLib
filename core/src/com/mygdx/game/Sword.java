package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.Cursor;
import com.volcano.game.Weapons;

public class Sword extends Weapons {

    float angle = 0f;
    float goTo = angle + 130;

    public boolean isSlashing = false;

    public Sword(Texture t, float w, float h, float x, float y) {
        super(t, w, h, x, y);
    }

    //Set method

    // Main method

    //Attack methods
    public void swordSlash()
    {
        angle = MathUtils.lerp(angle, goTo, 0.2f);
        if ((int)angle <= (int)goTo) {
            angle = this.sprite.getRotation() + 70f;
            goTo = angle - 130;
            this.isSlashing = false;
        }
        this.sprite.setRotation(angle);
    }

    public void update(SpriteBatch batch)
    {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            this.isSlashing = true;
        if (this.isSlashing)
            this.swordSlash();
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
