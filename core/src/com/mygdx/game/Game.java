package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.volcano.game.Cursor;
import com.volcano.game.Room;
import com.volcano.game.Scene;

public class Game extends Scene {


    //PLAYER PART
    Sprite sprite;
    Player player;

    //SYSTEM PART
    SpriteBatch batch;
    OrthographicCamera camera;

    //DUNGEON PART
    Room room;

    public Game() {
        this.camera = this.setCamera(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.sprite = new Sprite();
		this.sprite.setBounds(500, 200, 70, 70);
		this.player = new Player(this.sprite, 50, 50);
		this.batch = new SpriteBatch();
		this.room = new Room();
    }

    public OrthographicCamera setCamera(boolean ortho, float viewPortW, float viewPortH)
    {
        OrthographicCamera camera = new OrthographicCamera();

        camera.setToOrtho(ortho, viewPortW, viewPortH);
        return camera;
    }

    public void handleCamera(Cursor cursor)
    {
        this.player.smoothCameraPlayerFollow(this.camera, 10f);
        this.camera.update();
    }

    //load everything
    public void load()
    {

    }

    public void play(Cursor cursor)
    {
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.room.update(this.batch);
        this.player.update(this.batch, cursor);
        this.camera.update();
        this.handleCamera(cursor);
        this.batch.end();
        //this.player.sword.showHitbox();
    }

    public void dispose()
    {
        this.batch.dispose();
    }
}
