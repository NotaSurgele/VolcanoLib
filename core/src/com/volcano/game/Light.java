package com.volcano.game;

import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Light {

    SpriteBatch batch;
    Texture lightShader;
    float w;
    float h;

    public Light(Texture t, float r, float g, float b, float a, float w, float h) {
        this.lightShader = t;
        this.batch = new SpriteBatch();
        this.batch.setColor(r, g, b, a);
        this.w = w;
        this.h = h;
    }

    public void update(float x, float y, OrthographicCamera camera)
    {
        float fixedX = x - (this.w / 2);
        float fixedY = y - (this.h / 2);

        this.batch.enableBlending();
        this.batch.setBlendFunction(GL30.GL_DST_COLOR, GL30.GL_SRC_ALPHA);
        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();
        this.batch.draw(this.lightShader, fixedX, fixedY, w, h);
        this.batch.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        this.batch.end();
    }
}
