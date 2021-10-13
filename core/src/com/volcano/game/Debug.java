package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Debug {

    BitmapFont FPS;

    public Debug() {
        this.FPS = new BitmapFont();
    }

    public OrthographicCamera cameraZoom(OrthographicCamera camera)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            camera.zoom += 3f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            camera.zoom -= 3f;
        }
        return camera;
    }

    public void update(SpriteBatch batch)
    {
        this.FPS.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 50, 50);
    }
}
