package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Cursor {

    Texture texture;
    Vector2 position;
    public Sprite sprite;
    public Vector3 position3D;

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
        this.sprite.setBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), t.getWidth(), t.getHeight());
        this.setCursorCoordinate();
        this.position.x = this.sprite.getX();
        this.position.y = this.sprite.getY();
        this.position3D = new Vector3(this.position, 0);
    }

    public void setCursor(Texture t, float width, float height)
    {
        this.sprite = new Sprite();
        this.position = new Vector2();
        this.position3D = new Vector3(this.position, 0);
        this.sprite.setRegion(t);
        this.sprite.setBounds(0, 0, width, height);
        this.setCursorCoordinate();
        this.position.x = this.sprite.getX();
        this.position.y = this.sprite.getY();
    }

    public void setCursorSize(float newWidth, float newHeight)
    {
        this.sprite.setBounds(this.sprite.getX(), this.sprite.getY(), newWidth, newHeight);
    }

    public void setCursor(Texture t, float width, float height, float mouseSensivity)
    {
        this.sprite = new Sprite();
        this.sprite.setRegion(t);
        this.sprite.setBounds(0, 0, width, height);
        this.setCursorCoordinate(mouseSensivity);
    }

    public Cursor lockCursorOnCameraViewport(OrthographicCamera camera)
    {
        if (this.getCursorX() > (camera.position.x + camera.viewportWidth))
            this.setCustomPositionX(camera.position.x + camera.viewportWidth);
        if (this.getCursorX() < camera.position.x)
            this.setCustomPositionY(camera.position.y);
        if (this.getCursorY() > (camera.position.y + camera.viewportHeight))
            this.setCustomPositionY(camera.position.y + camera.viewportHeight);
        if (this.getCursorY() < camera.position.y)
            this.setCustomPositionY(camera.position.y);
        return this;
    }

    public void setCustomPosition(Vector2 toSet)
    {
        this.position = toSet;
    }

    public void setCustomPositionX(float toSetX)
    {
        this.position.x = toSetX;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setCustomPositionY(float toSetY)
    {
        this.position.y = toSetY;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    private void setCursorCoordinate(OrthographicCamera camera)
    {
        Vector3 mouseCoord = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        float x = mouseCoord.x - (this.sprite.getWidth() / 2);
        float y = mouseCoord.y - (this.sprite.getHeight() / 2);

        this.sprite.setPosition(x, y);
        this.position.x = this.sprite.getX();
        this.position.y = this.sprite.getY();
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

    public void update(SpriteBatch batch, OrthographicCamera camera)
    {
        this.setCursorCoordinate(camera);
        this.draw(batch);
    }

    public void update(SpriteBatch batch)
    {
        this.setCursorCoordinate();
        this.draw(batch);
    }

    public void dispose()
    {

    }
}
