package com.blogspot.osamatech442.superball.utils;

public interface ActionResolver {
    void showOrLoadInterstitialAd();
    boolean hasRewardedVideo();
    void showRewardedVideo(int type);
}