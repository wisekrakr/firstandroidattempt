package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.GameHelper;
import com.wisekrakr.androidmain.systems.PowerUpContext;

public class PowerUpComponent {

    public enum PowerUp{
        TIME_FREEZE, TIME_SLOW, NUKE, HOMING_BALL
    }

    private PowerUpComponent.PowerUp[] powerUps = PowerUpComponent.PowerUp.values();

    private PowerUpComponent.PowerUp randomPowerUp(){
        return powerUps[GameHelper.randomGenerator.nextInt(powerUps.length)];
    }

    public PowerUp powerUp = PowerUp.NUKE;


}
