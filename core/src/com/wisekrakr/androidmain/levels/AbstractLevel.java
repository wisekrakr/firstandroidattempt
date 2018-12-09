package com.wisekrakr.androidmain.levels;

import com.badlogic.gdx.Gdx;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;

class AbstractLevel {

    private static int rows = 0;
    private static int columns = 0;

    static void getLevel(LevelNumber levelNumber, EntityCreator entityCreator, int rows, int columns){
        setRows(rows);
        setColumns(columns);
        switch (levelNumber){
            case ONE:
                System.out.println("Making level 1, with a total of balls: " + getInitialBalls()); //todo remove
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowBall(j * GameUtilities.BALL_RADIUS,
                                Gdx.graphics.getHeight() - k * GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case TWO:
                System.out.println("Making level 2, with a total of balls: " + getInitialBalls()); //todo remove
                for(int i = 1; i < getRows(); i++){
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowBall(i * GameUtilities.BALL_RADIUS + j * GameUtilities.BALL_RADIUS/2,
                                Gdx.graphics.getHeight() - j * GameUtilities.BALL_RADIUS - i * GameUtilities.BALL_RADIUS/2);
                    }
                }
                break;
            case THREE:
                System.out.println("Making level 3, with a total of balls: " + getInitialBalls()); //todo remove
                for(int i = 1; i < getRows(); i++) {
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowBall(i * GameUtilities.BALL_RADIUS,
                               j * GameUtilities.BALL_RADIUS * 2);


                    }
                }
                break;
            case FOUR:
                System.out.println("Making level 4, with a total of balls: " + getInitialBalls()); //todo remove
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowBall(j * GameUtilities.BALL_RADIUS,
                                Gdx.graphics.getHeight() - k * GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case FIVE:

                break;
            case SIX:

                break;
                default:
                    System.out.println("No Level Available");
        }
    }

    private static void setRows(int rows){
        AbstractLevel.rows = rows;
    }

    private static void setColumns(int columns) {
        AbstractLevel.columns = columns;
    }

    private static int getRows() {
        return rows;
    }

    private static int getColumns() {
        return columns;
    }

    public static void clearRowsAndColumns(){
        rows =0;
        columns=0;
    }

    static int getInitialBalls(){
        return (rows - 1) * (columns - 1);
    }


}
