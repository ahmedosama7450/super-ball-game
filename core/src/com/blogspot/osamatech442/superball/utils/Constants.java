package com.blogspot.osamatech442.superball.utils;

import com.badlogic.gdx.graphics.Color;

public class Constants {

    //Balls Prices
    public static final int BALL0_PRICE = 0;
    public static final int BALL1_PRICE = 100;
    public static final int BALL2_PRICE = 200;
    public static final int BALL3_PRICE = 300;
    public static final int BALL4_PRICE = 350;
    public static final int BALL5_PRICE = 400;
    public static final int BALL6_PRICE = 450;
    public static final int BALL7_PRICE = 400;
    public static final int BALL8_PRICE = 420;
    public static final int BALL9_PRICE = 550;
    public static final int BALL10_PRICE = 560;
    public static final int BALL11_PRICE = 580;
    public static final int BALL12_PRICE = 500;
    public static final int BALL13_PRICE = 600;
    public static final int BALL14_PRICE = 650;

    //Play screen
    public static final float PLAY_SCREEN_SIZE = 100f;

    //Speed
    public static final float CAMERA_INITIAL_SPEED = 35.5f;
    public static final float CAMERA_MAX_SPEED = 40f;

    public static final float BALL_INITIAL_YVELOCITY = CAMERA_INITIAL_SPEED + 5.3f;//y  //10.5
    public static final float BALL_MAX_YVELOCITY = CAMERA_MAX_SPEED * BALL_INITIAL_YVELOCITY / CAMERA_INITIAL_SPEED;

    public static final float BALL_CONSTANT_XVELOCITY = 49.5f;//x //45
    public static final float BALL_MAX_XVELOCITY = 55f;

    public static final float BALL_INITIAL_YACCELERATION = 81f;//a //57
    public static final float BALL_MAX_YACCELERATION = 90f;

    public static final float SPEED_INCREMENT_MULTIPLIER = 1.006f; //must be greater than 1

    //Ball
    public static final float BALL_RADIUS = 3.13f;
    public static final float BALL_INITIAL_YPOS_MULTIPLIER = 3f / 4f;

    public static final float BALL_BOUNCE_VELOCITY = 5.5f;//8.1
    public static final float BALL_BOUNCING_TIME = 0.086f;//0.077
    public static final float SPIKE_ROTATION_PERIODIC_TIME = 0.8f;
    public static final float BALL_ROTATION_PERIODIC_TIME = 0.57f;

    //Platform
    public static final float PLATFORM_THICKNESS = 2.5f;
    public static final float PLATFORM_SIZE = 19.41f;
    public static final float FLAG_SIZE = 8f;

    public static final float WALL_PLATFORM_PADDING = 4.5f;

    public static final float HALF_PORTAL_SIZE = 24f;
    public static final float PORTAL_CREATION_MULTIPLIER = 4f;

    public static final float PLATFORM_CREATION_DIS = 1.5f; //1.2

    public static final float BALL_PLATFORM_CREATION_MARGIN = 1f;

    public static final int PORTAL_COINS = 2;
    public static final float PORTAL_COIN_SIZE = 7.5f;

    //key
    public static final float KEY_SIZE = 8.1f;
    public static final int NEEDED_KEYS = 3;

    //Spike
    public static final float SPIKE_SIZE = 6.9f;
    public static final float SPIKE_REAL_SIZE = 6.5f;
    public static final float SPIKE_SIDE = SPIKE_SIZE / 3;

    //Coin
    public static final float PRIZE_COIN_SIZE = 6.5f;

    //Items
    public static final float ITEM_SIZE = KEY_SIZE;
    public static final float ITEM_SPAWN_MARGIN = ITEM_SIZE / 3;

    public static final float ITEM_COLLISION_MARGIN = 2f;

    public static final int INITIAL_KEYS_PER_PORTAL = 8;
    public static final int MIN_KEYS_PER_PORTAL = 4;

