package com.blogspot.osamatech442.superball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.utils.Constants;

public class SplashScreen implements Screen {

    public SuperBallGame superBallGame;
    public SpriteBatch batch;
    private Texture logo;
    private float elapsedTime;

    public SplashScreen(SuperBallGame superBallGame) {
        this.superBallGame = superBallGame;
        this.batch = superBallGame.batch;
        this.logo = new Texture(Constants.START_LOGO_NAME);
        this.elapsedTime = 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (!superBallGame.assets.assetManager.update() || elapsedTime <= Constants.SPLASH_SCREEN_TIME) {
            clearScreen(Constants.SPLASH_SCREEN_COLOR);
            float logoWidth = (3f/4f) * Gdx.graphics.getWidth();
            float logoHeight = logoWidth * Constants.START_LOGO_RATIO;
            batch.begin();
            batch.draw(
                    logo,
                    Gdx.graphics.getWidth() / 2 - logoWidth / 2,
                    Gdx.graphics.getHeight() / 2 - logoHeight / 2,
                    logoWidth,
                    logoHeight

            );
            batch.end();
        } else {
            superBallGame.assets.prepareAssets();
            if (superBallGame.preferences.getBoolean(Constants.IS_MUSIC_ON_KEY, Constants.IS_MUSIC_ON_DEFAULT))
                superBallGame.assets.soundAssets.backgroundMusic.play();

            //Setting the start screen
            superBallGame.setScreen(new StartScreen(superBallGame));
        }

        elapsedTime += delta;

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
        this.logo.dispose();
    }

    private void clearScreen(Color color) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {

    }
}
