package com.blogspot.osamatech442.superball.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.blogspot.osamatech442.superball.utils.Constants;

public class Key extends Entity {

    private TextureRegion region;

    public Key(TextureRegion region, float x, float y) {
        this.region = region;
        this.bounds = new Rectangle(x, y, Constants.KEY_SIZE, Constants.KEY_SIZE);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                region,
                bounds.x,
                bounds.y,
                Constants.KEY_SIZE,
                Constants.KEY_SIZE
        );
    }

}
