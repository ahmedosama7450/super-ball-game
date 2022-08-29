package com.blogspot.osamatech442.superball.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    protected Rectangle bounds;

    public abstract void render(SpriteBatch batch);
    public Rectangle getBounds() {
        return bounds;
    }
}
