package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.SurfaceComponent;

public class WaterFloorSystem extends IteratingSystem {
    private Entity player;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);

    public WaterFloorSystem(Entity player) {
        super(Family.all(SurfaceComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get current y level of player entity
        float currentyLevel = player.getComponent(Box2dBodyComponent.class).body.getPosition().y;
        // get the body component of the wall we're updating
        Body bod = bodyComponentMapper.get(entity).body;

        float speed = (currentyLevel / 300);

        speed = speed>1?1:speed;

        bod.setTransform(bod.getPosition().x, bod.getPosition().y+speed, bod.getAngle());
    }
}
