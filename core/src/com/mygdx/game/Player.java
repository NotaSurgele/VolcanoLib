package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.Players;

public class Player extends Players {

    public Player(Sprite sprite, float x, float y) {
        super(sprite, x, y);
    }

    public Player(Sprite sprite, Vector2 position) {
        super(sprite, position);
    }

    public void update(SpriteBatch batch)
    {
        this.Move(false, 200f);
        this.sprite.draw(batch);
    }
}
