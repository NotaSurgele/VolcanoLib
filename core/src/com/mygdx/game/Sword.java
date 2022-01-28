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
import com.volcano.game.Enemies;
import com.volcano.game.Weapons;

public class Sword extends Weapons {

    float angle = 0f;
    float goTo = angle + 130;
    public boolean isSlashing = false;
    public boolean isUltimate = false;

    final float weaponDamage = 10f;

    Polygon hitbox;
    ShapeRenderer hitboxRenderer;

    public Sword(Texture t, float w, float h, float x, float y, float f) {
        super(t, w, h, x, y, f);
        this.hitboxRenderer = new ShapeRenderer();
        this.hitboxRenderer.setAutoShapeType(true);
        this.hitbox = new Polygon(new float[] {0, 0, this.getWeaponWidth() - 10, 30, this.getWeaponWidth() - 10, this.getWeaponHeight(), 0, this.getWeaponHeight() - 50});
        this.setSpriteColor(.4f, .4f, .4f, 1f);
    }

    //Set method

    // Main method

    //Attack methods
    public void swordSlash()
    {
        this.angle = MathUtils.lerp(this.angle, this.goTo, 30f * Game.deltaTime);
        if ((int)this.angle <= (int)this.goTo) {
            this.isSlashing = false;
        }
        this.sprite.setRotation(this.angle);
        this.hitEnemies();
    }

    public void ultimate()
    {
        this.angle = MathUtils.lerp(this.angle, this.goTo, 20f * Game.deltaTime);
        if ((int) this.angle <= (int) this.goTo) {
            this.isUltimate = false;
        }
        this.sprite.setRotation(this.angle);
        this.hitEnemies();
    }

    public void hitEnemies()
    {
        for (Enemies e : MobSpawner.spawner) {
            if (this.hitbox.contains(e.getPosition().x, e.getPosition().y)) {
                if (!e.getKnockBack()) {
                    e.setKnockBackDirection(e.getDirectionVector().add(e.getPosition()));
                    e.setKnockBack(true, this.knockBackForce);
                    e.takeDamage(this.weaponDamage);
                }
            }
        }
    }

    public void setHitbox()
    {
        this.hitbox.setPosition(this.getWeaponX(), this.getWeaponY());
        this.hitbox.setOrigin(this.sprite.getOriginX(), this.sprite.getOriginY());
        this.hitbox.setRotation(this.sprite.getRotation());
    }

    public void showHitbox(SpriteBatch batch)
    {
        batch.end();
        this.hitboxRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.hitboxRenderer.setProjectionMatrix(Game.camera.combined);
        this.hitboxRenderer.polygon(this.hitbox.getTransformedVertices());
        this.hitboxRenderer.end();
        batch.begin();
    }

    private void checkSlashing()
    {
        if (this.isSlashing)
            this.swordSlash();
    }

    private void checkUltimate()
    {
        if (this.isUltimate)
            this.ultimate();
    }

    private void setSlashing()
    {
        if (!this.isUltimate && !this.isSlashing && Gdx.input.isButtonPressed(Control.ATTACK1)) {
            this.angle = this.sprite.getRotation() + 70f;
            this.goTo = this.angle - 130f;
            this.isSlashing = true;
        }
    }

    private void setUltimate()
    {
        if (!this.isSlashing && !this.isUltimate && Gdx.input.isButtonJustPressed(Control.ATTACK2)) {
            this.angle = this.sprite.getRotation();
            this.goTo = this.angle - 360f;
            this.isUltimate = true;
        }
    }

    public void update(SpriteBatch batch)
    {
        this.setSlashing();
        this.setUltimate();
        this.checkSlashing();
        this.checkUltimate();
        this.setHitbox();
        this.draw(batch);
    }

    public void dispose()
    {
        this.sprite.getTexture().dispose();
    }
}
