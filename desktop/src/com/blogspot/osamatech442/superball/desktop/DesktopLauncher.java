package com.blogspot.osamatech442.superball.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.screens.PlayScreen;
import com.blogspot.osamatech442.superball.utils.ActionResolver;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 356;
        config.height = 588;
        new LwjglApplication(new SuperBallGame(new ActionResolver() {
            @Override
            public void showOrLoadInterstitialAd() {
            }

            @Override
            public boolean hasRewardedVideo() {
                return true;
            }

            @Override
            public void showRewardedVideo(int type) {
                if (type == 2) {
                    SuperBallGame superBallGame = (SuperBallGame) Gdx.app.getApplicationListener();
                    PlayScreen playScreen = (PlayScreen) superBallGame.getScreen();
                    playScreen.prepareForNewLife();
                }

            }
        }), config);
    }
}
