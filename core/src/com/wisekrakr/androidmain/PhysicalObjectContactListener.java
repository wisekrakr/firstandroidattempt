package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.wisekrakr.androidmain.components.CollisionComponent;

import java.util.ArrayList;
import java.util.List;

public class PhysicalObjectContactListener implements ContactListener {

    private void entityCollision(Entity entity, Fixture fixtureB) {
        if(fixtureB.getBody().getUserData() instanceof Entity){
            Entity collisionEntity = (Entity) fixtureB.getBody().getUserData();

            CollisionComponent object1 = entity.getComponent(CollisionComponent.class);
            CollisionComponent object2 = collisionEntity.getComponent(CollisionComponent.class);

            if(object1 != null){
                object1.collisionEntity = collisionEntity;
            }else if(object2 != null){
                object2.collisionEntity = entity;
            }
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getBody().getUserData() instanceof Entity){
            Entity entity = (Entity) fixtureA.getBody().getUserData();
            entityCollision(entity, fixtureB);
        }else if (fixtureB.getBody().getUserData() instanceof Entity){
            Entity entity = (Entity) fixtureB.getBody().getUserData();
            entityCollision(entity, fixtureA);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
