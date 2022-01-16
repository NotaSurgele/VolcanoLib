package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.volcano.game.Animator;
import com.volcano.game.Cursor;
import com.volcano.game.LayerData;
import com.volcano.game.Light;

public class Fireball extends Bullet {

    Animator spawning;
    Animator ashes;

    Vector2 direction;

    float stateTime;
    float charginTime;
    float lifeTime;

    boolean hasSpawn = false;
    boolean goTo = false;
    boolean isDead = false;

    public Fireball(Texture t, float w, float h, float x, float y, Light l) {
        super(t, w, h, x, y, l);
        this.spawning = Animator.initializeAnimation(this.spawning, "enemies/blue-mage/Blue-Mage_Fireball_Spawning.png", 5, 1, 0.07f);
        this.ashes = Animator.initializeAnimation(this.ashes, "Weapons/Blue-Fireball.png", 8, 1, 0.07f);
        this.direction = new Vector2(0, 0);
    }

    private void setPosition(float x, float y)
    {
        this.position.set(x, y);
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    private void animationController()
    {
        this.hasSpawn = this.spawning.isFinished(this.stateTime);

        if (!this.hasSpawn)
            this.spawning.playAnimationToSprite(this.sprite, this.stateTime, false);
        else {
            this.ashes.playAnimationToSprite(this.sprite, this.stateTime, true);
            if (this.direction.x == 0f && this.direction.y == 0f)
                this.charginTime += Game.deltaTime;
        }
    }

    private void checkCharginTime(Player player)
    {
        if (this.charginTime >= 3f) {
            this.direction.set(player.getPositionX() - this.position.x, player.getPositionY() - this.position.y);
            this.goTo = true;
            this.charginTime = 0.0f;
        }
    }

    private void checkPosition(float x, float y)
    {
        if (!goTo)
            this.setPosition(x, y);
        else {
            float hyp = (float)Math.sqrt(this.direction.x * this.direction.x + this.direction.y * this.direction.y);
            this.direction.x /= hyp;
            this.direction.y /= hyp;
            this.position.x += this.direction.x * 1000f * Game.deltaTime;
            this.position.y += this.direction.y * 1000f * Game.deltaTime;
            this.setPosition(this.position.x, this.position.y);
        }
    }

    private void checkLifeTime()
    {
        if (lifeTime >= 5f)
            this.isDead = true;
    }

    public boolean hasFinished()
    {
        return this.isDead;
    }

    public boolean hasShoot()
    {
        return this.goTo;
    }

    @Override
    public void update(SpriteBatch batch, Player player, float x, float y)
    {
        this.stateTime += Game.deltaTime;
        this.lifeTime += Game.deltaTime;
        float centeredX = this.position.x + (this.sprite.getWidth() / 2);
        float centeredY = this.position.y + (this.sprite.getHeight() / 2);

        this.checkLifeTime();
        this.checkCharginTime(player);
        this.animationController();
        this.checkPosition(x, y);
        this.sprite.draw(batch);
        this.bulletLight.update(centeredX, centeredY, Game.camera);
    }
}
