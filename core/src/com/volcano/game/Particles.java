package com.volcano.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Game;
import jdk.nashorn.internal.objects.annotations.Constructor;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Particles {

    static class Particle {

        float x;
        float y;
        Sprite sprite;
        float maxLifeTime = 1f;
        float w = -1;
        float h = -1;
        float lifeTime;

        boolean fadeout = false;

        float alphaFadeOut = 1f;

        Particle(float posX, float posY, Texture t, float maxLifeTime, float w, float h, boolean fadeout) {
            this.x = posX;
            this.y = posY;
            //this.maxLifeTime = MathUtils.random(1f, maxLifeTime);
            this.maxLifeTime = maxLifeTime;
            this.w = w;
            this.h = h;
            this.sprite = new Sprite();
            this.sprite.setBounds(posX, posY, w, h);
            this.sprite.setRegion(t);
            this.fadeout = fadeout;
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
            this.fadeout = p.fadeout;
            this.alphaFadeOut = p.alphaFadeOut;
        }

        public void update(float directionX, float directionY, SpriteBatch batch)
        {
            this.lifeTime += Game.deltaTime;
            this.x += (directionX * 100f) * Game.deltaTime;
            this.y += (directionY * 100f) * Game.deltaTime;


            if (this.fadeout)
                if (this.lifeTime >= (this.maxLifeTime - 1f))
                    this.alphaFadeOut -= Game.deltaTime;
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
    boolean randomPosition = false;
    float lifeTime = 0f;
    float width = 0f;
    float height = 0f;
    int howMany = 1;
    float directionX = 0f;
    float directionY = 0f;
    float maxDelay = 0f;
    float delay = 0f;
    int delayed = 0;

    Rectangle rectEmitter;
    ShapeRenderer rectEmitterShape;
    Particle[] particles;
    Particle[] cpy;

    /*
        recreate particle value
     */
    Texture t;

    /*public Particles(Texture particleT, int howMany, float lifeTime, float w, float h, float posX, float posY) {
        this.particles = new Particle[howMany];
        this.cpy = new Particle[howMany];
        this.howMany = howMany;
        this.lifeTime = lifeTime;
        this.t = particleT;
        this.width = w;
        this.height = h;
        this.batch = new SpriteBatch();

        this.setParticles(howMany, particleT, lifeTime);
    }*/

    public Particles(String fileName, float posX, float posY)
    {
        String fileDataTmp = Utils.readFile(fileName, true);

        this.parseParticuleData(fileDataTmp);
        this.rectEmitter = new Rectangle();
        this.rectEmitter.setSize(20, 10);
        this.rectEmitter.setPosition(posX - this.rectEmitter.getWidth(), posY - this.rectEmitter.getHeight());
        this.particles = new Particle[this.howMany];
        this.cpy = new Particle[this.howMany];
        this.setParticles(this.howMany, this.t, this.lifeTime);
        this.batch = new SpriteBatch();
    }

    private void parseParticuleData(String fileDataTmp)
    {
        String[] fileData = fileDataTmp.split(System.lineSeparator());

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
            else if (fileData[i].contains("random-position"))
                this.randomPosition = Boolean.parseBoolean(fileData[i].replaceAll("random-position=", ""));
            else if (fileData[i].contains("directionX"))
                this.directionX = Float.parseFloat(fileData[i].replaceAll("directionX=", ""));
            else if (fileData[i].contains("directionY"))
                this.directionY = Float.parseFloat(fileData[i].replaceAll("directionY=", ""));
            else if (fileData[i].contains("delay"))
                this.maxDelay = Float.parseFloat(fileData[i].replaceAll("delay=", ""));
        }
        this.delayed = (this.maxDelay > 0) ? 1 : this.howMany;
    }

    private void setParticles(int howMany, Texture toSet, float lifeTime)
    {
        for (int i = 0; i != howMany; i++) {
            float randX = 0f;
            float randY = 0f;

            randX = (this.randomPosition) ? MathUtils.random(this.rectEmitter.x, this.rectEmitter.x + this.rectEmitter.width) : this.rectEmitter.x;
            randY = (this.randomPosition) ? MathUtils.random(this.rectEmitter.y, this.rectEmitter.y + this.rectEmitter.height) : this.rectEmitter.y;

            this.particles[i] = new Particle(randX, randY, toSet, lifeTime, this.width, this.height, this.fadeOut);
            this.cpy[i] = new Particle(randX, randY, toSet, lifeTime, this.width, this.height, this.fadeOut);
        }
    }

    private void addDelay()
    {
        if (this.maxDelay > 0)
            this.delay += Game.deltaTime;
    }

    private void resetDelayedEndSequences()
    {
        if (this.delayed == this.howMany - 1) {
            this.delayed = 0;
        }
    }

    private void checkDelayValue()
    {
        if (this.delay >= this.maxDelay) {
            if (this.delayed != this.howMany)
                this.delayed++;
            this.delay = 0f;
        }
    }

    public void draw(float x, float y, OrthographicCamera camera)
    {
        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();

        this.rectEmitter.setPosition(x, y);
        this.addDelay();
        for (int i = 0; i != this.delayed; i++) {
            if (this.particles[i].destroy()) {
                float randX = 0f;
                float randY = 0f;

                randX = (this.randomPosition) ? MathUtils.random(this.rectEmitter.x, this.rectEmitter.x + this.rectEmitter.width) : this.rectEmitter.x;
                randY = (this.randomPosition) ? MathUtils.random(this.rectEmitter.y, this.rectEmitter.y + this.rectEmitter.height) : this.rectEmitter.y;
                this.particles[i] = new Particle(this.cpy[i], randX, randY);
                this.resetDelayedEndSequences();
            }
            this.particles[i].update(this.directionX, this.directionY, this.batch);
        }
        this.checkDelayValue();
        this.batch.end();
    }
}
