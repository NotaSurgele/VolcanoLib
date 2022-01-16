package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.*;
import org.graalvm.compiler.loop.MathUtil;

import static java.lang.Math.sqrt;

public abstract class Bullet {

    Sprite sprite;
    Vector2 position;
    Vector2 direction;
    Vector2 origin;
    Light bulletLight;

    float bulletSpeed = 1000f;

    float lifeTime;
    float maxLifeTime = 2.0f;
    boolean isDead = false;

    public Bullet(Texture t, float w, float h, Cursor c, Light l) {
        this.setBullet(t, w, h, c);
        this.bulletLight = l;
    }

    public Bullet(Texture t, float w, float h, float x, float y, Light l)
    {
        this.setBullet(t, w, h, x, y);
        this.bulletLight = l;
    }

    private void setBullet(Texture t, float w, float h, Cursor c)
    {
        this.sprite = new Sprite();
        this.sprite.setRegion(t);
        this.sprite.setBounds(0, 0, w, h);
        this.position = new Vector2(this.sprite.getX(), this.sprite.getY());
        this.direction = new Vector2();
        this.origin = new Vector2(this.sprite.getOriginX(), this.sprite.getOriginY());
        this.setBulletPosition();
        this.bulletMoving(c);
    }

    private void setBullet(Texture t, float w, float h, float x, float y)
    {
        this.sprite = new Sprite();
        this.sprite.setRegion(t);
        this.sprite.setBounds(x, y, w, h);
        this.position = new Vector2(this.sprite.getX(), this.sprite.getY());
        this.direction = new Vector2();
        this.origin = new Vector2(this.sprite.getOriginX(), this.sprite.getOriginY());
    }

    public void destroyBullet(LayerData layerData)
    {
        this.destroyOnWalls(layerData);
        if (this.lifeTime >= maxLifeTime) {
            this.isDead = true;
        }
    }

    public void destroyOnWalls(LayerData layerData)
    {
        int x1 = (int)(this.position.x - Dungeon.tileSize) / Dungeon.tileSize;
        int y1 = (int)(this.position.y - Dungeon.tileSize) / Dungeon.tileSize;

        int x2 = (int)(this.position.x + Dungeon.tileSize) / Dungeon.tileSize;
        int y2 = (int)(this.position.y + Dungeon.tileSize) / Dungeon.tileSize;

        if (layerData.layer[y1][x1] < 0 || layerData.layer[y2][x2] < 0)
            this.isDead = true;
    }

    public void setBulletPosition()
    {
        Weapons w = Inventory.getCurrentWeapon();
        Vector2 wPosition = w.position;
        float weaponWidth = w.getWeaponWidth();
        float weaponHeight = w.getWeaponHeight();

        this.position.x = wPosition.x + weaponWidth - 16f;
        this.position.y = wPosition.y + (weaponHeight / 2);
        if (w.isFlipY())
            this.position.set(this.position.x, this.position.y - 16f);
        Vector2 endPos = position.cpy().sub(wPosition.x, wPosition.y).rotateDeg(w.getWeaponRotation()).add(wPosition.x, wPosition.y);
        this.position = endPos;
        this.sprite.setPosition(endPos.x, endPos.y);
        this.sprite.setRotation(w.getWeaponRotation());
    }

    public void bulletMoving(Cursor cursor)
    {
        this.direction.set(this.position.x - cursor.getCursorX(), this.position.y - cursor.getCursorY()).setAngleDeg(this.sprite.getRotation());
    }

    public void update(SpriteBatch batch, LayerData layerData)
    {
        this.lifeTime += Game.deltaTime;

        this.destroyBullet(layerData);
        float hyp = (float)Math.sqrt(this.direction.x * this.direction.x + this.direction.y * this.direction.y);
        this.direction.x /= hyp;
        this.direction.y /= hyp;
        this.position.x += this.direction.x * this.bulletSpeed * Game.deltaTime;
        this.position.y += this.direction.y * this.bulletSpeed * Game.deltaTime;
        this.sprite.setPosition(this.position.x, this.position.y);
        this.bulletLight.update(this.position.x, this.position.y, Game.camera);
        this.sprite.draw(batch);
    }

    public abstract void update(SpriteBatch batch, Player player, float x, float y);
}
