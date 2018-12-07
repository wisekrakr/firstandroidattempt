package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.LevelComponent;

public class AbstractLevel extends AbstractLevelContext {

    private int rows;
    private int columns;
    private int balls;

    private boolean locked;
    private boolean completed;

    @Override
    public void init(Entity entity) {
        LevelComponent levelComponent = ComponentMapper.getFor(LevelComponent.class).get(entity);

        rows = levelComponent.rows;
        columns = levelComponent.columns;

        entity.add(levelComponent);
    }

    @Override
    public void startLevel() {


//        for (int j = 1; j < columns; j++) {
//            for (int k = 1; k < rows; k++) {
//                entityCreator.createRowBall(j * GameUtilities.BALL_RADIUS,
//                        Gdx.graphics.getHeight() - k * GameUtilities.BALL_RADIUS);
//            }
//        }
    }

    @Override
    public void updateLevel(float delta) {
        super.updateLevel(delta);
    }

    @Override
    public void completeLevel() {
        super.completeLevel();
    }
}
