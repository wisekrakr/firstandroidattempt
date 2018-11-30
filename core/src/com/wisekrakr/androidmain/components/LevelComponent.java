package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.utils.Pool;


public class LevelComponent implements Component, Pool.Poolable{

    public LevelNumber levelNumber = LevelComponent.LevelNumber.ONE;
    public boolean completed = false;
    public boolean locked = true;
    public int rows = 0;
    public int columns = 0;

    public enum LevelNumber{
        ONE, TWO, THREE, FOUR, FIVE, SEX, SEVEN, EIGHT
    }

    @Override
    public void reset() {
        levelNumber = LevelNumber.ONE;
        completed = false;
        locked = true;
        rows = 0;
        columns = 0;
    }
}
