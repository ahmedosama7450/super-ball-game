package com.blogspot.osamatech442.superball.sections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.components.Button;
import com.blogspot.osamatech442.superball.components.PurchaseButton;
import com.blogspot.osamatech442.superball.screens.InstructionScreen;
import com.blogspot.osamatech442.superball.screens.MainScreen;
import com.blogspot.osamatech442.superball.screens.PlayScreen;
import com.blogspot.osamatech442.superball.utils.Assets;
import com.blogspot.osamatech442.superball.utils.ButtonInput;
import com.blogspot.osamatech442.superball.utils.Constants;

public class BallChooser extends InputAdapter {

    //Singleton Objects
    private MainScreen mainScreen;
    private Viewport viewport;
    private Preferences preferences;
    private Assets assets;
    private BitmapFont bitmapFont;
    private GlyphLayout textLayout;

    //Others
    private boolean isPaused;
    private int ballNumber;
    private Vector2 touchDownPos;

    //Buttons
    private Button ballChooser;
    private Button playButton;
    private Button rightArrow;
    private Button leftArrow;
    private PurchaseButton purchaseButton;

    public BallChooser(final MainScreen mainScreen) {
        //Singleton Objects
        this.mainScreen = mainScreen;
        this.viewport = mainScreen.viewport;
        this.preferences = mainScreen.preferences;
        this.assets = mainScreen.assets;
        this.bitmapFont = mainScreen.bitmapFont;
        this.textLayout = mainScreen.textLayout;

        //Others
        this.ballNumber = preferences.getInteger(Constants.LAST_BALL_KEY, Constants.BALL_DEFAULT);
        this.touchDownPos = new Vector2();
        this.isPaused = false;

        //Buttons
        this.playButton = new Button(
                assets.iconAssets.playIcon,
                Constants.PLAY_BUTTON_SIZE,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        preferences.putInteger(Constants.CURRENT_BALL_KEY, ballNumber).flush();
                        preferences.putInteger(Constants.LAST_BALL_KEY, ballNumber).flush();
                        SuperBallGame superBallGame = (SuperBallGame) Gdx.app.getApplicationListener();
                        if (preferences.getBoolean(Constants.SHOWS_INSTRUCTIONS_KEY, Constants.SHOWS_INSTRUCTIONS_DEFAULT))
                            superBallGame.setScreen(new InstructionScreen(superBallGame));
                        else
                            superBallGame.setScreen(new PlayScreen(superBallGame));
                    }
                }
        );

        this.rightArrow = new Button(
                assets.iconAssets.rightArrow,
                Constants.ARROW_WIDTH,
                Constants.ARROW_HEIGHT,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        if (assets.ballAssets.balls.length - 1 > ballNumber) ballNumber++;
                    }
                }
        );

        this.leftArrow = new Button(
                assets.iconAssets.leftArrow,
                Constants.ARROW_WIDTH,
                Constants.ARROW_HEIGHT,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        if (ballNumber > 0) ballNumber--;
                    }
                }
        );

        this.ballChooser = new Button(
                assets.ballAssets.balls[ballNumber],
                Constants.BALL_CHOOSER_SIZE,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                    }
                }
        );
        this.purchaseButton = new PurchaseButton(
                assets.prizeAssets.coin,
                assets.decorationAssets.moreTransBG,
                Constants.PURCHASE_BUTTON_WIDTH,
                Constants.PURCHASE_BUTTON_HEIGHT,
                Constants.BUTTON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        purchaseBall();
                    }
                }
        );

    }

    public void render(SpriteBatch batch) {
        Vector2 touchPos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        //Ball Chooser
        ballChooser.setImage(assets.ballAssets.balls[ballNumber]);
        ballChooser.render(batch, touchDownPos, touchPos, isPaused);

        //Right Arrow
        rightArrow.render(batch, touchDownPos, touchPos, isPaused);

        //Left Arrow
        leftArrow.render(batch, touchDownPos, touchPos, isPaused);

        if (isBallOwned())
            playButton.render(batch, touchDownPos, touchPos, isPaused);
        else
            purchaseButton.render(batch, touchDownPos, touchPos, isPaused, bitmapFont, textLayout, getBallPrice());

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDownPos = viewport.unproject(new Vector2(screenX, screenY));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 touchUpPos = viewport.unproject(new Vector2(screenX, screenY));
        Sound sound = assets.soundAssets.clickSound;
        if (rightArrow.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if (leftArrow.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if (isBallOwned()) {
            if (playButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        } else {
            if (purchaseButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        }

        return false;
    }

    private boolean isBallOwned() {
        return preferences.getInteger(String.valueOf(ballNumber), Constants.BALL_DEFAULT) == ballNumber;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    private int getnCoins() {
        return preferences.getInteger(Constants.COIN_NUMBER_KEY, Constants.COIN_NUMBER_DEFAULT);
    }

    private int getBallPrice() {
        switch (ballNumber) {
            case 1:
                return Constants.BALL1_PRICE;
            case 2:
                return Constants.BALL2_PRICE;
            case 3:
                return Constants.BALL3_PRICE;
            case 4:
                return Constants.BALL4_PRICE;
            case 5:
                return Constants.BALL5_PRICE;
            case 6:
                return Constants.BALL6_PRICE;
            case 7:
                return Constants.BALL7_PRICE;
            case 8:
                return Constants.BALL8_PRICE;
            case 9:
                return Constants.BALL9_PRICE;
            case 10:
                return Constants.BALL10_PRICE;
            case 11:
                return Constants.BALL11_PRICE;
            case 12:
                return Constants.BALL12_PRICE;
            case 13:
                return Constants.BALL13_PRICE;
            case 14:
                return Constants.BALL14_PRICE;
            default:
                return Constants.BALL0_PRICE;
        }
    }

    private void purchaseBall() {
        int nCoins = getnCoins();
        int ballPrice = getBallPrice();
        if (nCoins >= ballPrice) {
            preferences.putInteger(Constants.COIN_NUMBER_KEY, nCoins - ballPrice).flush();
            preferences.putInteger(String.valueOf(ballNumber), ballNumber).flush();
        } else {
            SuperBallGame superBallGame = (SuperBallGame) Gdx.app.getApplicationListener();
            if (superBallGame.actionResolver.hasRewardedVideo())
                mainScreen.setAdRewardedVideoOverlay();
        }
    }

    public void recalculateButtonPositions(float ballChooserYPos) {
        ballChooser.recalculateButtonPosition(
                viewport.getWorldWidth() / 2 - Constants.BALL_CHOOSER_SIZE / 2,
                ballChooserYPos
        );
        rightArrow.recalculateButtonPosition(
                viewport.getWorldWidth() / 2 + Constants.BALL_CHOOSER_SIZE / 2 + Constants.ARROW_MARGIN,
                ballChooserYPos + (Constants.BALL_CHOOSER_SIZE - Constants.ARROW_HEIGHT) / 2
        );
        leftArrow.recalculateButtonPosition(
                viewport.getWorldWidth() / 2 - Constants.BALL_CHOOSER_SIZE / 2 - Constants.ARROW_MARGIN - Constants.ARROW_WIDTH,
                ballChooserYPos + (Constants.BALL_CHOOSER_SIZE - Constants.ARROW_HEIGHT) / 2
        );
        playButton.recalculateButtonPosition(
                viewport.getWorldWidth() / 2 - Constants.PLAY_BUTTON_SIZE / 2,
                ballChooserYPos - Constants.PLAY_BUTTON_SIZE - Constants.PLAY_BUTTON_MARGIN
        );
        purchaseButton.recalculateButtonPosition(
                viewport.getWorldWidth() / 2 - Constants.PURCHASE_BUTTON_WIDTH / 2,
                ballChooserYPos - Constants.PURCHASE_BUTTON_HEIGHT - Constants.PURCHASE_BUTTON_MARGIN
        );

    }

}
