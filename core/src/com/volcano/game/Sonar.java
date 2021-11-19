package com.volcano.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Sonar {

    Circle detector;
    Vector2 position;

    public Sonar(float rd) {
        this.detector = new Circle();
        this.detector.setRadius(rd);
        this.position = new Vector2();
    }

    public void setPosition(float x, float y)
    {
        this.position.set(x, y);
        this.detector.setPosition(this.position);
    }

    public boolean isPlayersDetected(Players p)
    {
        return this.detector.contains(p.getPositionVector());
    }
}
