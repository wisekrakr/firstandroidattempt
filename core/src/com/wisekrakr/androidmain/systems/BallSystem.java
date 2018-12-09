package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;


public class BallSystem extends IteratingSystem {

    private Entity player;
    private EntityCreator entityCreator;
    private float waitingForASpot = 0;

    @SuppressWarnings("unchecked")
    public BallSystem(Entity player, EntityCreator entityCreator){
        super(Family.all(BallComponent.class).get());
        this.player = player;
        this.entityCreator = entityCreator;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Box2dBodyComponent b2body = ComponentMapper.getFor(Box2dBodyComponent.class).get(entity);
        BallComponent ballComponent = ComponentMapper.getFor(BallComponent.class).get(entity);
        PlayerComponent playerComponent = ComponentMapper.getFor(PlayerComponent.class).get(player);

        b2body.body.setLinearVelocity(ballComponent.velocityX, ballComponent.velocityY);

        Box2dBodyComponent playerBodyComp = ComponentMapper.getFor(Box2dBodyComponent.class).get(player);
        float positionX = playerBodyComp.body.getPosition().x;
        float positionY = playerBodyComp.body.getPosition().y;

        if (!playerComponent.hasBall) {
            playerComponent.timeSinceLastShot += deltaTime;

            if (playerComponent.timeSinceLastShot > playerComponent.spawnDelay) {
                entity = entityCreator.createBall(
                        BodyFactory.Material.RUBBER,
                        positionX, positionY + GameUtilities.BALL_RADIUS,
                        0, 0);


                playerComponent.hasBall = true;
                playerComponent.timeSinceLastShot = 0f;

                entityCreator.totalBalls().add(0, entity);
            }
        }

        if (ballComponent.destroyed) {
            if (ballComponent.hitBall) {
                whenDestroyed();
            }
            b2body.isDead = true;
            entityCreator.totalBalls().remove(entity);
        }

        if (!ballComponent.destroyed) {
            if (ballComponent.hitBall) {
                notYetDestroyed(ballComponent, deltaTime);
            }
        } else {
            System.out.println("ball died"); //todo: remove
            b2body.isDead = true;
        }


    }

    private void whenDestroyed(){
        player.getComponent(PlayerComponent.class).score += 10;
    }

    private void notYetDestroyed(BallComponent ballComponent, float deltaTime){
        waitingForASpot += deltaTime;
        float timeToStopMoving = 5f;
        if (waitingForASpot > timeToStopMoving) {
            ballComponent.velocityX = 0f;
            ballComponent.velocityY = 0f;

            waitingForASpot = 0f;
        }
    }
}
