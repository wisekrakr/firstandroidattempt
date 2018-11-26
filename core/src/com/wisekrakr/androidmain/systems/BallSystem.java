package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.LevelFactory;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.RowComponent;

import java.util.ArrayList;
import java.util.List;

public class BallSystem extends IteratingSystem {

    private Entity player;
    private LevelFactory levelFactory;
    private float waitingForASpot = 0;
    private float timeToStopMoving = 10f;



    @SuppressWarnings("unchecked")
    public BallSystem(Entity player, LevelFactory levelFactory){
        super(Family.all(BallComponent.class).get());
        this.player = player;
        this.levelFactory = levelFactory;
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
                Entity ball = levelFactory.createBall(
                        BodyFactory.Material.RUBBER,
                        positionX, positionY + GameUtilities.BALL_RADIUS,
                        0 , 0 );

                playerComponent.balls.set(0, ball);
                playerComponent.hasBall = true;
                playerComponent.timeSinceLastShot = 0f;
            }
        }
        if (ballComponent.destroyed){
            b2body.isDead = true;
        }

        if(!ballComponent.destroyed ) {
            if (ballComponent.hitBall) {
                waitingForASpot += deltaTime;
                System.out.println(waitingForASpot);
                if (waitingForASpot > timeToStopMoving) {
                    ballComponent.velocityX = 0f;
                    ballComponent.velocityY = 0f;
                    ballComponent.getCollisionEntity().getComponent(BallComponent.class).velocityX = 0f;
                    ballComponent.getCollisionEntity().getComponent(BallComponent.class).velocityY = 0f;

                    waitingForASpot = 0f;
                }
            }
        }
        else {
            System.out.println("ball died"); //todo: remove
            b2body.isDead = true;
        }

    }

}