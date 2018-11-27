package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.sun.rowset.internal.Row;
import com.wisekrakr.androidmain.LevelFactory;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.RowComponent;

import java.util.Collections;

public class RowSystem extends IteratingSystem {
    private LevelFactory levelFactory;
    private Entity player;

    @SuppressWarnings("unchecked")
    public RowSystem(LevelFactory levelFactory, Entity player) {
        super(Family.all(RowComponent.class).get());
        this.levelFactory = levelFactory;
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        float score = player.getComponent(PlayerComponent.class).score;

        RowComponent rowComponent = ComponentMapper.getFor(RowComponent.class).get(entity);

        int color = Collections.frequency(levelFactory.totalBalls(), BallComponent.BallColor.values());

    }
}
