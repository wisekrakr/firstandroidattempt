package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import com.wisekrakr.androidmain.GameHelper;

public class EntityComponent implements Component, Pool.Poolable {

    public enum EntityColor {
        RED, BLUE, YELLOW, GREEN, PURPLE, PINK, CYAN
    }

    private EntityColor[] entityColors = EntityColor.values();

    public EntityColor randomBallColor(){
        return entityColors[GameHelper.randomGenerator.nextInt(entityColors.length)];
    }

    public EntityColor entityColor;
    public float velocityX = 0f;
    public float velocityY = 0f;

    public boolean hitSurface = false;
    public boolean destroyed = false;
    public boolean hitEntity = false;

    public Vector2 position = new Vector2();

    @Override
    public void reset() {
        position = new Vector2();
        velocityX = 0f;
        velocityY = 0f;

        hitSurface = false;
        destroyed = false;
        hitEntity = false;
    }
}
