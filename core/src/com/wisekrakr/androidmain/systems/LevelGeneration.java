package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.LevelFactory;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.StateComponent;



public class LevelGeneration extends IteratingSystem {
    private AndroidGame game;
    private LevelFactory levelFactory;


    @SuppressWarnings("unchecked")
    public LevelGeneration(AndroidGame game, LevelFactory levelFactory){
        super(Family.all(LevelComponent.class).get());
        this.game = game;
        this.levelFactory = levelFactory;


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        StateComponent stateComponent = ComponentMapper.getFor(StateComponent.class).get(levelFactory.getPlayer());
        LevelComponent levelComponent = ComponentMapper.getFor(LevelComponent.class).get(levelFactory.getPlayer());


//        if (stateComponent.time == 0) {
//            stateComponent.set(StateComponent.GAME_OVER);
//
//            if (!(levelFactory.totalBalls().size() <= 5)) {
//                //you lose...start again screen
//            } else {
//                stateComponent.set(StateComponent.NEW_LEVEL);
//            }
//        }
        /*TODO: if level one is clicked in selection, we set it to level 1 here...etc
            changeScreen(levelTwo)
         */



        if (!game.getGamePreferences().levelOneDone()){
            //create level one here ......in levels set up rows and columns and fill them in here new Levels(4, 10) something like that
            levelOne(levelComponent, stateComponent, deltaTime);
        }
        if (game.getGamePreferences().levelOneDone()){
           levelTwo(levelComponent, stateComponent, deltaTime);
        }

        if (levelComponent.completed){
            for (Entity entity1: levelFactory.totalBalls()) {
                entity1.getComponent(BallComponent.class).destroyed = true;
            }
        }

    }

    private void levelOne(LevelComponent levelComponent, StateComponent stateComponent, float deltaTime){
        stateComponent.time -= deltaTime;
        if (levelComponent.levelNumber == LevelComponent.LevelNumber.ONE) {
            levelFactory.generateLevel();
            levelComponent.levelNumber = LevelComponent.LevelNumber.values()[levelComponent.levelNumber.ordinal()+1];
        }
        System.out.println("BALLS" + levelFactory.totalBalls().size());//todo remove
        if (levelFactory.totalBalls().size() <= 26) {
            game.getGamePreferences().setLevelOneCompleted(true);
            levelComponent.completed = true;
            stateComponent.time = stateComponent.timeSaver;
            game.changeScreen(AndroidGame.LEVELSELECTION);
        }
    }

    private void levelTwo(LevelComponent levelComponent, StateComponent stateComponent, float deltaTime){
        levelComponent.completed = false;

        stateComponent.time -= deltaTime;
        if (levelComponent.levelNumber == LevelComponent.LevelNumber.TWO) {
            levelFactory.generateLevelTwo();
            levelComponent.levelNumber = LevelComponent.LevelNumber.values()[levelComponent.levelNumber.ordinal()+1];
        }
        if (levelFactory.totalBalls().size() <= 20) {
            levelComponent.completed = true;
            game.changeScreen(AndroidGame.LEVELSELECTION);
        }
    }

}
