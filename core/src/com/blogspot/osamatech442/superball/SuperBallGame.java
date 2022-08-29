package com.blogspot.osamatech442.superball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blogspot.osamatech442.superball.screens.SplashScreen;
import com.blogspot.osamatech442.superball.utils.ActionResolver;
import com.blogspot.osamatech442.superball.utils.Assets;
import com.blogspot.osamatech442.superball.utils.Constants;

public class SuperBallGame extends Game {

	//Singleton objects
	public SpriteBatch batch;
	public BitmapFont bitmapFont;
	public GlyphLayout textLayout;
	public Preferences preferences;
	public Assets assets;

	//preferences
	public static int playTimes;
	public static boolean isSoundOn;
	public static Color backgroundColor;
	public static int backgroundIndex;

	//ads
	public ActionResolver actionResolver;

	public SuperBallGame(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void create () {
		//Singleton Objects
		this.batch = new SpriteBatch();
		this.bitmapFont = new BitmapFont(Gdx.files.internal(Constants.NORMAL_FONT_NAME));
		this.textLayout = new GlyphLayout();
		this.preferences = Gdx.app.getPreferences(Constants.PREFERENCES_NAME);

		//Preferences
		playTimes = 0;

		int enterTimes = preferences.getInteger(Constants.ENTER_TIMES_KEY, Constants.ENTER_TIMES_DEFAULT) + 1;
		preferences.putInteger(Constants.ENTER_TIMES_KEY, enterTimes).flush();

		backgroundIndex = preferences.getInteger(Constants.BACKGROUND_COLOR_KEY, Constants.DEFAULT_BACKGROUND_COLOR);
		if (enterTimes % 8 == 0) {
			//change background
			backgroundIndex = backgroundIndex == Constants.BACKGROUNDS_COUNT - 1 ? 0 : backgroundIndex + 1;
			preferences.putInteger(Constants.BACKGROUND_COLOR_KEY, backgroundIndex).flush();
		}

		backgroundColor = Color.valueOf(Constants.BACKGROUNDS_COLORS[backgroundIndex]);

		isSoundOn = preferences.getBoolean(Constants.IS_SOUND_ON_KEY, Constants.IS_SOUND_ON_DEFAULT);

		//Assets
		this.assets = new Assets();
		assets.loadAssets();

		//Setting the splash screen
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
		bitmapFont.dispose();
		assets.dispose();
	}

	public void runClickSound() {
		if (isSoundOn) {
			assets.soundAssets.clickSound.play();
		}
	}
}