package com.wisekrakr.androidmain.retainers;

public class ScoreKeeper {

    private static int score = 0;

    public static void setScore(int scores){
        if (scores < 0 || scores > 10000){
            throw new IllegalArgumentException();
        }else {
            score += scores;
        }
    }

    public static int getScore() {

        int s = score;

        if (s < 0 || s > 999999999){
            s = 0;
        }else {
            return s;
        }
        return s;

    }

    private static int pointsToGive = 0;
    private static int multiplier = 0;

    public void setMultiplier(int multi) {
        multiplier = multi;
    }

    public int setPointsToGive(int points) {
        pointsToGive = points * multiplier;

        return pointsToGive;
    }

    public static void reset(){
        score = 0;
    }
}
