package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.retainers.ScoreKeeper;
import com.wisekrakr.androidmain.retainers.TimeKeeper;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TypeComponent;

/**
 * System for Entities (excl. walls/obstacles/player).
 * What does a entity do when it gets hit by another entity or when it hits a wall.
 * Activated by CollisionSystem.
 *
 * This System also spawns the Entities to shoot towards other Entities. Based on if a player needs a ball,
 * and the GameClock.
 */


public class EntitySystem extends IteratingSystem {

    private Entity player;
    private EntityCreator entityCreator;
    private TimeKeeper timer;
    private float waitingForASpot = 0f;

    private ComponentMapper<EntityComponent> entityComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;

    @SuppressWarnings("unchecked")
    public EntitySystem(Entity player, EntityCreator entityCreator, TimeKeeper timer){
        super(Family.all(EntityComponent.class).get());
        this.player = player;
        this.entityCreator = entityCreator;
        this.timer = timer;

        entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        //todo: split entity process and ball spawning for player. Create new system with playercomponent to spawn balls. Also new ways to integrate player in game

        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);
        EntityComponent entityComponent = entityComponentMapper.get(entity);
        PlayerComponent playerComponent = ComponentMapper.getFor(PlayerComponent.class).get(player);

        bodyComponent.body.applyForceToCenter(entityComponent.velocityX, entityComponent.velocityY, true);

       // bodyComponent.body.setLinearVelocity(entityComponent.velocityX, entityComponent.velocityY);

        if (!playerComponent.hasEntityToShoot) {
            if (playerComponent.timeSinceLastShot == 0){
                playerComponent.timeSinceLastShot = timer.gameClock;
            }

            if (timer.gameClock - playerComponent.timeSinceLastShot > playerComponent.spawnDelay) {

                spawnBall(entity);

                playerComponent.hasEntityToShoot = true;

                playerComponent.timeSinceLastShot = 0;
            }
        }

        //todo iterate through all the balls and do not use given entity, but create for loop.

        if (!entityComponent.destroy) {
            if (entityComponent.hitEntity) {
                notYetDestroyed(entity);

            }else if (entityComponent.hitSurface){
                bodyComponent.body.applyForceToCenter(-entityComponent.velocityX, -entityComponent.velocityY, true);

            }else if (entityComponent.hitObstacle){
                if (bodyComponentMapper.get(entity).body.getType() == BodyDef.BodyType.DynamicBody){
                    bodyComponentMapper.get(entity).body.setType(BodyDef.BodyType.StaticBody);
                    bodyComponentMapper.get(entity).body.setAwake(false);

                    System.out.println("bing");//todo remove
                }
            }
        } else {
            if (entityComponent.hitEntity) {
                whenDestroyed();
            }
            bodyComponent.isDead = true;
            entityCreator.getTotalEntities().remove(entity);
        }

        outOfBounds(bodyComponent, entityComponent);
    }

    /**
     * score points todo: get more time? power ups?
     */
    private void whenDestroyed(){
        ScoreKeeper.setScore(+10);
    }


    private void notYetDestroyed(Entity entity){
        if (waitingForASpot == 0){
            waitingForASpot = timer.gameClock;
        }

        float timeToStopMoving = 0.2f;
        if (timer.gameClock - waitingForASpot > timeToStopMoving) {

            if (bodyComponentMapper.get(entity).body.getType() == BodyDef.BodyType.StaticBody){
                bodyComponentMapper.get(entity).body.setType(BodyDef.BodyType.DynamicBody);
                bodyComponentMapper.get(entity).body.setAwake(true);
                System.out.println("bong");//todo remove
            }

            waitingForASpot = timer.gameClock;
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
            entityComponent.destroy = true;
        }else if (bodyComponent.body.getPosition().y > GameUtilities.WORLD_HEIGHT || bodyComponent.body.getPosition().y < 0){
            entityComponent.destroy = true;
        }
    }

    private void spawnBall(Entity entity){
        entity = entityCreator.createEntity(TypeComponent.Type.BALL,
                BodyFactory.Material.RUBBER,
                GameUtilities.WORLD_WIDTH/2,GameUtilities.BALL_RADIUS,
                0, 0,
                bodyComponentMapper.get(entity).body.getAngle());

        entityCreator.getTotalEntities().add(0, entity);
    }
}
