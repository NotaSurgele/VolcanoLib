package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volcano.game.Button;
import com.volcano.game.Cursor;
import com.volcano.game.Scene;

public class Menu extends Scene {

    Button play;
    Button quit;
    SpriteBatch batch;

    String uiFolder = "ui (new)/";

    public Menu () {
        this.play = new Button(new Texture(this.uiFolder + "menu_button.png"), 500, 500, 100, 50, "Play");
        this.quit = new Button(new Texture(this.uiFolder + "menu_button.png"), 500, 300, 100, 50, "Quit");
        this.batch = new SpriteBatch();
    }

    //Main method
    public void play(Cursor cursor, Scene scene)
    {
        this.batch.begin();
        this.play.render(cursor);
        this.play.onHoverSetNewTexture(new Texture(this.uiFolder + "menu_button_press.png"));
        this.play.onClickChangeScene(scene, SCENES.GAME);
        this.quit.render(cursor);
        this.quit.onHoverSetNewTexture(new Texture(this.uiFolder + "menu_button_press.png"));
        this.quit.onClickChangeScene(scene, SCENES.NULL);
        this.batch.end();
    }

    public void dispose()
    {
        this.batch.dispose();
    }
}
