package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {

    public boolean hasEntityToShoot = false;
    public float spawnDelayEntity = 0.8f;
    public float timeSinceLastShot = 0f;

    public float width = 0f;
    public float height = 0f;

    @Override
    public void reset() {

        hasEntityToShoot = false;
        spawnDelayEntity = 0.8f;
        timeSinceLastShot = 0f;

        width = 0f;
        height = 0f;
    }
}
