package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.SurfaceComponent;
import com.wisekrakr.androidmain.components.TypeComponent;


public class CollisionSystem extends IteratingSystem {
    private ComponentMapper<CollisionComponent> collisionComponentMapper;
    private ComponentMapper<PlayerComponent> playerComponentMapper;
    private ComponentMapper<BallComponent> ballComponentMapper;
    private ComponentMapper<SurfaceComponent> surfaceComponentMapper;
    private ComponentMapper<Box2dBodyComponent> box2dBodyComponentMapper;

    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        // only need to worry about player collisions
        super(Family.all(CollisionComponent.class).get());

        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
        ballComponentMapper = ComponentMapper.getFor(BallComponent.class);
        surfaceComponentMapper = ComponentMapper.getFor(SurfaceComponent.class);
        box2dBodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        CollisionComponent collisionComponent = collisionComponentMapper.get(entity);
        Box2dBodyComponent box2dBodyComponent = box2dBodyComponentMapper.get(entity);

        Entity collidedEntity = collisionComponent.collisionEntity;

        TypeComponent thisType = entity.getComponent(TypeComponent.class);

        if (thisType.type == TypeComponent.Type.BALL){
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.type) {
                        case BALL:

                            ballComponentMapper.get(entity).hitBall = true;
                            ballComponentMapper.get(entity).setCollisionEntity(collidedEntity);
                            ballComponentMapper.get(entity).getCollisionEntity().getComponent(BallComponent.class).setCollisionEntity(entity);

                            if (ballComponentMapper.get(entity).ballColor == ballComponentMapper.get(entity).getCollisionEntity().getComponent(BallComponent.class).ballColor) {

                                ballComponentMapper.get(entity).destroyed = true;
                                ballComponentMapper.get(entity).getCollisionEntity().getComponent(BallComponent.class).destroyed = true;


                            }

                            System.out.println("ball hit ball " + ballComponentMapper.get(entity).ballColor +  ballComponentMapper.get(entity).getCollisionEntity().getComponent(BallComponent.class).ballColor);

                            break;
                        case SCENERY:

                            ballComponentMapper.get(entity).velocityY = 0f;
                            ballComponentMapper.get(entity).velocityX = 0f;
                            System.out.println("ball hit surface " );
                            break;
                        case WATER:


                            break;
                        case OTHER:

                            System.out.println("ball hit other ");
                            break;
                        default:
                            System.out.println("ball: No matching type found " );
                    }
                    collisionComponent.collisionEntity = null;
                }else {
                    System.out.println("type == null");
                }
            }
        }else if (thisType.type == TypeComponent.Type.SCENERY){
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.type) {
                        case OTHER:
                            System.out.println("wall hit other");
                            break;
                        case BALL:
                            ballComponentMapper.get(entity).hitSurface = true;
                            System.out.println("wall hit ball");
                            break;
                        default:
                            System.out.println("wall: No matching type found");
                    }
                    collisionComponent.collisionEntity = null;
                }else {
                    System.out.println("type == null");
                }
            }
        }

    }
}
