package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;

public class BallSystem extends IteratingSystem {

    private Entity player;

    @SuppressWarnings("unchecked")
    public BallSystem(Entity player){
        super(Family.all(BallComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Box2dBodyComponent b2body = ComponentMapper.getFor(Box2dBodyComponent.class).get(entity);
        BallComponent ballComponent = ComponentMapper.getFor(BallComponent.class).get(entity);

        b2body.body.setLinearVelocity(ballComponent.velocityX, ballComponent.velocityY);

        Box2dBodyComponent playerBodyComp = ComponentMapper.getFor(Box2dBodyComponent.class).get(player);
        float positionX = playerBodyComp.body.getPosition().x;
        float positionY = playerBodyComp.body.getPosition().y;

        float bodyPositionX = b2body.body.getPosition().x;
        float bodyPositionY = b2body.body.getPosition().y;

        if(!ballComponent.destroyed ) {
            if (!ballComponent.sameColor) {
                for (Entity ent: player.getComponent(PlayerComponent.class).balls) {

                }
            }else {
                //todo :count the number of balls connected to each other....see if they are of the same color...if yes...call "destroyed" and add to score... else do  nothing


                float score = player.getComponent(PlayerComponent.class).score;
                score += 10f;
                //System.out.println("score = " + score); //todo: remove
            }
        }else {
            System.out.println("ball died"); //todo: remove
            b2body.isDead = true;
        }
    }
}
