package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.volcano.game.Animator;
import com.volcano.game.Enemies;
import com.volcano.game.Light;
import com.volcano.game.Weapons;

public class Bomb extends Weapons {

    Animator charging;
    Animator explosion;

    ShapeRenderer visualHitbox;
    Light light;

    final String dir = "props_itens/";
    final String effectDir = "effects (new)/";

    float stateTime;
    float explosionStateTime;

    enum State {
        NORMAL,
        CHARGING,
        EXPLOSE,
        DEAD
    }

    State STATE;

    public Bomb(Texture t, float w, float h, float x, float y, float f, float explosionRadius) {
        super(t, w, h, w, y, f, explosionRadius);
        this.charging = Animator.initializeAnimation(this.charging, this.dir + "bomb_anim_spritesheet.png", 10,1, 0.12f);
        this.explosion = Animator.initializeAnimation(this.explosion, this.effectDir + "explosion_anim_spritesheet.png", 7, 1, 0.07f);
        this.STATE = State.NORMAL;
        this.visualHitbox = new ShapeRenderer();
        this.light = new Light(new Texture("Shader/light.png"), 255, 255, 0, 1, 500, 500);
    }

    public void setBombState(State newState)
    {
        this.STATE = newState;
    }

    public State getState()
    {
        return this.STATE;
    }

    private void checkState(SpriteBatch batch)
    {
        if (this.STATE == State.CHARGING) {
            this.stateTime += Game.deltaTime;
            this.explosionCD();
            this.showHitbox(batch);
        }
    }

    private void explosionCD()
    {
        if (this.stateTime >= 3f) {
            this.STATE = State.EXPLOSE;
        }
    }

    private void animationController()
    {
        if (this.STATE == State.CHARGING)
            this.charging.playAnimationToSprite(this.sprite, this.stateTime, false);
        else if (this.STATE == State.EXPLOSE) {
            this.explosionStateTime += Game.deltaTime;
            this.setWeaponScale(3, 3);
            this.setWeaponX(this.getWeaponX() - (this.getWeaponWidthScaled() / 2));
            this.setWeaponY(this.getWeaponY() - (this.getWeaponHeightScaled() / 2));
            this.explosion.playAnimationToSprite(this.sprite, this.explosionStateTime, false);
        }
    }

    private void checkHit(Player player, ExplodingGoblin self)
    {
        if (this.STATE == State.EXPLOSE) {
            boolean playerHit = this.isEntityInsideExplosionHitbox(player.getSprite());
            if (playerHit) player.getDamaged(10f);
            for (int each = MobSpawner.spawner.size(); each != 0; each--) {
                if (MobSpawner.spawner.get(each - 1) != self) {
                    Enemies e = MobSpawner.spawner.get(each - 1);
                    boolean hit = this.isEntityInsideExplosionHitbox(e.getSprite());
                    if (hit) {
                        e.takeDamage(100f);
                        e.hide(true);
                    }
                } else MobSpawner.spawner.get(each - 1).hide(true);
            }
        }
    }

    public boolean isFinished()
    {
        if (this.explosion.isFinished(explosionStateTime)) {
            this.STATE = State.DEAD;
        }
        return (this.STATE == State.DEAD);
    }

    private void showHitbox(SpriteBatch batch)
    {
        batch.end();
        this.visualHitbox.setProjectionMatrix(Game.camera.combined);
        this.visualHitbox.begin(ShapeRenderer.ShapeType.Line);
        this.visualHitbox.setColor(Color.RED);
        this.visualHitbox.circle(this.getExplosionCircleX(), this.getExplosionCircleY(), this.getExplosionCircleRadius());
        this.visualHitbox.end();
        batch.begin();
    }

    public void update(SpriteBatch batch, Player player, ExplodingGoblin self)
    {
        float centeredX = this.position.x + (this.getWeaponWidth() / 2);
        float centeredY = this.position.y + (this.getWeaponHeight() / 2);

        this.setExplosionHitboxPosition(this.getWeaponX() + (this.getWeaponWidth() / 2), this.getWeaponY() + (this.getWeaponHeight() / 2));
        this.checkHit(player, self);
        this.checkState(batch);
        this.animationController();
        this.render(batch);
        this.light.update(centeredX, centeredY, Game.camera);
    }

    public void render(SpriteBatch batch)
    {
        this.draw(batch);
    }
}
