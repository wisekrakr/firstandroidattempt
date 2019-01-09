package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.PowerUpComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Initiate this system in EntitySystem since we did not extend this system with an IteratingSystem.
 *
 */

public class PowerUpSystem {

    private Entity entity;

    public enum PowerUpStage {
        INIT, UPDATE, EXIT
    }

    public void setPowerUpStage(PowerUpStage powerUpStage) {
        this.powerUpStage = powerUpStage;
    }

    private PowerUpStage powerUpStage = PowerUpStage.INIT;

    private float timeCount = 0f;
    private ArrayList<Entity>powerUps = new ArrayList<Entity>();

    private ComponentMapper<EntityComponent> entityComponentMapper;
        private AndroidGame game;

    public PowerUpSystem(AndroidGame game){
        this.game = game;

        entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);

    }

    /**
     * Initiate this method in the processEntity method in EntitySystem
     *
     * @param entity the ball that will hit the power up
     * @param spawnInterval seconds it will take to spawn another power up
     */

    public void traversePowerUpStages(Entity entity, float spawnInterval){

        switch (powerUpStage) {
            case INIT:
                powerUpContext.init(spawnInterval);
                break;
            case UPDATE:
                powerUpContext.powerTime(entity);
                break;
            case EXIT:
                powerUpContext.exit();
                break;
            default:
                 System.out.println("no power stage");
        }
    }

    private PowerUpContext powerUpContext = new PowerUpContext() {

        @Override
        public void init(float spawnInterval){

            if (timeCount == 0){
                timeCount = game.getTimeKeeper().gameClock;
            }
            if (game.getTimeKeeper().gameClock - timeCount > spawnInterval){
                if (powerUps.size() == 0) {
                    spawnPowerUp();
                }
            }
        }

        @Override
        public void spawnPowerUp() {

            entity = game.getEntityCreator().createPowerUp(100,100,
                    0, 0, GameUtilities.BALL_RADIUS/2, GameUtilities.BALL_RADIUS/2);

            powerUps.add(entity);

            setPowerUpStage(PowerUpStage.UPDATE);

        }

        @Override
        public void powerTime(Entity entity) {

            PowerUpComponent powerUpComponent = new PowerUpComponent();

            if (entityComponentMapper.get(entity).hitPowerUp) {
                switch (powerUpComponent.powerUp) {
                    case HOMING_BALL:
                        System.out.println("Homing ball power up");
                        setPowerUpStage(PowerUpStage.EXIT);
                        break;
                    case NUKE:
                        System.out.println("Nuke power up");

                        List<Entity> sub = game.getEntityCreator().getTotalEntities().subList(0, game.getEntityCreator().getTotalEntities().size()/2);
                        List<Entity> toBeKilled = new ArrayList<Entity>(sub);

                        for (Entity ent: toBeKilled){
                            entityComponentMapper.get(ent).setDestroy(true);
                        }

                        setPowerUpStage(PowerUpStage.EXIT);
                        break;
                    case TIME_SLOW:
                        System.out.println("Time Slow power up");
                        setPowerUpStage(PowerUpStage.EXIT);
                        break;
                    case TIME_FREEZE:
                        System.out.println("Time Freeze power up");

                        for (Entity ent: game.getEntityCreator().getTotalEntities()){
                            ent.getComponent(Box2dBodyComponent.class).body.setAwake(false);
                        }

                        setPowerUpStage(PowerUpStage.EXIT);
                        break;
                }
                entityComponentMapper.get(entity).setHitPowerUp(false);
            }
        }

        @Override
        public void exit() {

            powerUps.remove(entity);

            timeCount = 0;

            setPowerUpStage(PowerUpStage.INIT);

        }
    };

    public Entity getEntity() {
        return entity;
    }
}
