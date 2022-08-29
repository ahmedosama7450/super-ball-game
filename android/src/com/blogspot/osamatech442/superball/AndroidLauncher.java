package com.blogspot.osamatech442.superball;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.blogspot.osamatech442.superball.screens.MainScreen;
import com.blogspot.osamatech442.superball.screens.PlayScreen;
import com.blogspot.osamatech442.superball.screens.ResultScreen;
import com.blogspot.osamatech442.superball.utils.ActionResolver;
import com.blogspot.osamatech442.superball.utils.Constants;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AndroidLauncher extends AndroidApplication implements ActionResolver, RewardedVideoAdListener {

    private static final String APP_ID = "ca-app-pub-4863908481169653~5864095967";
    private static final String INTERSTITIAL_AD_ID = "ca-app-pub-4863908481169653/7723972544";
    private static final String REWARDED_VIDEO_AD_ID = "ca-app-pub-4863908481169653/2607377236";

    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;

    private SuperBallGame superBallGame;

    public int adType;
    private boolean hasRewardedVideo;
    private boolean isRewarded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useImmersiveMode = true;

        superBallGame = new SuperBallGame(this);
        initialize(superBallGame, config);

        MobileAds.initialize(this, APP_ID);

        hasRewardedVideo = false;
        isRewarded = false;
        adType = 0;

        //Creating the interstitial ad
        initInterstitialAd();

        //Creating the rewarded video ad
        initRewardedVideoAd();
    }

    private void initInterstitialAd() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(INTERSTITIAL_AD_ID);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                superBallGame.assets.soundAssets.backgroundMusic.stop();
            }

            @Override
            public void onAdClosed() {
                if (superBallGame.preferences.getBoolean(Constants.IS_MUSIC_ON_KEY, Constants.IS_MUSIC_ON_DEFAULT)) {
                    superBallGame.assets.soundAssets.backgroundMusic.play();
                }
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void initRewardedVideoAd() {
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        rewardedVideoAd.loadAd(REWARDED_VIDEO_AD_ID, new AdRequest.Builder().build());
    }

    @Override
    public void showOrLoadInterstitialAd() {
        try {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (interstitialAd.isLoaded())
                        interstitialAd.show();
                    else
                        interstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        } catch (Exception e) {
            //Nothing
        }
    }

    @Override
    public boolean hasRewardedVideo() {
        if (hasRewardedVideo) {
            return true;
        } else {
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadRewardedVideoAd();
                    }
                });
            } catch (Exception ex) {
                //Nothing
            }
            return false;
        }
    }

    @Override
    public void showRewardedVideo(final int type) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (rewardedVideoAd.isLoaded()) {
                        rewardedVideoAd.show();
                        adType = type;
                    } else
                        loadRewardedVideoAd();
                }
            });
        } catch (Exception e) {
            //Nothing
        }
    }

    @Override
    public void onResume() {
        rewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        rewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        rewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    //Rewarded Video
    @Override
    public void onRewarded(RewardItem rewardItem) {
        switch (adType) {
            case 1:
                //coin reward
                int currentCoins = superBallGame.preferences.getInteger(Constants.COIN_NUMBER_KEY, Constants.COIN_NUMBER_DEFAULT);
                superBallGame.preferences.putInteger(Constants.COIN_NUMBER_KEY, currentCoins + Constants.COIN_REWARD).flush();
                break;
            case 2:
                //life reward
                isRewarded = true;
                break;
        }
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        switch (adType) {
            case 1:
                //coin reward
                ((MainScreen) superBallGame.getScreen()).restoreScreen();
                break;
            case 2:
                //life reward
                if (isRewarded) {
                    PlayScreen playScreen = (PlayScreen) superBallGame.getScreen();
                    playScreen.prepareForNewLife();
                } else {
                    PlayScreen playScreen = (PlayScreen) superBallGame.getScreen();
                    superBallGame.setScreen(new ResultScreen(superBallGame, playScreen.getScore(), playScreen.getObtainedCoins()));
                }
                break;
        }

        if (superBallGame.preferences.getBoolean(Constants.IS_MUSIC_ON_KEY, Constants.IS_MUSIC_ON_DEFAULT)) {
            superBallGame.assets.soundAssets.backgroundMusic.play();
        }

        loadRewardedVideoAd();
        isRewarded = false;
        hasRewardedVideo = false;
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        hasRewardedVideo = true;
    }

    @Override
    public void onRewardedVideoAdOpened() {
        superBallGame.assets.soundAssets.backgroundMusic.stop();
        hasRewardedVideo = false;
    }

    @Override
    public void onRewardedVideoStarted() {
        hasRewardedVideo = false;
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

}
