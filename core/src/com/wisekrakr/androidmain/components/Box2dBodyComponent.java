package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Pool;

public class Box2dBodyComponent implements Component, Pool.Poolable{
    public Body body;
    public boolean isDead = false;

    @Override
    public void reset() {
        body = null;
        isDead = false;
    }
}
