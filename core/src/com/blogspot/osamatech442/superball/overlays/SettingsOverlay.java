package com.blogspot.osamatech442.superball.overlays;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.screens.MainScreen;
import com.blogspot.osamatech442.superball.utils.Assets;
import com.blogspot.osamatech442.superball.utils.ButtonInput;
import com.blogspot.osamatech442.superball.utils.Constants;

public class SettingsOverlay extends Overlay {

    private Preferences preferences;
    private Assets assets;
    private Item soundItem, musicItem, platformItem, backgroundItem;

    public SettingsOverlay(MainScreen mainScreen) {
        super(mainScreen, Constants.SETTINGS_OVERLAY_TITLE);
        this.preferences = mainScreen.preferences;
        this.assets = mainScreen.assets;

        //sound
        soundItem = addItem(
                Constants.SOUND_TITLE,
                assets.overlayAssets.overlaySoundIcon,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        if (SuperBallGame.isSoundOn) {
                            SuperBallGame.isSoundOn = false;
                            preferences.putBoolean(Constants.IS_SOUND_ON_KEY, false).flush();
                        } else {
                            SuperBallGame.isSoundOn = true;
                            preferences.putBoolean(Constants.IS_SOUND_ON_KEY, true).flush();
                        }
                    }
                }
        );

        //music
        musicItem = addItem(
                Constants.MUSIC_TITLE,
                assets.overlayAssets.overlayMusicIcon,
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

        //platform color
        platformItem = addItem(
                Constants.PLATFORM_COLOR_TITLE,
                assets.colorIconAssets.platformIcons[preferences.getInteger(Constants.PLATFORM_IMAGE_KEY, Constants.DEFAULT_PLATFORM_INDEX)],
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                    }
                }
        );
        platformItem.setButtonInput(new ButtonInput() {
            @Override
            public void onClicked() {
                int index = preferences.getInteger(Constants.PLATFORM_IMAGE_KEY, Constants.DEFAULT_PLATFORM_INDEX) + 1;
                if (index > Constants.PLATFORMS_COUNT - 1) {
                    index = 0;
                }
                preferences.putInteger(Constants.PLATFORM_IMAGE_KEY, index).flush();
                platformItem.icon.setImage(assets.colorIconAssets.platformIcons[index]);
            }
        });

        //background color
        backgroundItem = addItem(
                Constants.BACKGROUND_COLOR_TITLE,
                assets.colorIconAssets.backgroundIcons[preferences.getInteger(Constants.BACKGROUND_COLOR_KEY, Constants.DEFAULT_BACKGROUND_COLOR)],
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                    }
                }
        );
        backgroundItem.setButtonInput(new ButtonInput() {
            @Override
            public void onClicked() {
                int index = preferences.getInteger(Constants.BACKGROUND_COLOR_KEY, Constants.DEFAULT_BACKGROUND_COLOR) + 1;
                if (index > Constants.BACKGROUNDS_COUNT - 1) {
                    index = 0;
                }
                SuperBallGame.backgroundIndex = index;
                SuperBallGame.backgroundColor = Color.valueOf(Constants.BACKGROUNDS_COLORS[index]);
                preferences.putInteger(Constants.BACKGROUND_COLOR_KEY, index).flush();
                preferences.putInteger(Constants.ENTER_TIMES_KEY, Constants.ENTER_TIMES_DEFAULT).flush();
            }
        });
    }

    @Override
    public void render(SpriteBatch batch) {
        if (SuperBallGame.isSoundOn)
            soundItem.icon.setImage(assets.overlayAssets.overlaySoundIcon);
        else
            soundItem.icon.setImage(assets.overlayAssets.overlayNoSoundIcon);

        if (assets.soundAssets.backgroundMusic.isPlaying())
            musicItem.icon.setImage(assets.overlayAssets.overlayMusicIcon);
        else
            musicItem.icon.setImage(assets.overlayAssets.overlayNoMusicIcon);

        backgroundItem.icon.setImage(assets.colorIconAssets.backgroundIcons[SuperBallGame.backgroundIndex]);

        super.render(batch);
    }
}
