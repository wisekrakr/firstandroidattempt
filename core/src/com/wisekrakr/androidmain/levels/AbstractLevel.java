package com.wisekrakr.androidmain.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.TypeComponent;

class AbstractLevel {

    private static int rows = 0;
    private static int columns = 0;

    static void getLevel(LevelNumber levelNumber, EntityCreator entityCreator, int rows, int columns){
        setRows(rows);
        setColumns(columns);
        switch (levelNumber){
            case ONE:
                System.out.println("Making level 1, with a total of balls: " + getInitialEntities()); //todo remove
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.BALL, BodyDef.BodyType.StaticBody,
                                j * GameUtilities.BALL_RADIUS, Gdx.graphics.getHeight() - k * GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case TWO:
                for(int i = 1; i < getRows(); i++){
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.BALL, BodyDef.BodyType.StaticBody,
                                i * GameUtilities.BALL_RADIUS + j * GameUtilities.BALL_RADIUS/2, Gdx.graphics.getHeight() - j * GameUtilities.BALL_RADIUS - i * GameUtilities.BALL_RADIUS/2);
                    }
                }
                break;
            case THREE:
                for(int i = 1; i < getRows(); i++) {
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.BALL, BodyDef.BodyType.StaticBody,
                                i * GameUtilities.BALL_RADIUS, j * GameUtilities.BALL_RADIUS * 2);


                    }
                }
                break;
            case FOUR:
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.BALL, BodyDef.BodyType.StaticBody,
                                j * GameUtilities.BALL_RADIUS, Gdx.graphics.getHeight() - k * GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case FIVE:
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE, BodyDef.BodyType.StaticBody,
                                j * GameUtilities.BALL_RADIUS, Gdx.graphics.getHeight() - k * GameUtilities.BALL_RADIUS);
                    }
                }

                break;
            case SIX:
                for(int i = 1; i < getRows(); i++){
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE, BodyDef.BodyType.StaticBody,
                                i * GameUtilities.BALL_RADIUS + j * GameUtilities.BALL_RADIUS/2, Gdx.graphics.getHeight() - j * GameUtilities.BALL_RADIUS - i * GameUtilities.BALL_RADIUS/2);
                    }
                }

                break;
            case SEVEN:
                for(int i = 1; i < getRows(); i++) {
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE, BodyDef.BodyType.StaticBody,
                                i * GameUtilities.BALL_RADIUS, j * GameUtilities.BALL_RADIUS * 2);


                    }
                }
                break;
            case EIGHT:
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE, BodyDef.BodyType.StaticBody,
                                j * GameUtilities.BALL_RADIUS, Gdx.graphics.getHeight() - k * GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case NINE:

                break;
            case TEN:

                break;
            case ELEVEN:

                break;
            case TWELVE:

                break;
            case THIRTEEN:

                break;
            case FOURTEEN:

                break;
            case FIFTEEN:

                break;
            case SIXTEEN:

                break;
            case SEVENTEEN:

                break;
            case EIGHTEEN:

                break;
            case NINETEEN:

                break;
            case TWENTY:

                break;
            case TWENTY_ONE:

                break;
            case TWENTY_TWO:

                break;
            case TWENTY_THREE:

                break;
            case TWENTY_FOUR:

                break;
            case TWENTY_FIVE:

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

    static int getInitialEntities(){
        return (rows - 1) * (columns - 1);
    }


}