    public static final int INITIAL_SPIKES_PER_PORTAL = 20;
    public static final int MAX_SPIKES_PER_PORTAL = 35;
    public static final int SPIKES_INCREASE_PER_PORTAL = 3;

    public static final int COINS_PER_PORTAL = 2;

    //Hud
    public static final float HUD_SIZE = 480;
    public static final float ICON_SIZE = 51;

    public static final float HUD_SCORE_MARGIN = 6f;
    public static final float HUD1_OUTER_MARGIN = 5f;
    public static final float HUD1_INNER_MARGIN = 5f;

    public static final float SCORE_FONT_SCALE = 1.1f;
    public static final Color SCORE_FONT_COLOR = Color.WHITE;

    public static final float SCORE_MULTIPLIER = 26f;

    //Splash Screen
    public static final float SPLASH_SCREEN_TIME = 1.3f;

    //pause overlay
    public static final float START_ICON_SIZE = 146f;
    public static final float HOME_ICON_SIZE = 70f;
    public static final float REPLAY_ICON_SIZE = 70f;

    public static final float PAUSE_TIME = 1.1f;
    public static final float PAUSE_OVERLAY_MARGIN = 25f;

    public static final float COUNT_DOWN_SCALE = 1.7f;

    //settings overlay
    public static final String SETTINGS_OVERLAY_TITLE = "SETTINGS";
    public static final String SOUND_TITLE = "SOUND";
    public static final String MUSIC_TITLE = "MUSIC";
    public static final String PLATFORM_COLOR_TITLE = "PLATFORM";
    public static final String BACKGROUND_COLOR_TITLE = "BACKGROUND";

    //About Overlay
    public static final String ABOUT_OVERLAY_TITLE = "ABOUT";
    public static final String GAME_NAME = "SUPER BALL";
    public static final String GAME_VERSION = "Version 1.0";
    public static final String MY_RIGHTS = "Developed By";
    public static final String STARTUP_NAME = "OsamaTech";

    //Overlay
    public static final Color OVERLAY_TITLE_COLOR = Color.WHITE;
    public static final Color OVERLAY_TEXT_COLOR = Color.valueOf("715243");

    public static final float OVERLAY_SCALE = 1f;

    public static final float OVERLAY_WIDTH = 350f;
    public static final float OVERLAY_HEIGHT = 500f * OVERLAY_WIDTH / 400f; // 375
    public static final float OVERLAY_UPPER_HEIGHT = 82f * OVERLAY_HEIGHT / 500f; //61.5

    public static final float OVERLAY_ITEM_ICON_SIZE = 48f;
    public static final float OVERLAY_ICON_SIZE = 60f;
    public static final float OVERLAY_CLOSE_ICON_SIZE = 40f;

    public static final float OVERLAY_XMARGIN = 15f;
    public static final float OVERLAY_ITEM_YMARGIN = 25f;
    public static final float OVERLAY_TEXT_YMARGIN = 35f;
    public static final float OVERLAY_ICON_YMARGIN = 15f;

    //MainScreen Settings
    public static final float MAIN_SCREEN_SIZE = 480f;

    public static final Color PURCHASE_COIN_FONT_COLOR = Color.WHITE;
    public static final float PURCHASE_COIN_FONT_SCALE = 1f;

    public static final float HUD2_OUTER_MARGIN = 12f;
    public static final float HUD2_INNER_MARGIN = 8f;

    public static final float BALL_CHOOSER_SIZE = 110f;

    public static final float ARROW_WIDTH = 56f;
    public static final float ARROW_HEIGHT = 56f * ARROW_WIDTH / 68f;
    public static final float ARROW_MARGIN = 48f;

    public static final float PLAY_BUTTON_MARGIN = 49f;
    public static final float PLAY_BUTTON_SIZE = 69f;

    public static final float BUTTON_OUTER_MARGIN = 33f;

    public static final float PURCHASE_BUTTON_WIDTH = 126f;
    public static final float PURCHASE_BUTTON_HEIGHT = 60f;
    public static final float PURCHASE_BUTTON_MARGIN = 45f;
    public static final float PURCHASE_INNER_MARGIN = 10f;

