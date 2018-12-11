package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.TypeComponent;


public class CollisionSystem extends IteratingSystem {
    private ComponentMapper<CollisionComponent> collisionComponentMapper;
    private ComponentMapper<EntityComponent> entityComponentMapper;
    private ComponentMapper<Box2dBodyComponent> box2dBodyComponentMapper;

    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        super(Family.all(CollisionComponent.class).get());

        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
        entityComponentMapper = ComponentMapper.getFor(EntityComponent.class);
        box2dBodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        CollisionComponent collisionComponent = collisionComponentMapper.get(entity);
        Box2dBodyComponent box2dBodyComponent = box2dBodyComponentMapper.get(entity);

        Entity collidedEntity = collisionComponent.collisionEntity;

        TypeComponent thisType = ComponentMapper.getFor(TypeComponent.class).get(entity);

        if (thisType.type == TypeComponent.Type.BALL){
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.type) {
                        case BALL:
                            entityComponentMapper.get(entity).hitEntity = true;

                            if (entityComponentMapper.get(entity).entityColor == collisionComponent.collisionEntity.getComponent(EntityComponent.class).entityColor) {
                                entityComponentMapper.get(entity).destroyed = true;
                                collisionComponent.collisionEntity.getComponent(EntityComponent.class).destroyed = true;
                            }
                            break;
                        case SQUARE:
                            break;
                        case TRIANGLE:
                            break;
                        case SCENERY:
//                            entityComponentMapper.get(entity).velocityY = 0f;
//                            entityComponentMapper.get(entity).velocityX = 0f;

//                            System.out.println("ball hit surface " );
                            break;
                        default:
                            System.out.println("ball: No matching type found " );
                    }
                    //collisionComponent.collisionEntity = null;
                }else {
                    System.out.println("type == null");
                }
            }
        }else if (thisType.type == TypeComponent.Type.SQUARE){
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.type) {
                        case SQUARE:
                            entityComponentMapper.get(entity).hitEntity = true;
                            if (entityComponentMapper.get(entity).entityColor == collisionComponent.collisionEntity.getComponent(EntityComponent.class).entityColor) {
                                entityComponentMapper.get(entity).destroyed = true;
                                collisionComponent.collisionEntity.getComponent(EntityComponent.class).destroyed = true;
                            }
                            break;
                        case BALL:
                            System.out.println("square hit ball");
                            break;
                        case SCENERY:
                            entityComponentMapper.get(entity).velocityY = 0f;
                            entityComponentMapper.get(entity).velocityX = 0f;
                            break;
                        default:
                            System.out.println("square: No matching type found");
                    }
//                    collisionComponent.collisionEntity = null;
                }else {
                    System.out.println("type == null");
                }
            }
        }else if (thisType.type == TypeComponent.Type.TRIANGLE) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.type) {
                        case SQUARE:
                            break;
                        case TRIANGLE:
                            entityComponentMapper.get(entity).hitEntity = true;

                            if (entityComponentMapper.get(entity).entityColor == collisionComponent.collisionEntity.getComponent(EntityComponent.class).entityColor) {
                                entityComponentMapper.get(entity).destroyed = true;
                                collisionComponent.collisionEntity.getComponent(EntityComponent.class).destroyed = true;
                            }
                            break;
                        case BALL:
                            break;
                        case SCENERY:
                            entityComponentMapper.get(entity).velocityY = 0f;
                            entityComponentMapper.get(entity).velocityX = 0f;
                            break;
                        default:
                            System.out.println("triangle: No matching type found");
                    }
//                    collisionComponent.collisionEntity = null;
                } else {
                    System.out.println("type == null");
                }
            }
        }else if (thisType.type == TypeComponent.Type.SCENERY) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.type) {
                        case SQUARE:
                            entityComponentMapper.get(collidedEntity).hitSurface = true;
                            break;
                        case TRIANGLE:
                            entityComponentMapper.get(collidedEntity).hitSurface = true;
                            break;
                        case BALL:
                            entityComponentMapper.get(collidedEntity).hitSurface = true;
                            break;
                        default:
                            System.out.println("walls: No matching type found");
                    }
//                    collisionComponent.collisionEntity = null;
                } else {
                    System.out.println("type == null");
                }
            }
        }

    }
}
