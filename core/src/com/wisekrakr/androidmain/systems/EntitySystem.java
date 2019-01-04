package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.retainers.ScoreKeeper;

import static com.wisekrakr.androidmain.components.TypeComponent.Type.SCENERY;

/**
 * System for Entities (excl. walls/obstacles).
 * What does a entity do when it gets hit by another entity or when it hits a wall.
 * Activated by CollisionSystem.
 *
 * This System also spawns the Entities to shoot towards other Entities. Based on if a player needs a ball,
 * and the GameClock.
 */


public class EntitySystem extends IteratingSystem {

    private EntityCreator entityCreator;

    private ComponentMapper<EntityComponent> entityComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<CollisionComponent> collisionComponentMapper;

    @SuppressWarnings("unchecked")
    public EntitySystem(EntityCreator entityCreator){
        super(Family.all(EntityComponent.class).get());

        this.entityCreator = entityCreator;

        entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);
        EntityComponent entityComponent = entityComponentMapper.get(entity);
        CollisionComponent collisionComponent = collisionComponentMapper.get(entity);

        bodyComponent.body.applyForceToCenter(entityComponent.velocityX, entityComponent.velocityY, true);
       // bodyComponent.body.setLinearVelocity(entityComponent.velocityX, entityComponent.velocityY);

        //todo (BUG) sometimes balls spawn on top of each other, this makes them stick before shooting (BUG).

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
            entityCreator.getTotalEntities().remove(entity);
        }
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
        if (bodyComponent.body.getPosition().x > GameUtilities.WORLD_WIDTH || bodyComponent.body.getPosition().x < 0){
            entityComponent.setDestroy(true);
        }else if (bodyComponent.body.getPosition().y > GameUtilities.WORLD_HEIGHT || bodyComponent.body.getPosition().y < 0){
            entityComponent.setDestroy(true);
        }
    }
}
