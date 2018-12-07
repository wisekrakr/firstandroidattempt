package com.wisekrakr.androidmain;


import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.systems.BallSystem;
import com.wisekrakr.androidmain.systems.CollisionSystem;
import com.wisekrakr.androidmain.systems.LevelGenerationSystem;
import com.wisekrakr.androidmain.systems.PhysicsDebugSystem;
import com.wisekrakr.androidmain.systems.PhysicsSystem;
import com.wisekrakr.androidmain.systems.PlayerControlSystem;
import com.wisekrakr.androidmain.systems.RenderingSystem;
import com.wisekrakr.androidmain.systems.WallSystem;

class GameThread {

    private final EntityCreator entityCreator;
    private AndroidGame game;
    private PooledEngine engine;
    private RenderingSystem renderingSystem;

    GameThread(AndroidGame game) {
        this.game = game;

        entityCreator = new EntityCreator(game.getEngine(), game);
        engine = game.getEngine();

        renderingSystem = new RenderingSystem(game.getSpriteBatch());

        init(renderingSystem);
    }

    private void init(RenderingSystem renderingSystem) {
        engine.addSystem(renderingSystem);
        engine.addSystem(new PhysicsSystem(entityCreator.world));
        engine.addSystem(new PhysicsDebugSystem(entityCreator.world, renderingSystem.getCamera()));
        engine.addSystem(new CollisionSystem(entityCreator));

        engine.addSystem(new LevelGenerationSystem(game, entityCreator));
    }

    RenderingSystem getRenderingSystem() {
        return renderingSystem;
    }

    EntityCreator getEntityCreator(){return  entityCreator;}
}
