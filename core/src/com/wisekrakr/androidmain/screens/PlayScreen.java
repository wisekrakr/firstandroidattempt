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
import com.wisekrakr.androidmain.EntityCreator;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.GameTimer;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.systems.BallSystem;
import com.wisekrakr.androidmain.systems.PlayerControlSystem;
import com.wisekrakr.androidmain.systems.WallSystem;

public class PlayScreen extends ScreenAdapter {

    private final InputMultiplexer inputMultiplexer;

    private EntityCreator entityCreator;
    private final SpriteBatch spriteBatch;
    private final Controls controls;
    private final PooledEngine engine;
    private OrthographicCamera camera;

    private AndroidGame game;

    private ShapeRenderer shapeRenderer;

    private Viewport viewport;
    private InfoDisplay infoDisplay;

    public PlayScreen(AndroidGame game) {
        this.game = game;

        spriteBatch = game.getSpriteBatch();

        camera = game.getRenderingSystem().getCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        controls = new Controls();

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(controls);

        engine = game.getEngine();

        entityCreator = game.getEntityCreator();

        shapeRenderer = new ShapeRenderer();

        addSystems();
    }

    /*
    Add remaining systems we did not need to add to the Gamethread.
    These systems will be created anew every time we switch to a different level (we create a new playscreen every
    level change.
     */
    private void addSystems() {

        engine.addSystem(new PlayerControlSystem(controls, entityCreator, camera));
        engine.addSystem(new BallSystem(game.getLevelGenerationSystem().getLevelModel().getPlayer(), entityCreator));
        engine.addSystem(new WallSystem());

        entityCreator.createWalls(0,0, 5f, Gdx.graphics.getHeight()*2);
        entityCreator.createWalls(Gdx.graphics.getWidth(),0, 5f, Gdx.graphics.getHeight()*2);
        entityCreator.createWalls(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), Gdx.graphics.getWidth()*2,10f);
        entityCreator.createWalls(0,0, Gdx.graphics.getWidth()*2,5f);

//        entityCreator.createWaterFloor(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2, 80f,30f);

        infoDisplay = new InfoDisplay(game);

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

        game.getLevelGenerationSystem().updateLevels(delta);

        drawObjects();

        infoDisplay.renderDisplay(game.getLevelGenerationSystem().getLevelModel().getPlayer(),
                game.getLevelGenerationSystem().getLevelModel().getTimer(),
                delta
        );


    }

    private void drawObjects(){

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity: entityCreator.totalBalls()) {
            entity.getComponent(BallComponent.class);
            if (entity.getComponent(BallComponent.class) != null) {
                if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.MARS) {
                    shapeRenderer.setColor(Color.CYAN);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.MERCURY) {
                    shapeRenderer.setColor(Color.RED);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.JUPITER) {
                    shapeRenderer.setColor(Color.YELLOW);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.EARTH) {
                    shapeRenderer.setColor(Color.BLUE);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.NEPTUNE) {
                    shapeRenderer.setColor(Color.GREEN);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.SATURN) {
                    shapeRenderer.setColor(Color.PURPLE);
                } else if (entity.getComponent(BallComponent.class).ballColor == BallComponent.BallColor.URANUS) {
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
