package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.LevelFactory;
import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.StateComponent;

public class Levels {

    private LevelComponent levelComponent;
    private LevelFactory levelFactory;

    public Levels(LevelFactory levelFactory) {
        this.levelFactory = levelFactory;
        levelComponent = ComponentMapper.getFor(LevelComponent.class).get(levelFactory.getPlayer());
    }

    private void levelOne(){
        if (!levelComponent.completed) {
            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 4; j++) {
                    levelFactory.createRowBall(i * GameUtilities.BALL_RADIUS,
                            Gdx.graphics.getHeight() - j * GameUtilities.BALL_RADIUS);
                }
            }
        }else {
            levelComponent.levelNumber = LevelComponent.LevelNumber.TWO;
        }
    }

    private void levelTwo(){
        if (!levelComponent.completed) {
            for (int i = 1; i < 5; i++) {
                for (int j = 3; j < 8; j++) {
                    levelFactory.createRowBall(i * GameUtilities.BALL_RADIUS,
                            Gdx.graphics.getHeight() - j * GameUtilities.BALL_RADIUS);
                }
            }
        }else {
            levelComponent.levelNumber = LevelComponent.LevelNumber.THREE;
        }
    }

    public void loadLevel() {


        switch (levelComponent.levelNumber){
            case ONE:
                levelOne();
                break;
            case TWO:
                levelTwo();
                break;

                default:
                    System.out.println("No Level Selected");
        }


    }
}
