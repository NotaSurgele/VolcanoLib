package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.volcano.game.Animator;
import com.volcano.game.Enemies;
import com.volcano.game.Light;
import com.volcano.game.Sonar;

public class Slime extends Enemies {


    Sonar sonar;

    //Animators
    Animator idle;
    Animator moving;
    final String dir = "enemies/slime/";
    float stateTime;

    float health = 100;

    boolean isChasing = false;

    public Slime(Texture t, float w, float h, float m, Light l) {
        super(t, w, h, m, l);
        this.setHitbox();
        this.sonar = new Sonar(300f);
        this.idle = Animator.initializeAnimation(this.idle, this.dir + "slime_idle_spritesheet.png", 6, 1, 0.07f);
        this.moving = Animator.initializeAnimation(this.idle, this.dir + "slime_run_spritesheeet.png", 6, 1, 0.07f);
    }

    private void setHitboxPosition()
    {
        float x = this.position.x;
        float y = this.position.y;

        this.hitbox.setPosition(x, y);
    }

    private void focusPlayer(Player player)
    {
        if (this.sonar.isPlayersDetected(player)) {
            this.followPlayer(player);
            this.isChasing = true;
        } else this.isChasing = false;
    }

    private void checkKnockback(Player player)
    {
        if (!this.getKnockBack())
            this.focusPlayer(player);
        else
            this.knockBack(0.1f);
    }

    private void kill()
    {
        if (this.getHealth() <= 0)
            this.killed();
    }

    @Override
    public void update(SpriteBatch batch, Player player)
    {
        this.stateTime += Game.deltaTime;

        float centeredX = this.position.x + (this.getWidth() / 2);
        float centeredY = this.position.y + (this.getHeight() / 2);

        this.kill();
        this.setHitboxPosition();
        this.setSonarPosition(this.sonar);
        this.checkKnockback(player);
        this.render(batch);
        this.light.update(centeredX, centeredY, Game.camera);
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
        this.draw(batch);
    }

    public void dispose()
    {
        this.sprite.getTexture().dispose();
    }
}
