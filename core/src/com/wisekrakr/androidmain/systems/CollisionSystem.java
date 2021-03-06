package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
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

        if (thisType.getType().equals(TypeComponent.Type.BALL) || thisType.getType().equals(TypeComponent.Type.SQUARE) || thisType.getType().equals(TypeComponent.Type.TRIANGLE)){
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case BALL:
                            entityComponentMapper.get(entity).setHitEntity(true);
                            collidedEntity.getComponent(EntityComponent.class).setHitEntity(true);

                            if (entityComponentMapper.get(entity).getEntityColor() == collisionComponent.collisionEntity.getComponent(EntityComponent.class).getEntityColor()) {
                                entityComponentMapper.get(entity).setDestroy(true);
                                collisionComponent.collisionEntity.getComponent(EntityComponent.class).setDestroy(true);
                            }
                            break;
                        case SQUARE:
                            entityComponentMapper.get(entity).setHitEntity(true);
                            collidedEntity.getComponent(EntityComponent.class).setHitEntity(true);


                            if (entityComponentMapper.get(entity).getEntityColor() == collisionComponent.collisionEntity.getComponent(EntityComponent.class).getEntityColor()) {
                                entityComponentMapper.get(entity).setDestroy(true);
                                collisionComponent.collisionEntity.getComponent(EntityComponent.class).setDestroy(true);
                            }
                            break;
                        case TRIANGLE:
                            entityComponentMapper.get(entity).setHitEntity(true);
                            collidedEntity.getComponent(EntityComponent.class).setHitEntity(true);

                            if (entityComponentMapper.get(entity).getEntityColor() == collisionComponent.collisionEntity.getComponent(EntityComponent.class).getEntityColor()) {
                                entityComponentMapper.get(entity).setDestroy(true);
                                collisionComponent.collisionEntity.getComponent(EntityComponent.class).setDestroy(true);
                            }
                            break;
                        case POWER:

                            if (entity.getComponent(TypeComponent.class).tag == TypeComponent.Tag.PLAYER_BALL) {
                                entityComponentMapper.get(entity).setHitPower(true);
                                entityComponentMapper.get(collidedEntity).setDestroy(true);
                            }
                            break;
                        case SCENERY:
                            entityComponentMapper.get(entity).setHitSurface(true);
                            break;
                        case OBSTACLE:
                            entityComponentMapper.get(entity).setHitObstacle(true);
                            break;
                        default:
                            //System.out.println("ball: No matching type found " );
                    }
                    collisionComponent.collisionEntity = null;
                    entityComponentMapper.get(entity).setHitEntity(false);

                    //entityComponentMapper.get(entity).setHitSurface(false);
                }else {
                    //System.out.println("type == null");
                }
            }
        }else if (thisType.getType() == TypeComponent.Type.POWER) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case BALL:
                            if (collidedEntity.getComponent(TypeComponent.class).tag == TypeComponent.Tag.PLAYER_BALL) {
                                entityComponentMapper.get(collidedEntity).setHitPower(true);
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
