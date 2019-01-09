package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import com.wisekrakr.androidmain.GameHelper;

public class EntityComponent implements Component, Pool.Poolable {

    public boolean hitSurface = false;
    public boolean destroy = false;
    public boolean hitEntity = false;
    public boolean hitObstacle = false;
    public boolean hitPowerUp = false;

    public void setHitSurface(boolean hitSurface) {
        this.hitSurface = hitSurface;
    }
    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
    public void setHitEntity(boolean hitEntity) {
        this.hitEntity = hitEntity;
    }
    public void setHitObstacle(boolean hitObstacle) {
        this.hitObstacle = hitObstacle;
    }
    public void setHitPowerUp(boolean hitPowerUp) {
        this.hitPowerUp = hitPowerUp;
    }

    public enum EntityColor {
        RED, BLUE, YELLOW, GREEN, PURPLE, PINK, CYAN
    }

    private EntityColor[] entityColors = EntityColor.values();

    public EntityColor randomBallColor(){
        return entityColors[GameHelper.randomGenerator.nextInt(entityColors.length)];
    }

    private int pointsToGive = 0;
    public int multiplier = 0;

    public void setMultiplier(int multi) {
        multiplier = multi;
    }

    public int setPointsToGive(int points) {
        pointsToGive = points * multiplier;

        if (pointsToGive == 0){
            pointsToGive = 10;
        }

        return pointsToGive;
    }

    public EntityColor entityColor;
    public float velocityX = 0f;
    public float velocityY = 0f;
    public Vector2 position = new Vector2();

    public float width = 0f;
    public float height = 0f;

    @Override
    public void reset() {
        entityColor = null;

        multiplier = 0;
        pointsToGive = 10;

        position = new Vector2();
        velocityX = 0f;
        velocityY = 0f;

        hitSurface = false;
        destroy = false;
        hitEntity = false;
        hitPowerUp = false;
        hitObstacle = false;

        width = 0f;
        height = 0f;
    }
}
