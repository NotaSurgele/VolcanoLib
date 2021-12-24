package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.Animator;

public class Props {

    Texture t;
    int value;
    int cols;
    int rows;

    float frameDuration;

    boolean isAnimate;
    Vector2 position;
    PropsType type;
    String name;

    float stateTime;

    Animator animation;

    public Props(String n, Texture t, int v, PropsType tp) {
        this.name = n;
        this.t = t;
        this.value = v;
        this.position = new Vector2();
        this.type = tp;
        this.isAnimate = false;
    }

    public Props(String n, Texture t, int v, PropsType tp, int COLS, int ROWS, float frameDuration) {
        this.name = n;
        this.t = t;
        this.value = v;
        this.position = new Vector2();
        this.type = tp;
        this.isAnimate = true;
        this.cols = COLS;
        this.rows = ROWS;
        this.frameDuration = frameDuration;
        this.createAnimation();
    }

    private void createAnimation()
    {
        this.animation = Animator.initializeAnimation(this.animation, this.t, this.cols, this.rows, this.frameDuration);
    }

    public int getValue()
    {
        return this.value;
    }

    public Texture getTexture()
    {
        return this.t;
    }

    public void animate(SpriteBatch batch, boolean loop, float x, float y, float w, float h)
    {
        this.stateTime += Game.deltaTime;

        this.animation.playAnimation(batch, stateTime, loop, x, y, w ,h);
    }

    public Vector2 getArrayPosition()
    {
        return this.position;
    }

    public String getName()
    {
        return this.name;
    }

    public Vector2 getWorldPosition()
    {
        return new Vector2(this.position.x * Dungeon.tileSize, this.position.y * Dungeon.tileSize);
    }

    public PropsType getType()
    {
        return this.type;
    }

    public void setPositionInArray(int x, int y)
    {
        this.position.set(x, y);
    }

    public boolean isAnimate()
    {
        return this.isAnimate;
    }
}
