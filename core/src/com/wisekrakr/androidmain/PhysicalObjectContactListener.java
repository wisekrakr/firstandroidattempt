package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.wisekrakr.androidmain.components.CollisionComponent;

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
            //System.out.println(StringHelper.ANSI_RED_BACKGROUND + "Contact : " + StringHelper.ANSI_RESET + entity.toString() + " and " + fixtureB.getBody().getUserData());
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        //System.out.println(StringHelper.ANSI_RED_BACKGROUND + "Contact : " + StringHelper.ANSI_RESET + fixtureA.getBody().getUserData() + " and " + fixtureB.getBody().getUserData());

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
//        Body bodyA = contact.getFixtureA().getBody();
//        Body bodyB = contact.getFixtureB().getBody();
//
//        System.out.println(StringHelper.ANSI_RED_BACKGROUND + "Contact : " + StringHelper.ANSI_RESET + bodyA.getUserData() + " and " + bodyB.getUserData());
//
//        if (bodyA.getUserData() == "WATER"){
//            System.out.println("out of water");
//        }else if (bodyB.getUserData() == "WATER"){
//            System.out.println("out of water");
//        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
//        Body bodyA = contact.getFixtureA().getBody();
//        Body bodyB = contact.getFixtureB().getBody();
//
//        if (bodyA.getUserData() == "WALL_OF_DEATH"){
//
//        }else if (bodyB.getUserData() == "WALL_OF_DEATH"){
//
//        }
    }



    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
