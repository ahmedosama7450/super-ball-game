package com.blogspot.osamatech442.superball.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.blogspot.osamatech442.superball.utils.Constants;

public class Spike extends Entity {

    private Rectangle realBounds;
    private float rotation;
    private TextureRegion region;

    public Spike(TextureRegion region, float x, float y) {
        this.bounds = new Rectangle(x, y, Constants.SPIKE_SIZE, Constants.SPIKE_SIZE);
        this.realBounds = new Rectangle(
                x + (Constants.SPIKE_SIZE / 2 - Constants.SPIKE_REAL_SIZE / 2),
                y + (Constants.SPIKE_SIZE / 2 - Constants.SPIKE_REAL_SIZE / 2),
                Constants.SPIKE_REAL_SIZE,
                Constants.SPIKE_REAL_SIZE
        );
        this.rotation = 0;
        this.region = region;
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                region,
                bounds.x,
                bounds.y,
                bounds.width / 2,
                bounds.height / 2,
                bounds.width,
                bounds.height,
                1f,
                1f,
                rotation
        );
    }

    public Rectangle getRealBounds() {
        return realBounds;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
