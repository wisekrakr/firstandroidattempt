package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TextureComponent;
import com.wisekrakr.androidmain.components.TransformComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.components.SurfaceComponent;

import java.util.ArrayList;
import java.util.List;

import static com.wisekrakr.androidmain.components.TypeComponent.Type.BALL;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.PLAYER;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.SCENERY;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.SQUARE;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.TRIANGLE;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.WATER;


public class EntityCreator {

    private BodyFactory bodyFactory;
    public World world;
    private AndroidGame game;
    private PooledEngine engine;
    private TextureAtlas atlas;

    private List<Entity> totalEntities = new ArrayList<Entity>();

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

    public Entity createEntity(TypeComponent.Type type, float x, float y, float xVelocity, float yVelocity, float angle){

        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        EntityComponent entityComponent = engine.createComponent(EntityComponent.class);

        typeComponent.type = type;

        if (type == BALL){
            bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y,
                    GameUtilities.BALL_RADIUS,
                    BodyFactory.Material.RUBBER,
                    BodyDef.BodyType.DynamicBody,
                    false
            );
        }else if (type == SQUARE){
            bodyComponent.body = bodyFactory.makeBoxPolyBody(x,y,
                    GameUtilities.BALL_RADIUS,
                    GameUtilities.BALL_RADIUS,
                    BodyFactory.Material.RUBBER,
                    BodyDef.BodyType.DynamicBody,
                    false
            );
        }else if (type == TRIANGLE){
            bodyComponent.body = bodyFactory.makeTrianglePolyBody(x,y,
                    GameUtilities.BALL_RADIUS,
                    GameUtilities.BALL_RADIUS,
                    BodyFactory.Material.RUBBER,
                    BodyDef.BodyType.DynamicBody,
                    false
            );
        }

        bodyComponent.body.setBullet(true); // increase physics computation to limit body travelling through other objects
        //BodyFactory.makeAllFixturesSensors(bodyComponent.body); // make bullets sensors so they don't move player

        transformComponent.position.set(x, y, 0);
        transformComponent.rotation = angle;

        bodyComponent.body.setUserData(entity);
        entityComponent.velocityX = xVelocity;
        entityComponent.velocityY = yVelocity;
        entityComponent.position = bodyComponent.body.getPosition();

        entityComponent.entityColor = entityComponent.randomBallColor();

        entity.add(bodyComponent);
        entity.add(texture);
        entity.add(typeComponent);
        entity.add(transformComponent);
        entity.add(collisionComponent);
        entity.add(entityComponent);

        engine.addEntity(entity);

        return entity;
    }

    public Entity createRowEntity(TypeComponent.Type type, BodyDef.BodyType bodyType, float x, float y){
        Entity entity = engine.createEntity();

        Box2dBodyComponent ballBodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        EntityComponent entityComponent = engine.createComponent(EntityComponent.class);

        typeComponent.type = type;

        if (type == BALL) {
            ballBodyComponent.body = bodyFactory.makeCirclePolyBody(x, y,
                    GameUtilities.BALL_RADIUS,
                    BodyFactory.Material.RUBBER,
                    bodyType,
                    false
            );
            System.out.println(bodyType);
        }else if (type == SQUARE){
            ballBodyComponent.body = bodyFactory.makeBoxPolyBody(x,y,
                    GameUtilities.BALL_RADIUS,
                    GameUtilities.BALL_RADIUS,
                    BodyFactory.Material.RUBBER,
                    bodyType,
                    false
            );
        }else if (type == TRIANGLE){
            ballBodyComponent.body = bodyFactory.makeTrianglePolyBody(x,y,
                    GameUtilities.BALL_RADIUS,
                    GameUtilities.BALL_RADIUS,
                    BodyFactory.Material.RUBBER,
                    bodyType,
                    false
            );
        }

        transformComponent.position.set(x, y, 0);

        ballBodyComponent.body.setUserData(entity);
        entityComponent.position = ballBodyComponent.body.getPosition();
        entityComponent.velocityX = 0f;
        entityComponent.velocityY = 0f;

        entityComponent.entityColor = entityComponent.randomBallColor();

        entity.add(ballBodyComponent);
        entity.add(texture);
        entity.add(typeComponent);
        entity.add(transformComponent);
        entity.add(collisionComponent);
        entity.add(entityComponent);

        engine.addEntity(entity);

        totalEntities.add(entity);

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

        entity.add(levelComponent);

        engine.addEntity(entity);

        return entity;
    }

    public void createWalls(float posX, float posY, float width, float height) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(posX, posY, width, height, BodyFactory.Material.STEEL, BodyDef.BodyType.StaticBody);

        //texture.region = wallRegion;

        type.type = SCENERY;

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);
        entity.add(texture);
        entity.add(type);
        entity.add(collisionComponent);

        engine.addEntity(entity);

    }

    public List<Entity> totalEntities(){
        return totalEntities;
    }

//    public Entity getPlayer() {
//        return player;
//    }
}
