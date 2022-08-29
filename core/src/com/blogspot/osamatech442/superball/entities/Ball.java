package com.blogspot.osamatech442.superball.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.screens.PlayScreen;
import com.blogspot.osamatech442.superball.utils.Constants;

public class Ball extends Entity {

    //Properties
    protected TextureRegion region;

    //motion
    private Vector2 velocity;
    private float initialYVelocity;
    private float yAcceleration;
    private boolean isBouncing;

    //tracking variables
    private float elapsedBounceTime;
    private Vector2 lastPosition;
    private boolean isOnPlatform;
    private Platform myPlatform;

    private PlayScreen playScreen;

    public Ball(TextureRegion region, PlayScreen playScreen) {
        //Properties
        this.region = region;
        this.playScreen = playScreen;

        //initializing motion variables
        this.bounds = new Rectangle(
                0,
                0,
                2 * Constants.BALL_RADIUS,
                2 * Constants.BALL_RADIUS
        );
        this.initialYVelocity = Constants.BALL_INITIAL_YVELOCITY;
        this.yAcceleration = Constants.BALL_INITIAL_YACCELERATION;
        this.velocity = new Vector2(Constants.BALL_CONSTANT_XVELOCITY, initialYVelocity);
        this.isBouncing = false;

        //Initializing tracking variables
        this.elapsedBounceTime = 0;
        this.lastPosition = new Vector2();
        this.isOnPlatform = false;
        this.myPlatform = null;
    }

    public void init(float x, float y) {
        bounds.setPosition(x, y);
        lastPosition.set(x, y);
    }

    private void reverseXVelocityRight() {
        velocity.x = Math.abs(velocity.x);
    }

    private void reverseXVelocityLeft() {
        velocity.x = -Math.abs(velocity.x);
    }

    private void putAbove(Platform platform) {
        this.bounds.y = platform.getBounds().y + platform.getBounds().height;
        isOnPlatform = true;
        myPlatform = platform;
        velocity.y = initialYVelocity;
    }

    private void putToRight(Rectangle platformBounds) {
        this.bounds.x = platformBounds.x + platformBounds.width;
    }

    private void putToLeft(Rectangle platformBounds) {
        this.bounds.x = platformBounds.x - 2 * Constants.BALL_RADIUS;
    }

    private boolean hasLandedOnPlatform(Rectangle platformBounds) {
        return bounds.x + bounds.width > platformBounds.x &&
                bounds.x < platformBounds.x + platformBounds.width &&
                lastPosition.y >= platformBounds.y + platformBounds.height &&
                bounds.y < platformBounds.y + platformBounds.height;
    }

    private boolean hasFallenOffPlatform(Rectangle platformBounds) {
        return bounds.x + bounds.width < platformBounds.x ||
                bounds.x > platformBounds.x + platformBounds.width;
    }

    private void fallOffPlatform() {
        isOnPlatform = false;
        myPlatform = null;
    }

    private boolean hitsPlatformRight(Rectangle platformBounds) {
        return lastPosition.x > bounds.x && lastPosition.x >= platformBounds.x + platformBounds.width;
    }

    private boolean hitsPlatformLeft(Rectangle platformBounds) {
        return lastPosition.x < bounds.x && lastPosition.x + bounds.width <= platformBounds.x;
    }

    private void startBouncing() {
        isBouncing = true;
        elapsedBounceTime = 0;
    }

    private void bounce(float delta) {
        bounds.x += getXVelocitySign() * Constants.BALL_BOUNCE_VELOCITY * delta;
        elapsedBounceTime += delta;
    }

    private void endBouncing() {
        isBouncing = false;
    }

    private void move(float delta) {
        bounds.x += velocity.x * delta;
    }

