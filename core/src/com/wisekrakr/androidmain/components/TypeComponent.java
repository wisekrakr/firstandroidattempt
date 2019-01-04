package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TypeComponent implements Component, Pool.Poolable {

    public enum Type{
        PLAYER, BALL, SQUARE, TRIANGLE, THING, OBSTACLE, SCENERY, POWER_UP, OTHER;
    }

    public Type type = Type.OTHER;

    public enum Tag {
        PLAYER_BALL, A_PRIORI_ENTITY, NONE
    }

    public Tag tag = Tag.PLAYER_BALL;

    @Override
    public void reset() {
        type = Type.OTHER;
        tag = Tag.PLAYER_BALL;
    }
}
