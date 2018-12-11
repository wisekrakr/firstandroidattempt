package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TypeComponent;


public class EntitySystem extends IteratingSystem {

    private Entity player;
    private EntityCreator entityCreator;
    private float waitingForASpot = 0;

    @SuppressWarnings("unchecked")
    public EntitySystem(Entity player, EntityCreator entityCreator){
        super(Family.all(EntityComponent.class).get());
        this.player = player;
        this.entityCreator = entityCreator;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Box2dBodyComponent b2body = ComponentMapper.getFor(Box2dBodyComponent.class).get(entity);
        EntityComponent entityComponent = ComponentMapper.getFor(EntityComponent.class).get(entity);
        PlayerComponent playerComponent = ComponentMapper.getFor(PlayerComponent.class).get(player);

        b2body.body.applyForceToCenter(entityComponent.velocityX, entityComponent.velocityY, true);
        //b2body.body.setLinearVelocity(entityComponent.velocityX, entityComponent.velocityY);

        Box2dBodyComponent playerBodyComp = ComponentMapper.getFor(Box2dBodyComponent.class).get(player);
        float positionX = playerBodyComp.body.getPosition().x;
        float positionY = playerBodyComp.body.getPosition().y;

        if (!playerComponent.hasEntityToShoot) {
            playerComponent.timeSinceLastShot += deltaTime;

            if (playerComponent.timeSinceLastShot > playerComponent.spawnDelay) {

                entity = entityCreator.createEntity(TypeComponent.Type.BALL,
                        positionX, positionY + GameUtilities.BALL_RADIUS,
                        0, 0, b2body.body.getAngle());

                playerComponent.hasEntityToShoot = true;

                playerComponent.timeSinceLastShot = 0f;

                entityCreator.totalEntities().add(0, entity);
            }
        }

        if (entityComponent.destroyed) {
            if (entityComponent.hitEntity) {
                whenDestroyed();
            }
            b2body.isDead = true;
            entityCreator.totalEntities().remove(entity);
        }

        if (!entityComponent.destroyed) {
            if (entityComponent.hitEntity) {
                notYetDestroyed(entityComponent, deltaTime);
            }
        } else {
            System.out.println("ball died"); //todo: remove
            b2body.isDead = true;
        }
    }

    private void whenDestroyed(){
        player.getComponent(PlayerComponent.class).score += 10;
    }

    private void notYetDestroyed(EntityComponent entityComponent, float deltaTime){
        waitingForASpot += deltaTime;
        float timeToStopMoving = 0.3f;
        if (waitingForASpot > timeToStopMoving) {
            entityComponent.velocityX = 0f;
            entityComponent.velocityY = 0f;

            waitingForASpot = 0f;
        }
    }
}
