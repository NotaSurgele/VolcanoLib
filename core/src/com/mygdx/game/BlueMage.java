package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Animator;
import com.volcano.game.Enemies;
import com.volcano.game.Sonar;
import com.volcano.game.Weapons;

public class BlueMage extends Enemies {

    Animator move;
    Animator attack;

    Sonar sonar;
    Fireball fireBall;

    float stateTime;
    float cd;
    final int shootingCD = 5;

    State state;

    enum State {
        ATTACKING,
        CHASING,
        IDLE,
    }

    final String dir = "enemies/blue-mage/";

    public BlueMage(Texture t, float w, float h, float m) {
        super(t, w, h, m);
        this.move = Animator.initializeAnimation(this.move, this.dir + "Blue-Mage-spritesheet.png", 4, 1, 0.07f);
        this.attack = Animator.initializeAnimation(this.attack, this.dir + "Blue-Mage-attack-spritesheet.png", 17, 1, 0.30f);
        this.fireBall = new Fireball(new Texture("enemies/blue-mage/Blue-Mage_Fireball_Spawning.png"), 128, 128, 0, 0);
        this.state = State.IDLE;
        this.sonar = new Sonar(500f);
    }

    private void animationController()
    {
        if (this.state == State.IDLE || this.state == State.CHASING)
            this.move.playAnimationToSprite(this.sprite, this.stateTime, true);
        else if (this.state == State.ATTACKING)
            this.attack.playAnimationToSprite(this.sprite, this.stateTime, true);
    }

    private void checkShootingCD()
    {
        if (this.fireBall == null) return;
        if (this.fireBall.hasShoot()) {
            this.cd += Game.deltaTime;
        }
        if (this.cd >= this.shootingCD) {
            this.state = State.IDLE;
            this.cd = 0f;
        }
    }

    private void stateController(SpriteBatch batch, Player player)
    {
        this.sonar(player);
        this.checkShootingCD();
        if (this.fireBall != null) {
            if (this.state == State.ATTACKING)
                this.fireBall.update(batch, player, this.getPosition().x, this.getPosition().y);
            if (this.fireBall.hasFinished())
                this.state = State.CHASING;
        }
        if (this.state == State.CHASING)
            this.followPlayer(player);
    }

    private void checkFireball()
    {
        if (this.fireBall == null) return;
        if (this.fireBall.hasFinished())
            this.fireBall = null;
    }

    private void sonar(Player player)
    {
        this.sonar.setPosition(this.getPosition().x, this.getPosition().y);
        if (this.sonar.isPlayersDetected(player)) {
            this.state = State.ATTACKING;
            if (this.fireBall == null)
                this.fireBall = new Fireball(new Texture("enemies/blue-mage/Blue-Mage_Fireball_Spawning.png"), 128, 128, 0, 0);;
        }
    }

    @Override
    public void update(SpriteBatch batch, Player player) {
        this.stateTime += Game.deltaTime;

        this.animationController();
        this.draw(batch);
        this.stateController(batch, player);
        this.checkFireball();
    }
}
