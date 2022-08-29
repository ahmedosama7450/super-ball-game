package com.blogspot.osamatech442.superball.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.utils.Constants;


public class Platform extends Entity {

    //Type Definition
    public enum PlatformType {
        PLATFORM, FLAG, PORTAL, HALF_PORTAL
    }

    //Properties
    private PlatformType platformType;
    private TextureRegion coin;
    private TextureRegion platformRegion;

    public Platform(float x, float y, PlatformType platformType, TextureRegion platformRegion, TextureRegion coin, Viewport viewport) {
        //Initializing Properties
        this.platformType = platformType;
        this.platformRegion = platformRegion;
        this.coin = coin;

        //Initializing bounds
        this.bounds = new Rectangle(x, y, 0, 0);
        switch (platformType) {
            case FLAG:
                bounds.width = Constants.PLATFORM_THICKNESS;
                bounds.height = Constants.FLAG_SIZE;
                break;
            case PLATFORM:
                bounds.width = Constants.PLATFORM_SIZE;
                bounds.height = Constants.PLATFORM_THICKNESS;
                break;
            case PORTAL:
                bounds.width = viewport.getWorldWidth() + 2 * Constants.WALL_PLATFORM_PADDING;
                bounds.height = Constants.PLATFORM_THICKNESS;
                break;
            case HALF_PORTAL:
                bounds.width = Constants.HALF_PORTAL_SIZE;
                bounds.height = Constants.PLATFORM_THICKNESS;
                break;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                platformRegion,
                bounds.x,
                bounds.y,
                bounds.width,
                bounds.height
        );

        if (platformType == PlatformType.PORTAL) {
            batch.draw(
                    coin,
                    bounds.width / 2 - Constants.PORTAL_COIN_SIZE / 2,
                    bounds.y - (Constants.PORTAL_COIN_SIZE - Constants.PLATFORM_THICKNESS) / 2,
                    Constants.PORTAL_COIN_SIZE,
                    Constants.PORTAL_COIN_SIZE
            );
        }
    }

    public void setYPosition(float y) {
        bounds.y = y;
    }

}
