package com.nopo;

public class Config {
    public int scaleTimer;
    public float fullRangeChance;
    public boolean shouldEffectPlayers;
    public boolean uncapScale;

    public Config(int scaleTimer, float fullRangeChance, boolean shouldEffectPlayers, boolean uncapScale) {
        this.scaleTimer = scaleTimer;
        this.fullRangeChance = fullRangeChance;
        this.shouldEffectPlayers = shouldEffectPlayers;
        this.uncapScale = uncapScale;
    }
}
