package com.wisekrakr.androidmain.systems;

import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.levels.LevelModel;
import com.wisekrakr.androidmain.levels.LevelNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class LevelGenerationSystem {
    private AndroidGame game;
    private EntityCreator entityCreator;
    private LevelModel levelModel;

    private enum State{
        START, BUILDING, UPDATE, END
    }
    private State state = State.START;

    private List<LevelNumber> levelsToDo = new ArrayList<LevelNumber>();
    private List<LevelNumber> levelCompleted = new ArrayList<LevelNumber>();
    private int mainLevel = 1;

    public LevelGenerationSystem(AndroidGame game, EntityCreator entityCreator){

        this.game = game;
        this.entityCreator = entityCreator;

        levelModel = new LevelModel(game, entityCreator);

        levelsToDo.addAll(Arrays.asList(LevelNumber.values()));

    }

    public void updateLevels(float deltaTime) {

        switch (state){
            case START:
                start();
                System.out.println("start: " + mainLevel);
                break;
            case BUILDING:
                building();
                System.out.println("build: " + mainLevel);
                break;
            case UPDATE:
                update(deltaTime);
                break;
            case END:
                completedLevel();
                System.out.println("complete: " + mainLevel);
                break;
        }
    }

    private void start(){

        Iterator<LevelNumber>iterator = levelsToDo.iterator();

        if (iterator.hasNext()){
            mainLevel = iterator.next().getValue();

            game.getGamePreferences().setLevelGoing(mainLevel, false);

            state = State.BUILDING;
        }
    }

    private void building(){

        if (!game.getGamePreferences().levelGoing(mainLevel)) {
            levelModel.startLevel(mainLevel, 6, 6);

            game.getGamePreferences().setLevelGoing(mainLevel, true);

            game.getGamePreferences().setLevelCompleted(mainLevel, false);

            state = State.UPDATE;
        }
    }

    private void update(float deltaTime){

        if (game.getGamePreferences().levelGoing(mainLevel) && !game.getGamePreferences().levelDone(mainLevel)) {
            levelModel.updateLevel(mainLevel, deltaTime);

            if (game.getGamePreferences().levelDone(mainLevel)){
                levelCompleted.add(LevelNumber.valueOf(mainLevel));

                state = State.END;
            }
        }
    }

    private void completedLevel(){

        for (LevelNumber levelNumber: levelCompleted) {
            levelsToDo.remove(levelNumber);
        }
        state = State.START;
    }

    public LevelModel getLevelModel() {
        return levelModel;
    }
}
