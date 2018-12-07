package com.wisekrakr.androidmain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.wisekrakr.androidmain.components.LevelComponent;

public class GamePreferences {

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "bigballs";

    private boolean completed = false;


    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

    /*
    Level selection methods
     */

    public void setLevelCompleted(int numberOfLevel, boolean set) {
        completed = set;
        String string = Integer.toString(numberOfLevel);
        getPrefs().putBoolean(string, completed);
        getPrefs().flush();
    }

    public boolean levelDone(int numberOfLevel) {
        String string = Integer.toString(numberOfLevel);
        return getPrefs().getBoolean(string, completed);
    }


}
