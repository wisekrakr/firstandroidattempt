package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.LevelFactory;
import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.StateComponent;
import com.wisekrakr.androidmain.levels.Levels;


public class LevelGeneration {
    private AndroidGame game;
    private LevelFactory levelFactory;
    private Levels levels;

    public LevelGeneration(AndroidGame game, LevelFactory levelFactory){
        this.game = game;
        this.levelFactory = levelFactory;
        levels = new Levels(levelFactory);
    }

    public void levelUpdate(float deltaTime) {
        StateComponent stateComponent = ComponentMapper.getFor(StateComponent.class).get(levelFactory.getPlayer());
        LevelComponent levelComponent = ComponentMapper.getFor(LevelComponent.class).get(levelFactory.getPlayer());
        System.out.println(stateComponent.time + " " + levelFactory.totalBalls().size()); //todo remove

        stateComponent.time -= deltaTime;

        if (stateComponent.time == 0) {
            stateComponent.set(StateComponent.GAME_OVER);

            if (!(levelFactory.totalBalls().size() <= 5)) {
                //you lose...start again screen
            } else {
                stateComponent.set(StateComponent.NEW_LEVEL);
            }
        }

//
//        if (stateComponent.get() == StateComponent.NEW_LEVEL){
//            if (!(levelComponent.levelNumber == LevelComponent.LevelNumber.ONE)) {
//                levelComponent.levelNumber = LevelComponent.LevelNumber.ONE;
//                if (levelComponent.completed){
//                    if (!(levelComponent.levelNumber == LevelComponent.LevelNumber.TWO)){
//                        levelComponent.levelNumber = LevelComponent.LevelNumber.TWO;
//                    }
//                }
//            }
//        }else if (stateComponent.get() == StateComponent.GAME_OVER){
//            game.changeScreen(AndroidGame.ENDGAME);
//        }



    }

}
