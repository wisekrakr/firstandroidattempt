package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Initiate this system in EntitySystem since we did not extend this system with an IteratingSystem.
 *
 */

public class PowerImplementation {

    private Entity entity;

    public enum PowerStage {
        INIT, UPDATE, EXIT
    }

    private void setPowerStage(PowerStage powerStage) {
        this.powerStage = powerStage;
    }

    private PowerStage powerStage = PowerStage.INIT;

    private float timeCount = 0f;
    private ArrayList<Entity> powers = new ArrayList<Entity>(); //todo create a hashmap

    private ComponentMapper<EntityComponent> entityComponentMapper;
    private AndroidGame game;

    PowerImplementation(AndroidGame game){
        this.game = game;

        entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);
    }

    /**
     * Initiate this method in the processEntity method in EntitySystem
     *
     * @param entity the ball that will hit the power up
     * @param spawnInterval seconds it will take to spawn another power up
     */

    void updatingPowerUpSystem(Entity entity, float spawnInterval){
        switch (powerStage) {
            case INIT:
                powerContext.init(spawnInterval);
                break;
            case UPDATE:
                powerContext.powerTime(entity);
                break;
            case EXIT:
                powerContext.exit();
                break;
            default:
                System.out.println("no power stage");
        }
    }

    private PowerContext powerContext = new PowerContext() {


        @Override
        public void init(float spawnInterval){

            if (timeCount == 0){
                timeCount = game.getGameThread().getTimeKeeper().gameClock;
            }
            if (game.getGameThread().getTimeKeeper().gameClock - timeCount > spawnInterval){
                if (powers.size() == 0) {
                    spawnPower();
                }
            }
        }

        @Override
        public void spawnPower() {
            Vector2 filledPosition = new Vector2();
            for (Entity ent: game.getGameThread().getEntityCreator().getTotalShapes()){
                filledPosition = ent.getComponent(EntityComponent.class).position;
            }

            Vector2 newPosition = GameHelper.randomPosition();
            if (newPosition != filledPosition){
                entity = game.getGameThread().getEntityCreator().createPower(newPosition.x, newPosition.y,
                        0, 0,
                        GameConstants.BALL_RADIUS/2, GameConstants.BALL_RADIUS/2);

                PowerHelper.setPowerUp(entity, PowerHelper.randomPowerUp());

                System.out.println(PowerHelper.getPowerUpMap());

                powers.add(entity);

                setPowerStage(PowerStage.UPDATE);
            }else {
                setPowerStage(PowerStage.INIT);
            }
        }

        @Override
        public void powerTime(Entity entity) {
            float timeCounter = 0;

            if (entityComponentMapper.get(entity).hitPower) {
                switch (PowerHelper.getPower()) {
                    case EXTRA_TIME:
                        System.out.println("Extra time power up");

                        game.getGameThread().getTimeKeeper().setTime(game.getGameThread().getTimeKeeper().time += 30f);

                        setPowerStage(PowerStage.EXIT);
                        break;
                    case NUKE:
                        System.out.println("Nuke power up");

                        if (!game.getGameThread().getEntityCreator().getTotalShapes().isEmpty()) {
                            Collections.reverse(game.getGameThread().getEntityCreator().getTotalShapes());

                            List<Entity>sub = game.getGameThread().getEntityCreator().getTotalShapes().subList(0, game.getGameThread().getEntityCreator().getTotalShapes().size()/3);
                            ArrayList<Entity> toBeKilled = new ArrayList<Entity>(sub);

                            for (Entity ent : toBeKilled) {
                                entityComponentMapper.get(ent).setDestroy(true);
                            }
                        }
                        setPowerStage(PowerStage.EXIT);
                        break;
                    case THEY_LIVE:
                        System.out.println("THEY CAN MOVE?!");

                        for (Entity ent: game.getGameThread().getEntityCreator().getTotalShapes()){
                            if (ent.getComponent(Box2dBodyComponent.class).body.getType() == BodyDef.BodyType.DynamicBody){
                                ent.getComponent(Box2dBodyComponent.class).body.setType(BodyDef.BodyType.StaticBody);
                                ent.getComponent(Box2dBodyComponent.class).body.setAwake(false);
                            }else if (ent.getComponent(Box2dBodyComponent.class).body.getType() == BodyDef.BodyType.StaticBody) {
                                ent.getComponent(Box2dBodyComponent.class).body.setType(BodyDef.BodyType.DynamicBody);
                                ent.getComponent(Box2dBodyComponent.class).body.setAwake(true);
                            }
                        }

                        setPowerStage(PowerStage.EXIT);
                        break;
                    case MORE_BALLS:
                        System.out.println("More ball power down");
                        Vector2 filledPosition = new Vector2();
                        for (Entity ent: game.getGameThread().getEntityCreator().getTotalShapes()){
                            filledPosition = ent.getComponent(EntityComponent.class).position;
                        }

                        Vector2 newPosition = GameHelper.randomPosition();
                        if (newPosition != filledPosition) {
                            for (int i = 0; i < 10; i++) {
                                game.getGameThread().getEntityCreator().createRowEntity(TypeComponent.Type.BALL,
                                        BodyDef.BodyType.StaticBody,
                                        BodyFactory.Material.RUBBER,
                                        newPosition.x, newPosition.y,
                                        GameConstants.BALL_RADIUS, GameConstants.BALL_RADIUS,
                                        EntityComponent.randomBallColor()
                                );
                            }
                        }

                        setPowerStage(PowerStage.EXIT);
                        break;
                    case FREEZE_PLAYER:
                        System.out.println("Player Freeze power down");
                        Entity player = game.getGameThread().getLevelGenerationSystem().getLevelModel().getPlayer();
                        if (timeCounter == 0){
                            timeCounter = game.getGameThread().getTimeKeeper().gameClock;
                        }
                        while (game.getGameThread().getTimeKeeper().gameClock - timeCounter < 10f){
                            player.getComponent(Box2dBodyComponent.class).body.setAwake(false);
                            timeCounter = game.getGameThread().getTimeKeeper().gameClock;
                        }

                        setPowerStage(PowerStage.EXIT);
                        break;
                    case BLACK_AND_WHITE:
                        System.out.println("Black and white power down");
                        setPowerStage(PowerStage.EXIT);
                        break;
                }
                entityComponentMapper.get(entity).setHitPower(false);
            }
        }

        @Override
        public void exit() {
            powers.remove(PowerImplementation.this.entity);

            timeCount = 0;

            setPowerStage(PowerStage.INIT);
        }
    };
}
