package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.ObstacleComponent;

public class ObstacleSystem extends IteratingSystem {

    private EntityCreator entityCreator;

    public ObstacleSystem(EntityCreator entityCreator) {
        super(Family.all(ObstacleComponent.class).get());
        this.entityCreator = entityCreator;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        ObstacleComponent obstacleComponent = ComponentMapper.getFor(ObstacleComponent.class).get(entity);
        Box2dBodyComponent bodyComponent = ComponentMapper.getFor(Box2dBodyComponent.class).get(entity);

        if (bodyComponent.body.getPosition().x + obstacleComponent.width/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - obstacleComponent.width/2 < 0){
            obstacleComponent.velocityX = -obstacleComponent.velocityX;
        }else if (bodyComponent.body.getPosition().y + obstacleComponent.height/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - obstacleComponent.height/2 < 0){
            obstacleComponent.velocityY = -obstacleComponent.velocityY;
        }

        if (obstacleComponent.destroy) {
            bodyComponent.isDead = true;
            entityCreator.getTotalObstacles().remove(entity);
        }else {
            bodyComponent.body.setLinearVelocity(obstacleComponent.velocityX, obstacleComponent.velocityY);
        }
    }
}
