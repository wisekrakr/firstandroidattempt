package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GamePreferences;
import com.wisekrakr.androidmain.GameUtilities;

public class LevelSelectScreen extends ScreenAdapter {

    private final GamePreferences preferences;
    private AndroidGame game;
    private Stage stage;

    public LevelSelectScreen(AndroidGame game) {
        this.game = game;

        stage = new Stage(new FitViewport(GameUtilities.WORLD_WIDTH, GameUtilities.WORLD_HEIGHT), game.getSpriteBatch());

        preferences = game.getGamePreferences();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        stage.addActor(table);

        BitmapFont font = game.assetManager().assetManager.get("font/gamerFont.fnt");
        font.getData().setScale((GameUtilities.WORLD_WIDTH/100)/5);

        Skin skin = game.assetManager().assetManager.get(String.valueOf(Gdx.files.internal("font/flat-earth-ui.json")));

        Label playAgainLabel = new Label("Choose unlocked level to play", new Label.LabelStyle(font, Color.LIME));

        TextButton levelOne = new TextButton("Level 1", skin);
        TextButton levelTwo = new TextButton("Level 2", skin);
        TextButton levelThree = new TextButton("Level 3", skin);

        table.add(playAgainLabel).expandX().padTop(10f);
        table.row();
        table.add(levelOne).uniformX();
        table.row();
        table.add(levelTwo).uniformX();
        table.row();
        table.add(levelThree).uniformX();


        levelOne.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AndroidGame.APPLICATION);
                //app screen show level 1

                dispose();
            }
        });
        if (game.getGamePreferences().levelDone(1)) {

            levelTwo.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.changeScreen(AndroidGame.APPLICATION); //app screen shows level 2
                    dispose();
                }
            });
        }else if (game.getGamePreferences().levelDone(2)) {

            levelThree.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.changeScreen(AndroidGame.APPLICATION);//app screen shows level 3

                    dispose();
                }
            });
        }
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
