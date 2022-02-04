package com.volcano.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Game;
import sun.tools.java.Environment;

import java.util.HashMap;

public class Particles {

    static class Particle {

        float x;
        float y;
        Sprite sprite;
        float maxLifeTime;
        float w;
        float h;
        float lifeTime;

        float alphaFadeOut = 1f;

        Particle(float posX, float posY, Texture t, float maxLifeTime, float w, float h) {
            this.x = posX;
            this.y = posY;
            this.maxLifeTime = MathUtils.random(1f, maxLifeTime);
            //this.maxLifeTime = maxLifeTime;
            this.w = w;
            this.h = h;
            this.sprite = new Sprite();
            this.sprite.setBounds(posX, posY, w, h);
            this.sprite.setRegion(t);
        }

        Particle(Particle p, float x, float y)
        {
            this.x = x;
            this.y = y;
            this.sprite = p.sprite;
            this.maxLifeTime = p.maxLifeTime;
            this.w = p.w;
            this.h = p.h;
            this.lifeTime = p.lifeTime;
            this.alphaFadeOut = p.alphaFadeOut;
        }

        public void update(float directionX, float directionY, SpriteBatch batch)
        {
            this.lifeTime += Game.deltaTime;
            this.x += (directionX * 100f) * Game.deltaTime;
            this.y += (directionY * 100f) * Game.deltaTime;

            if (this.lifeTime >= 1f)
                this.alphaFadeOut -= (this.maxLifeTime / 2) * Game.deltaTime;
            this.sprite.setAlpha(this.alphaFadeOut);
            this.sprite.setPosition(x, y);
            this.sprite.setSize(w, h);
            this.sprite.draw(batch);
        }

        public boolean destroy()
        {
            return this.lifeTime >= this.maxLifeTime;
        }
    }

    SpriteBatch batch;

    boolean fadeOut = false;
    float lifeTime = -1;
    float width = -1;
    float height = -1;

    int howMany = 1;
    Rectangle rectEmitter;
    ShapeRenderer rectEmitterShape;
    Particle[] particles;
    Particle[] cpy;

    /*
        recreate particle value
     */
    Texture t;

    public Particles(Texture particleT, int howMany, float lifeTime, float w, float h, float posX, float posY) {
        this.particles = new Particle[howMany];
        this.cpy = new Particle[howMany];
        this.howMany = howMany;
        this.lifeTime = lifeTime;
        this.t = particleT;
        this.width = w;
        this.height = h;
        this.batch = new SpriteBatch();

        this.setParticles(howMany, particleT, lifeTime);
    }

    public Particles(String fileName, float posX, float posY)
    {
        String fileDataTmp = Utils.readFile(fileName, true);

        this.parseParticuleData(fileDataTmp);
        this.rectEmitter = new Rectangle();
        this.rectEmitter.setSize(50, 100);
        this.rectEmitter.setPosition(posX - this.rectEmitter.getWidth(), posY);
        this.particles = new Particle[this.howMany];
        this.cpy = new Particle[this.howMany];
        this.setParticles(this.howMany, this.t, this.lifeTime);
        this.batch = new SpriteBatch();
    }

    private void parseParticuleData(String fileDataTmp)
    {
        String[] fileData = fileDataTmp.split(System.lineSeparator());

        System.out.println(fileData[0]);
        for (int i = fileData.length - 1; i >= 0; i--) {
            if (fileData[i].contains("texture"))
                this.t = new Texture(fileData[i].replaceAll("texture=", ""));
            else if (fileData[i].contains("howmany"))
                this.howMany = Integer.parseInt(fileData[i].replaceAll("howmany=", ""));
            else if (fileData[i].contains("lifetime"))
                this.lifeTime = Float.parseFloat(fileData[i].replaceAll("lifetime=", ""));
            else if (fileData[i].contains("width"))
                this.width = Integer.parseInt(fileData[i].replaceAll("width=", ""));
            else if (fileData[i].contains("height"))
                this.height = Integer.parseInt(fileData[i].replaceAll("height=", ""));
            else if (fileData[i].contains("fadeout"))
                this.fadeOut = Boolean.parseBoolean(fileData[i].replaceAll("fadeout=", ""));
        }
    }

    private void setParticles(int howMany, Texture toSet, float lifeTime)
    {
        for (int i = 0; i != howMany; ) {
            float randX = MathUtils.random(this.rectEmitter.x, this.rectEmitter.x + this.rectEmitter.width);
            float randY = MathUtils.random(this.rectEmitter.y, this.rectEmitter.y + this.rectEmitter.height);

            this.particles[i] = new Particle(randX, randY, toSet, lifeTime, this.width, this.height);
            this.cpy[i++] = new Particle(randX, randY, toSet, lifeTime, this.width, this.height);
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

//                this.particles[i] = new Particle(randX, randY, this.t, this.lifeTime, this.width, this.height);
                this.particles[i] = new Particle(this.cpy[i], randX, randY);
            }
            this.particles[i].update(directionX, directionY, batch);
        }
        this.batch.end();
    }
}
