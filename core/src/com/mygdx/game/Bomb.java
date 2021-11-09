package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Animator;
import com.volcano.game.Enemies;
import com.volcano.game.Weapons;

public class Bomb extends Weapons {

    Animator charging;
    Animator explosion;

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
    }

    public void setBombState(State newState)
    {
        this.STATE = newState;
    }

    public State getState()
    {
        return this.STATE;
    }

    private void checkState()
    {
        if (this.STATE == State.CHARGING) {
            this.stateTime += Game.deltaTime;
            this.explosionCD();
        }
    }

    private void explosionCD()
    {
        if (this.stateTime >= 3f) {
            System.out.println("Hello World");
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

    private void checkHit(Player player)
    {
        System.out.println(STATE);
        if (this.explosion.isFinished(this.explosionStateTime)) {

            boolean playerHit = this.isEntityInsideExplosionHitbox(player.getSprite());
            if (playerHit) player.getDamaged(10f);
            for (int each = MobSpawner.spawner.size(); each != 0; each--) {
                Enemies e = MobSpawner.spawner.get(each - 1);
                boolean hit = this.isEntityInsideExplosionHitbox(e.getSprite());
                if (hit) {
                    e.takeDamage(100f);
                }
            }
            this.STATE = State.DEAD;
        }
    }

    public void killGoblin(ExplodingGoblin g)
    {
        g.killed();
    }

    public void update(SpriteBatch batch, Player player)
    {
        this.setExplosionHitboxPosition(this.getWeaponX() + (this.getWeaponWidth() / 2), this.getWeaponY() + (this.getWeaponHeight() / 2));
        this.checkHit(player);
        this.checkState();
        this.animationController();
        this.render(batch);
    }

    public void render(SpriteBatch batch)
    {
        this.draw(batch);
    }
}
