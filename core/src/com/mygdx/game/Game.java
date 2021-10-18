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
    OrthographicCamera camera;
    Debug debug;

    TriggerUI triggerUI;

    //DUNGEON PART
    Dungeon dj;

    public Game(float viewportWidth, float viewportHeight) {
        this.camera = this.setCamera(false, viewportWidth, viewportHeight);
        this.sprite = new Sprite();
		this.sprite.setBounds(500, 200, 70, 70);
		this.player = new Player(this.sprite, 70, 70);
		this.batch = new SpriteBatch();
		this.dj = new Dungeon();
        this.triggerUI = new TriggerUI(new Texture("ui (new)/keyboard_input.png"), 50, 50, "F", "Press F to use !");
        this.debug = new Debug();
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

    public void checkStairs()
    {
        float TriggerUIX = this.player.getPositionX() + this.player.getWidth() + 10f;
        float TriggerUIY = this.player.getPositionY() + (this.player.getHeight() / 2);
        if (this.dj.stairsCheck(
                 (int)(this.player.getPositionY() + (this.player.getHeight() / 2)) / 50,
                (int)(this.player.getPositionX() + (this.player.getWidth() / 2)) / 50,
                      this.dj.map
            )
        )
            this.triggerUI.draw(batch, TriggerUIX, TriggerUIY);

    }

    public void play(Cursor cursor)
    {
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.dj.update(batch);
        this.debug.cameraZoom(this.camera);
        this.player.update(this.batch, cursor);
        this.cameraHandler();
        this.checkStairs();
        this.batch.end();
        //this.player.sword.showHitbox();
    }

    public void dispose()
    {
        this.player.dispose();
        this.batch.dispose();
    }
}
