package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class GameTimer {

    public float time = 300;
    private float savedTime = 0;

    public void getSavedTime(){
        savedTime = time;
    }



}
