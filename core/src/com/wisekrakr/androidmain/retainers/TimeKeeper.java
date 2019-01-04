package com.wisekrakr.androidmain.retainers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TimeKeeper {

    public float time = 300f;
    private float savedTime = 0;

    public float gameClock = 0;

    public void getSavedTime(){
        savedTime = time;
    }

    public void reset(){
        time = 300f;
    }

}
