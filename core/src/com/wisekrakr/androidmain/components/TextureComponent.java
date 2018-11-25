package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class TextureComponent implements Component, Pool.Poolable {

    public TextureRegion region = null;

    public float width = 0f;
    public float height = 0f;

    @Override
    public void reset() {
        region = null;
        width = 0f;
        height = 0f;
    }
}
