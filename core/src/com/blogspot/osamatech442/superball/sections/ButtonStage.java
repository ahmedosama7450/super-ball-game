package com.blogspot.osamatech442.superball.sections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.components.Button;
import com.blogspot.osamatech442.superball.components.CoinButton;
import com.blogspot.osamatech442.superball.screens.InstructionScreen;
import com.blogspot.osamatech442.superball.screens.MainScreen;
import com.blogspot.osamatech442.superball.utils.Assets;
import com.blogspot.osamatech442.superball.utils.ButtonInput;
import com.blogspot.osamatech442.superball.utils.Constants;

public class ButtonStage extends InputAdapter {

    private Viewport viewport;
    private Preferences preferences;
    private Assets assets;
    private BitmapFont bitmapFont;
    private GlyphLayout textLayout;

    private Vector2 touchDownPos;
    private boolean isPaused;

    private Button settingsButton;
    private Button instructionButton;
    private Button aboutButton;
    private Button exitButton;

    private CoinButton coinButton;
    private Button soundButton;
    public Button facebookButton;

    public ButtonStage(final MainScreen mainScreen) {
        this.viewport = mainScreen.viewport;
        this.preferences = mainScreen.preferences;
        this.assets = mainScreen.assets;

        this.bitmapFont = mainScreen.bitmapFont;
        this.textLayout = mainScreen.textLayout;

        this.touchDownPos = new Vector2();
        this.isPaused = false;

        this.settingsButton = new Button(
                assets.iconAssets.settingsIcon,
                Constants.BUTTON_SIZE,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        mainScreen.setSettingsOverlay();
                    }
                }
        );
        this.instructionButton = new Button(
                assets.iconAssets.instructionIcon,
                Constants.BUTTON_SIZE,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        SuperBallGame superBallGame = (SuperBallGame) Gdx.app.getApplicationListener();
                        superBallGame.setScreen(new InstructionScreen(superBallGame));
                    }
                }
        );
        this.aboutButton = new Button(
                assets.iconAssets.aboutIcon,
                Constants.BUTTON_SIZE,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        mainScreen.setAboutOverlay();
                    }
                }
        );
        this.exitButton = new Button(
                assets.iconAssets.exitIcon,
                Constants.BUTTON_SIZE,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        Gdx.app.exit();
                    }
                }
        );
        this.coinButton = new CoinButton(
                assets.prizeAssets.coin,
                Constants.ICON_SIZE,
                Constants.ICON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        SuperBallGame superBallGame = (SuperBallGame) Gdx.app.getApplicationListener();
                        if (superBallGame.actionResolver.hasRewardedVideo())
                            mainScreen.setAdRewardedVideoOverlay();
                        else
                            mainScreen.setNoAdRewardedVideoOverlay();
                    }
                }
        );
        this.soundButton = new Button(
                assets.iconAssets.soundIcon,
                Constants.ICON_SIZE,
                Constants.ICON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        Music music = assets.soundAssets.backgroundMusic;
                        if (music.isPlaying()) {
                            music.stop();
                            preferences.putBoolean(Constants.IS_MUSIC_ON_KEY, false).flush();
                        } else {
                            music.play();
                            preferences.putBoolean(Constants.IS_MUSIC_ON_KEY, true).flush();
                        }
                    }
                }
        );
        this.facebookButton = new Button(
                assets.iconAssets.fbIcon,
                Constants.ICON_SIZE,
                Constants.ICON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        Gdx.net.openURI(Constants.FB_PAGE_URL);
                    }
                }
        );


    }

    public void render(SpriteBatch batch) {
        Vector2 touchPos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        //Bottom Clouds
        batch.draw(
                assets.decorationAssets.bottomCloud,
                Constants.BOTTOM_CLOUD_XPOS,
                Constants.BOTTOM_CLOUD_YPOS,
                Constants.BOTTOM_CLOUD_WIDTH,
                Constants.BOTTOM_CLOUD_HEIGHT
        );

        //Coin button
        coinButton.render(batch, touchDownPos, touchPos, isPaused, bitmapFont, textLayout, getnCoins(), viewport.getWorldHeight());

        //sound icon
        soundButton.setImage(assets.soundAssets.backgroundMusic.isPlaying() ? assets.iconAssets.soundIcon : assets.iconAssets.noSoundIcon);
        soundButton.render(batch, touchDownPos, touchPos, isPaused);

        //fb icon
        facebookButton.render(batch, touchDownPos, touchPos, isPaused);

        //Settings Button
        settingsButton.render(batch, touchDownPos, touchPos, isPaused);

        //Instruction Button
        instructionButton.render(batch, touchDownPos, touchPos, isPaused);

        //About Button
        aboutButton.render(batch, touchDownPos, touchPos, isPaused);

        //Exit Button
        exitButton.render(batch, touchDownPos, touchPos, isPaused);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDownPos = viewport.unproject(new Vector2(screenX, screenY));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 touchUpPos = viewport.unproject(new Vector2(screenX, screenY));
        Sound sound = assets.soundAssets.clickSound;
        if(soundButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if(facebookButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if(settingsButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if(aboutButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if(exitButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if(instructionButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if(coinButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        return false;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    private int getnCoins() {
        return preferences.getInteger(Constants.COIN_NUMBER_KEY, Constants.COIN_NUMBER_DEFAULT);
    }

    public void recalculateButtonPositions() {
        coinButton.recalculateButtonPosition(
                Constants.HUD2_OUTER_MARGIN,
                viewport.getWorldHeight() - Constants.HUD2_OUTER_MARGIN - Constants.ICON_SIZE
        );
        soundButton.recalculateButtonPosition(
                viewport.getWorldWidth() - Constants.HUD2_OUTER_MARGIN - Constants.ICON_SIZE,
                viewport.getWorldHeight() - Constants.HUD2_OUTER_MARGIN - Constants.ICON_SIZE
        );
        facebookButton.recalculateButtonPosition(
                viewport.getWorldWidth() - Constants.HUD2_OUTER_MARGIN - Constants.ICON_SIZE,
                viewport.getWorldHeight() - Constants.HUD2_OUTER_MARGIN - Constants.ICON_SIZE - Constants.HUD2_INNER_MARGIN - Constants.ICON_SIZE
        );
        float buttonMargin = (viewport.getWorldWidth() - 4f * Constants.BUTTON_SIZE) / 5f;
        settingsButton.recalculateButtonPosition(
                buttonMargin,
                Constants.BUTTON_OUTER_MARGIN
        );
        instructionButton.recalculateButtonPosition(
                2 * buttonMargin + Constants.BUTTON_SIZE,
                Constants.BUTTON_OUTER_MARGIN
        );
        aboutButton.recalculateButtonPosition(
                3 * buttonMargin + 2 * Constants.BUTTON_SIZE,
                Constants.BUTTON_OUTER_MARGIN
        );
        exitButton.recalculateButtonPosition(
                4 * buttonMargin + 3 * Constants.BUTTON_SIZE,
                Constants.BUTTON_OUTER_MARGIN
        );

    }

}
