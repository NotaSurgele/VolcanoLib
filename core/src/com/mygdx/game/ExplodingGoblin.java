package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.volcano.game.Animator;
import com.volcano.game.Enemies;
import com.volcano.game.Light;
import com.volcano.game.Sonar;

public class ExplodingGoblin extends Enemies {

    Sonar sonar;

    //Weapons
    Bomb bomb;

    //Animator
    Animator idle;
    Animator moving;
    float stateTime;

    final String dir = "enemies/goblin/";
    final String wDir = "props_itens/";

    boolean isChasing = false;

    public ExplodingGoblin(Texture t, float w, float h, float m, Light l) {
        super(t, w, h, m, l);
        this.setHitbox();
        this.sonar = new Sonar(500f);
        this.idle = Animator.initializeAnimation(this.idle, this.dir + "goblin_idle_spritesheet.png", 6, 1, 0.07f);
        this.moving = Animator.initializeAnimation(this.moving, this.dir + "goblin_run_spritesheet.png", 6, 1, 0.07f);
        this.bomb = new Bomb(new Texture(this.wDir + "bomb_anim_f0.png"), 64, 64, this.position.x, this.position.y, 15f, 150f);
    }


    private void focusPlayer(Player player)
    {
        this.isChasing = this.sonar.isPlayersDetected(player);
        if (this.isChasing) {
            if (bomb == null) return;
            if (this.bomb.getState() == Bomb.State.CHARGING)
                this.followPlayer(player);
            if (this.bomb.getState() == Bomb.State.NORMAL)
                this.bomb.setBombState(Bomb.State.CHARGING);
        }
    }

    private void animationController()
    {
        if (this.isChasing) {
            this.moving.playAnimationToSprite(this.sprite, this.stateTime, true);
        } else {
            this.idle.playAnimationToSprite(this.sprite, this.stateTime, true);
        }
    }

    private void render(SpriteBatch batch)
    {
        if (!this.isKilled()) {
            this.animationController();
            this.draw(batch);
        }
    }

    public void checkBomb()
    {
        if (this.bomb != null) {
            if (this.bomb.isFinished()) {
                this.bomb = null;
                this.killed();
            }
        }
    }

    public void bomb(SpriteBatch batch, Player player)
    {
        this.checkBomb();
        if (this.bomb != null) {
            this.bomb.setWeaponX(this.getPosition().x + (this.getWidth() / 2));
            this.bomb.setWeaponY(this.getPosition().y + (this.getHeight() / 2));
            this.bomb.update(batch, player, this);
        }
    }

    private void checkKnockBack(Player player)
    {
        if (!this.getKnockBack())
            this.focusPlayer(player);
        else
            this.knockBack(0.15f);
    }

    @Override
    public void update(SpriteBatch batch, Player player) {
        this.stateTime += Game.deltaTime;

        float centeredX = this.position.x + (this.getWidth() / 2);
        float centeredY = this.position.y + (this.getHeight() / 2);

        if (this.getHealth() <= 0)
            this.killed();
        if (!this.isHide()) {
            this.setSonarPosition(this.sonar);
            this.checkKnockBack(player);
            this.render(batch);
        }
        this.bomb(batch, player);
        if (this.light != null)
            this.light.update(centeredX, centeredY, Game.camera);
    }

    public void dispose()
    {
        this.sprite.getTexture().dispose();
    }
}
