package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.TypeComponent;

public interface ComponentInitializer {

    void typeComponent(PooledEngine engine, Entity mainEntity, TypeComponent.Type type, TypeComponent.Tag tag);
    void collisionComponent(PooledEngine engine, Entity mainEntity);
    void transformComponent(PooledEngine engine, Entity mainEntity, float x, float y, float rotation);
    void textureComponent(PooledEngine engine, Entity mainEntity);
    void levelComponent(PooledEngine engine, Entity mainEntity);
    void entityComponent(PooledEngine engine, Entity mainEntity, Box2dBodyComponent bodyComponent, float width, float height, float velocityX, float velocityY);
    void obstacleComponent(PooledEngine engine, Entity mainEntity, float width, float height, float velocityX, float velocityY, float x, float y);
    void playerComponent(PooledEngine engine, Entity mainEntity);

}
