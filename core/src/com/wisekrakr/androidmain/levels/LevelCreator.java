package com.wisekrakr.androidmain.levels;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.TypeComponent;

class LevelCreator {

    private static int rows = 0;
    private static int columns = 0;

    private static float width = GameUtilities.WORLD_WIDTH;
    private static float height = GameUtilities.WORLD_HEIGHT;

    static void getLevel(LevelNumber levelNumber, EntityCreator entityCreator, int rows, int columns){
        setRows(rows);
        setColumns(columns);
        switch (levelNumber){
            case ONE:
                System.out.println("Making level 1, with a total of entities: " + getInitialEntities());
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.BALL,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.RUBBER,
                                ((width/2) - (width/5))  + j * GameUtilities.BALL_RADIUS, height/2 - k * GameUtilities.BALL_RADIUS,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }

//                entityCreator.createObstacle(width/2,height/2,
//                        300f,0,
//                        80f, 10f,
//                        BodyFactory.Material.STEEL,
//                        BodyDef.BodyType.KinematicBody);



                break;
            case TWO:
                System.out.println("Making level 2, with a total of entities: " + getInitialEntities());
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.RUBBER,
                                ((width/2) - (width/5)) + j * GameUtilities.BALL_RADIUS, height - k * GameUtilities.BALL_RADIUS,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }

                break;
            case THREE:
                System.out.println("Making level 3, with a total of entities: " + getInitialEntities());
                for(int i = 1; i < getRows(); i++) {
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.BALL,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.RUBBER,
                                ((width/2) - (width/5)) + i * GameUtilities.BALL_RADIUS, height - j * GameUtilities.BALL_RADIUS *2,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }


                break;
            case FOUR:
                System.out.println("Making level 4, with a total of entities: " + getInitialEntities());
                for(int i = 1; i < getRows(); i++) {
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.RUBBER,
                                ((width/2) - (width/5)) + i * GameUtilities.BALL_RADIUS, height - j * GameUtilities.BALL_RADIUS *2,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case FIVE:
                System.out.println("Making level 5, with a total of entities: " + getInitialEntities());
                for(int i = 1; i < getRows(); i++) {
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.BALL,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.STONE,
                                ((width/2) - (width/5)) + i * GameUtilities.BALL_RADIUS, height - j * GameUtilities.BALL_RADIUS *2,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case SIX:
                System.out.println("Making level 6, with a total of entities: " + getInitialEntities());
                for(int i = 1; i < getRows(); i++){
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.STONE,
                                i * GameUtilities.BALL_RADIUS + j * GameUtilities.BALL_RADIUS/2,
                                height - j * GameUtilities.BALL_RADIUS - i * GameUtilities.BALL_RADIUS/2,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }

                break;
            case SEVEN:
                System.out.println("Making level 7, with a total of entities: " + getInitialEntities());
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.BALL,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.STONE,
                                j * GameUtilities.BALL_RADIUS, height - k * GameUtilities.BALL_RADIUS,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case EIGHT:
                System.out.println("Making level 8, with a total of entities: " + getInitialEntities());
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.STONE,
                                j * GameUtilities.BALL_RADIUS, height - k * GameUtilities.BALL_RADIUS,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case NINE:
                System.out.println("Making level 9, with a total of entities: " + getInitialEntities());
                for(int i = 1; i < getRows(); i++){
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.BALL,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.WOOD,
                                i * GameUtilities.BALL_RADIUS + j * GameUtilities.BALL_RADIUS/2,
                                height - j * GameUtilities.BALL_RADIUS - i * GameUtilities.BALL_RADIUS/2,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case TEN:

                System.out.println("Making level 10, with a total of entities: " + getInitialEntities());
                for(int i = 1; i < getRows(); i++){
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.STONE,
                                i * GameUtilities.BALL_RADIUS + j * GameUtilities.BALL_RADIUS/2,
                                height - j * GameUtilities.BALL_RADIUS - i * GameUtilities.BALL_RADIUS/2,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }
                break;
            case ELEVEN:
                System.out.println("Making level 11, with a total of entities: " + getInitialEntities());
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.BALL,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.STONE,
                                j * GameUtilities.BALL_RADIUS, height - k * GameUtilities.BALL_RADIUS,
                                GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS);
                    }
                }
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
        LevelCreator.rows = rows;
    }

    private static void setColumns(int columns) {
        LevelCreator.columns = columns;
    }

    private static int getRows() {
        return rows;
    }

    private static int getColumns() {
        return columns;
    }

    static void clearRowsAndColumns(){
        rows =0;
        columns=0;
    }

    static int getInitialEntities(){
        return (rows - 1) * (columns - 1);
    }

}
