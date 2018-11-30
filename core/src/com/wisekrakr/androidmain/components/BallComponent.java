package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import com.wisekrakr.androidmain.GameHelper;

public class BallComponent implements Component, Pool.Poolable {

    public enum BallColor{
        MERCURY, EARTH, JUPITER, NEPTUNE, SATURN, URANUS, MARS
    }

    private BallColor[]ballColors = BallColor.values();

    public BallColor randomBallColor(){
        return ballColors[GameHelper.randomGenerator.nextInt(ballColors.length)];
    }

    public BallColor ballColor;
    public float velocityX = 0f;
    public float velocityY = 0f;
    public boolean hitSurface = false;
    public boolean destroyed = false;
    public boolean hitBall = false;

    public Vector2 position = new Vector2();

    @Override
    public void reset() {
        position = new Vector2();
        velocityX = 0f;
        velocityY = 0f;

        hitSurface = false;
        destroyed = false;
        hitBall = false;
    }
}
