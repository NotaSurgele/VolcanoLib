package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Cursor;
import com.volcano.game.Scene;

public class Game extends Scene {

    Sprite sprite;
    Player player;
    SpriteBatch batch;

    public Game() {
        this.sprite = new Sprite();
		this.sprite.setBounds(500, 200, 70, 70);
		this.player = new Player(sprite, 50, 50);
		this.batch = new SpriteBatch();
    }

    public void play(Cursor cursor)
    {
        this.batch.begin();
        this.player.update(this.batch, cursor);
        this.batch.end();
    }

    public void dispose()
    {
        this.batch.dispose();
    }
}
