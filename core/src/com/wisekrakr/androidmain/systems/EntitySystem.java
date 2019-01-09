package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

import java.util.Optional;

/**
 * System for Entities (excl. walls/obstacles/powerups(see PowerUpSystem).
 * What does a entity do when it gets hit by another entity or when it hits a wall.
 * Activated by CollisionSystem.
 *
 */


public class EntitySystem extends IteratingSystem {

    private AndroidGame game;
    private PowerUpSystem powerUpSystem;

    private ComponentMapper<EntityComponent> entityComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;

    @SuppressWarnings("unchecked")
    public EntitySystem(AndroidGame game){
        super(Family.all(EntityComponent.class).get());

        this.game = game;

        powerUpSystem = new PowerUpSystem(game);

        entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);
        EntityComponent entityComponent = entityComponentMapper.get(entity);

        bodyComponent.body.applyForceToCenter(entityComponent.velocityX, entityComponent.velocityY, true);

        if (!entityComponent.destroy) {
            if (entityComponent.hitEntity) {
                setAlive(entity);

            } else if (entityComponent.hitSurface) {
                bodyComponent.body.applyForceToCenter(-entityComponent.velocityX, -entityComponent.velocityY, true);
                //multiplyPoints(entity);

            } else if (entityComponent.hitObstacle) {
                bodyComponent.body.applyForceToCenter(-entityComponent.velocityX, -entityComponent.velocityY, true);

                if (entityComponent.multiplier >= 0) {
                    entityComponent.setMultiplier(0);
                }
            }
        } else {
            if (entityComponent.hitEntity) {
                destroyed(entity);
            }
            bodyComponent.isDead = true;
            game.getEntityCreator().getTotalEntities().remove(entity);
        }

        powerUpSystem.traversePowerUpStages(entity, 3f);
        outOfBounds(bodyComponent, entityComponent);
    }

    private void multiplyPoints(Entity entity){
        entityComponentMapper.get(entity).setMultiplier(entityComponentMapper.get(entity).multiplier += 1);
    }

    /**
     * score points
     */
    private void destroyed(Entity entity){
        int score = entityComponentMapper.get(entity).setPointsToGive(10);

        ScoreKeeper.setScore(score);

    }

    private void setAlive(Entity entity){

        if (bodyComponentMapper.get(entity).body.getType() == BodyDef.BodyType.StaticBody){
            bodyComponentMapper.get(entity).body.setType(BodyDef.BodyType.DynamicBody);
            bodyComponentMapper.get(entity).body.setAwake(true);
        }
    }

    /**
     * The Entity gets destroyed (body as well) when it goes out of bounds.
     *
     * @param bodyComponent component of Box2dBody.
     * @param entityComponent component of an Entity
     */
    private void outOfBounds(Box2dBodyComponent bodyComponent, EntityComponent entityComponent){
        if (bodyComponent.body.getPosition().x + entityComponent.width/2 > GameUtilities.WORLD_WIDTH || bodyComponent.body.getPosition().x - entityComponent.width/2 < 0){
            entityComponent.setDestroy(true);
        }else if (bodyComponent.body.getPosition().y + entityComponent.height/2 > GameUtilities.WORLD_HEIGHT || bodyComponent.body.getPosition().y - entityComponent.height/2 < 0){
            entityComponent.setDestroy(true);
        }
    }
}
