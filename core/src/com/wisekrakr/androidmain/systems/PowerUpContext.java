package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;

public interface PowerUpContext {

    void init(float spawnInterval);
    void powerTime(Entity entity);
    void spawnPowerUp();
    void exit();

}
