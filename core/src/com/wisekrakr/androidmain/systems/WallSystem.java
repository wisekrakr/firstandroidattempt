package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.SurfaceComponent;



public class WallSystem extends IteratingSystem {

    private Entity entityObject;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);;

    @SuppressWarnings("unchecked")
    public WallSystem(Entity entityObject){
        super(Family.all(SurfaceComponent.class).get());
        this.entityObject = entityObject;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {


    }
}
