package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowComponent implements Component, Pool.Poolable{

    public List<Entity>rowOfBalls = new ArrayList<Entity>();

    @Override
    public void reset() {
        rowOfBalls = new ArrayList<Entity>();

    }
}
