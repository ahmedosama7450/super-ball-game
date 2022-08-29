package com.blogspot.osamatech442.superball.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.components.Button;
import com.blogspot.osamatech442.superball.screens.PlayScreen;
import com.blogspot.osamatech442.superball.screens.StartScreen;
import com.blogspot.osamatech442.superball.utils.Assets;
import com.blogspot.osamatech442.superball.utils.ButtonInput;
import com.blogspot.osamatech442.superball.utils.Constants;

public class PauseOverlay extends InputAdapter {
    private PlayScreen playScreen;
    private Viewport viewport;
    private Assets assets;

    private float elapsedTime;
    private boolean isPauseEnded;
    private Vector2 touchDownPos;

    private Button startButton;
    private Button replayButton;
    private Button homeButton;

    public PauseOverlay(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.viewport = playScreen.hudViewPort;
        this.assets = playScreen.assets;

        this.elapsedTime = 0;
        this.isPauseEnded = false;
        this.touchDownPos = new Vector2();

        this.startButton = new Button(
                assets.iconAssets.startIcon,
                Constants.START_ICON_SIZE,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        isPauseEnded = true;
                        elapsedTime = 0;
                    }
                }
        );
        this.replayButton = new Button(
                assets.iconAssets.replayIcon,
                Constants.REPLAY_ICON_SIZE,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        SuperBallGame superBallGame = ((SuperBallGame) Gdx.app.getApplicationListener());
                        superBallGame.setScreen(new PlayScreen(superBallGame));
                        if (superBallGame.preferences.getBoolean(Constants.IS_MUSIC_ON_KEY, Constants.IS_MUSIC_ON_DEFAULT)) {
                            assets.soundAssets.backgroundMusic.play();
                        }
                    }
                }
        );
        this.homeButton = new Button(
                assets.iconAssets.homeIcon,
                Constants.HOME_ICON_SIZE,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        SuperBallGame superBallGame = ((SuperBallGame) Gdx.app.getApplicationListener());
                        superBallGame.setScreen(new StartScreen(superBallGame));
                        if (superBallGame.preferences.getBoolean(Constants.IS_MUSIC_ON_KEY, Constants.IS_MUSIC_ON_DEFAULT)) {
                            assets.soundAssets.backgroundMusic.play();
                        }
                    }
                }
        );
    }

    public void render(SpriteBatch batch, BitmapFont bitmapFont, GlyphLayout textLayout, float delta) {
        Vector2 touchPos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        //transparent background
        batch.draw(assets.decorationAssets.pauseTransBG, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        if (!isPauseEnded) {
            //rendering the buttons
            startButton.render(batch, touchDownPos, touchPos, false);
            homeButton.render(batch, touchDownPos, touchPos, false);
            replayButton.render(batch, touchDownPos, touchPos, false);

        } else {
            //rendering the countdown
            if (elapsedTime >= Constants.PAUSE_TIME) {
                isPauseEnded = false;
                SuperBallGame superBallGame = ((SuperBallGame) Gdx.app.getApplicationListener());
                if (superBallGame.preferences.getBoolean(Constants.IS_MUSIC_ON_KEY, Constants.IS_MUSIC_ON_DEFAULT)) {
                    assets.soundAssets.backgroundMusic.play();
                }
                playScreen.restoreScreen();
            } else {
                String text;
                if (elapsedTime <= Constants.PAUSE_TIME / 3f)
                    text = "3";
                else if (elapsedTime > Constants.PAUSE_TIME / 3f && elapsedTime <= 2 * Constants.PAUSE_TIME / 3)
                    text = "2";
                else
                    text = "1";
                bitmapFont.setColor(Color.WHITE);
                bitmapFont.getData().setScale(Constants.COUNT_DOWN_SCALE);
                textLayout.setText(bitmapFont, text);
                bitmapFont.draw(
                        batch,
                        textLayout,
                        viewport.getWorldWidth() / 2 - textLayout.width / 2,
                        viewport.getWorldHeight() / 2 + textLayout.height / 2
                );
                elapsedTime += delta;
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDownPos = viewport.unproject(new Vector2(screenX, screenY));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isPauseEnded) return false;
        Vector2 touchUpPos = viewport.unproject(new Vector2(screenX, screenY));
        Sound sound = assets.soundAssets.clickSound;
        if (startButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if (homeButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if (replayButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        return false;
    }

    public void recalculateButtonPositions() {
        startButton.recalculateButtonPosition(
                viewport.getWorldWidth() / 2 - Constants.START_ICON_SIZE / 2,
                viewport.getWorldHeight() / 2 - Constants.START_ICON_SIZE / 2
        );
        homeButton.recalculateButtonPosition(
                startButton.getBounds().x + Constants.START_ICON_SIZE + Constants.PAUSE_OVERLAY_MARGIN,
                startButton.getBounds().y + Constants.START_ICON_SIZE / 2 - Constants.HOME_ICON_SIZE / 2
        );
        replayButton.recalculateButtonPosition(
                startButton.getBounds().x - Constants.HOME_ICON_SIZE - Constants.PAUSE_OVERLAY_MARGIN,
                startButton.getBounds().y + Constants.START_ICON_SIZE / 2 - Constants.HOME_ICON_SIZE / 2
        );
    }


}
