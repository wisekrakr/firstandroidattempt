package com.wisekrakr.androidmain.visuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.EntityComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.helpers.SpriteHelper;
import com.wisekrakr.androidmain.systems.EntitySystem;

public class EntityVisuals implements EntityVisualsContext {

    private AndroidGame game;
    private EntitySystem entitySystem;
    private SpriteBatch spriteBatch;

    public EntityVisuals(AndroidGame game, EntitySystem entitySystem, SpriteBatch spriteBatch) {
        this.game = game;
        this.entitySystem = entitySystem;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void visualizeColoredEntity(Entity entity, TypeComponent.Type type) {
        switch (entity.getComponent(EntityComponent.class).getEntityColor()) {
            case RED:
                if (type == TypeComponent.Type.BALL) {
                    drawObject(entity, "images/objects/gameobjects.atlas", "ball_red",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.SQUARE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "square_red",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.TRIANGLE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "red_triangle",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }
                break;
            case BLUE:
                if (type == TypeComponent.Type.BALL) {
                    drawObject(entity, "images/objects/gameobjects.atlas", "ball_blue",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.SQUARE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "square_blue",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.TRIANGLE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "blue_triangle",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }
                break;
            case CYAN:
                if (type == TypeComponent.Type.BALL) {
                    drawObject(entity, "images/objects/gameobjects.atlas", "ball_cyan",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.SQUARE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "square_cyan",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.TRIANGLE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "cyan_triangle",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }
                break;
            case GREEN:
                if (type == TypeComponent.Type.BALL) {
                    drawObject(entity, "images/objects/gameobjects.atlas", "ball_green",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.SQUARE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "square_green",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.TRIANGLE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "green_triangle",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }
                break;
            case PURPLE:
                if (type == TypeComponent.Type.BALL) {
                    drawObject(entity, "images/objects/gameobjects.atlas", "ball_purple",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.SQUARE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "square_purple",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.TRIANGLE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "purple_triangle",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }
                break;
            case YELLOW:
                if (type == TypeComponent.Type.BALL) {
                    drawObject(entity, "images/objects/gameobjects.atlas", "ball_yellow",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.SQUARE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "square_yellow",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.TRIANGLE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "yellow_triangle",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }
                break;
            case ORANGE:
                if (type == TypeComponent.Type.BALL) {
                    drawObject(entity, "images/objects/gameobjects.atlas", "ball_orange",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.SQUARE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "square_orange",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }else if (type == TypeComponent.Type.TRIANGLE){
                    drawObject(entity, "images/objects/gameobjects.atlas", "orange_triangle",
                            entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                }
                break;
        }
    }

    @Override
    public void visualizePower(Entity entity) {
        switch (PowerHelper.getPower()) {
            case FREEZE:
                drawObject(entity, "images/game/game.atlas", "earth",
                        entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                break;
            case NUKE:
                drawObject(entity, "images/game/game.atlas", "saturn",
                        entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                break;
            case EXTRA_TIME:
                drawObject(entity, "images/game/game.atlas", "neptune",
                        entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                break;
            case MORE_BALLS:
                drawObject(entity, "images/game/game.atlas", "mercury",
                        entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                break;
            case FREEZE_PLAYER:
                drawObject(entity, "images/game/game.atlas", "jupiter",
                        entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
                break;
            case BLACK_AND_WHITE:
                drawObject(entity, "images/game/game.atlas", "uranus",
                        entity.getComponent(EntityComponent.class).width, entity.getComponent(EntityComponent.class).height);
        }
    }

    @Override
    public void drawObject(Entity entity, String atlasPath, String regionPath, float width, float height) {
        if (entity != null){
            SpriteHelper.entitySpriteAtlas(
                    entity,
                    game.assetManager(),
                    atlasPath,
                    regionPath,
                    entity.getComponent(Box2dBodyComponent.class).body,
                    spriteBatch,
                    width, height);
        }
    }
}