    public static final float PURCHASE_COIN_SIZE = 40f;
    public static final float BUTTON_SIZE = 62f;

    public static final float LOGO_WIDTH = 347f;
    public static final float LOGO_HEIGHT = 242f * LOGO_WIDTH / 674f;
    public static final float LOGO_MARGIN = 48f;

    public static final float START_LOGO_RATIO = 250f / 400f;
    public static final Color SPLASH_SCREEN_COLOR = Color.valueOf("4a96ad");

    public static final String STR_PLUS_SIGN = "+";
    public static final String STR_SCORE_COIN = " Coins";

    public static final float BUTTON_INCREASE = 4.5f;
    public static final float ICON_INCREASE = 3f;
    public static final float REWARDED_BUTTON_INCREASE = 3f;

    public static final float BOTTOM_CLOUD_XPOS = -205f;
    public static final float BOTTOM_CLOUD_YPOS = -4f;
    public static final float BOTTOM_CLOUD_HEIGHT = 222f;
    public static final float BOTTOM_CLOUD_WIDTH = 1000f * BOTTOM_CLOUD_HEIGHT / 246f;

    public static final float NCOIN_SIZE = 80f;

    public static final float HOR_LINE_MARGIN = 22f;
    public static final float BALL_CHOOSER_MARGIN = 37f;
    public static final float LINE_THICKNESS = 3.8f;
    public static final float SCORE_TEXT_MARGIN = 15f;
    public static final float SCORE_VALUE_MARGIN = 27f;

    public static final float COIN_SCORE_YMARGIN = 16f;
    public static final float COIN_SCORE_XMARGIN = 6f;

    public static final float SCORE_COIN_SIZE = 37f;

    public static final float SCORE_TEXT_SCALE = 0.8f;
    public static final float SCORE_VALUE_SCALE = 1f;
    public static final float SCORE_COIN_SCALE = 0.92f;

    public static final String YOUR_SCORE_TEXT = "The Distance";
    public static final String BEST_SCORE_TEXT = "The Best";

    //rewarded video overlay
    public static final float REWARDED_OVERLAY_RATIO = 233f / 428f;
    public static final float BUTTON_REWARDED_OVERLAY_RATIO = 70f / 177f;

    public static final float REWARDED_OVERLAY_WIDTH = 400f;
    public static final float REWARDED_OVERLAY_HEIGHT = REWARDED_OVERLAY_WIDTH * REWARDED_OVERLAY_RATIO;

    public static final float REWARDED_VIDEO_BUTTON_WIDTH = 199f;
    public static final float REWARDED_VIDEO_BUTTON_HEIGHT = REWARDED_VIDEO_BUTTON_WIDTH * BUTTON_REWARDED_OVERLAY_RATIO;

    public static final int COIN_REWARD = 25;
    //Contact Info
    public static final String FB_PAGE_URL = "https://www.facebook.com/osamatech442/";
    public static final String TWITTER_PAGE_URL = "https://twitter.com/osamatech442";
    public static final String WEBSITE_URL = "https://osamatech442.blogspot.com";

    //Assets Names
    //logo
    public static final String START_LOGO_NAME = "start_logo.png";

    //fonts
    public static final String NORMAL_FONT_NAME = "fonts/normal_font.fnt";

    //sounds
    public static final String BG_MUSIC_NAME = "sounds/music.mp3";
    public static final String CLICK_SOUND_NAME = "sounds/click_sound.wav";
    public static final String KEY_SOUND_NAME = "sounds/key_sound.mp3";
    public static final String COIN_SOUND_NAME = "sounds/coin_sound.wav";
    public static final String PORTAL_SOUND_NAME = "sounds/portal_sound.wav";

    //sheets
    //sheet1
    public static final String GAME_SHEET1_NAME = "sheets/game_sheet1.atlas";
    public static final int PLATFORMS_COUNT = 15;
    public static final int BALLS_COUNT = 15;

