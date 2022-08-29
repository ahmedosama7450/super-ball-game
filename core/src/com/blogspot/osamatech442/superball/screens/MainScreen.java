package com.blogspot.osamatech442.superball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.overlays.AboutOverlay;
import com.blogspot.osamatech442.superball.overlays.AdRewardedVideoOverlay;
import com.blogspot.osamatech442.superball.overlays.NoAdRewardedVideoOverlay;
import com.blogspot.osamatech442.superball.overlays.SettingsOverlay;
import com.blogspot.osamatech442.superball.sections.BallChooser;
import com.blogspot.osamatech442.superball.sections.ButtonStage;
import com.blogspot.osamatech442.superball.utils.Assets;
import com.blogspot.osamatech442.superball.utils.Constants;

public abstract class MainScreen implements AdvancedScreen {

    //Singleton Objects
    public SpriteBatch batch;
    public BitmapFont bitmapFont;
    public GlyphLayout textLayout;
    public Preferences preferences;
    public Assets assets;
    public Viewport viewport;

    //Screen Contents
    public ButtonStage buttonStage;
    public BallChooser ballChooser;

    //Screen Overlays
    private SettingsOverlay settingsOverlay;
    private AboutOverlay aboutOverlay;
    private AdRewardedVideoOverlay adRewardedVideoOverlay;
    private NoAdRewardedVideoOverlay noAdRewardedVideoOverlay;

    //flags
    private boolean isSettingsOverlay;
    private boolean isAboutOverlay;
    private boolean isAdRewardedVideoOverlay;
    private boolean isNoAdRewardedVideoOverlay;

    //Input
    private InputMultiplexer inputMultiplexer;

    //others
    float ballChooserYPos = 0;

    public MainScreen(SuperBallGame superBallGame) {
        //Screen objects
        this.batch = superBallGame.batch;
        this.bitmapFont = superBallGame.bitmapFont;
        this.textLayout = superBallGame.textLayout;
        this.preferences = superBallGame.preferences;
        this.assets = superBallGame.assets;
        this.viewport = new ExtendViewport(Constants.MAIN_SCREEN_SIZE, Constants.MAIN_SCREEN_SIZE);

        //Contents
        this.ballChooser = new BallChooser(this);
        this.buttonStage = new ButtonStage(this);

        //Overlays
        this.settingsOverlay = new SettingsOverlay(this);
        this.aboutOverlay = new AboutOverlay(this);
        this.adRewardedVideoOverlay = new AdRewardedVideoOverlay(
                1, this,
                assets.overlayAssets.coinRewardedVideoOverlay,
                assets.overlayAssets.overlayYesButton,
                assets.overlayAssets.overlayNoButton,
                viewport
        );
        this.noAdRewardedVideoOverlay = new NoAdRewardedVideoOverlay(
                this,
                assets.overlayAssets.noRewardedVideoOverlay,
                viewport
        );

        //flags
        this.isSettingsOverlay = false;
        this.isAboutOverlay = false;
        this.isAdRewardedVideoOverlay = false;
        this.isNoAdRewardedVideoOverlay = false;

        //Input
        this.inputMultiplexer = new InputMultiplexer(ballChooser, buttonStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        recalculateContentsBounds();
    }

    public void recalculateContentsBounds() {
        buttonStage.recalculateButtonPositions();
        recalculateBallChooserPosition();

        aboutOverlay.recalculateButtonPositions();
        settingsOverlay.recalculateButtonPositions();
        adRewardedVideoOverlay.recalculateButtonBounds();
        noAdRewardedVideoOverlay.recalculateButtonBounds();
    }

    public void recalculateBallChooserPosition() {
        ballChooser.recalculateButtonPositions(ballChooserYPos);
    }

    @Override
    public void render(float delta) {
        //rendering main screen
        ballChooser.render(batch);
        buttonStage.render(batch);

        //rendering the overlays
        if (isSettingsOverlay)
            settingsOverlay.render(batch);
        else if (isAboutOverlay)
            aboutOverlay.render(batch);
        else if (isAdRewardedVideoOverlay)
            adRewardedVideoOverlay.render(batch);
        else if (isNoAdRewardedVideoOverlay)
            noAdRewardedVideoOverlay.render(batch);
    }

    private void setPaused(boolean paused) {
        buttonStage.setPaused(paused);
        ballChooser.setPaused(paused);
    }

    public void setSettingsOverlay() {
        isSettingsOverlay = true;
        setPaused(true);
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(settingsOverlay);
    }

    public void setAboutOverlay() {
        isAboutOverlay = true;
        setPaused(true);
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(aboutOverlay);
    }

    public void setAdRewardedVideoOverlay() {
        isAdRewardedVideoOverlay = true;
        setPaused(true);
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(adRewardedVideoOverlay);
    }

    public void setNoAdRewardedVideoOverlay() {
        isNoAdRewardedVideoOverlay = true;
        setPaused(true);
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(noAdRewardedVideoOverlay);
    }

    @Override
    public void restoreScreen() {
        isSettingsOverlay = false;
        isAboutOverlay = false;
        isAdRewardedVideoOverlay = false;
        isNoAdRewardedVideoOverlay = false;
        setPaused(false);
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(ballChooser);
        inputMultiplexer.addProcessor(buttonStage);
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
