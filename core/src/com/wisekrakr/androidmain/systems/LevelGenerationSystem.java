package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.LevelFactory;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.RowComponent;
import com.wisekrakr.androidmain.components.TransformComponent;

import java.util.HashMap;
import java.util.Map;


public class LevelGenerationSystem extends IteratingSystem {
    private LevelFactory levelFactory;


    public LevelGenerationSystem(LevelFactory levelFactory){
        super(Family.all(RowComponent.class).get());
        this.levelFactory = levelFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RowComponent rowComponent = ComponentMapper.getFor(RowComponent.class).get(entity);
        BallComponent ballComponent = ComponentMapper.getFor(BallComponent.class).get(entity);



    }
}
