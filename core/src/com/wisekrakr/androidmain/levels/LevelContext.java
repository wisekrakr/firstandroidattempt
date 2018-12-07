package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;

public interface LevelContext {

    void init(Entity entity);

    void startLevel();

    void updateLevel(float delta);

    void completeLevel();
}
