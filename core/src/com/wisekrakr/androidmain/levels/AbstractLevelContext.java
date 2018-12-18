package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;

public class AbstractLevelContext implements LevelContext {

    private LevelCreator levelCreator = new LevelCreator();

    public LevelCreator getLevelCreator(){
        return levelCreator;
    }

    @Override
    public void init(Entity entity) {

    }

    @Override
    public void startLevel(int numberOfLevel, int rows, int columns) {

    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {

    }

    @Override
    public void completeLevel(int numberOfLevel) {

    }
}