    private void applyGravity(float delta) {
        //Gdx.app.log("TAG", "before v : " + velocity.y);
        velocity.y += yAcceleration * delta;
        //Gdx.app.log("TAG", "gravity : " + yAcceleration);
        //Gdx.app.log("TAG", "delta : " + delta);
        //Gdx.app.log("TAG", "amount : " + yAcceleration * delta);
        //Gdx.app.log("TAG", "after v : " + velocity.y);
        bounds.y -= velocity.y * delta;
    }

    int getXVelocitySign() {
        return velocity.x > 0 ? 1 : -1;
    }

    public void update(float delta) {
        //Movement
        if (isOnPlatform) {
            move(delta);
        } else {
            applyGravity(delta);
            if (isBouncing) {
                if (elapsedBounceTime >= Constants.BALL_BOUNCING_TIME)
                    endBouncing();
                else
                    bounce(delta);
            }
        }

        //Collisions
        if (myPlatform != null && hasFallenOffPlatform(myPlatform.getBounds())) {
            fallOffPlatform();
            startBouncing();
        }

        boolean isAlreadyHit = false;
        for (Platform platform : playScreen.getPlatforms()) {
            final Rectangle platformBounds = platform.getBounds();
            if (bounds.overlaps(platformBounds)) {
                //falling
                if (!isOnPlatform && hasLandedOnPlatform(platformBounds)) {
                    putAbove(platform);
                    //crossing the portals if possible
                    if (platform == playScreen.getCurrentPortal() && playScreen.getVirtualObtainedKeys() >= Constants.NEEDED_KEYS) {
                        //rewarding the player
                        playScreen.onPortalCrossed();

                        //letting the ball fall
                        fallOffPlatform();
                    }

                }

                //hitting
                if (!isAlreadyHit) {
                    //right the ball
                    if (hitsPlatformLeft(platformBounds)) {
                        putToLeft(platformBounds);
                        reverseXVelocityLeft();
                        endBouncing();
                        isAlreadyHit = true;
                    } else if (hitsPlatformRight(platformBounds)) {
                        putToRight(platformBounds);
                        reverseXVelocityRight();
                        endBouncing();
                        isAlreadyHit = true;
                    }
                }
            }
        }

        if (!isAlreadyHit) {
            if (bounds.x < 0) {
                this.bounds.x = 0;
                reverseXVelocityRight();
            } else if (bounds.x + bounds.width > playScreen.viewport.getWorldWidth()) {
                this.bounds.x = playScreen.viewport.getWorldWidth() - bounds.width;
                reverseXVelocityLeft();
            }
        }

        //keeping the last position of the ball
        lastPosition.set(bounds.x, bounds.y);
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                region,
                bounds.x,
                bounds.y,
                bounds.width,
                bounds.height
        );

    }

    public void speedMeUp() {
        float newXVelocity = this.velocity.x * Constants.SPEED_INCREMENT_MULTIPLIER;
        this.velocity.x = newXVelocity <= Constants.BALL_MAX_XVELOCITY ? newXVelocity : Constants.BALL_MAX_XVELOCITY;

        float newYAcceleration = this.yAcceleration * Constants.SPEED_INCREMENT_MULTIPLIER;
        this.yAcceleration = newYAcceleration <= Constants.BALL_MAX_YACCELERATION ? newYAcceleration : Constants.BALL_MAX_YACCELERATION;

        float newYVelocity = this.initialYVelocity * Constants.SPEED_INCREMENT_MULTIPLIER;
        this.initialYVelocity = newYVelocity <= Constants.BALL_MAX_YVELOCITY ? newYVelocity : Constants.BALL_MAX_YVELOCITY;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void prepareBall(Viewport viewport) {
        bounds.x = viewport.getWorldWidth() / 2 - Constants.BALL_RADIUS;
        lastPosition.x = bounds.x;

        velocity.set(Math.abs(velocity.x), initialYVelocity);

        isBouncing = false;
        elapsedBounceTime = 0;

        isOnPlatform = false;
        myPlatform = null;
    }

}
