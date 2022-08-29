package com.blogspot.osamatech442.superball.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.components.Button;
import com.blogspot.osamatech442.superball.screens.AdvancedScreen;
import com.blogspot.osamatech442.superball.screens.PlayScreen;
import com.blogspot.osamatech442.superball.screens.ResultScreen;
import com.blogspot.osamatech442.superball.utils.ButtonInput;
import com.blogspot.osamatech442.superball.utils.Constants;


public class AdRewardedVideoOverlay extends RewardedVideoOverlay {
    private Button yesButton;
    private Button noButton;
    public int type; //1 for coin, 2 for life

    public AdRewardedVideoOverlay(int type, AdvancedScreen screen, TextureRegion region, TextureRegion yesButtonRegion, TextureRegion noButtonRegion, Viewport viewport) {
        super(screen, region, viewport);
        this.type = type;
        this.yesButton = new Button(
                yesButtonRegion,
                Constants.REWARDED_VIDEO_BUTTON_WIDTH,
                Constants.REWARDED_VIDEO_BUTTON_HEIGHT,
                Constants.REWARDED_BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        onYesClicked();
                    }
                }
        );
        this.noButton = new Button(
                noButtonRegion,
                Constants.REWARDED_VIDEO_BUTTON_WIDTH,
                Constants.REWARDED_VIDEO_BUTTON_HEIGHT,
                Constants.REWARDED_BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        onNoClicked();
                    }
                }
        );
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        Vector2 touchPos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        yesButton.render(batch, touchDownPos, touchPos, false);
        noButton.render(batch, touchDownPos, touchPos, false);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 touchUpPos = viewport.unproject(new Vector2(screenX, screenY));
        SuperBallGame superBallGame = (SuperBallGame) Gdx.app.getApplicationListener();
        if(yesButton.onClicked(touchDownPos, touchUpPos, superBallGame.assets.soundAssets.clickSound)) return true;
        if(noButton.onClicked(touchDownPos, touchUpPos, superBallGame.assets.soundAssets.clickSound)) return true;
        return false;
    }

    private void onYesClicked() {
        SuperBallGame superBallGame = (SuperBallGame) Gdx.app.getApplicationListener();
        superBallGame.actionResolver.showRewardedVideo(type);
    }

    private void onNoClicked() {
        SuperBallGame superBallGame = (SuperBallGame) Gdx.app.getApplicationListener();
        if (type == 1) {
            screen.restoreScreen();
        } else if (type == 2) {
            PlayScreen playScreen = (PlayScreen) screen;
            superBallGame.setScreen(new ResultScreen(superBallGame, playScreen.getScore(), playScreen.getObtainedCoins()));
        }
    }

    @Override
    public void recalculateButtonBounds() {
        super.recalculateButtonBounds();
        yesButton.recalculateButtonPosition(
                overlayBounds.x,
                overlayBounds.y
        );
        noButton.recalculateButtonPosition(
                overlayBounds.x + overlayBounds.width - noButton.getBounds().width,
                overlayBounds.y
        );
    }
}
