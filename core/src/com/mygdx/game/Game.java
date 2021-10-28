package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.volcano.game.*;

public class Game extends Scene {


    //PLAYER PART
    Sprite sprite;
    Player player;

    //SYSTEM PART
    SpriteBatch batch;
    public static OrthographicCamera camera;
    public static float deltaTime;
    Debug debug;

    TriggerUI triggerUI;

    //DUNGEON PART
    Dungeon dj;

    //UTILS
    public static float slowTime = 0.2f;

    public Game(float viewportWidth, float viewportHeight) {
        camera = this.setCamera(false, viewportWidth, viewportHeight);
        this.sprite = new Sprite();
		this.sprite.setBounds(500, 200, 70, 70);
		this.player = new Player(this.sprite, 70, 70);
		this.batch = new SpriteBatch();
		this.dj = new Dungeon();
        this.triggerUI = new TriggerUI(new Texture("ui (new)/keyboard_input.png"), 24, 24, "F", "Press F to use !", 0.8f);
        this.debug = new Debug();
        this.player.spawnPoint(this.dj.getChoosenRoomSpawnPoint(0));
    }

    public OrthographicCamera setCamera(boolean ortho, float viewPortW, float viewPortH)
    {
        OrthographicCamera camera = new OrthographicCamera();

        camera.setToOrtho(ortho, viewPortW, viewPortH);
        return camera;
    }

    public void cameraHandler()
    {
        this.player.smoothCameraPlayerFollow(camera, 5f);
        camera.update();
    }

    //load everything
    public void load()
    {

    }

    public void play(Cursor cursor)
    {
        Game.deltaTime = Gdx.graphics.getDeltaTime();

        if (Inventory.inventoryIsOpen)
            Game.deltaTime *= Game.slowTime;
        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();
        this.debug.cameraZoom(camera);
        this.dj.update(this.batch, this.player, this.triggerUI);
        this.player.update(this.batch, Game.deltaTime, cursor, this.dj.layerData);
        this.cameraHandler();
        this.batch.end();
    }

    public void dispose()
    {
        this.player.dispose();
        this.dj.dispose();
        this.debug.dispose();
        this.batch.dispose();
    }
}
