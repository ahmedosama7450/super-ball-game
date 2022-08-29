package com.blogspot.osamatech442.superball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.entities.Ball;
import com.blogspot.osamatech442.superball.entities.Platform;
import com.blogspot.osamatech442.superball.screens.PlayScreen;

public class PlatformCreator extends InputAdapter {

    //reference to play screen objects
    private Ball ball;
    private PlayScreen playScreen;
    private Array<Platform> platforms;
    private Viewport viewport;

    //coordinates info
    private Vector2 touchDownPos;
    private boolean isDragged;

    public PlatformCreator(PlayScreen playScreen) {
        //Initializing references
        this.playScreen = playScreen;
        this.platforms = playScreen.getPlatforms();
        this.viewport = playScreen.viewport;
        this.ball = playScreen.getBall();

        //Initializing coordinates info
        this.touchDownPos = new Vector2();
        this.isDragged = false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDownPos = viewport.unproject(new Vector2(screenX, screenY));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //setting and checking
        if (isDragged) {
            isDragged = false;
            return false;
        }

        //Getting the touch Position in the world coordinates
        Vector2 touchUpPos = viewport.unproject(new Vector2(screenX, screenY));

        //Drawing if possible
        drawPlatform(touchUpPos.x - Constants.PLATFORM_SIZE / 2, touchUpPos.y - Constants.PLATFORM_THICKNESS / 2);

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //setting and checking
        if (isDragged) return false;

        //Getting the touch Position in the world coordinates
        Vector2 touchDraggedPos = viewport.unproject(new Vector2(screenX, screenY));

        //Drawing if possible
        if (slidesRight(touchDraggedPos.x)) {
            //left
            drawFlaggedPlatform(
                    touchDraggedPos.x - Constants.PLATFORM_SIZE / 2,
                    touchDraggedPos.y - Constants.PLATFORM_THICKNESS / 2,
                    true
            );
            isDragged = true;
        } else if (slidesLeft(touchDraggedPos.x)) {
            //right
            drawFlaggedPlatform(
                    touchDraggedPos.x - Constants.PLATFORM_SIZE / 2,
                    touchDraggedPos.y - Constants.PLATFORM_THICKNESS / 2,
                    false
            );
            isDragged = true;
        }

        return false;
    }

    private boolean slidesLeft(float xDragged) {
        return touchDownPos.x > xDragged && touchDownPos.x - xDragged >= Constants.PLATFORM_CREATION_DIS;
    }

    private boolean slidesRight(float xDragged) {
        return xDragged > touchDownPos.x && xDragged - touchDownPos.x >= Constants.PLATFORM_CREATION_DIS;
    }

    private void drawPlatform(float x, float y) {
        //creating the platform
        Platform newPlatform = createPlatform(x, y);

        //checking for collisions
        if (!(overlapsPlatforms(newPlatform.getBounds()))) {
            if (overlapsBall(newPlatform.getBounds())) {
                newPlatform.setYPosition(ball.getBounds().y - Constants.PLATFORM_THICKNESS - Constants.BALL_PLATFORM_CREATION_MARGIN);
            }
            platforms.add(newPlatform);
        }
    }

    private void drawFlaggedPlatform(float x, float y, boolean isLeftFlag /* -1 for true, 1 for false*/) {
        //Creating the platforms
        Platform newPlatform = createPlatform(x, y);
        Platform newFlag;
        if (isLeftFlag)
            newFlag = createLeftFlag(x, y);
        else
            newFlag = createRightFlag(x, y);

        //checking for collisions
        if (!overlapsPlatforms(newPlatform.getBounds()) && !overlapsPlatforms(newFlag.getBounds())) {
            if (overlapsBall(newPlatform.getBounds())) {
                newPlatform.setYPosition(ball.getBounds().y - Constants.PLATFORM_THICKNESS - Constants.BALL_PLATFORM_CREATION_MARGIN);
                newFlag.setYPosition(newPlatform.getBounds().y);
            } else if (overlapsBall(newFlag.getBounds())) {
                newFlag.setYPosition(ball.getBounds().y - Constants.FLAG_SIZE - Constants.BALL_PLATFORM_CREATION_MARGIN);
                newPlatform.setYPosition(newFlag.getBounds().y);
            }
            platforms.add(newPlatform);
            platforms.add(newFlag);
        }

    }

    public void addPlatform(float x, float y) {
        platforms.add(createPlatform(x, y));
    }

    public void addFlaggedPlatform(float x, float y, boolean isLeftFlag) {
        platforms.add(createPlatform(x, y));
        if (isLeftFlag)
            platforms.add(createLeftFlag(x, y));
        else
            platforms.add(createRightFlag(x, y));
    }

    public void addPortal(float y) {
        Platform portal = new Platform(
                -Constants.WALL_PLATFORM_PADDING, y,
                Platform.PlatformType.PORTAL,
                playScreen.getPlatformRegion(),
                playScreen.assets.prizeAssets.coin,
                viewport
        );
        platforms.add(portal);
        playScreen.setCurrentPortal(portal);
    }

    public void addHalfPortals(float y, Platform currentPortal) {
        platforms.add(new Platform(
                -Constants.WALL_PLATFORM_PADDING, y,
                Platform.PlatformType.HALF_PORTAL,
                playScreen.getPlatformRegion(),
                playScreen.assets.prizeAssets.coin,
                viewport
        ));
        platforms.add(new Platform(
                viewport.getWorldWidth() - (Constants.HALF_PORTAL_SIZE - Constants.WALL_PLATFORM_PADDING), y,
                Platform.PlatformType.HALF_PORTAL,
                playScreen.getPlatformRegion(),
                playScreen.assets.prizeAssets.coin,
                viewport
        ));
        playScreen.setHalfPortalYPos(currentPortal.getBounds().y);
        platforms.removeValue(currentPortal, false);
        playScreen.setCurrentPortal(null);
    }

    private boolean overlapsBall(Rectangle newPlatformBounds) {
        //checking the bounds of the ball
        return newPlatformBounds.overlaps(ball.getBounds());
    }

    private boolean overlapsPlatforms(Rectangle newPlatformBounds) {
        //checking the bounds of the other platforms
        for (Platform platform : platforms) {
            if (newPlatformBounds.overlaps(platform.getBounds())) {
                return true;
            }
        }

        return false;
    }

    private Platform createPlatform(float x, float y) {
        return new Platform(
                x, y,
                Platform.PlatformType.PLATFORM,
                playScreen.getPlatformRegion(),
                playScreen.assets.prizeAssets.coin,
                viewport
        );
    }

    private Platform createLeftFlag(float x, float y) {
        return new Platform(
                x,
                y,
                Platform.PlatformType.FLAG,
                playScreen.getPlatformRegion(),
                playScreen.assets.prizeAssets.coin,
                viewport
        );
    }

    private Platform createRightFlag(float x, float y) {
        return new Platform(
                x + Constants.PLATFORM_SIZE - Constants.PLATFORM_THICKNESS,
                y,
                Platform.PlatformType.FLAG,
                playScreen.getPlatformRegion(),
                playScreen.assets.prizeAssets.coin,
                viewport
        );
    }


}
