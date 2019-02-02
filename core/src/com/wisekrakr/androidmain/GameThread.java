package com.wisekrakr.androidmain;


import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wisekrakr.androidmain.retainers.TimeKeeper;
import com.wisekrakr.androidmain.systems.EntitySystem;
import com.wisekrakr.androidmain.systems.LevelGenerationSystem;
import com.wisekrakr.androidmain.systems.PhysicsSystem;
import com.wisekrakr.androidmain.systems.RenderingSystem;

public class GameThread {

    private final EntityCreator entityCreator;
    private LevelGenerationSystem levelGenerationSystem;
    private AndroidGame game;
    private PooledEngine engine;
    private TimeKeeper timeKeeper;

    protected GameThread(AndroidGame game) {
        this.game = game;

        timeKeeper = new TimeKeeper();

        engine = game.getEngine();

        entityCreator = new EntityCreator(game, engine);
        levelGenerationSystem = new LevelGenerationSystem(game, entityCreator);

        init();
    }

    private void init() {
        engine.addSystem(new PhysicsSystem(entityCreator.world));
    }

    public EntityCreator getEntityCreator(){return  entityCreator;}

    public LevelGenerationSystem getLevelGenerationSystem(){return levelGenerationSystem;}

    public TimeKeeper getTimeKeeper() {
        return timeKeeper;
    }

}
