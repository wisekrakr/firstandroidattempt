package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.TypeComponent;


public class CollisionSystem extends IteratingSystem {
    private ComponentMapper<CollisionComponent> collisionComponentMapper;
    private ComponentMapper<EntityComponent> entityComponentMapper;


    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        super(Family.all(CollisionComponent.class).get());

        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
        entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        CollisionComponent collisionComponent = collisionComponentMapper.get(entity);

        Entity collidedEntity = collisionComponent.collisionEntity;

        TypeComponent thisType = ComponentMapper.getFor(TypeComponent.class).get(entity);


        if (thisType.type.equals(TypeComponent.Type.BALL) || thisType.type.equals(TypeComponent.Type.SQUARE) || thisType.type.equals(TypeComponent.Type.TRIANGLE)){
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.type) {
                        case BALL:
                            entityComponentMapper.get(entity).setHitEntity(true);

                            if (entityComponentMapper.get(entity).entityColor == collisionComponent.collisionEntity.getComponent(EntityComponent.class).entityColor) {
                                entityComponentMapper.get(entity).setDestroy(true);
                                collisionComponent.collisionEntity.getComponent(EntityComponent.class).setDestroy(true);
                            }
                            break;
                        case SQUARE:
                            entityComponentMapper.get(entity).setHitEntity(true);

                            if (entityComponentMapper.get(entity).entityColor == collisionComponent.collisionEntity.getComponent(EntityComponent.class).entityColor) {
                                entityComponentMapper.get(entity).setDestroy(true);
                                collisionComponent.collisionEntity.getComponent(EntityComponent.class).setDestroy(true);
                            }
                            break;
                        case TRIANGLE:
                            entityComponentMapper.get(entity).setHitEntity(true);

                            if (entityComponentMapper.get(entity).entityColor == collisionComponent.collisionEntity.getComponent(EntityComponent.class).entityColor) {
                                entityComponentMapper.get(entity).setDestroy(true);
                                collisionComponent.collisionEntity.getComponent(EntityComponent.class).setDestroy(true);
                            }
                            break;
                        case POWER_UP:
                            System.out.println("ball hit power up"); //todo remove

                            if (entity.getComponent(TypeComponent.class).tag == TypeComponent.Tag.PLAYER_BALL) {
                                entityComponentMapper.get(entity).setHitPowerUp(true);
                                entityComponentMapper.get(collidedEntity).setDestroy(true);
                            }
                            break;
                        default:
                            //System.out.println("ball: No matching type found " );
                    }
                    collisionComponent.collisionEntity = null;

                }else {
                    //System.out.println("type == null");
                }
            }
        }else if (thisType.type == TypeComponent.Type.SCENERY) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.type) {
                        case SQUARE:
                            if (!entityComponentMapper.get(collidedEntity).hitSurface){
                                entityComponentMapper.get(collidedEntity).setHitSurface(true);
                            }
                            break;
                        case TRIANGLE:
                            if (!entityComponentMapper.get(collidedEntity).hitSurface){
                                entityComponentMapper.get(collidedEntity).setHitSurface(true);
                            }
                            break;
                        case BALL:
                            if (!entityComponentMapper.get(collidedEntity).hitSurface){
                                entityComponentMapper.get(collidedEntity).setHitSurface(true);
                            }
                            break;
                        case POWER_UP:
                            if (!entityComponentMapper.get(collidedEntity).hitSurface){
                                entityComponentMapper.get(collidedEntity).setHitSurface(true);
                            }
                            break;
                        default:
                            System.out.println("walls: No matching type found");
                    }
                    collisionComponent.collisionEntity = null;
                } else {
                    //System.out.println("type == null");
                }
            }
        }else if (thisType.type == TypeComponent.Type.OBSTACLE) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.type) {
                        case SQUARE:
                            entityComponentMapper.get(collidedEntity).setHitObstacle(true);
                            break;
                        case TRIANGLE:
                            entityComponentMapper.get(collidedEntity).setHitObstacle(true);
                            break;
                        case BALL:
                            entityComponentMapper.get(collidedEntity).setHitObstacle(true);
                            break;

                        default:
                            System.out.println("obstacle: No matching type found");
                    }
                    collisionComponent.collisionEntity = null;
                } else {
                    //System.out.println("type == null");
                }
            }
        }else if (thisType.type == TypeComponent.Type.POWER_UP) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.type) {
                        case BALL:

                            if (collidedEntity.getComponent(TypeComponent.class).tag == TypeComponent.Tag.PLAYER_BALL) {
                                entityComponentMapper.get(collidedEntity).setHitPowerUp(true);
                                entityComponentMapper.get(entity).setDestroy(true);
                            }
                            break;
                        default:
                            System.out.println("power up: No matching type found");
                    }
                    collisionComponent.collisionEntity = null;
                } else {
                    //System.out.println("type == null");
                }
            }
        }
    }
}
