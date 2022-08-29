package com.blogspot.osamatech442.superball.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Line {
    public Rectangle bounds;
    public TextureRegion region;

    public Line(TextureRegion region) {
        this.region = region;
        this.bounds = new Rectangle();
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                region,
                bounds.x,
                bounds.y,
                bounds.width,
                bounds.height
        );
    }

    public void recalculateBounds(float x, float y, float width, float height) {
        bounds.set(x, y, width, height);
    }
}
