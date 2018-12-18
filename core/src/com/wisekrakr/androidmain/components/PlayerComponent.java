package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.systems.RenderingSystem;

public class PlayerComponent implements Component, Pool.Poolable {

    public boolean hasEntityToShoot = false;
    public boolean isDead = false;
    public float spawnDelay = 0.5f;
    public float timeSinceLastShot = 0f;
    public float score = 0f;
    public float angle = 0f;

    @Override
    public void reset() {

        hasEntityToShoot = false;
        isDead = false;
        spawnDelay = 0.5f;
        timeSinceLastShot = 0f;
        score = 0f;
        angle = 0f;
    }
}
