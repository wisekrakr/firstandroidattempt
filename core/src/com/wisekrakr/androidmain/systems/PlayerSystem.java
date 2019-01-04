package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;
import com.wisekrakr.androidmain.retainers.TimeKeeper;

import java.util.Iterator;

/**
 * System for Entities (excl. walls/obstacles).
 * What does a entity do when it gets hit by another entity or when it hits a wall.
 * Activated by CollisionSystem.
 *
 * This System also spawns the Entities to shoot towards other Entities. Based on if a player needs a ball,
 * and the GameClock.
 */


public class PlayerSystem extends IteratingSystem {

    private EntityCreator entityCreator;
    private TimeKeeper timer;

    private ComponentMapper<EntityComponent> entityComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;

    @SuppressWarnings("unchecked")
    public PlayerSystem(EntityCreator entityCreator, TimeKeeper timer){
        super(Family.all(PlayerComponent.class).get());

        this.entityCreator = entityCreator;
        this.timer = timer;

        entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);
        EntityComponent entityComponent = entityComponentMapper.get(entity);
        PlayerComponent playerComponent = ComponentMapper.getFor(PlayerComponent.class).get(entity);

        //bodyComponent.body.applyForceToCenter(entityComponent.velocityX, entityComponent.velocityY, true);

        if (!playerComponent.hasEntityToShoot) {
            if (playerComponent.timeSinceLastShot == 0){
                playerComponent.timeSinceLastShot = timer.gameClock;
            }

            if (timer.gameClock - playerComponent.timeSinceLastShot > playerComponent.spawnDelay) {
                spawnBall();

                playerComponent.hasEntityToShoot = true;
                playerComponent.timeSinceLastShot = 0;
            }
        }

    }

    private void spawnBall(){
        Entity entity = entityCreator.createEntity(TypeComponent.Type.BALL,
                BodyFactory.Material.RUBBER,
                GameUtilities.WORLD_WIDTH/2,GameUtilities.BALL_RADIUS,
                0, 0,
                0);
        entityCreator.getTotalEntities().add(0, entity);
    }
}
