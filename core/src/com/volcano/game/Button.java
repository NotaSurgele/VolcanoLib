package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

import static com.badlogic.gdx.Gdx.input;

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

    private void drawSprite()
    {
        this.sprite.draw(this.batch);
    }

    private boolean isInBox()
    {
        float x = input.getX();
        float y = input.getY();
        float posX = this.position.x;
        float posY = this.position.y;
        float width = this.width;
        float height = this.height;

        return (x >= posX && x <= (posX + width)) && (y >= posY && y <= (posY + height));
    }

    private void getButtonState()
    {
        boolean isInBox = this.isInBox();

        if (isInBox) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.state = State.CLICK;
            } else {
                this.state = State.HOVER;
            }
        } else {
            this.state = State.NOTHING;
        }
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

    public void setForcedState(State state)
    {
        this.state = state;
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

    //Main method

    public void update()
    {
        this.getButtonState();
    }

    public void render()
    {
        this.update();
        this.batch.begin();
        this.drawSprite();
        this.batch.end();
    }

    public void dipose()
    {
        this.texture.dispose();
        this.batch.dispose();
    }
}
