package com.wisekrakr.androidmain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class GameHelper {

    /**
     * Utility methods available to scenarios
     */
    public static Random randomGenerator = new Random();

    public static float generateRandomNumberBetween(float min, float max){
        return randomGenerator.nextFloat() * (max - min) + min;
    }

    public static float randomDirection(){
        return randomGenerator.nextFloat() * 200 - 100;
    }


    public static float distanceBetween(Vector2 subject, Vector2 target) {
        float attackDistanceX = target.x - subject.x;
        float attackDistanceY = target.y - subject.y;

        return (float) Math.hypot(attackDistanceX, attackDistanceY);
    }

    public static float angleBetween(Vector2 subject, Vector2 target) {
        float attackDistanceX = target.x - subject.x;
        float attackDistanceY = target.y - subject.y;

        return (float) Math.atan2(attackDistanceY, attackDistanceX);
    }

    public static float betweenZeroAndOne(){
        return randomGenerator.nextInt(1);
    }


}
