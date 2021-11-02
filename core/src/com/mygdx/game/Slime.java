package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.volcano.game.Animator;
import com.volcano.game.Enemies;

public class Slime extends Enemies {

    Circle range;
    Rectangle hitbox;

    //Animators
    Animator idle;
    Animator moving;
    final String dir = "enemies/slime/";
    float stateTime;

    boolean isChasing = false;

    public Slime(Texture t, float w, float h, float m) {
        super(t, w, h, m);
        this.setHitbox();
        this.setDetector();
        this.idle = Animator.initializeAnimation(this.idle, this.dir + "slime_idle_spritesheet.png", 6, 1, 0.07f);
        this.moving = Animator.initializeAnimation(this.idle, this.dir + "slime_run_spritesheeet.png", 6, 1, 0.07f);
    }

    private void setHitbox()
    {
        this.hitbox = new Rectangle();
        this.hitbox.set(this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    private void setDetector()
    {
        float x = this.position.x + (this.getWidth() / 2);
        float y = this.position.y + (this.getHeight() / 2);

        this.range = new Circle();
        this.range.setPosition(x, y);
        this.range.setRadius(300f);
    }

    private void setDetectorPosition()
    {
        float x = this.position.x + (this.getWidth() / 2);
        float y = this.position.y + (this.getHeight() / 2);

        this.range.setPosition(x, y);
    }

    private void setHitboxPosition()
    {
        float x = this.position.x;
        float y = this.position.y;

        this.hitbox.setPosition(x, y);
    }

    private void focusPlayer(Player player)
    {
        if (this.range.contains(player.position)) {
            this.followPlayer(player);
            this.isChasing = true;
        } else this.isChasing = false;
    }

    @Override
    public void update(SpriteBatch batch, Player player)
    {
        this.stateTime += Game.deltaTime;

        this.setHitboxPosition();
        this.setDetectorPosition();
        this.focusPlayer(player);
        this.render(batch);
    }

    public void setDetectorNewRadius(float rd)
    {
        this.range.setRadius(rd);
    }

    private void animationController()
    {
        if (this.isChasing)
            this.moving.playAnimationToSprite(this.sprite, this.stateTime, true);
        else
            this.idle.playAnimationToSprite(this.sprite, this.stateTime, true);
    }

    private void render(SpriteBatch batch)
    {
        this.animationController();
        this.sprite.draw(batch);
    }

    public void dispose()
    {
        this.sprite.getTexture().dispose();
    }
}
