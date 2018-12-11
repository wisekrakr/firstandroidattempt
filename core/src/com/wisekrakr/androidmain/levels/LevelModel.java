package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.GameTimer;
import com.wisekrakr.androidmain.components.PlayerComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LevelModel extends AbstractLevelContext{

    private Entity player;
    private AndroidGame game;
    private EntityCreator entityCreator;
    private GameTimer timer;

    private boolean isAvailable;

    public LevelModel(AndroidGame game, EntityCreator entityCreator) {
        this.game = game;
        this.entityCreator = entityCreator;

        player = entityCreator.createPlayer(Gdx.graphics.getWidth()/2, 5);

        timer = new GameTimer();
    }

    @Override
    public void startLevel(int numberOfLevel, int rows, int columns) {
        setAvailability(numberOfLevel, true);

        if (isAvailable()) {
            AbstractLevel.getLevel(LevelNumber.valueOf(numberOfLevel), entityCreator, rows, columns);
        }
    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {

        timer.time -= delta;

        if (numberOfLevel != 0) {
            if (timer.time != 0) {
                if (entityCreator.totalEntities().size() <= 24) {
                    completeLevel(numberOfLevel);
                } else if (timer.time <= 0) {
                    gameOver();
                }
            }
        }else {
            System.out.println("No level number given ");
        }

//        System.out.println(entityCreator.totalEntities().size() +
//                "   " + numberOfLevel + "   " +
//                timer.time ); //todo remove

    }

    @Override
    public void completeLevel(int numberOfLevel) {
        game.getGamePreferences().setLevelCompleted(numberOfLevel, true);

        timer.time += 30f;

        setAvailability(numberOfLevel, false);

        cleanUp();

    }


    private void gameOver() {

        cleanUp();

        game.changeScreen(AndroidGame.ENDGAME);
    }

    private void cleanUp(){

        List<Entity>balls = entityCreator.totalEntities();

        for (Entity entity: balls){
            entity.getComponent(EntityComponent.class).destroyed = true;
            player.getComponent(PlayerComponent.class).hasEntityToShoot = false;
        }

        //game.changeScreen(AndroidGame.APPLICATION);
    }

    public Entity getPlayer() {
        return player;
    }

    public GameTimer getTimer() {
        return timer;
    }

    private Map<Integer, Boolean> setAvailability(int number, boolean set){
        isAvailable = set;

        Map<Integer, Boolean>map = new HashMap<Integer, Boolean>();

        map.put(number, set);

        return map;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
