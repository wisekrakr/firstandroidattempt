package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
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

        player = entityCreator.createPlayer(GameUtilities.WORLD_WIDTH /2, GameUtilities.BALL_RADIUS/2);
    }

    @Override
    public void startLevel(int numberOfLevel, int rows, int columns) {
        LevelCreator.getLevel(LevelNumber.valueOf(numberOfLevel), entityCreator, rows, columns);
    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {

        game.getTimeKeeper().time -= delta;

        if (numberOfLevel != 0) {
            if (game.getTimeKeeper().time != 0) {
                if (entityCreator.getTotalEntities().size() <= 15) {
                    completeLevel(numberOfLevel);
                } else if (game.getTimeKeeper().time <= 0) {
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

        game.getTimeKeeper().time += 30f;

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
