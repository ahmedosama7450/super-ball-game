package com.blogspot.osamatech442.superball.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.blogspot.osamatech442.superball.utils.ButtonInput;
import com.blogspot.osamatech442.superball.utils.Constants;

public class CoinButton extends Button{
    private float coinButtonWidth;

    public CoinButton(TextureRegion image, float size, float pressInc, ButtonInput buttonInput) {
        super(image, size, pressInc, buttonInput);
        this.coinButtonWidth = Constants.ICON_SIZE + Constants.HUD2_INNER_MARGIN + Constants.NCOIN_SIZE;
    }

    public void render(SpriteBatch batch, Vector2 touchDownPos, Vector2 touchPos, boolean isPaused, BitmapFont bitmapFont, GlyphLayout textLayout, int nCoin, float worldHeight) {
        super.render(batch, touchDownPos, touchPos, isPaused);

        //number of coins
        bitmapFont.setColor(Constants.PURCHASE_COIN_FONT_COLOR);
        bitmapFont.getData().setScale(Constants.PURCHASE_COIN_FONT_SCALE);
        textLayout.setText(bitmapFont, nCoin + " " + Constants.STR_PLUS_SIGN);
        bitmapFont.draw(
                batch,
                textLayout,
                Constants.HUD2_OUTER_MARGIN + Constants.ICON_SIZE + Constants.HUD2_INNER_MARGIN,
                worldHeight - (Constants.HUD2_OUTER_MARGIN + Constants.ICON_SIZE / 2 - textLayout.height / 2)
        );
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(
                bounds.x,
                bounds.y,
                coinButtonWidth,
                bounds.height
        );
    }
}
