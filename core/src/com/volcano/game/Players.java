package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Control;
import com.mygdx.game.Dungeon;

public class Players {

    public Sprite sprite;
    public Vector2 position;
    public Collision collider;
    Vector3 cameraPosition;

    public Players(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        this.position = new Vector2(x, y);
        this.collider = new Collision();
    }

    public Players(Sprite sprite, Vector2 position) {
        this.sprite = sprite;
        this.position = new Vector2(position);
        this.collider = new Collision();
    }

    //Main method

    public void Move(boolean qwert, float moveSpeed, float deltaTime)
    {
        Vector2 direction = new Vector2(this.position);

        direction.y += (Gdx.input.isKeyPressed(Control.FORWARD)) ? moveSpeed * deltaTime: 0;
        direction.y += (Gdx.input.isKeyPressed(Control.BACKWARD)) ? -moveSpeed * deltaTime: 0;
        direction.x += (Gdx.input.isKeyPressed(Control.LEFT)) ? -moveSpeed * deltaTime: 0;
        direction.x += (Gdx.input.isKeyPressed(Control.RIGHT)) ? moveSpeed * deltaTime: 0;
        this.setPositionVector(direction);
    }

    public void Move(float moveSpeed, float runningSpeed, float deltaTime)
    {
        Vector2 direction = new Vector2(this.position);
        boolean isRunning = this.isRunning();

        moveSpeed = isRunning ? moveSpeed += runningSpeed : moveSpeed;
        direction.y += (Gdx.input.isKeyPressed(Control.FORWARD)) ? moveSpeed * deltaTime: 0;
        direction.y += (Gdx.input.isKeyPressed(Control.BACKWARD)) ? -moveSpeed * deltaTime: 0;
        direction.x += (Gdx.input.isKeyPressed(Control.LEFT)) ? -moveSpeed * deltaTime: 0;
        direction.x += (Gdx.input.isKeyPressed(Control.RIGHT)) ? moveSpeed * deltaTime: 0;
        this.setPositionVector(direction);
    }

    public void cameraFollowPlayer(OrthographicCamera camera)
    {
        Vector2 cameraPosition = new Vector2(this.position.x + (this.getWidth() / 2), this.position.y + (this.getHeight() / 2));
        camera.position.set(cameraPosition, 0);
    }

    public void smoothCameraPlayerFollow(OrthographicCamera camera, float smoothness)
    {
        Vector3 playerPosition = new Vector3(this.sprite.getX(), this.sprite.getY(), 0);
        this.cameraPosition = new Vector3(camera.position);

        this.cameraPosition.slerp(playerPosition, smoothness * Gdx.graphics.getDeltaTime());
        camera.position.set(this.cameraPosition);
    }

    public void smoothCameraPlayerFollow(OrthographicCamera camera, float smoothness, boolean shouldFollow)
    {
        if (shouldFollow) {
            Vector3 playerPosition = new Vector3(this.sprite.getX(), this.sprite.getY(), 0);
            this.cameraPosition = new Vector3(camera.position);

            this.cameraPosition.slerp(playerPosition, smoothness * Gdx.graphics.getDeltaTime());
            camera.position.set(this.cameraPosition);
        }
    }

    public void draw(SpriteBatch batch)
    {
        this.sprite.draw(batch);
    }

    // Get Method
    public Vector2 getPositionVector()
    {
        return this.position;
    }

    public float getPositionX()
    {
        return this.position.x;
    }

    public float getPositionY()
    {
        return this.position.y;
    }

    public float getWidth()
    {
        return this.sprite.getWidth();
    }

    public float getHeight()
    {
        return this.sprite.getHeight();
    }

    public Vector2 getPlayerPositionInLayer()
    {
        int x = (int)(this.position.x + this.getWidth() / 2) / Dungeon.tileSize;
        int y = (int)(this.position.y + this.getHeight() / 2) / Dungeon.tileSize;

        return new Vector2(x, y);
    }

    public Sprite getSprite()
    {
        return this.sprite;
    }

    // Set Method
    public void setPositionVector(Vector2 newPosition)
    {
        this.position = newPosition;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public void setPosition(float newX, float newY)
    {
        this.position.x = newX;
        this.position.y = newY;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setPositionX(float newX)
    {
        this.position.x = newX;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setPositionY(float newY)
    {
        this.position.y = newY;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void spawnPoint(Vector2 point)
    {
        this.setPositionVector(point);
    }

    // Boolean method
    public boolean isMoving()
    {
        return Gdx.input.isKeyPressed(Control.FORWARD) || Gdx.input.isKeyPressed(Control.LEFT) ||
                Gdx.input.isKeyPressed(Control.BACKWARD) || Gdx.input.isKeyPressed(Control.RIGHT);
    }

    public boolean isRunning()
    {
        boolean isMoving = this.isMoving();

        if (isMoving) {
            return (Gdx.input.isKeyPressed(Control.RUN));
        }
        return false;
    }

    public boolean isFlipX()
    {
        return this.sprite.isFlipX();
    }

    public boolean isFlipY()
    {
        return this.sprite.isFlipY();
    }
}
