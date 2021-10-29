package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

public class Slime extends Enemies {

    Circle range;

    public Slime(Texture t, float w, float h, float m, float x, float y) {
        super(t, w, h, m);
        this.setPosition(x, y);
        this.setDetector();
    }

    private void setDetector()
    {
        float x = this.position.x + (this.getWidth() / 2);
        float y = this.position.y + (this.getHeight() / 2);

        this.range = new Circle();
        this.range.setPosition(x, y);
        this.range.setRadius(300f);
    }

    public void setDetectorPosition()
    {
        float x = this.position.x + (this.getWidth() / 2);
        float y = this.position.y + (this.getHeight() / 2);

        this.range.setPosition(x, y);
    }

    public void focusPlayer(Player player)
    {
        if (this.range.contains(player.position)) {
            this.followPlayer(player);
        }
    }

    public void update(SpriteBatch batch, Player player)
    {
        this.setDetectorPosition();
        this.focusPlayer(player);
        this.render(batch);
    }

    public void setDetectorNewRadius(float rd)
    {
        this.range.setRadius(rd);
    }

    public void render(SpriteBatch batch)
    {
        this.sprite.draw(batch);
    }

    public void dispose()
    {
        this.sprite.getTexture().dispose();
    }
}
