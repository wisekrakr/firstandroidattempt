package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.ObstacleComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TransformComponent;
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

        entityVisuals = new EntityVisuals(game, spriteBatch);
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
                    float x = entity.getComponent(TransformComponent.class).position.x;

                    if (x > entity.getComponent(TransformComponent.class).position.x) {
                        entityVisuals.drawObjectViaAtlas(entity,
                                "images/game/game.atlas",
                                "dude_left",
                                GameConstants.WORLD_WIDTH / 10,
                                GameConstants.BALL_RADIUS / 3);
                    }else if (x < entity.getComponent(TransformComponent.class).position.x){
                        entityVisuals.drawObjectViaAtlas(entity,
                                "images/game/game.atlas",
                                "dude_right",
                                GameConstants.WORLD_WIDTH / 10,
                                GameConstants.BALL_RADIUS / 3);
                    }else {
                        entityVisuals.drawObjectViaAtlas(entity,
                                "images/game/game.atlas",
                                "dude_front",
                                GameConstants.WORLD_WIDTH / 10,
                                GameConstants.BALL_RADIUS / 3);
                    }

                    break;
                case OBSTACLE:
                    entityVisuals.drawObjectViaAtlas(entity,
                            "images/game/game.atlas",
                            "woodfloor",
                            entity.getComponent(ObstacleComponent.class).width,
                            entity.getComponent(ObstacleComponent.class).height);
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
