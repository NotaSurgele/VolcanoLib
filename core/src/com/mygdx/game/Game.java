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
    MobSpawner mobSpawner;

    TriggerUI triggerUI;

    //DUNGEON PART
    Dungeon dj;

    CutScene cutScene;

    private boolean isLoaded = false;

    //UTILS
    public static float slowTime = 0.2f;

    enum State {
        SPAWNING,
        CHANGE,
        NOTHING
    }

    public static State STATE;

    public Game(float viewportWidth, float viewportHeight) {
        camera = this.setCamera(false, viewportWidth, viewportHeight);
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
        if (this.isLoaded) return;
        this.sprite = new Sprite();
        this.sprite.setBounds(500, 200, 70, 70);
        this.player = new Player(this.sprite, 70, 70);
        this.batch = new SpriteBatch();
        this.dj = new Dungeon();
        this.triggerUI = new TriggerUI(new Texture("ui (new)/keyboard_input.png"), 24, 24, "F", "Press F to use !", 0.8f);
        this.debug = new Debug();
        this.player.spawnPoint(this.dj.getChoosenRoomSpawnPoint(0));
        this.mobSpawner = new MobSpawner(this.dj);
        Game.STATE = State.SPAWNING;
        camera.position.set(new Vector3(this.player.getPositionVector(), 0));
        this.cutScene = new CutScene();
        this.isLoaded = true;
    }

    public static void setState(State newState)
    {
        STATE = newState;
    }

    public void checkCutScene()
    {
        if (STATE == State.SPAWNING)
            this.cutScene.spawning(this.player.getPositionY() - 500f, this.player.getPositionY(), 5f);
    }

    public void play(Cursor cursor)
    {
        if (!this.isLoaded) return;
        this.checkCutScene();
        Game.deltaTime = Gdx.graphics.getDeltaTime();

        if (Inventory.inventoryIsOpen)
            Game.deltaTime *= Game.slowTime;
        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();
        this.debug.cameraZoom(camera);
        this.dj.update(this.batch, this.player, this.triggerUI);
        this.mobSpawner.getDungeon(dj);
        this.mobSpawner.update(this.batch, this.player);
        if (STATE == State.NOTHING)
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
