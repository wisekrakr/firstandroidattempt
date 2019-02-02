package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.controls.Controls;

import java.util.Iterator;

public class PlayerControlSystem extends IteratingSystem {

    private ComponentMapper<EntityComponent>ballComponentMapper;
    private ComponentMapper<PlayerComponent> playerComponentMapper;
    private ComponentMapper<Box2dBodyComponent> box2dBodyComponentMapper;
    private AndroidGame game;
    private Controls controller;
    private OrthographicCamera camera;


    @SuppressWarnings("unchecked")
    public PlayerControlSystem(AndroidGame game, Controls controls, OrthographicCamera camera) {
        super(Family.all(PlayerComponent.class).get());
        this.game = game;
        controller = controls;
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
            b2body.body.setLinearVelocity(-500f, 0);
        }
        if (controller.right) {
            b2body.body.setLinearVelocity(500f, 0);
        }
        if (controller.down) {
            b2body.body.setLinearVelocity(0, 0);
        }

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        camera.unproject(mousePos); // convert position from screen to box2d world position

        float speed = 1000000000000f;  // set the speed of the ball

        float xVelocity = mousePos.x - b2body.body.getPosition().x; // get distance from shooter to target on x plain
        float yVelocity = mousePos.y - b2body.body.getPosition().y; // get distance from shooter to target on y plain

        if (playerComponent.hasEntityToShoot){
            if (controller.isLeftMouseDown || Gdx.input.isTouched()) {


                float length = (float) Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity); // get distance to target direct

                if (length != 0) {
                    xVelocity = xVelocity / length;  // get required x velocity to aim at target
                    yVelocity = yVelocity / length;  // get required y velocity to aim at target
                }

                Iterator<Entity> iterator = game.getGameThread().getEntityCreator().getTotalShapes().iterator();
                if (iterator.hasNext()) {
                    game.getGameThread().getEntityCreator().getTotalShapes().get(0).getComponent(
                            Box2dBodyComponent.class).body.applyForceToCenter(
                                    xVelocity * speed, yVelocity * speed, true);
                }
                playerComponent.hasEntityToShoot = false;

            }else if (controller.up){
                game.getGameThread().getEntityCreator().getTotalShapes().get(0).getComponent(
                        Box2dBodyComponent.class).body.applyForceToCenter(
                        xVelocity * speed, yVelocity * speed, true);

                playerComponent.hasEntityToShoot = false;
            }
        }
    }
}
