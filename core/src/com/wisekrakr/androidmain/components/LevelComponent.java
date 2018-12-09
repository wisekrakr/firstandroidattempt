package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.levels.LevelModel;
import com.wisekrakr.androidmain.levels.LevelNumber;


import java.util.ArrayList;
import java.util.List;


public class LevelComponent implements Component, Pool.Poolable{

    public int rows = 0;
    public int columns = 0;

    public ArrayList<Integer> levelList = new ArrayList<Integer>();

    public Vector2 ballSpawnPosition = new Vector2();

    @Override
    public void reset() {

        rows = 0;
        columns = 0;

        ballSpawnPosition = new Vector2();

    }
}
