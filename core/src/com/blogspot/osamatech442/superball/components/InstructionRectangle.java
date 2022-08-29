package com.blogspot.osamatech442.superball.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class InstructionRectangle {
    public TextureRegion region;
    public Rectangle bounds;

    public InstructionRectangle(TextureRegion region, float width, float height) {
        this.region = region;
        this.bounds = new Rectangle(0, 0, width, height);
    }

    public void setPosition(float x, float y) {
        bounds.setPosition(x, y);
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
}
