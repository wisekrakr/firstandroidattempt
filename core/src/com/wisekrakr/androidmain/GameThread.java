package com.wisekrakr.androidmain;


import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.systems.CollisionSystem;
import com.wisekrakr.androidmain.systems.LevelGenerationSystem;
import com.wisekrakr.androidmain.systems.PhysicsDebugSystem;
import com.wisekrakr.androidmain.systems.PhysicsSystem;
import com.wisekrakr.androidmain.systems.RenderingSystem;

class GameThread {

    private final EntityCreator entityCreator;
    private LevelGenerationSystem levelGenerationSystem;
    private AndroidGame game;
    private PooledEngine engine;
    private RenderingSystem renderingSystem;

    GameThread(AndroidGame game) {
        this.game = game;

        entityCreator = new EntityCreator(game, game.getEngine());
        engine = game.getEngine();

        renderingSystem = new RenderingSystem(game.getSpriteBatch());
        levelGenerationSystem = new LevelGenerationSystem(game, entityCreator);

        init(renderingSystem);
    }

    private void init(RenderingSystem renderingSystem) {
        engine.addSystem(renderingSystem);
        engine.addSystem(new PhysicsSystem(entityCreator.world));
        engine.addSystem(new PhysicsDebugSystem(entityCreator.world, renderingSystem.getCamera()));
        engine.addSystem(new CollisionSystem());

    }

    RenderingSystem getRenderingSystem() {
        return renderingSystem;
    }

    EntityCreator getEntityCreator(){return  entityCreator;}

    LevelGenerationSystem getLevelGenerationSystem(){return levelGenerationSystem;}
}
