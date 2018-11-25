package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import com.wisekrakr.androidmain.GameHelper;

import java.util.ArrayList;

import java.util.List;

public class BallComponent implements Component, Pool.Poolable {

    public enum BallColor{
        RED, BLUE, YELLOW, GREEN, PURPLE, GOLD, SILVER
    }

    private BallColor[]ballColors = BallColor.values();

    public BallColor randomBallColor(){
        return ballColors[GameHelper.randomGenerator.nextInt(ballColors.length)];
    }

    public BallColor ballColor;
    public float velocityX = 0f;
    public float velocityY = 0f;
    public boolean sameColor = false;
    public boolean hitSurface = false;
    public boolean destroyed = false;

    public Vector2 position = new Vector2();

    @Override
    public void reset() {
        position = new Vector2();
        velocityX = 0f;
        velocityY = 0f;
        sameColor = false;
        hitSurface = false;
        destroyed = false;
    }
}
