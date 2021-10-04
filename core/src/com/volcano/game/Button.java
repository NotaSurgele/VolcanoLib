package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;
import com.volcano.game.Scene.SCENES;

public class Button {

    SpriteBatch batch;
    Sprite sprite;
    Texture texture;
    Texture oldTexture;
    Vector2 position;
    float width;
    float height;
    String name;
    State state;
    BitmapFont buttonName;

    float nameX;
    float nameY;

    public Button(Texture t, Vector2 p, float w, float h, String n) {
        this.texture = t;
        this.position = p;
        this.width = w;
        this.height = h;
        this.name = n;
        this.batch = new SpriteBatch();
        this.sprite = this.createSprite();
        this.oldTexture = t;
        this.buttonName = new BitmapFont();
        this.nameX = this.position.x + ((this.width / 2) / 2);
        this.nameY = this.position.y + ((this.height / 2) / 2);
    }

    public Button(Texture t, float x, float y, float w, float h, String n) {
        this.texture = t;
        this.position = new Vector2(x, y);
        this.width = w;
        this.height = h;
        this.name = n;
        this.batch = new SpriteBatch();
        this.sprite = this.createSprite();
        this.oldTexture = t;
        this.buttonName = new BitmapFont();
        this.nameX = this.position.x + ((this.width / 2) / 2);
        this.nameY = this.position.y + ((this.height / 2) / 2);
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

    private void drawSprite()
    {
        this.sprite.draw(this.batch);
    }

    private boolean isMouseInBox(Cursor cursor)
    {
        float x = cursor.getCursorX();
        float y = cursor.getCursorY();
        float posX = this.position.x;
        float posY = this.position.y;
        float width = this.width;
        float height = this.height;

        return (x >= posX && x <= (posX + width)) && (y >= posY && y <= (posY + height));
    }

    private void getButtonState(Cursor cursor)
    {
        boolean isInBox = this.isMouseInBox(cursor);

        if (isInBox) {
            this.state = (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) ? State.CLICK: State.HOVER;
        } else {
            this.state = State.NOTHING;
        }
    }

    // Get Method
    public Vector2 getButtonPosition()
    {
        return this.position;
    }

    public float getButtonPositionX()
    {
        return this.position.x;
    }

    public float getButtonPositionY()
    {
        return this.position.y;
    }

    public float getButtonWidth()
    {
        return this.width;
    }

    public float getButtonHeight()
    {
        return this.height;
    }

    public Vector2 getButtonScale()
    {
        return new Vector2(this.width, this.height);
    }

    public Sprite getButtonSprite()
    {
        return this.sprite;
    }

    public Texture getButtonTexture()
    {
        return this.texture;
    }

    public TextureRegion getButtonTextureRegion()
    {
        return new TextureRegion(this.texture);
    }

    public State getButtonCurrentState()
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

    public static State getButtonCurrentState(@NotNull Button button)
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

    public void setNewScale(Vector2 newScale)
    {
        this.sprite.setBounds(this.position.x, this.position.y, newScale.x, newScale.y);
    }

    public void setForcedState(State state)
    {
        this.state = state;
    }

    public void onHoverSetNewTexture(Texture newTexture)
    {
        if (this.state == State.HOVER) {
            this.setNewTexture(newTexture);
        } else if (this.state == State.NOTHING) {
            this.setNewTexture(this.oldTexture);
        }
    }

    public void onHoverSetNewScale(Vector2 newScale)
    {
        if (this.state == State.HOVER)
            this.setNewScale(newScale);
    }

    public void onHoverSetNewScale(float newWidth, float newHeight)
    {
        if (this.state == State.HOVER)
            this.setNewScale(newWidth, newHeight);
    }

    public void onClickChangeScene(Scene currentScene, SCENES changeTo)
    {
        if (this.state == State.CLICK)
            currentScene.setScenes(changeTo);
    }

    //Boolean method
    public boolean isClicked()
    {
        return (this.state == State.CLICK);
    }

    public boolean isHovered()
    {
        return (this.state == State.HOVER);
    }

    public boolean isNothing()
    {
        return (this.state == State.NOTHING);
    }

    //Main method
    private void update(Cursor cursor)
    {
        this.getButtonState(cursor);
    }

    public void render(Cursor cursor)
    {
        this.update(cursor);
        this.batch.begin();
        this.drawSprite();
        this.buttonName.draw(this.batch, this.name, this.nameX, this.nameY);
        this.batch.end();
    }

    public void dispose()
    {
        this.texture.dispose();
        this.batch.dispose();
    }
}
