package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;

import java.util.Iterator;


public class BallSystem extends IteratingSystem {

    private Entity player;
    private EntityCreator entityCreator;
    private float waitingForASpot = 0;
    private float timeToStopMoving = 10f;

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
        PlayerComponent playerComponent = player.getComponent(PlayerComponent.class);

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

                playerComponent.balls.set(0, entity);
                playerComponent.hasBall = true;
                playerComponent.timeSinceLastShot = 0f;
                entityCreator.totalBalls().add(entity);
            }
        }

        if (ballComponent.destroyed) {
            b2body.isDead = true;
            player.getComponent(PlayerComponent.class).score += 10;
            entityCreator.totalBalls().remove(entity);
        }

        if (!ballComponent.destroyed) {
            if (ballComponent.hitBall) {
                waitingForASpot += deltaTime;
                if (waitingForASpot > timeToStopMoving) {
                    ballComponent.velocityX = 0f;
                    ballComponent.velocityY = 0f;

                    waitingForASpot = 0f;
                }
            }
        } else {
            System.out.println("ball died"); //todo: remove
            b2body.isDead = true;
        }
    }
}
