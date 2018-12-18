package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.LevelComponent;
import com.wisekrakr.androidmain.components.ObstacleComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TextureComponent;
import com.wisekrakr.androidmain.components.TransformComponent;
import com.wisekrakr.androidmain.components.TypeComponent;

import java.util.ArrayList;
import java.util.List;

import static com.wisekrakr.androidmain.components.TypeComponent.Type.BALL;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.PLAYER;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.SCENERY;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.SQUARE;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.TRIANGLE;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.OBSTACLE;


public class EntityCreator {

    private BodyFactory bodyFactory;
    public World world;
    private AndroidGame game;
    private PooledEngine engine;
    private TextureAtlas atlas;

    private List<Entity> totalEntities = new ArrayList<Entity>();
    private List<Entity> totalObstacles = new ArrayList<Entity>();

    private String region;

    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    public EntityCreator(AndroidGame game, PooledEngine pooledEngine){
        this.game = game;
        engine = pooledEngine;

        //atlas = game.assetManager().assetManager.get("images/game/game.atlas", TextureAtlas.class);;

        world = new World(new Vector2(0,0), true);
        world.setContactListener(new PhysicalObjectContactListener());

        bodyFactory = BodyFactory.getBodyFactoryInstance(world);
    }

    private String getRegionName() {
        return region;
    }

    public void createTextureRegion(String textureRegion) {
        atlas.findRegion(textureRegion);
        region = textureRegion;
    }

    public void createObstacle(float x, float y, float velocityX, float velocityY, float width, float height, BodyFactory.Material material, BodyDef.BodyType bodyType){
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
        ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);

        typeComponent.type = OBSTACLE;
        //texture.region = textureRegion;
        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, material, bodyType);
        transformComponent.position.set(x, y,0);

        obstacle.position.set(x, y);
        obstacle.velocityX = velocityX;
        obstacle.velocityY = velocityY;
        obstacle.width = width;
        obstacle.height = height;

        entity.add(bodyComponent);
        entity.add(transformComponent);
        entity.add(texture);
        entity.add(typeComponent);
        entity.add(obstacle);
        entity.add(collisionComponent);

        bodyComponent.body.setUserData(entity);

        engine.addEntity(entity);
        totalObstacles.add(entity);
    }

    public Entity createEntity(TypeComponent.Type type, BodyFactory.Material material, float x, float y, float velocityX, float velocityY, float angle){

        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        EntityComponent entityComponent = engine.createComponent(EntityComponent.class);

        typeComponent.type = type;

//        texture.region = SpriteHelper.entitySpriteAtlas(entity,
//                game.assetManager(),
//                getRegionName(),
//                bodyComponent.body,
//                game.getSpriteBatch(),
//                GameUtilities.BALL_RADIUS,
//                GameUtilities.BALL_RADIUS
//        );



        if (type == BALL){
            bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y,
                    GameUtilities.BALL_RADIUS,
                    material,
                    BodyDef.BodyType.DynamicBody
            );
        }else if (type == SQUARE){
            bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y,
                    GameUtilities.BALL_RADIUS,
                    GameUtilities.BALL_RADIUS,
                    material,
                    BodyDef.BodyType.DynamicBody
            );
        }else if (type == TRIANGLE){
            bodyComponent.body = bodyFactory.makeTrianglePolyBody(x, y,
                    GameUtilities.BALL_RADIUS,
                    GameUtilities.BALL_RADIUS,
                    material,
                    BodyDef.BodyType.DynamicBody,
                    true
            );
        }

        bodyComponent.body.setBullet(true); // increase physics computation to limit body travelling through other objects
        //BodyFactory.makeAllFixturesSensors(bodyComponent.body); // make bullets sensors so they don't move player

        transformComponent.position.set(x, y, 0);
        transformComponent.rotation = angle;

        bodyComponent.body.setUserData(entity);
        entityComponent.velocityX = velocityX;
        entityComponent.velocityY = velocityY;
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


    public void createRowEntity(TypeComponent.Type type, BodyDef.BodyType bodyType, BodyFactory.Material material, float x, float y){
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
                    material,
                    bodyType,
                    false
            );
        }else if (type == SQUARE){
            ballBodyComponent.body = bodyFactory.makeBoxPolyBody(x, y,
                    GameUtilities.BALL_RADIUS,
                    GameUtilities.BALL_RADIUS,
                    material,
                    bodyType,
                    false
            );
        }else if (type == TRIANGLE){
            ballBodyComponent.body = bodyFactory.makeTrianglePolyBody(x, y,
                    GameUtilities.BALL_RADIUS,
                    GameUtilities.BALL_RADIUS,
                    material,
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

    }

    public Entity createPlayer(float x, float y){

        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        //TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        //TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        //CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);

        LevelComponent levelComponent = engine.createComponent(LevelComponent.class);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, 5, 20, BodyFactory.Material.STONE, BodyDef.BodyType.StaticBody, true);

        //transformComponent.position.set(10,10,0);

        type.type = PLAYER;

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);
        //entity.add(transformComponent);
        //entity.add(texture);
        entity.add(player);
        //entity.add(collisionComponent);
        entity.add(type);

        entity.add(levelComponent);

        engine.addEntity(entity);

        return entity;
    }

    public void createWalls(float x, float y, float width, float height) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.STEEL, BodyDef.BodyType.StaticBody);

        type.type = SCENERY;

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);
        entity.add(texture);
        entity.add(type);
        entity.add(collisionComponent);

        engine.addEntity(entity);

    }

    public List<Entity> getTotalEntities(){
        return totalEntities;
    }

    public List<Entity> getTotalObstacles() {
        return totalObstacles;
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    /*
    Here we add the actual level that was created in Tiled. The level has a background and sides that can be collided with.
     */

    public void loadMap(){
        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("levels/levelOne.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }
}
