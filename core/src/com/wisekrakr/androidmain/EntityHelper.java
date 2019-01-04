package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.ObstacleComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.PowerUpComponent;
import com.wisekrakr.androidmain.components.TextureComponent;
import com.wisekrakr.androidmain.components.TransformComponent;
import com.wisekrakr.androidmain.components.TypeComponent;

public class EntityHelper {


    private AndroidGame game;

    public EntityHelper(AndroidGame game) {
        this.game = game;
    }

    public ComponentInitializer getComponentInitializer() {
        return componentInitializer;
    }

    private ComponentInitializer componentInitializer = new ComponentInitializer() {


        @Override
        public void typeComponent(PooledEngine engine, Entity mainEntity, TypeComponent.Type type, TypeComponent.Tag tag) {
            TypeComponent typeComponent = engine.createComponent(TypeComponent.class);

            typeComponent.type = type;
            typeComponent.tag = tag;

            mainEntity.add(typeComponent);
        }

        @Override
        public void collisionComponent(PooledEngine engine, Entity mainEntity) {
            CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);

            mainEntity.add(collisionComponent);
        }

        @Override
        public void transformComponent(PooledEngine engine, Entity mainEntity, float x, float y, float rotation) {
            TransformComponent transformComponent = engine.createComponent(TransformComponent.class);

            transformComponent.position.set(x, y, 0);
            transformComponent.rotation = rotation;

            mainEntity.add(transformComponent);
        }

        @Override
        public void textureComponent(PooledEngine engine, Entity mainEntity) {
            TextureComponent textureComponent = engine.createComponent(TextureComponent.class);

            mainEntity.add(textureComponent);
        }

        @Override
        public void levelComponent(PooledEngine engine, Entity mainEntity) {
            LevelComponent levelComponent = engine.createComponent(LevelComponent.class);

            mainEntity.add(levelComponent);
        }

        @Override
        public void entityComponent(PooledEngine engine, Entity mainEntity, Box2dBodyComponent bodyComponent, float velocityX, float velocityY) {
            EntityComponent entityComponent = engine.createComponent(EntityComponent.class);

            entityComponent.velocityX = velocityX;
            entityComponent.velocityY = velocityY;
            entityComponent.position = bodyComponent.body.getPosition();

            entityComponent.entityColor = entityComponent.randomBallColor();

            mainEntity.add(entityComponent);
        }

        @Override
        public void obstacleComponent(PooledEngine engine, Entity mainEntity, float width, float height, float velocityX, float velocityY, float x, float y) {
            ObstacleComponent obstacleComponent = engine.createComponent(ObstacleComponent.class);

            obstacleComponent.width = width;
            obstacleComponent.height = height;
            obstacleComponent.velocityX = velocityX;
            obstacleComponent.velocityY = velocityY;
            obstacleComponent.position.set(x,y);

            mainEntity.add(obstacleComponent);
        }

        @Override
        public void powerUpComponent(PooledEngine engine, Entity mainEntity, float x, float y, float velocityX, float velocityY, float rotation) {
            PowerUpComponent powerUpComponent = engine.createComponent(PowerUpComponent.class);

            powerUpComponent.position.set(x, y);
            powerUpComponent.velocityX = velocityX;
            powerUpComponent.velocityY = velocityY;
            powerUpComponent.powerUp = powerUpComponent.randomPowerUp();

            mainEntity.add(powerUpComponent);
        }

        @Override
        public void playerComponent(PooledEngine engine, Entity mainEntity) {
            PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

            mainEntity.add(playerComponent);
        }
    };

}
