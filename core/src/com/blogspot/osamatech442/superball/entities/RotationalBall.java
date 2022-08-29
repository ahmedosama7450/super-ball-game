package com.blogspot.osamatech442.superball.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.blogspot.osamatech442.superball.screens.PlayScreen;
import com.blogspot.osamatech442.superball.utils.Constants;

public class RotationalBall extends Ball {
    private float elapsedTime;

    public RotationalBall(TextureRegion region, PlayScreen playScreen) {
        super(region, playScreen);
        this.elapsedTime = 0;
    }

    private float getRotation() {
        return -getXVelocitySign() * Math.abs(((elapsedTime / Constants.BALL_ROTATION_PERIODIC_TIME) % 1f) * 360f);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                region,
                bounds.x,
                bounds.y,
                bounds.width / 2,
                bounds.height / 2,
                bounds.width,
                bounds.height,
                1,
                1,
                getRotation()
        );
        elapsedTime += Gdx.graphics.getDeltaTime();
    }
}
