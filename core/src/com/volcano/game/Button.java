package com.volcano.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

public class Button {

    SpriteBatch batch;
    Sprite sprite;
    Texture texture;
    Vector2 position;
    float width;
    float height;
    String name;
    State state;

    public Button(Texture t, Vector2 p, float w, float h, String n) {
        this.texture = t;
        this.position = p;
        this.width = w;
        this.height = h;
        this.name = n;
        this.batch = new SpriteBatch();
        this.sprite = this.createSprite();
    }

    enum State {
        CLICK,
        HOVER,
        NOTHING
    }

    //Private method

    private Sprite createSprite()
    {
        Sprite sprite = new Sprite();

        sprite.setRegion(this.texture);
        sprite.setBounds(this.position.x, this.position.y, this.width, this.height);
        return sprite;
    }


    // Get Method
    public Vector2 getButtonPosition()
    {
        return this.position;
    }

    public float getButtonWidth()
    {
        return this.width;
    }

    public float getButtonHeight()
    {
        return this.height;
    }

    public Sprite getButtonSprite()
    {
        return this.sprite;
    }

    public Texture getButtonTexture()
    {
        return this.texture;
    }

    public State getButtonState()
    {
        return this.state;
    }


    //Get static method
    public static Vector2 getButtonPosition(@NotNull Button button)
    {
        return button.position;
    }

    public static float getButtonWidth(@NotNull Button button)
    {
        return button.width;
    }

    public static float getButtonHeight(@NotNull Button button)
    {
        return button.height;
    }

    public static Sprite getButtonSprite(@NotNull Button button)
    {
        return button.sprite;
    }

    public static Texture getButtonTexture(@NotNull Button button)
    {
        return button.texture;
    }

    public static State getButtonState(@NotNull Button button)
    {
        return button.state;
    }


//  Set Method

    public void setNewSprite(Sprite newSprite)
    {
        this.sprite = newSprite;
    }

    public void setNewTexture(Texture newTexture)
    {
        this.texture = newTexture;
        this.sprite.setTexture(this.texture);
        this.sprite.setRegion(this.texture);
    }

    public void setNewPosition(Vector2 newPosition)
    {
        this.position = newPosition;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setNewPosition(float x, float y)
    {
        this.position.x = x;
        this.position.y = y;
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public void setNewName(String newName)
    {
        this.name = newName;
    }

    public void setNewScale(float newWidth, float newHeight)
    {
        this.sprite.setBounds(this.position.x, this.position.y, newWidth, newHeight);
    }

    //Boolean method


    //Main method

    public void draw()
    {
        this.sprite.draw(this.batch);
    }

    public void update()
    {
        this.render();
    }

    public void render()
    {
        this.batch.begin();
        this.render();
        this.batch.end();
    }

    public void dipose()
    {
        this.texture.dispose();
        this.batch.dispose();
    }
}
