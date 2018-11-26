package com.wisekrakr.androidmain.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.LevelFactory;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.systems.BallSystem;
import com.wisekrakr.androidmain.systems.CollisionSystem;
import com.wisekrakr.androidmain.systems.LevelGenerationSystem;
import com.wisekrakr.androidmain.systems.PhysicsDebugSystem;
import com.wisekrakr.androidmain.systems.PhysicsSystem;
import com.wisekrakr.androidmain.systems.PlayerControlSystem;
import com.wisekrakr.androidmain.systems.RenderingSystem;
import com.wisekrakr.androidmain.systems.RowSystem;
import com.wisekrakr.androidmain.systems.WallSystem;

public class PlayScreen extends ScreenAdapter {

    private final SpriteBatch spriteBatch;
    private final InputMultiplexer inputMultiplexer;
    private final PooledEngine engine;
    private final LevelFactory levelFactory;
    private OrthographicCamera camera;

    private AndroidGame androidGame;

    private ShapeRenderer shapeRenderer;
    private Entity player;
    private float ballTime = 0f;

    private Viewport viewport;

    public PlayScreen(AndroidGame androidGame) {
        this.androidGame = androidGame;

        spriteBatch = new SpriteBatch();

        RenderingSystem renderingSystem = new RenderingSystem(spriteBatch);

        camera = renderingSystem.getCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        Controls controls = new Controls();

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(controls);

        engine = new PooledEngine();

        levelFactory = new LevelFactory(engine);

        shapeRenderer = new ShapeRenderer();

        addSystems(renderingSystem, controls);
    }

    private void addSystems(RenderingSystem renderingSystem, Controls controls) {
        engine.addSystem(renderingSystem);
        engine.addSystem(new PhysicsSystem(levelFactory.world));
        engine.addSystem(new PhysicsDebugSystem(levelFactory.world, renderingSystem.getCamera()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controls, levelFactory, camera));

        player = levelFactory.createPlayer();

        engine.addSystem(new BallSystem(player, levelFactory));
        engine.addSystem(new RowSystem(levelFactory, player));
        engine.addSystem(new WallSystem());
        engine.addSystem(new LevelGenerationSystem(levelFactory));

        levelFactory.createWalls(0,0, 5f, Gdx.graphics.getHeight()*2);
        levelFactory.createWalls(Gdx.graphics.getWidth(),0, 5f, Gdx.graphics.getHeight()*2);
        levelFactory.createWalls(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), Gdx.graphics.getWidth()*2,10f);


//        for (int i = 0; i< 20; i++){
//            Entity levelBall = levelFactory.createBall(GameUtilities.BALL_RADIUS, BodyFactory.Material.RUBBER,
//                    GameHelper.generateRandomNumberBetween(0, Gdx.graphics.getWidth()),
//                    GameHelper.generateRandomNumberBetween(Gdx.graphics.getHeight()- GameUtilities.BALL_RADIUS *5,
//                            Gdx.graphics.getHeight() - GameUtilities.BALL_RADIUS),
//                    0,0);
//            player.getComponent(PlayerComponent.class).balls.add(levelBall);
//        }

//        levelFactory.createWaterFloor(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2, 80f,30f);

        levelFactory.generateLevel();
    }

    @Override
    public void show() {

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.input.setInputProcessor(inputMultiplexer);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);
        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);

        ballTime += delta;
//        if (ballTime > 20f){ // time when more balls come on the screen
//            if (player.getComponent(PlayerComponent.class).balls.size() > 60){ //maximum number of balls on the screen
//                return;
//            }else {
//                for (int i = 0; i < 10; i++) {
//                    Entity levelBall = levelFactory.createBall(GameUtilities.BALL_RADIUS, BodyFactory.Material.RUBBER,
//                            GameHelper.generateRandomNumberBetween(0, Gdx.graphics.getWidth()),
//                            GameHelper.generateRandomNumberBetween(Gdx.graphics.getHeight() - GameUtilities.BALL_RADIUS * 5,
//                                    Gdx.graphics.getHeight() - GameUtilities.BALL_RADIUS),
//                            0, 0);
//                    player.getComponent(PlayerComponent.class).balls.add(levelBall);
//                }
//            }
//            ballTime = 0f;
//        }

        drawObjects();

    }

    private void drawObjects(){

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity: levelFactory.totalBalls()) {
            entity.getComponent(BallComponent.class);

            if (entity.getComponent(BallComponent.class) != null) {
                if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.SILVER) {
                    shapeRenderer.setColor(Color.CYAN);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.RED) {
                    shapeRenderer.setColor(Color.RED);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.YELLOW) {
                    shapeRenderer.setColor(Color.YELLOW);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.BLUE) {
                    shapeRenderer.setColor(Color.BLUE);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.GREEN) {
                    shapeRenderer.setColor(Color.GREEN);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.PURPLE) {
                    shapeRenderer.setColor(Color.PURPLE);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.GOLD) {
                    shapeRenderer.setColor(Color.GOLD);
                }
                shapeRenderer.circle(entity.getComponent(BallComponent.class).position.x, entity.getComponent(BallComponent.class).position.y, GameUtilities.BALL_RADIUS / 2);
            }
        }

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
    }
}
