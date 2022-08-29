package com.blogspot.osamatech442.superball.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.components.Button;
import com.blogspot.osamatech442.superball.components.Icon;
import com.blogspot.osamatech442.superball.screens.PlayScreen;
import com.blogspot.osamatech442.superball.utils.Assets;
import com.blogspot.osamatech442.superball.utils.ButtonInput;
import com.blogspot.osamatech442.superball.utils.Constants;

public class Hud extends InputAdapter {

    private PlayScreen playScreen;
    private Viewport viewport;
    private Assets assets;

    private Vector2 touchDownPos;

    private Button soundButton;
    private Button pauseButton;
    private Icon leftKeyIcon;
    private Icon middleKeyIcon;
    private Icon rightKeyIcon;

    public Hud(final PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.viewport = playScreen.hudViewPort;
        this.assets = playScreen.assets;
        final Preferences preferences = playScreen.preferences;

        this.touchDownPos = new Vector2();

        soundButton = new Button(
                assets.iconAssets.soundIcon,
                Constants.ICON_SIZE,
                Constants.ICON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        Music music = assets.soundAssets.backgroundMusic;
                        if (music.isPlaying()) {
                            music.stop();
                            preferences.putBoolean(Constants.IS_MUSIC_ON_KEY, false).flush();
                        } else {
                            music.play();
                            preferences.putBoolean(Constants.IS_MUSIC_ON_KEY, true).flush();
                        }
                    }
                }
        );
        pauseButton = new Button(assets.iconAssets.pauseIcon,
                Constants.ICON_SIZE,
                Constants.ICON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        playScreen.setPauseOverlay();
                    }
                }
        );

        leftKeyIcon = new Icon(Constants.ICON_SIZE);
        middleKeyIcon = new Icon(Constants.ICON_SIZE);
        rightKeyIcon = new Icon(Constants.ICON_SIZE);

    }

    public void render(SpriteBatch batch, BitmapFont bitmapFont, GlyphLayout textLayout) {
        Vector2 touchPos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        //sound icon
        soundButton.setImage(
                assets.soundAssets.backgroundMusic.isPlaying() ?
                        assets.iconAssets.soundIcon :
                        assets.iconAssets.noSoundIcon
        );
        soundButton.render(batch, touchDownPos, touchPos, false);

        //pause icon
        pauseButton.render(batch, touchDownPos, touchPos, false);

        //keys icons
        TextureAtlas.AtlasRegion[] keyImages = new TextureAtlas.AtlasRegion[3];
        int obtainedKeys = playScreen.getVirtualObtainedKeys() > 3 ? 2 : playScreen.getVirtualObtainedKeys() - 1;
        //obtained keys
        for (int i = 0; i <= obtainedKeys; i++) {
            keyImages[i] =assets.iconAssets.keyIcon;
        }
        //not obtained keys
        for (int i = 2; i > obtainedKeys && i >= 0; i--) {
            keyImages[i] = assets.iconAssets.blankKeyIcon;
        }

        leftKeyIcon.render(keyImages[0], batch);
        middleKeyIcon.render(keyImages[1], batch);
        rightKeyIcon.render(keyImages[2], batch);

        //display score
        bitmapFont.setColor(Constants.SCORE_FONT_COLOR);
        bitmapFont.getData().setScale(Constants.SCORE_FONT_SCALE);
        textLayout.setText(bitmapFont, String.valueOf(playScreen.getScore()));
        bitmapFont.draw(
                batch,
                textLayout,
                viewport.getWorldWidth() / 2 - textLayout.width / 2,
                viewport.getWorldHeight() - Constants.ICON_SIZE - 2 * Constants.HUD_SCORE_MARGIN
        );
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
        if(soundButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        if(pauseButton.onClicked(touchDownPos, touchUpPos, sound)) return true;
        return false;
    }

    public void recalculateButtonPositions() {
        soundButton.recalculateButtonPosition(
                Constants.HUD1_OUTER_MARGIN,
                viewport.getWorldHeight() - Constants.ICON_SIZE - Constants.HUD1_OUTER_MARGIN
        );
        pauseButton.recalculateButtonPosition(
                viewport.getWorldWidth() - Constants.ICON_SIZE - Constants.HUD1_OUTER_MARGIN,
                viewport.getWorldHeight() - Constants.ICON_SIZE - Constants.HUD1_OUTER_MARGIN
        );
        float midX = viewport.getWorldWidth() / 2 - Constants.ICON_SIZE / 2;
        rightKeyIcon.recalculateButtonPosition(
                midX + Constants.ICON_SIZE + Constants.HUD1_INNER_MARGIN,
                viewport.getWorldHeight() - Constants.ICON_SIZE - Constants.HUD1_OUTER_MARGIN
        );
        leftKeyIcon.recalculateButtonPosition(
                midX - Constants.ICON_SIZE - Constants.HUD1_INNER_MARGIN,
                viewport.getWorldHeight() - Constants.ICON_SIZE - Constants.HUD1_OUTER_MARGIN
        );
        middleKeyIcon.recalculateButtonPosition(
                midX,
                viewport.getWorldHeight() - Constants.ICON_SIZE - Constants.HUD1_OUTER_MARGIN
        );

    }


}
