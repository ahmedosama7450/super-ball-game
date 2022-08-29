package com.blogspot.osamatech442.superball.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.blogspot.osamatech442.superball.SuperBallGame;

public class Assets implements Disposable, AssetErrorListener {


    public AssetManager assetManager;

    public BallAssets ballAssets;
    public PlatformAssets platformAssets;
    public IconAssets iconAssets;
    public PrizeAssets prizeAssets;
    public DecorationAssets decorationAssets;
    public SoundAssets soundAssets;
    public OverlayAssets overlayAssets;
    public ColorIconAssets colorIconAssets;
    public InstructionAssets instructionAssets;

    public Assets() {
        this.assetManager = new AssetManager();
        assetManager.setErrorListener(this);
    }

    public void init() {

        loadAssets();

        //assetManager.finishLoading();

        prepareAssets();

    }

    public void loadAssets() {
        assetManager.load(Constants.GAME_SHEET1_NAME, TextureAtlas.class);
        assetManager.load(Constants.GAME_SHEET2_NAME, TextureAtlas.class);
        assetManager.load(Constants.GAME_SHEET3_NAME, TextureAtlas.class);

        assetManager.load(Constants.BG_MUSIC_NAME, Music.class);
        assetManager.load(Constants.CLICK_SOUND_NAME, Sound.class);
        assetManager.load(Constants.KEY_SOUND_NAME, Sound.class);
        assetManager.load(Constants.COIN_SOUND_NAME, Sound.class);
        assetManager.load(Constants.PORTAL_SOUND_NAME, Sound.class);

    }

