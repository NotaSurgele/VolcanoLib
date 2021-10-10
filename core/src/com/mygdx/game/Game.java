package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.volcano.game.Cursor;
import com.volcano.game.MazeGenerator;
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
    Dungeon dj;
    MazeGenerator maze;

    public Game(float viewportWidth, float viewportHeight) {
        this.camera = this.setCamera(false, viewportWidth, viewportHeight);
        this.sprite = new Sprite();
		this.sprite.setBounds(500, 200, 70, 70);
		this.player = new Player(this.sprite, 50, 50);
		this.batch = new SpriteBatch();
		this.dj = new Dungeon();
		this.maze = new MazeGenerator(10, 11);
    }

    public OrthographicCamera setCamera(boolean ortho, float viewPortW, float viewPortH)
    {
        OrthographicCamera camera = new OrthographicCamera();

        camera.setToOrtho(ortho, viewPortW, viewPortH);
        return camera;
    }

    public void cameraHandler()
    {
        this.player.smoothCameraPlayerFollow(this.camera, 5f);
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
        this.dj.update(batch);
        this.player.update(this.batch, cursor);
        this.camera.update();
        this.maze.update();
        this.cameraHandler();
        this.batch.end();
        //this.player.sword.showHitbox();
    }

    public void dispose()
    {
        this.batch.dispose();
    }
}
