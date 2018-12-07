package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.systems.RenderingSystem;

public class MenuScreen extends ScreenAdapter {

    private Stage stage;
    private AndroidGame game;

    public MenuScreen(AndroidGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth() , Gdx.graphics.getHeight(), new OrthographicCamera()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        Skin skin = game.assetManager().assetManager.get(String.valueOf(Gdx.files.internal("font/flat-earth-ui.json")));

        TextButton selectLevel = new TextButton("Select Level", skin);
        TextButton preferences = new TextButton("Preferences", skin);
        TextButton exit = new TextButton("Exit", skin);

        table.add(selectLevel).uniformX();
        table.row();
        table.add(preferences).uniformX();
        table.row();
        table.add(exit).uniformX();

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();

            }
        });

        selectLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AndroidGame.APPLICATION);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AndroidGame.PREFERENCES);
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }



    @Override
    public void dispose() {
        stage.dispose();
    }
}
