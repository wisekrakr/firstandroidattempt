package com.wisekrakr.androidmain.visuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.systems.EntitySystem;
import com.wisekrakr.androidmain.systems.PhysicsDebugSystem;
import com.wisekrakr.androidmain.systems.RenderingSystem;

public class Visualizer implements Disposable {

    private AndroidGame game;
    private EntitySystem entitySystem;
    private RenderingSystem renderingSystem;
    private EntityVisuals entityVisuals;

    private final SpriteBatch spriteBatch;

    public Visualizer(AndroidGame game, EntitySystem entitySystem) {
        this.game = game;
        this.entitySystem = entitySystem;

        spriteBatch = new SpriteBatch();

        addSystems();
    }

    private void addSystems(){
        renderingSystem = new RenderingSystem(spriteBatch);

        game.getEngine().addSystem(renderingSystem);
        game.getEngine().addSystem(new PhysicsDebugSystem(game.getGameThread().getEntityCreator().world, renderingSystem.getCamera()));

        entityVisuals = new EntityVisuals(game, entitySystem, spriteBatch);
    }

    public RenderingSystem getRenderingSystem() {
        return renderingSystem;
    }

    public void draw(){

        spriteBatch.setProjectionMatrix(renderingSystem.getCamera().combined);

        spriteBatch.begin();

        for (Entity entity: game.getEngine().getEntities()){
            TypeComponent.Type type = entity.getComponent(TypeComponent.class).getType();

            switch (type){
                case BALL:
                    entityVisuals.visualizeColoredEntity(entity, type);
                    break;
                case SQUARE:
                    entityVisuals.visualizeColoredEntity(entity, type);
                    break;
                case TRIANGLE:
                    entityVisuals.visualizeColoredEntity(entity, type);
                    break;
                case PLAYER:

                    break;
                case OBSTACLE:

                    break;
                case POWER:
                    entityVisuals.visualizePower(entity);
                    break;
                case THING:

                    break;

            }
        }
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
