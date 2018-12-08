package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TextureComponent;
import com.wisekrakr.androidmain.components.TimeComponent;
import com.wisekrakr.androidmain.components.TransformComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.components.SurfaceComponent;

import java.util.ArrayList;
import java.util.List;

import static com.wisekrakr.androidmain.components.TypeComponent.Type.BALL;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.PLAYER;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.SCENERY;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.WATER;


public class EntityCreator {

    private BodyFactory bodyFactory;
    public World world;
    private AndroidGame game;
    private PooledEngine engine;
    private TextureAtlas atlas;

    private List<Entity> totalBalls = new ArrayList<Entity>();

    public EntityCreator(AndroidGame game, PooledEngine pooledEngine){
        this.game = game;
        engine = pooledEngine;

        //atlas = game.assetManager().assetManager.get("images/game/game.atlas", TextureAtlas.class);;

        world = new World(new Vector2(0,0), true);
        world.setContactListener(new PhysicalObjectContactListener());

        bodyFactory = BodyFactory.getBodyFactoryInstance(world);



    }


    public void createWaterFloor(float posX, float posY, float width, float height){
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        SurfaceComponent waterFloor = engine.createComponent(SurfaceComponent.class);

        type.type = WATER;
        //texture.region = textureRegion;
        bodyComponent.body = bodyFactory.makeBoxPolyBody(posX, posY, width, height, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody,true);
        //transformComponent.position.set(-20,-20,0);

        entity.add(bodyComponent);
        entity.add(transformComponent);
        entity.add(texture);
        entity.add(type);
        entity.add(waterFloor);

        bodyComponent.body.setUserData(entity);

        engine.addEntity(entity);

    }

    public Entity createBall(BodyFactory.Material material, float x, float y, float xVelocity, float yVelocity){

        Entity entity = engine.createEntity();

        Box2dBodyComponent ballBodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        BallComponent ballComponent = engine.createComponent(BallComponent.class);

        ballBodyComponent.body = bodyFactory.makeCirclePolyBody(x, y, GameUtilities.BALL_RADIUS, material, BodyDef.BodyType.DynamicBody, false);
        ballBodyComponent.body.setBullet(true); // increase physics computation to limit body travelling through other objects
        //BodyFactory.makeAllFixturesSensors(ballBodyComponent.body); // make bullets sensors so they don't move player

        transformComponent.position.set(x, y, 0);

//        if (entity.getComponent(BallComponent.class) != null) {
//            if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.MARS) {
//                texture.region = atlas.findRegion("mars");
//            } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.MERCURY) {
//                texture.region = atlas.findRegion("mercury");
//            } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.JUPITER) {
//                texture.region = atlas.findRegion("jupiter");
//            } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.EARTH) {
//                texture.region = atlas.findRegion("earth");
//            } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.NEPTUNE) {
//                texture.region = atlas.findRegion("neptune");
//            } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.SATURN) {
//                texture.region = atlas.findRegion("saturn");
//            } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.URANUS) {
//                texture.region = atlas.findRegion("uranus");
//            }
//        }

        type.type = BALL;

        ballBodyComponent.body.setUserData(entity);
        ballComponent.velocityX = xVelocity;
        ballComponent.velocityY = yVelocity;
        ballComponent.position = ballBodyComponent.body.getPosition();

        ballComponent.ballColor = ballComponent.randomBallColor();

        entity.add(ballBodyComponent);
        entity.add(texture);
        entity.add(type);
        entity.add(transformComponent);
        entity.add(collisionComponent);
        entity.add(ballComponent);

        engine.addEntity(entity);
        game.getLevelGenerationSystem().getPlayer().getComponent(PlayerComponent.class).balls.add(0, entity);

        return entity;
    }

    public Entity createEntity(float x, float y, Component component, TypeComponent.Type typeOfComponent){
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        engine.createComponent(component.getClass());

        bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y, GameUtilities.BALL_RADIUS, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody, false);

        type.type = typeOfComponent;

        transformComponent.position.set(x, y, 0);

        bodyComponent.body.setUserData(entity);
        //position and velocity... also color of entity

        entity.add(bodyComponent);
        entity.add(texture);
        entity.add(type);
        entity.add(transformComponent);
        entity.add(collisionComponent);
        entity.add(component);

        engine.addEntity(entity);

        //add to a different list than totalBalls?

        return entity;
    }

    public Entity createRowBall(float x, float y){
        Entity entity = engine.createEntity();

        Box2dBodyComponent ballBodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        BallComponent ballComponent = engine.createComponent(BallComponent.class);

        ballBodyComponent.body = bodyFactory.makeCirclePolyBody(x, y, GameUtilities.BALL_RADIUS, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody, false);

        type.type = BALL;

        transformComponent.position.set(x, y, 0);

        ballBodyComponent.body.setUserData(entity);
        ballComponent.position = ballBodyComponent.body.getPosition();
        ballComponent.velocityX = 0f;
        ballComponent.velocityY = 0f;

        ballComponent.ballColor = ballComponent.randomBallColor();

        entity.add(ballBodyComponent);
        entity.add(texture);
        entity.add(type);
        entity.add(transformComponent);
        entity.add(collisionComponent);
        entity.add(ballComponent);

        engine.addEntity(entity);

        totalBalls.add(entity);

        return entity;

    }

    public Entity createPlayer(float x, float y){

        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        TimeComponent timeComponent = engine.createComponent(TimeComponent.class);
        LevelComponent levelComponent = engine.createComponent(LevelComponent.class);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, 5, 20, BodyFactory.Material.STONE, BodyDef.BodyType.DynamicBody, true);

        transformComponent.position.set(10,10,0);

        //texture.region = textureRegion;

        type.type = PLAYER;

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);
        entity.add(transformComponent);
        entity.add(texture);
        entity.add(player);
        entity.add(collisionComponent);
        entity.add(type);
        entity.add(timeComponent);
        entity.add(levelComponent);

        engine.addEntity(entity);

        return entity;
    }

    public void createWalls(float posX, float posY, float width, float height) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);


        bodyComponent.body = bodyFactory.makeBoxPolyBody(posX, posY, width, height, BodyFactory.Material.STONE, BodyDef.BodyType.StaticBody, false);

        //texture.region = wallRegion;

        type.type = SCENERY;

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);
        entity.add(texture);
        entity.add(type);

        engine.addEntity(entity);

    }

    public List<Entity> totalBalls(){
        return totalBalls;
    }

//    public Entity getPlayer() {
//        return player;
//    }
}
