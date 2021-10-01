package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Cursor {

    Texture texture;
    Vector2 position;
    public Sprite sprite;

    public Cursor(Texture t, float width, float height) {
        this.setCursor(t, width, height);
    }

    public Cursor(Texture t) {
        this.setCursor(t);
    }

    public Cursor(Texture t, float width, float height, boolean hideDefaultCursor) {
        this.setCursor(t, width, height);
        this.hideDefaultCursor(hideDefaultCursor);
    }

    public Cursor(Texture t, boolean hideDefaultCursor) {
        this.setCursor(t);
        this.hideDefaultCursor(hideDefaultCursor);
    }

    // Set method

    public void setCursor(Texture t)
    {
        this.sprite = new Sprite();
        this.position = new Vector2();
        this.sprite.setRegion(t);
        this.sprite.setBounds(0, 0, t.getWidth(), t.getHeight());
        this.setCursorCoordinate();
        this.position.x = this.sprite.getX();
        this.position.y = this.sprite.getY();
    }

    public void setCursor(Texture t, float width, float height)
    {
        this.sprite = new Sprite();
        this.position = new Vector2();
        this.sprite.setRegion(t);
        this.sprite.setBounds(0, 0, width, height);
        this.setCursorCoordinate();
        this.position.x = this.sprite.getX();
        this.position.y = this.sprite.getY();
    }

    public void setCursor(Texture t, float width, float height, float mouseSensivity)
    {
        this.sprite = new Sprite();
        this.sprite.setRegion(t);
        this.sprite.setBounds(0, 0, width, height);
        this.setCursorCoordinate(mouseSensivity);
    }

    public void setCustomPosition(Vector2 toSet)
    {
        this.position = toSet;
    }

    public void setCustomPositionX(float toSetX)
    {
        this.position.x = toSetX;
    }

    public void setCustomPositionY(float toSetY)
    {
        this.position.y = toSetY;
    }

    private void setCursorCoordinate()
    {
        float x = Gdx.input.getX() - this.sprite.getWidth() / 2;
        float y = (Gdx.graphics.getHeight() - Gdx.input.getY()) - (this.sprite.getHeight() / 2);
        this.sprite.setPosition(x, y);
        this.position.x = this.sprite.getX();
        this.position.y = this.sprite.getY();
    }

    private void setCursorCoordinate(float mouseSensitivity)
    {
        float x = Gdx.input.getX() - this.sprite.getWidth() / 2;
        float y = (Gdx.graphics.getHeight() - Gdx.input.getY()) - (this.sprite.getHeight() / 2);

        this.sprite.setPosition(x * mouseSensitivity, y * mouseSensitivity);
        this.position.x = this.sprite.getX();
        this.position.y = this.sprite.getY();
    }

    // Get Method

    public float getCursorX()
    {
        return this.position.x;
    }

    public float getCursorY()
    {
        return this.position.y;
    }

    public Vector2 getCursorPosition()
    {
        return this.position;
    }

    // Main method

    public void hideDefaultCursor(boolean hide)
    {
        Gdx.input.setCursorCatched(hide);
    }

    public void draw(SpriteBatch batch)
    {
        this.sprite.draw(batch);
    }

    public void update(SpriteBatch batch)
    {
        this.setCursorCoordinate();
        this.draw(batch);
    }

    public void dispose()
    {
        this.texture.dispose();
    }
}
