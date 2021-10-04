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
import com.volcano.game.Scene;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Cursor cursor;
	Game game;
	Menu menu;
	Scene scene;

	public void create () {
		batch = new SpriteBatch();
		cursor = new Cursor(new Texture("ui (new)/crosshair_1.png"), 25, 25, true);
		game = new Game();
		menu = new Menu();
		scene = new Scene();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		batch.begin();
		cursor.update(batch);
		scene.update(menu, game, cursor);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		cursor.dispose();
	}
}
