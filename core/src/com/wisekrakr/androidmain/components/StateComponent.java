package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class StateComponent implements Component, Pool.Poolable {

    public static final int GAME_OVER = 0;
    public static final int NEW_LEVEL = 1;
    public static final int OTHER = 2;

    private int state = 0;
    public float time = 300f;
    public float timeSaver = time;

    public void set(int newState){
        state = newState;
        time = timeSaver;
    }

    public int get(){
        return state;
    }

    @Override
    public void reset() {
        state = 0;
        time = timeSaver;
    }
}
