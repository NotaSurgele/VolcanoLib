package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemies {

    Sprite sprite;
    Vector2 position;

    Vector2 direction;

    float moveSpeed;

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
        this.moveSpeed = m;
    }

    //Main functions
    public void followPlayer(Player player)
    {
        Vector2 playerPos = player.position;
        this.direction.set(playerPos.x - this.position.x, playerPos.y - this.position.y);
        this.position.add(this.direction.x * this.moveSpeed * Game.deltaTime, this.direction.y * this.moveSpeed * Game.deltaTime);
        this.sprite.setPosition(this.position.x, this.position.y);
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


    //Get method
    public float getWidth()
    {
        return this.sprite.getWidth();
    }

    public float getHeight()
    {
        return this.sprite.getHeight();
    }
}
