package com.blogspot.osamatech442.superball.overlays;

import com.badlogic.gdx.Gdx;
import com.blogspot.osamatech442.superball.screens.MainScreen;
import com.blogspot.osamatech442.superball.utils.Assets;
import com.blogspot.osamatech442.superball.utils.ButtonInput;
import com.blogspot.osamatech442.superball.utils.Constants;

public class AboutOverlay extends Overlay {

    public AboutOverlay(MainScreen mainScreen) {
        super(mainScreen, Constants.ABOUT_OVERLAY_TITLE);
        Assets assets = mainScreen.assets;

        // /Adding the rights
        addText(Constants.GAME_NAME);
        addText(Constants.GAME_VERSION);
        addText(Constants.MY_RIGHTS);
        addText(Constants.STARTUP_NAME);

        //Adding contact icons
        addIcon(
                assets.overlayAssets.overlayWebsiteIcon,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        Gdx.net.openURI(Constants.WEBSITE_URL);
                    }
                }
        );
        addIcon(
                assets.overlayAssets.overlayTwitterIcon,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        Gdx.net.openURI(Constants.TWITTER_PAGE_URL);
                    }
                }
        );
        addIcon(
                assets.overlayAssets.overlayFbIcon,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        Gdx.net.openURI(Constants.FB_PAGE_URL);
                    }
                }
        );







    }

}
