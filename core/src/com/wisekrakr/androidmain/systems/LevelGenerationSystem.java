package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.LevelFactory;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TransformComponent;


public class LevelGenerationSystem extends IteratingSystem {
    // get transform component so we can check players height
    private ComponentMapper<TransformComponent> transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
    private LevelFactory lvl;

    public LevelGenerationSystem(LevelFactory levelFactory){
        super(Family.all(PlayerComponent.class).get());
        this.lvl = levelFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformComponentMapper.get(entity);
        int currentPosition = (int) transformComponent.position.y ;
        if((currentPosition + 7) > lvl.currentLevel){
            lvl.generateLevel(currentPosition + 7);
        }
    }
}
