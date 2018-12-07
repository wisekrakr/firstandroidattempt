package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.TimeComponent;

import java.util.ArrayList;
import java.util.List;


public class LevelModel {

    private AndroidGame game;
    private EntityCreator entityCreator;
    private LevelFactory levelFactory;

    private ArrayList<Integer> levelList = new ArrayList<Integer>();

    public LevelModel(AndroidGame game, EntityCreator entityCreator) {
        this.game = game;
        this.entityCreator = entityCreator;

        levelFactory = new LevelFactory(game, entityCreator);

    }

    public void updatingLevel(int numberOfLevel, int rows, int columns, float deltaTime){

        TimeComponent timeComponent = ComponentMapper.getFor(TimeComponent.class).get(entityCreator.getPlayer());

        timeComponent.time -= deltaTime;

        if (timeComponent.time != 0) {
            if (levelList.size() == numberOfLevel - 1) {
                level(rows, columns, numberOfLevel);

            } else if (levelList.size() == numberOfLevel) {
                if (entityCreator.totalBalls().size() <= 8) {
                    game.getGamePreferences().setLevelCompleted(numberOfLevel, true);
                    if (game.getGamePreferences().levelDone(numberOfLevel)) {
                        cleanUp();
                        game.getGamePreferences().setLevelCompleted(numberOfLevel + 1, false);
                    }
                }
            }
        }
        //TODO GEEN LEVELSELECT .... APP WORD GESTART MET LEVEL. ALS LEVEL KLAAR IS, CLEAR PLAYSCREEN, en TEKEN PLAYSCREEN OPNIEUW.

        System.out.println(entityCreator.totalBalls().size() + "   " + levelList.size() + "   " + numberOfLevel + "   " + timeComponent.time); //todo remove

    }

    private void level(int rows, int columns, int levelNumber){

        for (int j = 1; j < columns; j++) {
            for (int k = 1; k < rows; k++) {
                entityCreator.createRowBall(j * GameUtilities.BALL_RADIUS,
                        Gdx.graphics.getHeight() - k * GameUtilities.BALL_RADIUS);
            }
        }
        levelList.add(levelNumber - levelList.size());

    }



    private void cleanUp(){

        //game.changeScreen(AndroidGame.APPLICATION);
        List<Entity>balls = entityCreator.totalBalls();
        balls.remove(0);
        for (Entity entity: balls){
            entity.getComponent(BallComponent.class).destroyed = true;
        }
    }

}
