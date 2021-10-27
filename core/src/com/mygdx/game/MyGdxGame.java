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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volcano.game.*;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Cursor cursor;
	Game game;
	Menu menu;
	Scene scene;
	Debug debug;

	public void create () {
		batch = new SpriteBatch();
		cursor = new Cursor(new Texture("ui (new)/crosshair_1.png"), 16, 16, true);
		game = new Game(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		menu = new Menu(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		scene = new Scene();
		debug = new Debug();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		batch.begin();
		cursor.update(batch, Game.camera);
		scene.update(menu, game, cursor);
		batch.end();
		batch.begin();
		debug.update(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		scene.dispose(menu, game);
		debug.dispose();
		cursor.dispose();
	}
}
