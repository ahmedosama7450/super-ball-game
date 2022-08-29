package com.blogspot.osamatech442.superball.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.utils.ButtonInput;

public class Button {

    protected Rectangle bounds;
    private Rectangle bigBounds;
    private TextureRegion image;
    private ButtonInput buttonInput;
    private float pressInc;

    public Button(TextureRegion image, float width, float height, float pressInc, ButtonInput buttonInput) {
        this.image = image;
        this.bounds = new Rectangle(0, 0, width, height);
        this.bigBounds = new Rectangle(0, 0, width + pressInc, height * (width + pressInc) / width);
        this.buttonInput = buttonInput;
        this.pressInc = pressInc;
    }

    public Button(TextureRegion image, float size, float pressInc, ButtonInput buttonInput) {
        this(image, size, size, pressInc, buttonInput);
    }

    public void render(SpriteBatch batch, Vector2 touchDownPos, Vector2 touchPos, boolean isPaused) {
        if (isSmallBounds(touchDownPos, touchPos, isPaused)) {
            batch.draw(image, bounds.x, bounds.y, bounds.width, bounds.height);
        } else {
            batch.draw(image, bigBounds.x, bigBounds.y, bigBounds.width, bigBounds.height);
        }
    }

    private boolean isSmallBounds(Vector2 touchDownPos, Vector2 touchPos, boolean isPaused) {
        return isPaused || !(Gdx.input.isTouched() && bounds.contains(touchDownPos) && bounds.contains(touchPos));
    }

    public void setImage(TextureRegion image) {
        this.image = image;
    }

    public boolean onClicked(Vector2 touchDownPos, Vector2 touchUpPos, Sound sound) {
        if (getBounds().contains(touchDownPos) && getBounds().contains(touchUpPos)) {
            runClickSound(sound);
            buttonInput.onClicked();
            return true;
        }
        return false;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void recalculateButtonPosition(float x, float y) {
        this.bounds.setPosition(x, y);
        this.bigBounds.setPosition(x - pressInc / 2, y - (bigBounds.height - bounds.height) / 2);
    }

    public void setButtonInput(ButtonInput buttonInput) {
        this.buttonInput = buttonInput;
    }

    private void runClickSound(Sound sound) {
        if (SuperBallGame.isSoundOn) {
            sound.play();
        }
    }

}
