package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.LevelFactory;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.RowComponent;

public class RowSystem extends IteratingSystem {
    private ComponentMapper ballComponentMapper;
    private LevelFactory levelFactory;
    private Entity player;

    @SuppressWarnings("unchecked")
    public RowSystem(LevelFactory levelFactory, Entity player) {
        super(Family.all(BallComponent.class).get());
        this.levelFactory = levelFactory;
        this.player = player;
        ballComponentMapper = ComponentMapper.getFor(BallComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        BallComponent ballComponent = (BallComponent) ballComponentMapper.get(entity);

//        if (ballComponent.hitBall) {
//            for (Entity ball : levelFactory.totalBalls()) {
//                if (ball.getComponent(BallComponent.class).hitBall) {
//                    if (ball.getComponent(BallComponent.class).ballColor == ball.getComponent(BallComponent.class).getCollisionEntity().getComponent(BallComponent.class).ballColor) {
//                        player.getComponent(PlayerComponent.class).balls.remove(ball);
//                        player.getComponent(PlayerComponent.class).balls.remove(ball.getComponent(BallComponent.class).getCollisionEntity());
//
//                        ballComponent.destroyed = true;
//                        ball.getComponent(BallComponent.class).getCollisionEntity().getComponent(BallComponent.class).destroyed = true;
//
//
//                        float score = player.getComponent(PlayerComponent.class).score;
//                        score += 10f;
//                        //System.out.println("score = " + score); //todo: remove
//                    }
//                }
//            }
//        }

    }
}
