package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;

public class PlayerComponent implements Component, Pool.Poolable {

    public boolean hasBall = false;
    public boolean isDead = false;
    public float shootDelay = 0.5f;
    public float timeSinceLastShot = 0f;
    public float score = 0f;

    public List<Entity> balls = new ArrayList<Entity>();

    @Override
    public void reset() {

        hasBall = false;
        isDead = false;
        shootDelay = 0.5f;
        timeSinceLastShot = 0f;
        score = 0f;
    }
}
