package com.blogspot.osamatech442.superball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.components.Line;
import com.blogspot.osamatech442.superball.utils.Constants;

public class ResultScreen extends MainScreen {

    private int score;
    private int bestScore;
    private int coins;

    private Line topLine;
    private Line middleLine;
    private Line bottomLine;
    private Line veryBottomLine;
    private Line verticalLine;

    public ResultScreen(SuperBallGame superBallGame, int score, int coins) {
        super(superBallGame);
        Preferences preferences = superBallGame.preferences;

        //Setting the score
        this.score = score;
        
        //Adding coins
        this.coins = coins;
        preferences.putInteger(
                Constants.COIN_NUMBER_KEY,
                preferences.getInteger(Constants.COIN_NUMBER_KEY, Constants.COIN_NUMBER_DEFAULT) + coins
        ).flush();

        //Defining Best Score
        int bestScore = preferences.getInteger(Constants.BEST_SCORE_KEY, Constants.BEST_SCORE_DEFAULT);
        if (score > bestScore) {
            preferences.putInteger(Constants.BEST_SCORE_KEY, score).flush();
            bestScore = score;
        }

        //Setting Best Score
        this.bestScore = bestScore;

        //Lines
        topLine = new Line(assets.decorationAssets.horLine);
        middleLine = new Line(assets.decorationAssets.horLine);
        bottomLine = new Line(assets.decorationAssets.horLine);
        veryBottomLine = new Line(assets.decorationAssets.horLine);
        verticalLine = new Line(assets.decorationAssets.horLine);

        //Increasing the number of playing the game
        SuperBallGame.playTimes++;

        //Showing the ads
        if (isToShowAd()) {
            superBallGame.actionResolver.showOrLoadInterstitialAd();
        }
    }

    private boolean isToShowAd() {
        return SuperBallGame.playTimes % 2 == 0;
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        clearScreen(SuperBallGame.backgroundColor);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        //top line
        topLine.render(batch);

        bitmapFont.setColor(Color.WHITE);

        //Your Score Text
        bitmapFont.getData().setScale(Constants.SCORE_TEXT_SCALE);
        textLayout.setText(bitmapFont, Constants.YOUR_SCORE_TEXT);
        bitmapFont.draw(
                batch,
                textLayout,
                ((viewport.getWorldWidth() - Constants.LINE_THICKNESS) / 2f - textLayout.width) / 2,
                topLine.bounds.y - Constants.SCORE_TEXT_MARGIN
        );

        //Best Score Text
        textLayout.setText(bitmapFont, Constants.BEST_SCORE_TEXT);
        bitmapFont.draw(
                batch,
                Constants.BEST_SCORE_TEXT,
                ((viewport.getWorldWidth() - Constants.LINE_THICKNESS) / 2f - textLayout.width) / 2 + (viewport.getWorldWidth() - Constants.LINE_THICKNESS) / 2,
                topLine.bounds.y - Constants.SCORE_TEXT_MARGIN
        );

        //Middle Line
        middleLine.render(batch);

        //best score
        bitmapFont.getData().setScale(Constants.SCORE_VALUE_SCALE);
        textLayout.setText(bitmapFont, bestScore + " m");
        bitmapFont.draw(
                batch,
                textLayout,
                ((viewport.getWorldWidth() - Constants.LINE_THICKNESS) / 2f - textLayout.width) / 2 + (viewport.getWorldWidth() - Constants.LINE_THICKNESS) / 2,
                middleLine.bounds.y - Constants.SCORE_VALUE_MARGIN
        );

        //score
        textLayout.setText(bitmapFont, score + " m");
        bitmapFont.draw(
                batch,
                textLayout,
                ((viewport.getWorldWidth() - Constants.LINE_THICKNESS) / 2f - textLayout.width) / 2,
                middleLine.bounds.y - (middleLine.bounds.y - bottomLine.bounds.y - Constants.LINE_THICKNESS - textLayout.height) / 2
        );

        //Bottom Line
        bottomLine.render(batch);

        //Vertical Line
        verticalLine.render(batch);

        //very bottom line
        veryBottomLine.render(batch);

        //Coins
        bitmapFont.getData().setScale(Constants.SCORE_COIN_SCALE);
        textLayout.setText(bitmapFont, Constants.STR_PLUS_SIGN + coins + Constants.STR_SCORE_COIN);
        float textXPos = viewport.getWorldWidth() / 2 - (textLayout.width + Constants.COIN_SCORE_XMARGIN + Constants.SCORE_COIN_SIZE) / 2;
        float coinYPos = bottomLine.bounds.y - Constants.SCORE_COIN_SIZE - (bottomLine.bounds.y - veryBottomLine.bounds.y - Constants.LINE_THICKNESS - Constants.SCORE_COIN_SIZE) / 2;
        bitmapFont.draw(
                batch,
                textLayout,
                textXPos,
                coinYPos + textLayout.height + Constants.SCORE_COIN_SIZE / 2 - textLayout.height / 2
        );

        batch.draw(
                assets.prizeAssets.coin,
                textXPos + textLayout.width + Constants.COIN_SCORE_XMARGIN,
                coinYPos,
                Constants.SCORE_COIN_SIZE,
                Constants.SCORE_COIN_SIZE
        );

        super.render(delta);

        batch.end();
    }

    private void clearScreen(Color color) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void recalculateBallChooserPosition() {
        float topLineYPos = buttonStage.facebookButton.getBounds().y - Constants.HOR_LINE_MARGIN;
        topLine.recalculateBounds(
                0, topLineYPos,
                viewport.getWorldWidth(),
                Constants.LINE_THICKNESS
        );

        bitmapFont.getData().setScale(Constants.SCORE_TEXT_SCALE);
        textLayout.setText(bitmapFont, Constants.BEST_SCORE_TEXT);
        float middleLineYPos = topLineYPos - 2 * Constants.SCORE_TEXT_MARGIN - textLayout.height - Constants.LINE_THICKNESS;
        middleLine.recalculateBounds(
                0, middleLineYPos,
                viewport.getWorldWidth(),
                Constants.LINE_THICKNESS
        );

        bitmapFont.getData().setScale(Constants.SCORE_VALUE_SCALE);
        textLayout.setText(bitmapFont, bestScore + " m");
        float bottomLineYPos = middleLineYPos - 2 * Constants.SCORE_VALUE_MARGIN - textLayout.height - Constants.LINE_THICKNESS;
        bottomLine.recalculateBounds(
                0, bottomLineYPos,
                viewport.getWorldWidth(),
                Constants.LINE_THICKNESS
        );

        verticalLine.recalculateBounds(
                viewport.getWorldWidth() / 2 - Constants.LINE_THICKNESS / 2,
                bottomLineYPos,
                Constants.LINE_THICKNESS,
                topLineYPos - bottomLineYPos
        );

        bitmapFont.getData().setScale(Constants.SCORE_COIN_SCALE);
        textLayout.setText(bitmapFont, Constants.STR_PLUS_SIGN + coins + Constants.STR_SCORE_COIN);
        float veryBottomLineYPos = bottomLineYPos - 2 * Constants.COIN_SCORE_YMARGIN - textLayout.height - Constants.LINE_THICKNESS;
        veryBottomLine.recalculateBounds(
                0, veryBottomLineYPos,
                viewport.getWorldWidth(),
                Constants.LINE_THICKNESS
        );
        ballChooserYPos = veryBottomLineYPos - Constants.BALL_CHOOSER_MARGIN - Constants.BALL_CHOOSER_SIZE;

        super.recalculateBallChooserPosition();
    }
}