    //sheet2
    public static final String GAME_SHEET2_NAME = "sheets/game_sheet2.atlas";
    public static final String PAUSE_TRANSPARENT_BG_NAME = "transparent_bg";
    public static final String BOTTOM_CLOUD_NAME = "bottom_cloud";
    public static final String COIN_NAME = "coin";
    public static final String SPIKE_NAME = "spike";
    public static final String FB_ICON_NAME = "fb_icon";
    public static final String KEY_BLUE_NAME = "keyBlue";
    public static final String LEFT_ARROW_NAME = "left_arrow";
    public static final String MORE_TRANS_BG_NAME = "more_trans_bg";
    public static final String RIGHT_ARROW_NAME = "right_arrow";
    public static final String START_ICON_NAME = "start_icon";
    public static final String REPLAY_ICON_NAME = "replay_icon";
    public static final String HOME_ICON_NAME = "home_icon";
    public static final String HOR_LINE_NAME = "hor_line";
    public static final String ABOUT_ICON_NAME = "about_icon";
    public static final String BLANK_KEY_ICON_NAME = "blank_key_icon";
    public static final String EXIT_ICON_NAME = "exit_icon";
    public static final String KEY_ICON_NAME = "key_icon";
    public static final String NO_SOUND_ICON_NAME = "no_sound_icon";
    public static final String PAUSE_ICON_NAME = "pause_icon";
    public static final String PLAY_ICON_NAME = "play_icon";
    public static final String SETTINGS_ICON_NAME = "settings_icon";
    public static final String SOUND_ICON_NAME = "sound_icon";
    public static final String INSTRUCTION_ICON = "instruction_icon";
    public static final String LOGO_NAME = "logo";
    public static final String OVERLAY_NAME = "overlay";
    public static final String OVERLAY_CLOSE_ICON_NAME = "overlay_close_icon";
    public static final String OVERLAY_FB_ICON_NAME = "overlay_fb_icon";
    public static final String OVERLAY_MUSIC_ICON_NAME = "overlay_music_icon";
    public static final String OVERLAY_NO_MUSIC_ICON_NAME = "overlay_no_music_icon";
    public static final String OVERLAY_SOUND_ICON_NAME = "overlay_sound_icon";
    public static final String OVERLAY_NO_SOUND_ICON_NAME = "overlay_no_sound_icon";
    public static final String OVERLAY_TWITTER_ICON_NAME = "overlay_twitter_icon";
    public static final String OVERLAY_WEBSITE_ICON_NAME = "overlay_website_icon";
    public static final String TAP_INSTRUCTION_NAME = "tap_instruction";
    public static final String SLIDE_RIGHT_INSTRUCTION_NAME = "slide_right_instruction";
    public static final String SLIDE_LEFT_INSTRUCTION_NAME = "slide_left_instruction";
    public static final String COLLECT_KEYS_INSTRUCTION_NAME = "collect_keys_instruction";
    public static final String AVOID_SPIKES_INSTRUCTION_NAME = "avoid_spikes_instruction";
    public static final String INSTRUCTIONS_NOTE_NAME = "instructions_note";
    public static final String INSTRUCTIONS_TAP_NAME = "tap_note";
    public static final String TAP_NAME = "tap";
    public static final String TAP_TICK_NAME = "tap_tick";
    public static final String COIN_REWARDED_VIDEO_OVERLAY = "coin_rewarded_video_overlay";
    public static final String LIFE_REWARDED_VIDEO_OVERLAY = "life_rewarded_video_overlay";
    public static final String NO_REWARDED_VIDEO_OVERLAY = "no_rewarded_video_overlay";
    public static final String OVERLAY_YES_BUTTON = "overlay_yes_button";
    public static final String OVERLAY_NO_BUTTON = "overlay_no_button";

