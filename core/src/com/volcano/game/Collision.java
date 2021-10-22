package com.volcano.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Collision {

    public Collision() {}

    public boolean isColliding(int x, int y, int[][] layer)
    {
        return (layer[y][x] < 0);
    }

    public boolean isColliding(LayerData layerData)
    {
        return (layerData.layer[layerData.posY][layerData.posX] < 0);
    }

    public void onCollidingSetOldEntityPosition(Sprite entity, LayerData layerData, float oldX, float oldY)
    {
        boolean isColliding = this.isColliding(layerData.posX, layerData.posY, layerData.layer);

        if (isColliding) {
            entity.setPosition(oldX, oldY);
            System.out.println(layerData.layer[layerData.posY][layerData.posX]);
        }
    }

    public void onCollidingSetOldEntityPosition(Sprite entity, LayerData layerData, float oldX, float oldY, Vector2 position)
    {
        boolean isColliding = this.isColliding(layerData.posX, layerData.posY, layerData.layer);

        if (isColliding) {
            entity.setPosition(oldX, oldY);
            position.set(oldX, oldY);
        }
    }
}
