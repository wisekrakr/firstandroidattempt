package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.ObstacleComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;


public class LevelModel extends AbstractLevelContext{

    private Entity player;
    private AndroidGame game;
    private EntityCreator entityCreator;

    public LevelModel(AndroidGame game, EntityCreator entityCreator) {
        this.game = game;
        this.entityCreator = entityCreator;

        player = entityCreator.createPlayer(GameConstants.WORLD_WIDTH /2, GameConstants.BALL_RADIUS/2,
                GameConstants.WORLD_WIDTH/10, GameConstants.BALL_RADIUS/3);
    }

    @Override
    public void startLevel(int numberOfLevel, int rows, int columns) {
        LevelCreator.getLevel(LevelNumber.valueOf(numberOfLevel), entityCreator, rows, columns);
    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {

        game.getGameThread().getTimeKeeper().time -= delta;

        if (numberOfLevel != 0) {
            if (game.getGameThread().getTimeKeeper().time != 0) {
                if (entityCreator.getTotalEntities().size() <= 10) {
                    completeLevel(numberOfLevel);
                } else if (game.getGameThread().getTimeKeeper().time <= 0) {
                    gameOver();
                }
            }
        }else {
            System.out.println("No level number given ");
        }
    }

    @Override
    public void completeLevel(int numberOfLevel) {
        game.getGamePreferences().setLevelCompleted(numberOfLevel, true);

        game.getGameThread().getTimeKeeper().setTime(game.getGameThread().getTimeKeeper().time + 60f);

        cleanUp();
    }


    private void gameOver() {

        cleanUp();

        game.changeScreen(AndroidGame.ENDGAME);
    }

    private void cleanUp(){

        for (Entity entity: entityCreator.getTotalEntities()){
            entity.getComponent(EntityComponent.class).setDestroy(true);
            player.getComponent(PlayerComponent.class).hasEntityToShoot = false;
        }

        for (Entity entity: entityCreator.getTotalObstacles()){
            entity.getComponent(ObstacleComponent.class).destroy = true;
        }

        //game.changeScreen(AndroidGame.APPLICATION);
    }


}
