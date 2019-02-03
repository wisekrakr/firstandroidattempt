package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.helpers.GameHelper;

import java.util.List;

/**
 * System for Entities (excl. walls/obstacles).
 * What does a entity do when it gets hit by another entity or when it hits a wall.
 * Activated by CollisionSystem.
 *
 * This System also spawns the Entities to shoot towards other Entities. Based on if a player needs a ball,
 * and the GameClock.
 */


public class PlayerSystem extends IteratingSystem {

    private AndroidGame game;

    private ComponentMapper<Box2dBodyComponent>box2dBodyComponentMapper;
    private ComponentMapper<PlayerComponent>playerComponentMapper;

    @SuppressWarnings("unchecked")
    public PlayerSystem(AndroidGame game){
        super(Family.all(PlayerComponent.class).get());
        this.game = game;

        box2dBodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        PlayerComponent playerComponent = playerComponentMapper.get(entity);
        Box2dBodyComponent bodyComponent = box2dBodyComponentMapper.get(entity);

        if (!playerComponent.hasEntityToShoot) {
            if (playerComponent.timeSinceLastShot == 0){
                playerComponent.timeSinceLastShot = game.getGameThread().getTimeKeeper().gameClock;
            }

            if (game.getGameThread().getTimeKeeper().gameClock - playerComponent.timeSinceLastShot > playerComponent.spawnDelayEntity) {
                spawnBall(entity);

                playerComponent.hasEntityToShoot = true;
                playerComponent.timeSinceLastShot = 0;
            }
        }else {
            Entity ent = game.getGameThread().getEntityCreator().getTotalShapes().get(0);

            ent.getComponent(Box2dBodyComponent.class).body.setTransform(new Vector2(
                    bodyComponent.body.getPosition().x,
                    bodyComponent.body.getPosition().y + GameConstants.BALL_RADIUS
            ), 0);
        }
        outOfBounds(entity);
    }

    private void spawnBall(Entity entity){
        Box2dBodyComponent bodyComponent = box2dBodyComponentMapper.get(entity);

        EntityComponent.EntityColor color = null;

        if (game.getGameThread().getEntityCreator().getTotalShapes().size() < 10) {

            List<Entity> lastStanding = game.getGameThread().getEntityCreator().getTotalShapes();

            Entity ent = lastStanding.get(GameHelper.randomGenerator.nextInt(lastStanding.size()));
            color = ent.getComponent(EntityComponent.class).getEntityColor();


        }else {
            color = EntityComponent.randomBallColor();
        }

        Entity playerBall = game.getGameThread().getEntityCreator().createEntity(TypeComponent.Type.BALL,
                BodyFactory.Material.RUBBER,
                bodyComponent.body.getPosition().x,
                bodyComponent.body.getPosition().y + GameConstants.BALL_RADIUS,
                GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS,
                0, 0, 0,
                color);

        game.getGameThread().getEntityCreator().getTotalShapes().add(0, playerBall);
    }

    private void outOfBounds(Entity entity){
        Box2dBodyComponent bodyComponent = box2dBodyComponentMapper.get(entity);
        PlayerComponent playerComponent = playerComponentMapper.get(entity);

        if (bodyComponent.body.getPosition().x + playerComponent.width > GameConstants.WORLD_WIDTH || bodyComponent.body.getPosition().x - playerComponent.width < 0){
            bodyComponent.body.setLinearVelocity(-bodyComponent.body.getLinearVelocity().x, 0);
        }else if (bodyComponent.body.getPosition().y + playerComponent.height > GameConstants.WORLD_HEIGHT || bodyComponent.body.getPosition().y - playerComponent.height < 0){
            bodyComponent.body.setLinearVelocity(0, -bodyComponent.body.getLinearVelocity().x);
        }
    }
}
