package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.components.TypeComponent;

public interface EntityVisualsContext {

    void visualizeColoredEntity(Entity entity, TypeComponent.Type type);
    void visualizePower(Entity entity);
    void drawObject(Entity entity, String atlasPath, String regionPath, float width, float height);

}
