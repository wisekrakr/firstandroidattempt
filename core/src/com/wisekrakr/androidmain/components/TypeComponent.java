package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TypeComponent implements Component, Pool.Poolable {

    public enum Type{
        PLAYER, BALL, WATER, SCENERY, OTHER
    }

    public Type type = Type.OTHER;

    @Override
    public void reset() {
        type = Type.OTHER;
    }
}
