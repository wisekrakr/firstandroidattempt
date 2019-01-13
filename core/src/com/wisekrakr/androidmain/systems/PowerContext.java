package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;

public interface PowerContext {

    void init(float spawnInterval);
    void powerTime(Entity entity);
    void spawnPower();
    void exit();

}
