package com.wisekrakr.androidmain.levels;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.TypeComponent;

class LevelCreator {

    private static int rows = 0;
    private static int columns = 0;

    private static float width = GameConstants.WORLD_WIDTH;
    private static float height = GameConstants.WORLD_HEIGHT;

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
                                BodyFactory.Material.STONE,
                                ((width/2) - (width/5))  + j * GameConstants.BALL_RADIUS, height/2 - k * GameConstants.BALL_RADIUS,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());


                    }
                }

                break;
            case TWO:
                System.out.println("Making level 2, with a total of entities: " + getInitialEntities());
                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.RUBBER,
                                ((width/2) - (width/5))  + j * GameConstants.BALL_RADIUS, height/2 - k * GameConstants.BALL_RADIUS,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());
                    }
                }
                break;
            case THREE:
                System.out.println("Making level 3, with a total of entities: " + getInitialEntities());

                for (int j = 1; j < getRows(); j++) {
                    for (int k = 1; k < getColumns(); k++) {

                        entityCreator.createRowEntity(TypeComponent.Type.BALL,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.RUBBER,
                                ((width/2) - (width/5))  + j * GameConstants.BALL_RADIUS, height/2 - k * GameConstants.BALL_RADIUS,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());
                    }
                }

                entityCreator.createObstacle(width/2,height/2,
                        300f,0,
                        80f, 10f,
                        BodyFactory.Material.STEEL,
                        BodyDef.BodyType.KinematicBody);
                break;
            case FOUR:
                System.out.println("Making level 4, with a total of entities: " + getInitialEntities());

                for(int i = 1; i < getRows(); i++) {
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.SQUARE,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.RUBBER,
                                ((width/2) - (width/5)) + i * GameConstants.BALL_RADIUS, height - j * GameConstants.BALL_RADIUS *2,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());
                    }
                }

                entityCreator.createObstacle(width/2,height/2,
                        300f,0,
                        80f, 10f,
                        BodyFactory.Material.STEEL,
                        BodyDef.BodyType.KinematicBody);
                break;
            case FIVE:
                System.out.println("Making level 5, with a total of entities: " + getInitialEntities());
                for(int i = 1; i < getRows(); i++) {
                    for (int j = 1; j < getColumns(); j++) {
                        entityCreator.createRowEntity(TypeComponent.Type.BALL,
                                BodyDef.BodyType.StaticBody,
                                BodyFactory.Material.STONE,
                                ((width/2) - (width/5)) + i * GameConstants.BALL_RADIUS, height - j * GameConstants.BALL_RADIUS *2,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());
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
                                i * GameConstants.BALL_RADIUS + j * GameConstants.BALL_RADIUS/2,
                                height - j * GameConstants.BALL_RADIUS - i * GameConstants.BALL_RADIUS/2,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());
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
                                j * GameConstants.BALL_RADIUS, height - k * GameConstants.BALL_RADIUS,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());
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
                                j * GameConstants.BALL_RADIUS, height - k * GameConstants.BALL_RADIUS,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());
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
                                i * GameConstants.BALL_RADIUS + j * GameConstants.BALL_RADIUS/2,
                                height - j * GameConstants.BALL_RADIUS - i * GameConstants.BALL_RADIUS/2,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());
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
                                i * GameConstants.BALL_RADIUS + j * GameConstants.BALL_RADIUS/2,
                                height - j * GameConstants.BALL_RADIUS - i * GameConstants.BALL_RADIUS/2,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());
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
                                j * GameConstants.BALL_RADIUS, height - k * GameConstants.BALL_RADIUS,
                                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS, EntityComponent.randomBallColor());
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
