package com.blogspot.osamatech442.superball.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.blogspot.osamatech442.superball.utils.Constants;

public class Coin extends Entity {
    private TextureRegion region;

    public Coin(TextureRegion region, float x, float y) {
        this.region = region;
        this.bounds = new Rectangle(x, y, Constants.PRIZE_COIN_SIZE, Constants.PRIZE_COIN_SIZE);
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


}
