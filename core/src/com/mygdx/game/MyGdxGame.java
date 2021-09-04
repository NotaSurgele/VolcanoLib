package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volcano.game.Animator;
import com.volcano.game.Button;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Animator idle;
	Sprite sprite;
	float stateTime;
	Player player;

	public void initAnimation()
	{
		idle.createAnimation(new Texture("noBKG_KnightIdle_strip.png"), 15, 1, 0.1f);
	}

	public void create () {
		batch = new SpriteBatch();
		idle = new Animator();
		initAnimation();
		sprite = new Sprite();
		sprite.setBounds(500, 200, 100, 100);
		player = new Player(sprite, 50, 50);
	}


	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		stateTime += Gdx.graphics.getDeltaTime();
		batch.begin();
		TextureRegion currentFrame = idle.getCurrentAnimationFrame(stateTime, true);
		idle.setFrameToSprite(sprite, currentFrame);
		player.update();
		sprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
