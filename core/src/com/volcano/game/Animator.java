package com.volcano.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {

    Texture texture;
    Animation<TextureRegion> animation;
    TextureRegion currentFrame;

    public Animator() {}

    //Main method
    public void createAnimation(Texture t, int FRAME_COLS, int FRAME_ROWS, float frameDuration)
    {
        if (t == null) {
            System.err.println("You should initialise a Texture!!");
            return;
        }
        this.texture = t;
        TextureRegion[][] tmp = TextureRegion.split(
                t,
                t.getWidth() / FRAME_COLS,
                t.getHeight() / FRAME_ROWS
        );
        TextureRegion[] animationFrame = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrame[index++] = tmp[i][j];
            }
        }
        this.animation = new Animation<TextureRegion>(frameDuration, animationFrame);
    }

    public void playAnimation(SpriteBatch batch, float stateTime, boolean loop, float x, float y, float w, float h)
    {
        this.currentFrame = this.animation.getKeyFrame(stateTime, loop);

        batch.draw(this.currentFrame, x, y, w, h);
    }

    public void stopAnimation(float stateTime)
    {
        this.animation.getKeyFrame(stateTime, false);
        this.currentFrame = null;
        stateTime = 0f;
    }

    public TextureRegion playAnimationToSprite(Sprite sprite, float stateTime, boolean loop)
    {
        this.currentFrame = this.animation.getKeyFrame(stateTime, loop);

        this.setFrameToSprite(sprite, currentFrame);
        return currentFrame;
    }

    //Set method
    public void setNewFrameDuration(float newFrameDuration)
    {
        this.animation.setFrameDuration(newFrameDuration);
    }

    public void setFrameToSprite(Sprite sprite, TextureRegion toSet)
    {
        sprite.setRegion(toSet);
    }

    public void setFrameBoundsToSprite(TextureRegion currentFrame, Sprite sprite)
    {
        float x = sprite.getX();
        float y = sprite.getY();
        float w = this.getFrameWidth(currentFrame);
        float h = this.getFrameHeight(currentFrame);

        sprite.setBounds(x, y, w, h);
    }

    //Get method
    public int getAnimationFramesNumb()
    {
        return this.animation.getKeyFrames().length;
    }

    public TextureRegion[] getAnimationFrames()
    {
        return this.animation.getKeyFrames();
    }

    public TextureRegion getCurrentAnimationFrame(float stateTime)
    {
        return this.animation.getKeyFrame(stateTime);
    }

    public TextureRegion getCurrentAnimationFrame(float stateTime, boolean loop)
    {
        return this.animation.getKeyFrame(stateTime, loop);
    }

    public float getAnimationDuration()
    {
        return this.animation.getAnimationDuration();
    }

    public float getCurrentFrameWidth(float stateTime, boolean isLooping)
    {
        this.currentFrame = this.animation.getKeyFrame(stateTime, isLooping);

        return currentFrame.getRegionWidth();
    }

    public float getCurrentFrameHeight(float stateTime, boolean isLooping)
    {
        this.currentFrame = this.animation.getKeyFrame(stateTime, isLooping);

        return currentFrame.getRegionHeight();
    }

    public float getFrameWidth(TextureRegion frame)
    {
        return frame.getRegionWidth();
    }

    public float getFrameHeight(TextureRegion frame)
    {
        return frame.getRegionHeight();
    }

    public int getKeyFrameIndex(float stateTime)
    {
        return this.animation.getKeyFrameIndex(stateTime);
    }

    //Static method
    public static Animator initializeAnimation(Animator animation, String texturePath, int FRAME_COLS, int FRAME_ROWS, float frameDuration)
    {
        animation = new Animator();
        animation.createAnimation(new Texture(texturePath), FRAME_COLS, FRAME_ROWS, frameDuration);

        return animation;
    }

    public static Animator initializeAnimation(Animator animation, Texture t, int FRAME_COLS, int FRAME_ROWS, float frameDuration)
    {
        animation = new Animator();
        animation.createAnimation(t, FRAME_COLS, FRAME_ROWS, frameDuration);

        return animation;
    }

    //Boolean
    public boolean isFinished(float stateTime)
    {
        return this.animation.isAnimationFinished(stateTime);
    }

    public boolean isGivenFrame(float stateTime, int givenFrame)
    {
        int frame = this.animation.getKeyFrameIndex(stateTime);

        return frame == givenFrame;
    }

    public void dispose()
    {
        this.texture.dispose();
    }


}
