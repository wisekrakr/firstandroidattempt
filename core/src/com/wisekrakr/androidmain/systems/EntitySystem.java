package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

/**
 * System for Entities (excl. walls/obstacles/powerups(see PowerImplementation).
 * What does a entity do when it gets hit by another entity or when it hits a wall.
 * Activated by CollisionSystem.
 *
 */

public class EntitySystem extends IteratingSystem {

    private AndroidGame game;
    private PowerImplementation powerImplementation;

    private ComponentMapper<EntityComponent> entityComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private float timeCounter = 0;

    @SuppressWarnings("unchecked")
    public EntitySystem(AndroidGame game){
        super(Family.all(EntityComponent.class).get());

        this.game = game;

        powerImplementation = new PowerImplementation(game);

        entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);
        EntityComponent entityComponent = entityComponentMapper.get(entity);

        if (!entityComponent.destroy) {
            if (entityComponent.hitSurface || entityComponent.hitObstacle) {
                bodyComponent.body.applyForceToCenter(-entityComponent.velocityX, -entityComponent.velocityY, true);
            }else if (entityComponent.hitEntity && entity != game.getGameThread().getEntityCreator().getTotalShapes().get(0)){
                bodyComponentMapper.get(entity).body.setAwake(false);
            }
        } else {
            if (entityComponent.hitEntity) {
                scoreCounter(10);
            }
            scoreCounter(5);
            bodyComponent.isDead = true;
            game.getGameThread().getEntityCreator().getTotalShapes().remove(entity);
        }

        powerImplementation.updatingPowerUpSystem(entity, GameHelper.generateRandomNumberBetween(20f, 40f));
        outOfBounds(entity);
    }

    /**
     * score points
     */
    private void scoreCounter(int points){
        ScoreKeeper.setScore(points);
    }

    private void setAlive(Entity entity){
        if (bodyComponentMapper.get(entity).body.getType() == BodyDef.BodyType.DynamicBody){
            bodyComponentMapper.get(entity).body.setType(BodyDef.BodyType.StaticBody);
            bodyComponentMapper.get(entity).body.setAwake(false);
        }else if (bodyComponentMapper.get(entity).body.getType() == BodyDef.BodyType.StaticBody) {
            bodyComponentMapper.get(entity).body.setType(BodyDef.BodyType.DynamicBody);
            bodyComponentMapper.get(entity).body.setAwake(true);
        }
    }

    /**
     * The Entity gets scoreCounter (body as well) when it goes out of bounds.
     *
     * @param entity
     */
    private void outOfBounds(Entity entity){
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);
        EntityComponent entityComponent = entityComponentMapper.get(entity);

        if (bodyComponent.body.getPosition().x + entityComponent.width/2 > GameConstants.WORLD_WIDTH || bodyComponent.body.getPosition().x - entityComponent.width/2 < 0){
            entityComponent.setDestroy(true);
        }else if (bodyComponent.body.getPosition().y + entityComponent.height/2 > GameConstants.WORLD_HEIGHT || bodyComponent.body.getPosition().y - entityComponent.height/2 < 0){
            entityComponent.setDestroy(true);
            game.getGameThread().getTimeKeeper().setTime(game.getGameThread().getTimeKeeper().time - 10);
        }
    }
}
