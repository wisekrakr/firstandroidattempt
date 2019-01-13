package com.wisekrakr.androidmain.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.TypeComponent;

public class TouchControl implements Disposable {
    private Stage stage;
    private AndroidGame game;
    private Touchpad touchPad;

    public TouchControl(AndroidGame game) {
        this.game = game;

        stage = new Stage(new FitViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        setUpTouchPad();

        stage.addActor(touchPad);
    }

    private void setUpTouchPad(){
        Skin skin = game.assetManager().assetManager.get(String.valueOf(Gdx.files.internal("font/flat-earth-ui.json")));

        touchPad = new Touchpad(5, skin);
        touchPad.setBounds(10,10,GameConstants.WORLD_WIDTH/2.5f,GameConstants.WORLD_HEIGHT/5);
    }

    public void renderTouchControls() {
        stage.act();
        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}