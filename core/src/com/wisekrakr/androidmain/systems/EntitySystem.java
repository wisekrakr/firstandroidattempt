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

import com.wisekrakr.androidmain.components.GameTimer;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TypeComponent;


public class EntitySystem extends IteratingSystem {

    private Entity player;
    private EntityCreator entityCreator;
    private GameTimer timer;
    private float waitingForASpot = 0f;
    float save = 0;
    @SuppressWarnings("unchecked")
    public EntitySystem(Entity player, EntityCreator entityCreator, GameTimer timer){
        super(Family.all(EntityComponent.class).get());
        this.player = player;
        this.entityCreator = entityCreator;
        this.timer = timer;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Box2dBodyComponent bodyComponent = ComponentMapper.getFor(Box2dBodyComponent.class).get(entity);
        EntityComponent entityComponent = ComponentMapper.getFor(EntityComponent.class).get(entity);
        PlayerComponent playerComponent = ComponentMapper.getFor(PlayerComponent.class).get(player);

        bodyComponent.body.applyForceToCenter(entityComponent.velocityX, entityComponent.velocityY, true);

        //bodyComponent.body.setLinearVelocity(entityComponent.velocityX, entityComponent.velocityY);

        Box2dBodyComponent playerBodyComp = ComponentMapper.getFor(Box2dBodyComponent.class).get(player);
        float positionX = playerBodyComp.body.getPosition().x;
        float positionY = playerBodyComp.body.getPosition().y;


        //TODO bugfix: sometimes spawns two balls too quickly after one another. Then the balls get stuck together or get de-spawned.

        if (!playerComponent.hasEntityToShoot) {
            if (playerComponent.timeSinceLastShot == 0){
                playerComponent.timeSinceLastShot = timer.gameClock;
            }

            if (timer.gameClock - playerComponent.timeSinceLastShot > playerComponent.spawnDelay) {

                entity = entityCreator.createEntity(TypeComponent.Type.BALL,
                        BodyFactory.Material.RUBBER,
                        positionX,positionY + GameUtilities.BALL_RADIUS,
                        0, 0,
                        bodyComponent.body.getAngle());

                entityCreator.getTotalEntities().add(0, entity);

                playerComponent.hasEntityToShoot = true;

                playerComponent.timeSinceLastShot = 0;
            }
        }

        if (!entityComponent.destroy) {
            if (entityComponent.hitEntity) {
                notYetDestroyed(bodyComponent, deltaTime);
                if (bodyComponent.body.getType() == BodyDef.BodyType.StaticBody){
                    bodyComponent.body.setType(BodyDef.BodyType.DynamicBody);
                    bodyComponent.body.setAwake(true);
                }
            }else if (entityComponent.hitSurface){
                //notYetDestroyed(bodyComponent, deltaTime);
                bodyComponent.body.applyForceToCenter(-entityComponent.velocityX, -entityComponent.velocityY, true);
            }else if (entityComponent.hitObstacle){
                bodyComponent.body.applyForceToCenter(-entityComponent.velocityX, -entityComponent.velocityY, true);
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

    private void whenDestroyed(){
        player.getComponent(PlayerComponent.class).score += 10;
    }

    private void notYetDestroyed(Box2dBodyComponent bodyComponent, float deltaTime){
        waitingForASpot += (deltaTime/100);
        float timeToStopMoving = 3f;
        if (waitingForASpot > timeToStopMoving) {

            if (bodyComponent.body.getType() == BodyDef.BodyType.DynamicBody){
                bodyComponent.body.setType(BodyDef.BodyType.StaticBody);
                bodyComponent.body.setAwake(false);
            }

            waitingForASpot = 0f;
        }
    }

    private void outOfBounds(Box2dBodyComponent bodyComponent, EntityComponent entityComponent){
        if (bodyComponent.body.getPosition().x > GameUtilities.WORLD_WIDTH || bodyComponent.body.getPosition().x < 0){
            entityComponent.destroy = true;
        }else if (bodyComponent.body.getPosition().y > GameUtilities.WORLD_HEIGHT || bodyComponent.body.getPosition().y < 0){
            entityComponent.destroy = true;
        }
    }


}
