package com.blogspot.osamatech442.superball.overlays;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.screens.AdvancedScreen;
import com.blogspot.osamatech442.superball.utils.Constants;

public abstract class RewardedVideoOverlay extends InputAdapter {

    public AdvancedScreen screen;
    public TextureRegion region;
    public Rectangle overlayBounds;
    public Vector2 touchDownPos;
    public Viewport viewport;

    public RewardedVideoOverlay(AdvancedScreen screen, TextureRegion region, Viewport viewport) {
        this.viewport = viewport;
        this.region = region;
        this.overlayBounds = new Rectangle(0, 0, Constants.REWARDED_OVERLAY_WIDTH, Constants.REWARDED_OVERLAY_HEIGHT);
        this.touchDownPos = new Vector2();
        this.screen = screen;
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                region,
                overlayBounds.x,
                overlayBounds.y,
                overlayBounds.width,
                overlayBounds.height
        );
    }

    public void recalculateButtonBounds() {
        overlayBounds.setPosition(
                viewport.getWorldWidth() / 2 - overlayBounds.width / 2,
                viewport.getWorldHeight() / 2 - overlayBounds.height / 2
        );

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDownPos.set(viewport.unproject(new Vector2(screenX, screenY)));
        return false;
    }
}
