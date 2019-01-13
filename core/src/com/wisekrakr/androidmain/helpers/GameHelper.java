package com.wisekrakr.androidmain.helpers;

import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.GameConstants;

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

    public static Vector2 randomPosition() {

        return new Vector2(randomGenerator.nextFloat() *  GameConstants.WORLD_WIDTH,
                randomGenerator.nextFloat() * GameConstants.WORLD_HEIGHT);
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
