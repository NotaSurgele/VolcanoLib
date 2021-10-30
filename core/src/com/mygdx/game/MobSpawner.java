package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Enemies;
import com.volcano.game.Room;
import com.mygdx.game.Game.State;

import java.util.ArrayList;

public class MobSpawner {

    Slime slime;
    ArrayList<Enemies> spawner;
    Dungeon dj;

    public MobSpawner(Dungeon dj) {
        this.slime = new Slime(new Texture("enemies/slime/slime_idle_anim_f0.png"), 32, 32, 1f);
        this.spawner = new ArrayList<>();
        this.spawner.add(this.slime);
        this.dj = dj;
    }

    public void getDungeon(Dungeon dj)
    {
        this.dj = dj;
    }

    public void onChangeSpawnMob(Enemies e)
    {
        if (Game.STATE == State.CHANGE) {
            Room currentRoom = this.dj.getCurrentRoom();
            float x = (currentRoom.getRoomX() + (float)(currentRoom.getWidth() / 2)) * Dungeon.tileSize;
            float y = (currentRoom.getRoomY() + (float)(currentRoom.getHeight() / 2)) * Dungeon.tileSize;

            e.setPosition(x, y);
            Game.STATE = State.NOTHING;
        }
    }

    public void update(SpriteBatch batch, Player player)
    {
        Enemies e = this.spawner.get(0);

        this.onChangeSpawnMob(e);
        e.update(batch, player);
    }
}
