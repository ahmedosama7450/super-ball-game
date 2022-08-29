package com.blogspot.osamatech442.superball.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Icon {
    protected Rectangle bounds;

    public Icon(float size) {
        this.bounds = new Rectangle(0, 0, size, size);
    }

    public void render(TextureRegion image, SpriteBatch batch) {
        batch.draw(
                image,
                bounds.x,
                bounds.y,
                bounds.width,
                bounds.height
        );
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void recalculateButtonPosition(float x, float y) {
        this.bounds.setPosition(x, y);
    }

}