    public void prepareAssets() {
        TextureAtlas gameSheet1 = assetManager.get(Constants.GAME_SHEET1_NAME, TextureAtlas.class);
        TextureAtlas gameSheet2 = assetManager.get(Constants.GAME_SHEET2_NAME, TextureAtlas.class);
        TextureAtlas gameSheet3 = assetManager.get(Constants.GAME_SHEET3_NAME, TextureAtlas.class);

        ballAssets = new BallAssets(gameSheet1);
        platformAssets = new PlatformAssets(gameSheet1);
        iconAssets = new IconAssets(gameSheet2);
        prizeAssets = new PrizeAssets(gameSheet2);
        decorationAssets = new DecorationAssets(gameSheet2);
        soundAssets = new SoundAssets();
        overlayAssets = new OverlayAssets(gameSheet2);
        colorIconAssets = new ColorIconAssets(gameSheet3);
        instructionAssets = new InstructionAssets(gameSheet2);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.log("TAG", "error loading assets");
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class BallAssets {

        public final TextureAtlas.AtlasRegion[] balls;

        public BallAssets(TextureAtlas gameSheet1) {
            balls = new TextureAtlas.AtlasRegion[Constants.BALLS_COUNT];
            for (int i = 0; i < Constants.BALLS_COUNT; i++) {
                balls[i] = gameSheet1.findRegion(java.lang.String.valueOf(i));
            }
        }
    }

    public class PlatformAssets {

        public final TextureAtlas.AtlasRegion[] platforms;

        public PlatformAssets(TextureAtlas gameSheet1) {
            platforms = new TextureAtlas.AtlasRegion[Constants.PLATFORMS_COUNT];
            for (int i = 0; i < Constants.PLATFORMS_COUNT; i++) {
                platforms[i] = gameSheet1.findRegion("0" + i);
            }
        }
    }

    public class IconAssets {

        public final TextureAtlas.AtlasRegion fbIcon;
        public final TextureAtlas.AtlasRegion startIcon;
        public final TextureAtlas.AtlasRegion replayIcon;
        public final TextureAtlas.AtlasRegion homeIcon;
        public final TextureAtlas.AtlasRegion aboutIcon;
        public final TextureAtlas.AtlasRegion blankKeyIcon;
        public final TextureAtlas.AtlasRegion exitIcon;
        public final TextureAtlas.AtlasRegion keyIcon;
        public final TextureAtlas.AtlasRegion noSoundIcon;
        public final TextureAtlas.AtlasRegion pauseIcon;
        public final TextureAtlas.AtlasRegion playIcon;
        public final TextureAtlas.AtlasRegion settingsIcon;
        public final TextureAtlas.AtlasRegion soundIcon;
        public final TextureAtlas.AtlasRegion instructionIcon;
        public final TextureAtlas.AtlasRegion leftArrow;
        public final TextureAtlas.AtlasRegion rightArrow;

        public IconAssets(TextureAtlas gameSheet2) {
            fbIcon = gameSheet2.findRegion(Constants.FB_ICON_NAME);
            startIcon = gameSheet2.findRegion(Constants.START_ICON_NAME);
            replayIcon = gameSheet2.findRegion(Constants.REPLAY_ICON_NAME);
            homeIcon = gameSheet2.findRegion(Constants.HOME_ICON_NAME);
            aboutIcon = gameSheet2.findRegion(Constants.ABOUT_ICON_NAME);
            blankKeyIcon = gameSheet2.findRegion(Constants.BLANK_KEY_ICON_NAME);
            exitIcon = gameSheet2.findRegion(Constants.EXIT_ICON_NAME);
            keyIcon = gameSheet2.findRegion(Constants.KEY_ICON_NAME);
            noSoundIcon = gameSheet2.findRegion(Constants.NO_SOUND_ICON_NAME);
            pauseIcon = gameSheet2.findRegion(Constants.PAUSE_ICON_NAME);
            playIcon = gameSheet2.findRegion(Constants.PLAY_ICON_NAME);
            settingsIcon = gameSheet2.findRegion(Constants.SETTINGS_ICON_NAME);
            soundIcon = gameSheet2.findRegion(Constants.SOUND_ICON_NAME);
            instructionIcon = gameSheet2.findRegion(Constants.INSTRUCTION_ICON);
            leftArrow = gameSheet2.findRegion(Constants.LEFT_ARROW_NAME);
            rightArrow = gameSheet2.findRegion(Constants.RIGHT_ARROW_NAME);
        }
    }

    public class OverlayAssets {

        public final TextureAtlas.AtlasRegion overlay;
        public final TextureAtlas.AtlasRegion overlayCloseIcon;
        public final TextureAtlas.AtlasRegion overlayMusicIcon;
        public final TextureAtlas.AtlasRegion overlayNoMusicIcon;
        public final TextureAtlas.AtlasRegion overlaySoundIcon;
        public final TextureAtlas.AtlasRegion overlayNoSoundIcon;
        public final TextureAtlas.AtlasRegion overlayFbIcon;
        public final TextureAtlas.AtlasRegion overlayTwitterIcon;
        public final TextureAtlas.AtlasRegion overlayWebsiteIcon;
        public final TextureAtlas.AtlasRegion coinRewardedVideoOverlay;
        public final TextureAtlas.AtlasRegion lifeRewardedVideoOverlay;
        public final TextureAtlas.AtlasRegion noRewardedVideoOverlay;
        public final TextureAtlas.AtlasRegion overlayYesButton;
        public final TextureAtlas.AtlasRegion overlayNoButton;

        public OverlayAssets(TextureAtlas gameSheet2) {
            overlay = gameSheet2.findRegion(Constants.OVERLAY_NAME);
            overlayCloseIcon = gameSheet2.findRegion(Constants.OVERLAY_CLOSE_ICON_NAME);
            overlayMusicIcon = gameSheet2.findRegion(Constants.OVERLAY_MUSIC_ICON_NAME);
            overlayNoMusicIcon = gameSheet2.findRegion(Constants.OVERLAY_NO_MUSIC_ICON_NAME);
            overlaySoundIcon = gameSheet2.findRegion(Constants.OVERLAY_SOUND_ICON_NAME);
            overlayNoSoundIcon = gameSheet2.findRegion(Constants.OVERLAY_NO_SOUND_ICON_NAME);
            overlayFbIcon = gameSheet2.findRegion(Constants.OVERLAY_FB_ICON_NAME);
            overlayTwitterIcon = gameSheet2.findRegion(Constants.OVERLAY_TWITTER_ICON_NAME);
            overlayWebsiteIcon = gameSheet2.findRegion(Constants.OVERLAY_WEBSITE_ICON_NAME);
            coinRewardedVideoOverlay = gameSheet2.findRegion(Constants.COIN_REWARDED_VIDEO_OVERLAY);
            lifeRewardedVideoOverlay = gameSheet2.findRegion(Constants.LIFE_REWARDED_VIDEO_OVERLAY);
            noRewardedVideoOverlay = gameSheet2.findRegion(Constants.NO_REWARDED_VIDEO_OVERLAY);
            overlayYesButton = gameSheet2.findRegion(Constants.OVERLAY_YES_BUTTON);
            overlayNoButton = gameSheet2.findRegion(Constants.OVERLAY_NO_BUTTON);
        }
    }

    public class PrizeAssets {

        public final TextureAtlas.AtlasRegion blueKey;
        public final TextureAtlas.AtlasRegion coin;
        public final TextureAtlas.AtlasRegion spike;

        public PrizeAssets(TextureAtlas gameSheet2) {
            blueKey = gameSheet2.findRegion(Constants.KEY_BLUE_NAME);
            coin = gameSheet2.findRegion(Constants.COIN_NAME);
            spike = gameSheet2.findRegion(Constants.SPIKE_NAME);
        }
    }

    public class DecorationAssets {

        public final TextureAtlas.AtlasRegion pauseTransBG;
        public final TextureAtlas.AtlasRegion bottomCloud;
        public final TextureAtlas.AtlasRegion moreTransBG;
        public final TextureAtlas.AtlasRegion horLine;
        public final TextureAtlas.AtlasRegion logo;

        public DecorationAssets(TextureAtlas gameSheet2) {
            pauseTransBG = gameSheet2.findRegion(Constants.PAUSE_TRANSPARENT_BG_NAME);
            bottomCloud = gameSheet2.findRegion(Constants.BOTTOM_CLOUD_NAME);
            moreTransBG = gameSheet2.findRegion(Constants.MORE_TRANS_BG_NAME);
            horLine = gameSheet2.findRegion(Constants.HOR_LINE_NAME);
            logo = gameSheet2.findRegion(Constants.LOGO_NAME);
        }
    }

    public class InstructionAssets {

        public final TextureAtlas.AtlasRegion tap;
        public final TextureAtlas.AtlasRegion tapTick;
        public final TextureAtlas.AtlasRegion tapInstruction;
        public final TextureAtlas.AtlasRegion slideLeftInstruction;
        public final TextureAtlas.AtlasRegion slideRightInstruction;
        public final TextureAtlas.AtlasRegion collectKeysInstruction;
        public final TextureAtlas.AtlasRegion avoidSpikesInstruction;
        public final TextureAtlas.AtlasRegion instructionsNote;
        public final TextureAtlas.AtlasRegion tapNote;
        public final Animation<TextureRegion> tapAnimation;

        public InstructionAssets(TextureAtlas gameSheet2) {
            tap = gameSheet2.findRegion(Constants.TAP_NAME);
            tapTick = gameSheet2.findRegion(Constants.TAP_TICK_NAME);
            tapInstruction = gameSheet2.findRegion(Constants.TAP_INSTRUCTION_NAME);
            slideLeftInstruction = gameSheet2.findRegion(Constants.SLIDE_LEFT_INSTRUCTION_NAME);
            slideRightInstruction = gameSheet2.findRegion(Constants.SLIDE_RIGHT_INSTRUCTION_NAME);
            collectKeysInstruction = gameSheet2.findRegion(Constants.COLLECT_KEYS_INSTRUCTION_NAME);
            avoidSpikesInstruction = gameSheet2.findRegion(Constants.AVOID_SPIKES_INSTRUCTION_NAME);
            instructionsNote = gameSheet2.findRegion(Constants.INSTRUCTIONS_NOTE_NAME);
            tapNote = gameSheet2.findRegion(Constants.INSTRUCTIONS_TAP_NAME);
            tapAnimation = new Animation<TextureRegion>(Constants.TAP_ANIMATION_DURATION, tap, tapTick);
        }
    }

    public class SoundAssets {

        public final Music backgroundMusic;
        public final Sound clickSound;
        public final Sound keySound;
        public final Sound coinSound;
        public final Sound portalSound;

        public SoundAssets() {
            backgroundMusic = assetManager.get(Constants.BG_MUSIC_NAME, Music.class);
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.8f);

            clickSound = assetManager.get(Constants.CLICK_SOUND_NAME, Sound.class);
            keySound = assetManager.get(Constants.KEY_SOUND_NAME, Sound.class);
            coinSound = assetManager.get(Constants.COIN_SOUND_NAME, Sound.class);
            portalSound = assetManager.get(Constants.PORTAL_SOUND_NAME, Sound.class);

        }
    }

    public class ColorIconAssets {

        public final TextureAtlas.AtlasRegion[] platformIcons;
        public final TextureAtlas.AtlasRegion[] backgroundIcons;

        public ColorIconAssets(TextureAtlas gameSheet3) {
            platformIcons = new TextureAtlas.AtlasRegion[Constants.PLATFORMS_COUNT];
            for (int i = 0; i < Constants.PLATFORMS_COUNT; i++) {
                platformIcons[i] = gameSheet3.findRegion("0" + i);
            }

            backgroundIcons = new TextureAtlas.AtlasRegion[Constants.BACKGROUNDS_COUNT];
            for (int i = 0; i < Constants.BACKGROUNDS_COUNT; i++) {
                backgroundIcons[i] = gameSheet3.findRegion(java.lang.String.valueOf(i));
            }
        }

    }


}
