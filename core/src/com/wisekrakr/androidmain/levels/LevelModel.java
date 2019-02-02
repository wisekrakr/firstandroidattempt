package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.ObstacleComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;


public class LevelModel extends AbstractLevelContext{

    public boolean gameOver = false;
    private Entity player;
    private AndroidGame game;
    private EntityCreator entityCreator;

    public LevelModel(AndroidGame game, EntityCreator entityCreator) {
        this.game = game;
        this.entityCreator = entityCreator;

        constantEntities();
    }

    private void constantEntities(){
        player = entityCreator.createPlayer(GameConstants.WORLD_WIDTH /2, GameConstants.BALL_RADIUS/2,
                GameConstants.WORLD_WIDTH/10, GameConstants.BALL_RADIUS/3);

        entityCreator.createWalls(0,0, 5f, GameConstants.WORLD_HEIGHT *2);
        entityCreator.createWalls(GameConstants.WORLD_WIDTH,0, 5f, GameConstants.WORLD_HEIGHT *2);
        entityCreator.createWalls(GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT, GameConstants.WORLD_WIDTH *2,10f);
        //entityCreator.createWalls(0,0, GameConstants.WORLD_WIDTH *2,5f);
    }

    @Override
    public void startLevel(int numberOfLevel, int rows, int columns) {

        LevelCreator.getLevel(LevelNumber.valueOf(numberOfLevel), entityCreator, rows, columns);
    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {
        game.getGameThread().getTimeKeeper().time -= delta;

        if (game.getGameThread().getTimeKeeper().time > 0) {

            if (entityCreator.getTotalShapes().size() <= 5) {
                completeLevel(numberOfLevel);
            }

        }else if (game.getGameThread().getTimeKeeper().time <= 0) {
            gameOver = true;
            gameOver(numberOfLevel);
        }

    }

    @Override
    public void completeLevel(int numberOfLevel) {
        game.getGamePreferences().setLevelCompleted(numberOfLevel, true);

        game.getGameThread().getTimeKeeper().setTime(game.getGameThread().getTimeKeeper().time + 60f);

        cleanUp();
    }

    @Override
    public void gameOver(int numberOfLevel) {

        System.out.println("game over bitch");

        game.getGameThread().getTimeKeeper().reset();
        ScoreKeeper.reset();

        cleanUp();

        game.changeScreen(AndroidGame.ENDGAME);
    }


    private void cleanUp(){
        player.getComponent(PlayerComponent.class).hasEntityToShoot = false;

        for (Entity entity: entityCreator.getTotalShapes()){
            entity.getComponent(EntityComponent.class).setDestroy(true);
        }
        for (Entity entity: entityCreator.getTotalObstacles()){
            entity.getComponent(ObstacleComponent.class).destroy = true;
        }
//        for (Entity entity: game.getEngine().getEntities()){
//            entity.getComponent(Box2dBodyComponent.class).isDead = true;
//        }
    }

    public Entity getPlayer() {
        return player;
    }
}
