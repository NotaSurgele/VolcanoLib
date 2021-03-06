package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TriggerUI {

    Texture texture;
    float x;
    float y;
    float w;
    float h;

    String inputButton;
    String action;

    float scale;

    BitmapFont inputButtonBitMapFont;
    BitmapFont actionBitMapFont;

    public TriggerUI(Texture t, float w, float h, String inputButton, String action, float fontSize) {
        this.texture = t;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.inputButton = inputButton;
        this.inputButtonBitMapFont = new BitmapFont();
        this.action = action;
        this.actionBitMapFont = new BitmapFont();
        this.scale = fontSize;
    }

     public void draw(SpriteBatch batch, float x, float y)
     {
        batch.draw(this.texture, x, y, this.w, this.h);
        this.inputButtonBitMapFont.getData().setScale(this.scale);
        this.inputButtonBitMapFont.draw(batch, this.inputButton, x + (w / 2.3f), y + (h / 1.5f));
        this.actionBitMapFont.draw(batch, this.action, x + w + 10, y + h / 2);
     }

     public void dispose()
     {
         this.inputButtonBitMapFont.dispose();
         this.actionBitMapFont.dispose();
         this.texture.dispose();
     }
}

