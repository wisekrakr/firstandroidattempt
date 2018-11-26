package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.RowComponent;
import com.wisekrakr.androidmain.components.StateComponent;
import com.wisekrakr.androidmain.components.TextureComponent;
import com.wisekrakr.androidmain.components.TransformComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.components.SurfaceComponent;
import com.wisekrakr.androidmain.systems.RenderingSystem;

import java.util.ArrayList;
import java.util.List;

import static com.wisekrakr.androidmain.components.TypeComponent.Type.BALL;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.PLAYER;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.SCENERY;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.WATER;


public class LevelFactory {

    private Entity player;

    private BodyFactory bodyFactory;
    public World world;
    private PooledEngine engine;
    private TextureAtlas atlas;

    private TextureRegion floorTex;
    private TextureRegion enemyTexture;
    private TextureRegion platformTex;
    private TextureRegion bulletTex;

    private List<Entity> totalBalls = new ArrayList<Entity>();

    public LevelFactory(PooledEngine pooledEngine){
        engine = pooledEngine;

        //this.atlas = atlas;

        world = new World(new Vector2(0,0f), true);
        world.setContactListener(new PhysicalObjectContactListener());

        bodyFactory = BodyFactory.getBodyFactoryInstance(world);

        floorTex = GameUtilities.makeTextureRegion(40*RenderingSystem.PPM, 0.5f*RenderingSystem.PPM, "111111FF");
        platformTex = GameUtilities.makeTextureRegion(2*RenderingSystem.PPM, 0.1f*RenderingSystem.PPM, "221122FF");

    }

    public void generateLevel(){

        for(int i = 1; i < 10; i++){
            for (int j = 1; j < 4; j++) {
                createRowBall(i * GameUtilities.BALL_RADIUS,
                        Gdx.graphics.getHeight() - j * GameUtilities.BALL_RADIUS);
            }
        }
    }

    private void createBouncyPlatform(float x, float y) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, .5f, 0.5f, BodyFactory.Material.STONE, BodyDef.BodyType.StaticBody);
        //make it a sensor so not to impede movement
        //BodyFactory.makeAllFixturesSensors(bodyComponent.body, true);

        //TODO: give it a texture... get another texture and anim for springy action

        texture.region = floorTex;

        //type.type = SPRING;

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);
        entity.add(texture);
        entity.add(type);

        engine.addEntity(entity);

    }


    public void createPlatform(float x, float y){
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, Gdx.graphics.getWidth(), 50f, BodyFactory.Material.STONE, BodyDef.BodyType.StaticBody, false);

        texture.region = platformTex;

        type.type = SCENERY;

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);
        entity.add(texture);
        entity.add(type);

        engine.addEntity(entity);

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

        //texture.region = textureRegion;

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
        player.getComponent(PlayerComponent.class).balls.add(entity);

        totalBalls.addAll(player.getComponent(PlayerComponent.class).balls);

        return entity;
    }

    private Entity createRowBall(float x, float y){
        Entity entity = engine.createEntity();

        Box2dBodyComponent ballBodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        BallComponent ballComponent = engine.createComponent(BallComponent.class);
        RowComponent rowComponent = engine.createComponent(RowComponent.class);

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
        entity.add(rowComponent);
        entity.add(collisionComponent);
        entity.add(ballComponent);

        engine.addEntity(entity);
        rowComponent.rowOfBalls.add(entity);

        totalBalls.addAll(rowComponent.rowOfBalls);

        return entity;

    }

    public Entity createPlayer(){

        Entity entity = engine.createEntity();
        player = entity;

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(Gdx.graphics.getWidth()/2, 5, 5, 20, BodyFactory.Material.STONE, BodyDef.BodyType.DynamicBody, true);

        transformComponent.position.set(10,10,0);

        //texture.region = textureRegion;

        type.type = PLAYER;
        stateComponent.set(StateComponent.STATE_NORMAL);
        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);
        entity.add(transformComponent);
        entity.add(texture);
        entity.add(player);
        entity.add(collisionComponent);
        entity.add(type);
        entity.add(stateComponent);

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
}
