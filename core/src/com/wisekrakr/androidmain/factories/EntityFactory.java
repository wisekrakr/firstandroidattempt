package com.wisekrakr.androidmain.factories;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.components.TypeComponent;

public interface EntityFactory<EntityT extends Entity> {
    EntityT createEntity(Vector2 position, Vector2 velocity, Component component, TypeComponent.Type typeOfComponent);
}
