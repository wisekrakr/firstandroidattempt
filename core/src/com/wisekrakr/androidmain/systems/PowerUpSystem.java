package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameHelper;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.retainers.TimeKeeper;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.PowerUpComponent;

public class PowerUpSystem extends IteratingSystem {

    private EntityCreator entityCreator;
    private TimeKeeper timer;
    private float timeCount = 0f;

    private ComponentMapper<EntityComponent> entityComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<PowerUpComponent> powerUpComponentMapper;

    @SuppressWarnings("unchecked")
    public PowerUpSystem(EntityCreator entityCreator, TimeKeeper timer){
        super(Family.all(PowerUpComponent.class).get());

        this.entityCreator = entityCreator;
        this.timer = timer;

        entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        powerUpComponentMapper = ComponentMapper.getFor(PowerUpComponent.class);
    }

    @Override
    public void update(float deltaTime) {

        float directionChangeInterval = 3f;
        if (timeCount == 0){
            timeCount = timer.gameClock;
        }
        if (timer.gameClock - timeCount > directionChangeInterval){
            spawnPowerUp();
            timeCount = 0;
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EntityComponent entityComponent = entityComponentMapper.get(entity);
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);
        PowerUpComponent powerUpComponent = powerUpComponentMapper.get(entity);

        bodyComponentMapper.get(entity).body.applyForceToCenter(new Vector2(GameHelper.generateRandomNumberBetween(10, 2000),
                GameHelper.generateRandomNumberBetween(10, 2000)), true);

        if (!entityComponent.destroy) {
            if (entityComponent.hitEntity || entityComponent.hitSurface || entityComponent.hitObstacle) {
                bodyComponent.body.applyForceToCenter(-entityComponent.velocityX, -entityComponent.velocityY, true);
            }
        } else {
            if (entityComponent.hitEntity) {
                powerTime(entity);
            }
            bodyComponent.isDead = true;
        }

        outOfBounds(bodyComponent, entityComponent);
    }

    private void powerTime(Entity entity) {

        if (entity != null){
            switch (powerUpComponentMapper.get(entity).powerUp){
                case HOMING_BALL:
                    System.out.println("Homing ball power up");
                    break;
                case NUKE:
                    System.out.println("Nuke power up");
                    break;
                case TIME_SLOW:
                    System.out.println("Time SLow power up");
                    break;
                case TIME_FREEZE:
                    System.out.println("Time Freeze power up");
                    break;
            }
        }
    }

    private void spawnPowerUp(){

        entityCreator.createPowerUp(100,100,
                0,0);

    }

    /**
     * The Entity gets destroyed (body as well) when it goes out of bounds.
     *
     * @param bodyComponent component of Box2dBody.
     * @param entityComponent component of an Entity
     */
    private void outOfBounds(Box2dBodyComponent bodyComponent, EntityComponent entityComponent){
        if (bodyComponent.body.getPosition().x > GameUtilities.WORLD_WIDTH || bodyComponent.body.getPosition().x < 0){
            entityComponent.setDestroy(true);
        }else if (bodyComponent.body.getPosition().y > GameUtilities.WORLD_HEIGHT || bodyComponent.body.getPosition().y < 0){
            entityComponent.setDestroy(true);
        }
    }
}
