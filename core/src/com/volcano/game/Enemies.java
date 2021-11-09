package com.volcano.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game;
import com.mygdx.game.Player;

public abstract class Enemies {

    public Sprite sprite;
    public Vector2 position;
    public Rectangle hitbox;
    public float health = 100;

    private boolean isKnockback = false;

    Vector2 direction;
    Vector2 knockback;
    Vector2 playerPos;
    float moveSpeed;
    float knockbackTime;
    float knockbackForce;

    public Enemies(Texture t, float w, float h, float m)
    {
        this.setEnnemies(t, w, h, m);
    }

    private void setEnnemies(Texture t, float w, float h, float m)
    {
        this.sprite = new Sprite();
        this.sprite.setRegion(t);
        this.sprite.setBounds(0, 0, w, h);
        this.position = new Vector2(0, 0);
        this.direction = new Vector2();
        this.knockback = new Vector2();
        this.moveSpeed = m;
    }

    //Main functions
    public void followPlayer(Player player)
    {
        this.playerPos = player.position.cpy();

        if (this.position.y > playerPos.y + (player.getHeight() / 2))
            playerPos.y = playerPos.y + (player.getHeight() / 2);
        if (this.position.y > playerPos.y + (player.getHeight()))
            playerPos.y = playerPos.y + player.getHeight();
        if (this.position.x > playerPos.x + (player.getWidth() / 2))
            playerPos.x = playerPos.x + (player.getWidth() / 2);
        if (this.position.x > playerPos.x + player.getWidth())
            playerPos.x = playerPos.x + player.getWidth();
        this.direction.set(playerPos.x - this.position.x, playerPos.y - this.position.y);
        this.position.x += this.direction.x * this.moveSpeed * Game.deltaTime;
        this.position.y += this.direction.y * this.moveSpeed * Game.deltaTime;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void takeDamage(float dmg)
    {
        this.health -= dmg;
    }

    public void knockBack(float time)
    {
        if (this.knockbackTime >= time) {
            this.isKnockback = false;
            this.knockbackTime = 0.0f;
        }
        this.direction.set(playerPos.x - this.position.x, playerPos.y - this.position.y);
        this.position.x += -this.direction.x * this.knockbackForce * Game.deltaTime;
        this.position.y += -this.direction.y * this.knockbackForce * Game.deltaTime;
        this.sprite.setPosition(this.position.x, this.position.y);
        this.knockbackTime += Game.deltaTime;
    }

    //Set function
    public void setPosition(float x, float y)
    {
        this.position.set(x, y);
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setPosition(Vector2 position)
    {
        this.position.set(position.x, position.y);
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setKnockBack(boolean knockBack, float force)
    {
        this.knockbackForce = force;
        this.isKnockback = knockBack;
    }

    public void setKnockBackDirection(Vector2 dir)
    {
        this.knockback = dir;
    }

    //Get method
    public float getWidth()
    {
        return this.sprite.getWidth();
    }

    public float getHeight()
    {
        return this.sprite.getHeight();
    }

    public float getDirectionX()
    {
        return this.direction.x;
    }

    public float getDirectionY()
    {
        return this.direction.y;
    }

    public Vector2 getDirectionVector()
    {
        return this.direction;
    }

    public Vector2 getPosition()
    {
        return this.position.cpy();
    }

    public float getHealth()
    {
        return this.health;
    }

    public boolean getKnockBack()
    {
        return this.isKnockback;
    }

    public abstract void update(SpriteBatch batch, Player player);
}
