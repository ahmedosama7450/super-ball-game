package com.blogspot.osamatech442.superball.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.blogspot.osamatech442.superball.utils.ButtonInput;
import com.blogspot.osamatech442.superball.utils.Constants;

public class PurchaseButton extends Button {
    public TextureRegion coinRegion;

    public PurchaseButton(TextureRegion coinRegion, TextureRegion image, float width, float height, float pressInc, ButtonInput buttonInput) {
        super(image, width, height, pressInc, buttonInput);
        this.coinRegion = coinRegion;
    }

    public void render(SpriteBatch batch, Vector2 touchDownPos, Vector2 touchPos, boolean isPaused, BitmapFont bitmapFont, GlyphLayout textLayout, int ballPrice) {
        //Background
        super.render(batch, touchDownPos, touchPos, isPaused);

        //price
        bitmapFont.setColor(Constants.PURCHASE_COIN_FONT_COLOR);
        bitmapFont.getData().setScale(Constants.PURCHASE_COIN_FONT_SCALE);
        textLayout.setText(bitmapFont, String.valueOf(ballPrice));

        float xMargin = (Constants.PURCHASE_BUTTON_WIDTH - (Constants.PURCHASE_COIN_SIZE + textLayout.width + Constants.PURCHASE_INNER_MARGIN)) / 2f;
        float yMargin = (Constants.PURCHASE_BUTTON_HEIGHT - Constants.PURCHASE_COIN_SIZE) / 2f;

        bitmapFont.draw(
                batch,
                textLayout,
                bounds.x + xMargin,
                bounds.y + yMargin + Constants.PURCHASE_COIN_SIZE / 2 + textLayout.height / 2
        );

        //purchase coin
        batch.draw(
                coinRegion,
                bounds.x + xMargin + textLayout.width + Constants.PURCHASE_INNER_MARGIN,
                bounds.y + Constants.PURCHASE_BUTTON_HEIGHT / 2 - Constants.PURCHASE_COIN_SIZE / 2,
                Constants.PURCHASE_COIN_SIZE,
                Constants.PURCHASE_COIN_SIZE

        );
    }
}
