package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.controls.Controls;

import java.util.Iterator;

public class PlayerControlSystem extends IteratingSystem {

    private ComponentMapper<EntityComponent>ballComponentMapper;
    private ComponentMapper<PlayerComponent> playerComponentMapper;
    private ComponentMapper<Box2dBodyComponent> box2dBodyComponentMapper;
    private Controls controller;
    private EntityCreator entityCreator;
    private OrthographicCamera camera;


    @SuppressWarnings("unchecked")
    public PlayerControlSystem(Controls controls, EntityCreator entityCreator, OrthographicCamera camera) {
        super(Family.all(PlayerComponent.class).get());
        controller = controls;
        this.entityCreator = entityCreator;
        this.camera = camera;

        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
        box2dBodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        ballComponentMapper = ComponentMapper.getFor(EntityComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent b2body = box2dBodyComponentMapper.get(entity);
        PlayerComponent playerComponent = playerComponentMapper.get(entity);

        if (controller.left) {
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -5f, 0.2f), b2body.body.getLinearVelocity().y);
//                b2body.body.setTransform(b2body.body.getPosition().x,b2body.body.getPosition().y, b2body.body.getAngle() + 15f * deltaTime);
        }
        if (controller.right) {
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 5f, 0.2f), b2body.body.getLinearVelocity().y);
//                b2body.body.setTransform(b2body.body.getPosition().x,b2body.body.getPosition().y, b2body.body.getAngle() + -15f * deltaTime);
        }


        if (controller.isLeftMouseDown || Gdx.input.isTouched()) {
            if (playerComponent.hasEntityToShoot) {

                Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

                camera.unproject(mousePos); // convert position from screen to box2d world position

                float speed = 10000000f;  // set the speed of the ball

                float xVelocity = mousePos.x - b2body.body.getPosition().x; // get distance from shooter to target on x plain
                float yVelocity = mousePos.y - b2body.body.getPosition().y; // get distance from shooter to target on y plain

                float length = (float) Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity); // get distance to target direct

                if (length != 0) {
                    xVelocity = xVelocity / length;  // get required x velocity to aim at target
                    yVelocity = yVelocity / length;  // get required y velocity to aim at target
                }

                Iterator<Entity> iterator = entityCreator.getTotalEntities().iterator();
                if (iterator.hasNext()) {
                    entityCreator.getTotalEntities().get(0).getComponent(EntityComponent.class).velocityX = xVelocity * speed;
                    entityCreator.getTotalEntities().get(0).getComponent(EntityComponent.class).velocityY = yVelocity * speed;
                }
                playerComponent.hasEntityToShoot = false;
            }
        }
    }
}
