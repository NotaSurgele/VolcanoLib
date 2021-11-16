package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Animator;
import com.volcano.game.Enemies;

public class BlueMage extends Enemies {

    Animator move;

    float stateTime;

    final String dir = "enemies/blue-mage/";

    public BlueMage(Texture t, float w, float h, float m) {
        super(t, w, h, m);
        this.move = Animator.initializeAnimation(this.move, this.dir + "Blue-Mage-spritesheet.png", 3, 1, 0.10f);
    }

    @Override
    public void update(SpriteBatch batch, Player player) {
        this.stateTime += Game.deltaTime;

        this.setPosition(player.getPositionX() + 200f, player.getPositionY() + 20f);
        this.move.playAnimationToSprite(this.sprite, this.stateTime, true);
        this.draw(batch);
    }
}