    //sheet3
    public static final String GAME_SHEET3_NAME = "sheets/game_sheet3.atlas";
    public static final String[] BACKGROUNDS_COLORS = new String[]{
            "79b9d3",
            "4a96ad",
            "558c89",
            "588c7e",
            "777888",
            "359797",
            "7d1935",
            "118c4e",
            "74afad",
            "7e8f7c",
            "c69c6d",
            "9faa9a"
    };
    public static final int BACKGROUNDS_COUNT = BACKGROUNDS_COLORS.length;/*12*/

    //Preferences
    public static final String PREFERENCES_NAME = "my_preferences";

    public static final String COIN_NUMBER_KEY = "coin_number";
    public static final int COIN_NUMBER_DEFAULT = 50;

    public static final String CURRENT_BALL_KEY = "current_ball";
    public static final String LAST_BALL_KEY = "last_ball";
    public static final int BALL_DEFAULT = 0;

    public static final String IS_MUSIC_ON_KEY = "is_music_on";
    public static final boolean IS_MUSIC_ON_DEFAULT = true;

    public static final String IS_SOUND_ON_KEY = "is_music_on";
    public static final boolean IS_SOUND_ON_DEFAULT = true;

    public static final String PLATFORM_IMAGE_KEY = "platform_texture";
    public static final int DEFAULT_PLATFORM_INDEX = 0;

    public static final String BACKGROUND_COLOR_KEY = "background_color";
    public static final int DEFAULT_BACKGROUND_COLOR = 0;

    public static final String BEST_SCORE_KEY = "best_score";
    public static final int BEST_SCORE_DEFAULT = 0;

    public static final String SHOWS_INSTRUCTIONS_KEY = "is_first_time_opened";
    public static final boolean SHOWS_INSTRUCTIONS_DEFAULT = true;

    public static final String ENTER_TIMES_KEY = "enter_times";
    public static final int ENTER_TIMES_DEFAULT = 0;


    //Instruction Screen
    public static final float INSTRUCTION_START_TIME = 0.4f;

    public static final float INSTRUCTION_RECTANGLE_WIDTH = 58f;
    public static final float INSTRUCTION_RECTANGLE_HEIGHT = 123f * INSTRUCTION_RECTANGLE_WIDTH / 266f;

    public static final float FINAL_INSTRUCTION_RECTANGLE_WIDTH = 80f;
    public static final float FINAL_INSTRUCTION_RECTANGLE_HEIGHT = 93f * FINAL_INSTRUCTION_RECTANGLE_WIDTH / 268f;

    public static final float INSTRUCTION_SQUARE_WIDTH = 18f;
    public static final float INSTRUCTION_SQUARE_HEIGHT = 18f;

    public static final float INSTRUCTION_SQUARE_BALL_MARGIN = 24f;
    public static final float INSTRUCTION_SQUARE_RECTANGLE_MARGIN = 0.5f;
    public static final float FINAL_INSTRUCTION_RECTANGLE_MARGIN = 6f;

    public static final float NOTE_INSTRUCTION_WIDTH = PLAY_SCREEN_SIZE;
    public static final float NOTE_INSTRUCTION_HEIGHT = 78f * NOTE_INSTRUCTION_WIDTH / 592f;

    public static final float INSTRUCTION_DISPLACEMENT = 4f;

    public static final float TAP_INSTRUCTION_TRIANGLE_LOCATION = 125f * INSTRUCTION_RECTANGLE_WIDTH / 266f;
    public static final float SLIDE_RIGHT_INSTRUCTION_TRIANGLE_LOCATION = 77f * INSTRUCTION_RECTANGLE_WIDTH / 266f;
    public static final float SLIDE_LEFT_INSTRUCTION_TRIANGLE_LOCATION = 195f * INSTRUCTION_RECTANGLE_WIDTH / 266f;

    public static final float POINTER_SIZE = 11f;

    public static final float TAP_ANIMATION_DURATION = 0.4f;
    public static final float SLIDE_ANIMATION_DURATION = 1f;
    public static final float TAP_VELOCITY = 17f / SLIDE_ANIMATION_DURATION;
    public static final float EXTRA_TIME = 0.2f;

}
