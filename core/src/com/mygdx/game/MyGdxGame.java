package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volcano.game.Animator;
import com.volcano.game.Button;
import com.volcano.game.Cursor;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Sprite sprite;
	Player player;
	Cursor cursor;

	public void create () {
		batch = new SpriteBatch();
		sprite = new Sprite();
		sprite.setBounds(500, 200, 70, 70);
		player = new Player(sprite, 50, 50);
		cursor = new Cursor(new Texture("ui (new)/crosshair_1.png"), 25, 25, true);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		batch.begin();
		player.update(batch);
		cursor.update(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
