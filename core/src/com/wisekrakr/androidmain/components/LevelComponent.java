package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.levels.Levels;

import java.util.ArrayList;
import java.util.List;

public class LevelComponent implements Component, Pool.Poolable{

    public LevelNumber levelNumber = LevelNumber.ONE;
    public boolean completed = false;

    public enum LevelNumber{
        ONE, TWO, THREE, FOUR, FIVE, SEX, SEVEN, EIGHT
    }

    @Override
    public void reset() {
        levelNumber = LevelNumber.ONE;
        completed = false;
    }
}
