package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;

public interface LevelContext {

    void init(Entity entity);

    void startLevel(int numberOfLevel, int rows, int columns);

    void updateLevel(int numberOfLevel, float delta);

    void completeLevel(int numberOfLevel);
}
