package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class mainMenu implements Screen {
    Texture background= new Texture("bg.png");
    SpriteBatch batch = new SpriteBatch(); //putting something on the screen

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //keeps on rendering items untill the app is open
        batch.begin();
        batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
