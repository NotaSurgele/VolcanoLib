package com.volcano.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Game;

import java.util.HashMap;

public class Particles {

    private class Particle {
        float x;
        float y;
        Texture t;
        float maxLifeTime;
        float w;
        float h;
        float lifeTime;

        Particle(float posX, float posY, Texture t, float maxLifeTime, float w, float h) {
            this.x = posX;
            this.y = posY;
            this.t = t;
            this.maxLifeTime = maxLifeTime;
            this.w = w;
            this.h = h;
        }

        public void update(float directionX, float directionY, SpriteBatch batch)
        {
            this.lifeTime += Game.deltaTime;
            this.x += (directionX * 100f) * Game.deltaTime;
            this.y += (directionY * 100f) * Game.deltaTime;

            batch.draw(this.t, this.x, this.y, this.w, this.h);
        }

        public boolean destroy()
        {
            return this.lifeTime >= this.maxLifeTime;
        }
    }

    SpriteBatch batch;

    float lifeTime;
    float width;
    float height;

    int howMany;
    Rectangle rectEmitter;
    ShapeRenderer rectEmitterShape;
    Particle[] particles;

    /*
        recreate particle value
     */
    float x;
    float y;
    Texture t;

    public Particles(Texture particleT, int howMany, float lifeTime, float w, float h, float posX, float posY) {
        this.particles = new Particle[howMany];
        this.howMany = howMany;
        this.lifeTime = lifeTime;
        this.t = particleT;
        this.width = w;
        this.height = h;
        this.batch = new SpriteBatch();
        this.rectEmitter = new Rectangle();
        this.rectEmitter.setSize(100, 100);
        this.rectEmitter.setPosition(posX - this.rectEmitter.getWidth(), posY);
        this.setParticles(howMany, particleT, posX, posY, lifeTime);
    }

    private void setParticles(int howMany, Texture toSet, float x, float y, float lifeTime)
    {
        for (int i = 0; i != howMany; ) {
            float randX = MathUtils.random(this.rectEmitter.x, this.rectEmitter.x + this.rectEmitter.width);
            float randY = MathUtils.random(this.rectEmitter.y, this.rectEmitter.y + this.rectEmitter.height);

            this.particles[i++] = new Particle(randX, randY, toSet, lifeTime, 50, 50);
        }
    }

    public void draw(float directionX, float directionY, float x, float y, OrthographicCamera camera)
    {
        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();

        this.rectEmitter.setPosition(x, y);
        for (int i = 0; i != this.howMany; i++) {
            if (this.particles[i].destroy()) {
                float randX = MathUtils.random(this.rectEmitter.x, this.rectEmitter.x + this.rectEmitter.width);
                float randY = MathUtils.random(this.rectEmitter.y, this.rectEmitter.y + this.rectEmitter.height);

                this.particles[i] = new Particle(randX, randY, this.t, this.lifeTime, this.width, this.height);
            }
            this.particles[i].update(directionX, directionY, batch);
        }
        this.batch.end();
    }
}
