package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Debug {

    BitmapFont FPS;

    public Debug() {
        this.FPS = new BitmapFont();
    }

    public void update(SpriteBatch batch)
    {
        this.FPS.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 50, 50);
    }
}
