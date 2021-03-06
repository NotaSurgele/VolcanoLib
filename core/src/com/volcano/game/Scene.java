package com.volcano.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Game;
import com.mygdx.game.Menu;

import java.util.ArrayList;

public class Scene {

    public enum SCENES {
        MENU,
        GAME,
        NULL
    }

    public SCENES scenes;

    public Scene() {
        this.scenes = SCENES.MENU;
    }

    // Main method
    public SCENES getCurrentScene()
    {
        return this.scenes;
    }

    public void setScenes(SCENES set)
    {
        this.scenes = set;
    }

    public void playMenu(Menu scene, Cursor cursor)
    {
        this.setScenes(SCENES.MENU);
        scene.play(cursor, this);
    }

    public void playGame(Game game, Cursor cursor)
    {
        this.setScenes(SCENES.GAME);
        game.load();
        game.play(cursor);
    }

    public void exitGame(Menu menu)
    {
        this.setScenes(SCENES.NULL);
        menu.dispose();
        Gdx.app.exit();
    }

    public void update(Menu menu, Game game, Cursor cursor)
    {
        switch (this.scenes) {
            case MENU: this.playMenu(menu, cursor); break;
            case GAME: this.playGame(game, cursor); break;
            case NULL: this.exitGame(menu); break;
            default: break;
        }
    }

    public void dispose(Menu menu, Game game)
    {
        if (this.scenes != SCENES.NULL) {
            menu.dispose();
            game.dispose();
        }
    }
}
