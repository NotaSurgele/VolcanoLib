package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.Cursor;
import com.volcano.game.Weapons;

public class Sword extends Weapons {

    float angle = 0f;
    float goTo = angle + 130;

    public boolean isSlashing = false;
    public boolean isUltimate = false;

    Polygon hitbox;
    ShapeRenderer hitboxRenderer;

    public Sword(Texture t, float w, float h, float x, float y) {
        super(t, w, h, x, y);
        this.hitboxRenderer = new ShapeRenderer();
        this.hitboxRenderer.setAutoShapeType(true);
        this.hitbox = new Polygon(new float[] {0, 0, this.getWeaponWidth() - 10, 30, this.getWeaponWidth() - 10, this.getWeaponHeight(), 0, this.getWeaponHeight() - 50});
    }

    //Set method

    // Main method

    //Attack methods
    public void swordSlash()
    {
        this.angle = MathUtils.lerp(this.angle, this.goTo, 30f * Gdx.graphics.getDeltaTime());
        if ((int)this.angle <= (int)this.goTo) {
            this.isSlashing = false;
        }
        this.sprite.setRotation(this.angle);
    }

    public void ultimate()
    {
        this.angle = MathUtils.lerp(this.angle, this.goTo, 20f * Gdx.graphics.getDeltaTime());
        if ((int) this.angle <= (int) this.goTo) {
            this.isUltimate = false;
        }
        this.sprite.setRotation(this.angle);
    }

    public void setHitbox()
    {
        this.hitbox.setPosition(this.getWeaponX(), this.getWeaponY());
        this.hitbox.setOrigin(this.sprite.getOriginX(), this.sprite.getOriginY());
        this.hitbox.setRotation(this.sprite.getRotation());
    }

    public void showHitbox()
    {
        this.hitboxRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.hitboxRenderer.polygon(this.hitbox.getTransformedVertices());
        this.hitboxRenderer.end();
    }

    public void update(SpriteBatch batch)
    {
        if (!this.isUltimate && !this.isSlashing && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
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
        this.setHitbox();
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
