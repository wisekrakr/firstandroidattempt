package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import com.wisekrakr.androidmain.helpers.GameHelper;

public class EntityComponent implements Component, Pool.Poolable {

    public boolean hitSurface = false;
    public boolean destroy = false;
    public boolean hitEntity = false;
    public boolean hitObstacle = false;
    public boolean hitPower = false;

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
    public void setHitPower(boolean hitPower) {
        this.hitPower = hitPower;
    }

    public enum EntityColor {
        RED, BLUE, YELLOW, GREEN, PURPLE, ORANGE, CYAN
    }

    private static EntityColor[] entityColors = EntityColor.values();

    public static EntityColor randomBallColor(){
        return entityColors[GameHelper.randomGenerator.nextInt(entityColors.length)];
    }

    private EntityColor entityColor;

    public EntityColor getEntityColor() {
        return entityColor;
    }

    public void setEntityColor(EntityColor entityColor) {
        this.entityColor = entityColor;
    }

    public float velocityX = 0f;
    public float velocityY = 0f;
    public Vector2 position = new Vector2();

    public float width = 0f;
    public float height = 0f;

    @Override
    public void reset() {
        entityColor = null;

        position = new Vector2();
        velocityX = 0f;
        velocityY = 0f;

        hitSurface = false;
        destroy = false;
        hitEntity = false;
        hitPower = false;
        hitObstacle = false;

        width = 0f;
        height = 0f;
    }
}
