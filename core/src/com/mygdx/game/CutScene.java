package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class CutScene {

    float x;
    float y;

    public CutScene() {
        this.x = 0f;
        this.y = 0f;
    }

    public void spawning(float startY, float endY, float smoothness)
    {
        if (this.y == 0) {
            this.y = startY;
        }
        this.y = MathUtils.lerp(this.y, endY, smoothness * Gdx.graphics.getDeltaTime());
        Game.camera.position.set(Game.camera.position.x, this.y, 0);
        if ((int)this.y + 1 == (int)endY) {
            Game.setState(Game.State.NOTHING);
            this.y = 0f;
        }
    }
}
