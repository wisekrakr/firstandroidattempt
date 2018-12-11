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
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.TransformComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.systems.EntitySystem;
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
        engine.addSystem(new EntitySystem(game.getLevelGenerationSystem().getLevelModel().getPlayer(), entityCreator));

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
        for (Entity entity: entityCreator.totalEntities()) {
            entity.getComponent(EntityComponent.class);
            if (entity.getComponent(EntityComponent.class) != null) {

                switch (entity.getComponent(EntityComponent.class).entityColor){
                    case RED:
                        shapeRenderer.setColor(Color.RED);
                        break;
                    case BLUE:
                        shapeRenderer.setColor(Color.BLUE);
                        break;
                    case CYAN:
                        shapeRenderer.setColor(Color.CYAN);
                        break;
                    case GREEN:
                        shapeRenderer.setColor(Color.GREEN);
                        break;
                    case PURPLE:
                        shapeRenderer.setColor(Color.PURPLE);
                        break;
                    case YELLOW:
                        shapeRenderer.setColor(Color.YELLOW);
                        break;
                    case PINK:
                        shapeRenderer.setColor(Color.ORANGE);
                        break;
                }

                if (entity.getComponent(TypeComponent.class).type == TypeComponent.Type.BALL) {

                    shapeRenderer.circle(entity.getComponent(EntityComponent.class).position.x,
                            entity.getComponent(EntityComponent.class).position.y, GameUtilities.BALL_RADIUS / 2
                    );

                }else if (entity.getComponent(TypeComponent.class).type == TypeComponent.Type.SQUARE) {

                    shapeRenderer.rect(entity.getComponent(EntityComponent.class).position.x - GameUtilities.BALL_RADIUS/2,
                            entity.getComponent(EntityComponent.class).position.y - GameUtilities.BALL_RADIUS/2,
                            GameUtilities.BALL_RADIUS/2,
                            GameUtilities.BALL_RADIUS/2,
                            GameUtilities.BALL_RADIUS, GameUtilities.BALL_RADIUS,
                            1,1,
                            entity.getComponent(TransformComponent.class).rotation);
                }else if (entity.getComponent(TypeComponent.class).type == TypeComponent.Type.TRIANGLE){


                    shapeRenderer.triangle(entity.getComponent(EntityComponent.class).position.x - GameUtilities.BALL_RADIUS/2,
                            entity.getComponent(EntityComponent.class).position.y ,
                            entity.getComponent(EntityComponent.class).position.x - GameUtilities.BALL_RADIUS/2 + GameUtilities.BALL_RADIUS/2,
                            entity.getComponent(EntityComponent.class).position.y + GameUtilities.BALL_RADIUS,
                            entity.getComponent(EntityComponent.class).position.x + GameUtilities.BALL_RADIUS/2,
                            entity.getComponent(EntityComponent.class).position.y )
                    ;

                }
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
