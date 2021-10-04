package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Cursor;
import com.volcano.game.Room;
import com.volcano.game.Scene;

public class Game extends Scene {

    Sprite sprite;
    Player player;
    SpriteBatch batch;
    Room room;

    public Game() {
        this.sprite = new Sprite();
		this.sprite.setBounds(500, 200, 70, 70);
		this.player = new Player(this.sprite, 50, 50);
		this.batch = new SpriteBatch();
		room = new Room();
    }

    //load everything
    public void load()
    {

    }

    public void play(Cursor cursor)
    {
        this.batch.begin();
        this.room.update(this.batch);
        this.player.update(this.batch, cursor);
        this.batch.end();
        //this.player.sword.showHitbox();
    }

    public void dispose()
    {
        this.batch.dispose();
    }
}
