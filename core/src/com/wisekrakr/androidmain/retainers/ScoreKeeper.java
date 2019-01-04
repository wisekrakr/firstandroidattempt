package com.wisekrakr.androidmain.retainers;

public class ScoreKeeper {

    private static int score = 0;

    public static void setScore(int scores){
        score += scores;
    }

    public static int getScore() {
        return score;
    }


    public void reset(){

        score = 0;

    }
}
