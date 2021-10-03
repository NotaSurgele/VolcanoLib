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
    public boolean isUltimate = false;

    public Sword(Texture t, float w, float h, float x, float y) {
        super(t, w, h, x, y);
    }

    //Set method

    // Main method

    //Attack methods
    public void swordSlash()
    {
        this.angle = MathUtils.lerp(this.angle, this.goTo, 0.3f);
        if ((int)this.angle <= (int)this.goTo) {
            this.isSlashing = false;
        }
        this.sprite.setRotation(this.angle);
    }

    public void ultimate()
    {
        this.angle = MathUtils.lerp(this.angle, this.goTo, 0.2f);
        if ((int) this.angle <= (int) this.goTo) {
            this.isUltimate = false;
        }
        this.sprite.setRotation(this.angle);
    }

    public void update(SpriteBatch batch)
    {
        if (!this.isUltimate && !this.isSlashing && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            this.angle = this.sprite.getRotation() + 70f;
            this.goTo = this.angle - 130f;
            this.isSlashing = true;
        }
        if (this.isSlashing)
            this.swordSlash();
        if (!this.isSlashing && !this.isUltimate && Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            this.angle = this.sprite.getRotation();
            this.goTo = this.angle - 360f;
            this.isUltimate = true;
        }
        if (this.isUltimate)
            this.ultimate();
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
