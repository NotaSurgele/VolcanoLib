package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.volcano.game.Enemies;
import com.volcano.game.Light;
import com.volcano.game.Room;
import com.mygdx.game.Game.State;

import java.util.ArrayList;

public class MobSpawner {

    final int MAXENTITY = 2;
    public static ArrayList<Enemies> spawner;
    Dungeon dj;
    Texture lightTexture;

    public MobSpawner(Dungeon dj) {
        spawner = new ArrayList<>();
        this.lightTexture = new Texture("Shader/light.png");
        this.dj = dj;
    }

    private void entityCreator()
    {
        for (int i = this.MAXENTITY; i != 0; i--) {
            BlueMage bm = new BlueMage(new Texture("enemies/blue-mage/Blue-Mage-spritesheet.png"), 126, 126, 1f, new Light(this.lightTexture, 255, 255, 0, 1, 500, 500));
            spawner.add(bm);

            if (i == 1) {
                ExplodingGoblin eg = new ExplodingGoblin(new Texture("enemies/goblin/goblin_idle_anim_f0.png"), 64, 64, 3f, new Light(this.lightTexture, 255, 255, 0, 1, 500, 500));
                spawner.add(eg);
            } else {
                Slime s = new Slime(new Texture("enemies/slime/slime_idle_anim_f0.png"), 32, 32, 1f, new Light(this.lightTexture, 255, 255, 0, 1, 500, 500));
                spawner.add(s);
            }
        }
    }

    public void getDungeon(Dungeon dj)
    {
        this.dj = dj;
    }

    private void setRandomSpawn(Enemies e)
    {
        Room currentRoom = this.dj.getCurrentRoom();

        float minX = (currentRoom.getRoomX()) * Dungeon.tileSize;
        float minY = (currentRoom.getRoomY()) * Dungeon.tileSize;
        float maxX = (currentRoom.getRoomX() + currentRoom.getWidth()) * Dungeon.tileSize;
        float maxY = (currentRoom.getRoomY() + currentRoom.getHeight()) * Dungeon.tileSize;
        float x = MathUtils.random(minX + 16f, maxX - 16f);
        float y = MathUtils.random(minY + 16f, maxY - 16f);

        e.setPosition(x, y);
    }

    public void onChangeSpawnMob()
    {
        if (Game.STATE == State.CHANGE) {
            this.entityCreator();
            for (int i = spawner.size() - 1; i >= 0; i--) {
                Enemies e = spawner.get(i);
                e.setSpriteColor(.4f, .4f, .4f, 1f);
                this.setRandomSpawn(e);
            }
            Game.STATE = State.NOTHING;
        }
    }

    public void update(SpriteBatch batch, Player player)
    {
        this.onChangeSpawnMob();
        for (int i = spawner.size() - 1; i >= 0; i--) {
            Enemies e = spawner.get(i);

            e.update(batch, player);
            if (spawner.size() != 0 && e.isKilled()) {
                spawner.remove(i);
            }
        }
    }
}
