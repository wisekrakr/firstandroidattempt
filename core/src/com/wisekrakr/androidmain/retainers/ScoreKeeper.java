package com.wisekrakr.androidmain.retainers;

public class ScoreKeeper {

    private static int score = 0;

    public static void setScore(int scores){
        score += scores;
    }

    public static int getScore() {
        return score;
    }

    //todo: create multiplier. Every bounce on the wall, the score is multiplied by 2

    public void reset(){

        score = 0;
    }
}
