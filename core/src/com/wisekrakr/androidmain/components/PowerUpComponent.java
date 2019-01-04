package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.GameHelper;

public class PowerUpComponent implements Component, Pool.Poolable{

    public enum PowerUp{
        TIME_FREEZE, TIME_SLOW, NUKE, HOMING_BALL
    }

    private PowerUpComponent.PowerUp[] powerUps = PowerUpComponent.PowerUp.values();

    public PowerUpComponent.PowerUp randomPowerUp(){
        return powerUps[GameHelper.randomGenerator.nextInt(powerUps.length)];
    }

    public PowerUp powerUp;

    public float velocityX = 0f;
    public float velocityY = 0f;
    public Vector2 position = new Vector2();


    @Override
    public void reset() {
        powerUp = null;

        velocityX = 0f;
        velocityY = 0f;
        position = new Vector2();

    }
}
