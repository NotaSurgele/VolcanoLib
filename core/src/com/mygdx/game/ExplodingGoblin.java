package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.volcano.game.Animator;
import com.volcano.game.Enemies;

public class ExplodingGoblin extends Enemies {

    Circle range;

    //Weapons
    Bomb bomb;

    //Animator
    Animator idle;
    Animator moving;
    float stateTime;

    final String dir = "enemies/goblin/";
    final String wDir = "props_itens/";

    boolean isChasing = false;

    public ExplodingGoblin(Texture t, float w, float h, float m) {
        super(t, w, h, m);
        this.setHitbox();
        this.setDetector();
        this.idle = Animator.initializeAnimation(this.idle, this.dir + "goblin_idle_spritesheet.png", 6, 1, 0.07f);
        this.moving = Animator.initializeAnimation(this.moving, this.dir + "goblin_run_spritesheet.png", 6, 1, 0.07f);
        this.bomb = new Bomb(new Texture(this.wDir + "bomb_anim_f0.png"), 64, 64, this.position.x, this.position.y, 15f, 350f);
    }

    private void setDetector()
    {
        float x = this.position.x + (this.getWidth() / 2);
        float y = this.position.y + (this.getHeight() / 2);

        this.range = new Circle();
        this.range.setPosition(x, y);
        this.range.setRadius(500f);
    }

    private void focusPlayer(Player player)
    {
        this.isChasing = this.isPlayerInsideCircle(this.range, player);
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

    @Override
    public void update(SpriteBatch batch, Player player) {
        this.stateTime += Game.deltaTime;

        System.out.println(this.getHealth());

        if (this.getHealth() <= 0)
            this.killed();
        if (!this.isHide()) {
            this.setDetectorPosition(this.range);
            this.focusPlayer(player);
            this.render(batch);
        }
        this.bomb(batch, player);
    }

    public void dispose()
    {
        this.sprite.getTexture().dispose();
    }
}
