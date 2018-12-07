package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.components.LevelComponent;

public class LevelFactory extends Entity {

    private AndroidGame game;
    private EntityCreator entityCreator;
    private PooledEngine engine;

    public LevelFactory(AndroidGame game, EntityCreator entityCreator) {
        this.game = game;
        this.entityCreator = entityCreator;

        engine = game.getEngine();
    }



    public Entity createLevel(float x, float y){



        entityCreator.createRowBall(x,y);

        Entity entity = engine.createEntity();

        LevelComponent levelComponent = engine.createComponent(LevelComponent.class);

        entity.add(levelComponent);

        engine.addEntity(entity);

        return entity;

    }


}
