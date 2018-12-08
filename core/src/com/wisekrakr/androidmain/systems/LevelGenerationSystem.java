package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.TimeComponent;
import com.wisekrakr.androidmain.levels.LevelModel;


public class LevelGenerationSystem extends IteratingSystem {
    private AndroidGame game;
    private EntityCreator entityCreator;
    private LevelModel levelModel;


    @SuppressWarnings("unchecked")
    public LevelGenerationSystem(AndroidGame game, EntityCreator entityCreator){
        super(Family.all(LevelComponent.class).get());
        this.game = game;
        this.entityCreator = entityCreator;

        levelModel = new LevelModel(game, entityCreator);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        LevelComponent levelComponent = ComponentMapper.getFor(LevelComponent.class).get(entity);


        //maybe use a switch

        if (!game.getGamePreferences().levelDone(1)) {

            levelModel.updatingLevel(1, 4, 4, deltaTime);
        }else if (!game.getGamePreferences().levelDone(2) && game.getGamePreferences().levelDone(1)) {

            levelModel.updatingLevel(2, 5, 8, deltaTime);
        }


    }

    public Entity getPlayer() {
        return levelModel.getPlayer();
    }
}
