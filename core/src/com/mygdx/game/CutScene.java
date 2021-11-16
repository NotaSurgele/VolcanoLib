package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.volcano.game.Animator;

public class CutScene {

    float x;
    float y;

    final String effects = "effects (new)/";

    Animator spawningEffect;
    Sprite sprite;

    float stateTime;

    public CutScene() {
        this.sprite = new Sprite();
        this.sprite.setBounds(0, 0, 150, 150);
        this.x = 0f;
        this.y = 0f;
        this.spawningEffect = Animator.initializeAnimation(this.spawningEffect, this.effects + "Spawning-effect.png", 17, 1, 0.1f);
    }

    public void spawning(float startY, float endY, float smoothness, SpriteBatch batch, Player player)
    {
        if (this.y == 0) this.y = startY;

        this.y = MathUtils.lerp(this.y, endY, smoothness * Gdx.graphics.getDeltaTime());
        Game.camera.position.set(Game.camera.position.x, this.y, 0);

        if ((int)this.y + 1 == (int)endY) {
            this.stateTime += Game.deltaTime;
            this.sprite.setPosition(Game.camera.position.x - 40, endY - 40);
            this.spawningEffect.playAnimationToSprite(this.sprite, this.stateTime, false);

            if (this.spawningEffect.getKeyFrameIndex(this.stateTime) >= 11)
                player.draw(batch);

            this.sprite.draw(batch);
            if (this.spawningEffect.isFinished(this.stateTime)) {
                Game.STATE = Game.State.NOTHING;
                this.sprite = null;
                this.y = 0f;
            }
        }
    }
}
