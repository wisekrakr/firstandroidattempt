package com.wisekrakr.androidmain.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
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
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.GameTimer;
import com.wisekrakr.androidmain.components.TransformComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.systems.EntitySystem;
import com.wisekrakr.androidmain.systems.ObstacleSystem;
import com.wisekrakr.androidmain.systems.PlayerControlSystem;

public class PlayScreen extends ScreenAdapter {

    private final InputMultiplexer inputMultiplexer;
    private Viewport viewport;

    private EntityCreator entityCreator;
    private final SpriteBatch spriteBatch;
    private final Controls controls;
    private final PooledEngine engine;
    private OrthographicCamera camera;

    private AndroidGame game;

    private ShapeRenderer shapeRenderer;

    private InfoDisplay infoDisplay;

    public PlayScreen(AndroidGame game) {
        this.game = game;

        spriteBatch = game.getSpriteBatch();

        camera = game.getRenderingSystem().getCamera();
        viewport = new FitViewport(GameUtilities.WORLD_WIDTH, GameUtilities.WORLD_HEIGHT, camera);

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
        engine.addSystem(new EntitySystem(game.getLevelGenerationSystem().getLevelModel().getPlayer(), entityCreator, game.getGameTimer()));
        engine.addSystem(new ObstacleSystem(entityCreator));

        entityCreator.loadMap();

        entityCreator.createWalls(0,0, 5f, GameUtilities.WORLD_HEIGHT *2);
        entityCreator.createWalls(GameUtilities.WORLD_WIDTH,0, 5f, GameUtilities.WORLD_HEIGHT *2);
        entityCreator.createWalls(GameUtilities.WORLD_WIDTH,GameUtilities.WORLD_HEIGHT, GameUtilities.WORLD_WIDTH *2,10f);
        entityCreator.createWalls(0,0, GameUtilities.WORLD_WIDTH *2,5f);

        infoDisplay = new InfoDisplay(game);

    }

    @Override
    public void show() {

        camera.setToOrtho(false, GameUtilities.WORLD_WIDTH, GameUtilities.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        /*
        This gameclock will keep time per second and will be used over the whole game. This delta is holy. (for now)
        This screen will therefore keep the time, so when you get a game over or switch from screen and then start a new game,
        a new gameclock will start (with every new instance of a PlayScreen.
         */

        game.getGameTimer().gameClock += delta;

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);
        camera.update();

        entityCreator.getTiledMapRenderer().setView(camera);
        entityCreator.getTiledMapRenderer().render();

        spriteBatch.setProjectionMatrix(camera.combined);

        game.getLevelGenerationSystem().updateLevels(delta);

        drawObjects();

        infoDisplay.renderDisplay(game.getLevelGenerationSystem().getLevelModel().getPlayer(),
                game.getGameTimer(),
                delta
        );
    }

    private void drawObjects(){
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Entity entity: entityCreator.getTotalEntities()) {
            entity.getComponent(EntityComponent.class);
            if (entity.getComponent(EntityComponent.class) != null) {
                //spriteBatch.begin();
                switch (entity.getComponent(EntityComponent.class).entityColor){
                    case RED:
                        shapeRenderer.setColor(Color.RED);
                        break;
                    case BLUE:
                        /*
                        to create an image instead of a simple color, use the following:
                        */
//                        SpriteHelper.entitySpriteAtlas(entity,game.assetManager(),"earth",
//                        entity.getComponent(Box2dBodyComponent.class).body,spriteBatch,
//                        GameUtilities.BALL_RADIUS,GameUtilities.BALL_RADIUS);

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
                //spriteBatch.end();

                if (entity.getComponent(TypeComponent.class).type == TypeComponent.Type.BALL) {

                    shapeRenderer.circle((entity.getComponent(EntityComponent.class).position.x ),
                            (entity.getComponent(EntityComponent.class).position.y),
                            GameUtilities.BALL_RADIUS / 2
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
