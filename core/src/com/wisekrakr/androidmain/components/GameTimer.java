package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class GameTimer {

    public float time = 300f;
    private float savedTime = 0;

    public float gameClock = 0;

    public void getSavedTime(){
        savedTime = time;
    }

    public float getGameClock(){
        return gameClock;
    }

    public void reset(){
        time = 300f;
    }

}
