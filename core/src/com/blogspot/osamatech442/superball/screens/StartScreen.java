package com.blogspot.osamatech442.superball.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.utils.Constants;

public class StartScreen extends MainScreen {

    public StartScreen(SuperBallGame superBallGame) {
        super(superBallGame);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        clearScreen(SuperBallGame.backgroundColor);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(
                assets.decorationAssets.logo,
                viewport.getWorldWidth() / 2 - Constants.LOGO_WIDTH / 2,
                viewport.getWorldHeight() / 2 + Constants.BALL_CHOOSER_SIZE / 2 + Constants.LOGO_MARGIN,
                Constants.LOGO_WIDTH,
                Constants.LOGO_HEIGHT
        );
        super.render(delta);
        batch.end();
    }

    private void clearScreen(Color color) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void recalculateBallChooserPosition() {
        ballChooserYPos = viewport.getWorldHeight() / 2 - Constants.BALL_CHOOSER_SIZE / 2;
        super.recalculateBallChooserPosition();
    }
}
