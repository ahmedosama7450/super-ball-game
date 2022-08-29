package com.blogspot.osamatech442.superball.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.screens.AdvancedScreen;
import com.blogspot.osamatech442.superball.utils.Constants;

public class NoAdRewardedVideoOverlay extends RewardedVideoOverlay {
    public Rectangle okButtonBounds;

    public NoAdRewardedVideoOverlay(AdvancedScreen screen, TextureRegion region, Viewport viewport) {
        super(screen, region, viewport);
        okButtonBounds = new Rectangle(0, 0, overlayBounds.width, Constants.REWARDED_VIDEO_BUTTON_HEIGHT);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 touchUpPos = viewport.unproject(new Vector2(screenX, screenY));
        if (okButtonBounds.contains(touchDownPos) && okButtonBounds.contains(touchUpPos)) {
            onOkClicked();
            return true;
        }
        return false;
    }

    public void onOkClicked() {
        SuperBallGame superBallGame = (SuperBallGame) Gdx.app.getApplicationListener();
        superBallGame.runClickSound();
        screen.restoreScreen();
    }

    @Override
    public void recalculateButtonBounds() {
        super.recalculateButtonBounds();
        okButtonBounds.setPosition(
                overlayBounds.x,
                overlayBounds.y
        );
    }


}
