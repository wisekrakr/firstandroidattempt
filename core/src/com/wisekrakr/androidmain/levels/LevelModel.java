package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.TimeComponent;

import java.util.ArrayList;
import java.util.List;


public class LevelModel {

    private LevelComponent levelComponent;
    private TimeComponent timeComponent;
    private Entity player;
    private AndroidGame game;
    private EntityCreator entityCreator;
    private LevelFactory levelFactory;

    public LevelModel(AndroidGame game, EntityCreator entityCreator) {
        this.game = game;
        this.entityCreator = entityCreator;

        levelFactory = new LevelFactory(game, entityCreator);

        player = entityCreator.createPlayer(Gdx.graphics.getWidth()/2, 5);
    }


    public void updatingLevel(int numberOfLevel, int rows, int columns, float deltaTime){
        timeComponent = ComponentMapper.getFor(TimeComponent.class).get(getPlayer());
        levelComponent = ComponentMapper.getFor(LevelComponent.class).get(getPlayer());

        timeComponent.time -= deltaTime;

        if (timeComponent.time != 0) {
            if (levelComponent.levelList.size() == numberOfLevel - 1) {
                level(rows, columns, numberOfLevel);

            } else if (levelComponent.levelList.size() == numberOfLevel) {
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

        System.out.println(entityCreator.totalBalls().size() + "   " + levelComponent.levelList.size() + "   " + numberOfLevel + "   " + timeComponent.time); //todo remove

    }

    private void level(int rows, int columns, int levelNumber){

        if (levelNumber != 0) {
            for (int j = 1; j < columns; j++) {
                for (int k = 1; k < rows; k++) {

                    entityCreator.createRowBall(j * GameUtilities.BALL_RADIUS,
                            Gdx.graphics.getHeight() - k * GameUtilities.BALL_RADIUS);
                }
            }
            levelComponent.levelList.add(levelNumber - levelComponent.levelList.size());
        }
    }



    private void cleanUp(){

        //game.changeScreen(AndroidGame.APPLICATION);
        List<Entity>balls = entityCreator.totalBalls();

        for (Entity entity: balls){
            entity.getComponent(BallComponent.class).destroyed = true;
        }
    }

    public Entity getPlayer() {
        return player;
    }
}
