package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TimeComponent implements Component, Pool.Poolable {

    public float time = 300;


    @Override
    public void reset() {
        time = 300;
    }
}
