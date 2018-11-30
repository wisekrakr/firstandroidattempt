package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public class LevelSelectScreen extends ScreenAdapter {

    private final GamePreferences preferences;
    private PlayScreen playScreen;
    private AndroidGame game;
    private Stage stage;

    public LevelSelectScreen(AndroidGame game, PlayScreen playScreen) {
        this.game = game;
        this.playScreen = playScreen;

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()), game.getSpriteBatch());

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
        font.getData().setScale(1f);

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
                game.getGamePreferences().setLevelOneCompleted(false);
                if (game.getGamePreferences().levelOneDone()) {playScreen.dispose();}
                dispose();
            }
        });

        if (game.getGamePreferences().levelOneDone()) {
            levelTwo.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.changeScreen(AndroidGame.APPLICATION);
                    //app screen shows level 2
                    game.getGamePreferences().setLevelTwoCompleted(false);
                    if (game.getGamePreferences().levelTwoDone()) {playScreen.dispose();}
                    dispose();
                }
            });
        }
        if (game.getGamePreferences().levelTwoDone()) {
            levelThree.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.changeScreen(AndroidGame.APPLICATION);
                    //app screen shows level 3
                    dispose();
                }
            });
        }

    }

    @Override
    public void render(float delta) {
        System.out.println(" and this " + game.getGamePreferences().levelOneDone());//todo remove
//        if(Gdx.input.justTouched()) {
//            game.changeScreen(AndroidGame.APPLICATION);
//            playScreen.show();
//            dispose();
//        }
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
