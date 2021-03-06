package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.TextureComponent;
import com.wisekrakr.androidmain.components.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {

    public static final float PPM = 64.0f;

    public static final float PIXELS_TO_METRES = 1f / PPM;

    public static float PixelsToMeters(float pixelValue){
        return pixelValue * PIXELS_TO_METRES;
    }

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera camera;

    private ComponentMapper<TextureComponent> textureComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;

    @SuppressWarnings("unchecked")
    public RenderingSystem(SpriteBatch batch) {

        super(Family.all(TransformComponent.class, TextureComponent.class).get(), new ZComparator());

        this.batch = batch;

        textureComponentMapper = ComponentMapper.getFor(TextureComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);

        renderQueue = new Array<Entity>();

        camera = new OrthographicCamera(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
//        camera.position.set(GameConstants.WORLD_WIDTH/2, GameConstants.WORLD_HEIGHT/2,0);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

//        renderQueue.sort(comparator);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureComponentMapper.get(entity);
            TransformComponent t = transformComponentMapper.get(entity);

            if (tex.region == null || t.isHidden) {
                continue;
            }

            float width = tex.region.getRegionWidth()/2;
            float height = tex.region.getRegionHeight()/2;

            float originX = width/2f;
            float originY = height/2f;

            batch.draw(tex.region,
                    t.position.x - originX, t.position.y - originY,
                    originX, originY,
                    width, height,
                    PixelsToMeters(t.scale.x), PixelsToMeters(t.scale.y),
                    t.rotation);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }


}
