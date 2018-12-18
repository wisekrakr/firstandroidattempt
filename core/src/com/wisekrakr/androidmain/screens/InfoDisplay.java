package com.wisekrakr.androidmain.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.components.GameTimer;
import com.wisekrakr.androidmain.components.PlayerComponent;


public class InfoDisplay implements Disposable {

    private Integer score;
    private Label scoreCountLabel;
    private Integer worldTimer;
    private Label timeCountLabel;
    private Label timeLabel;
    private Label scoreLabel;

    private AndroidGame game;

    private Stage stage;
    private float timeCounter;

    InfoDisplay(AndroidGame game) {
        this.game = game;

        worldTimer = 0;

        stage = new Stage(new FitViewport(GameUtilities.WORLD_WIDTH, GameUtilities.WORLD_HEIGHT));

        BitmapFont font = game.assetManager().assetManager.get("font/gamerFont.fnt");
        font.getData().setScale((GameUtilities.WORLD_WIDTH/100)/5);

        timeLabel = new Label("TIME", new Label.LabelStyle(font, Color.WHITE));
        timeCountLabel = new Label(String.format("%06d", worldTimer), new Label.LabelStyle(font, Color.GOLDENROD));
        scoreLabel = new Label("Score", new Label.LabelStyle(font, Color.WHITE));
        scoreCountLabel = new Label(String.format("%06d", score), new Label.LabelStyle(font, Color.GOLDENROD));

        Table table = new Table();
        table.setFillParent(true);
        table.bottom();

        table.add(timeLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);
        table.row();
        table.add(timeCountLabel).expandX().padBottom(10);
        table.add(scoreCountLabel).expandX().padBottom(10);

        stage.addActor(table);
    }

    void renderDisplay(Entity entity, GameTimer timer, float delta){

        stage.act();
        stage.draw();

        timeCounter += delta;
        if (timeCounter >= 1) {
            timeCounter = 0;
            worldTimer = (int) timer.time;

            timeCountLabel.setText(String.format("%s",worldTimer));
            scoreCountLabel.setText(Float.toString(entity.getComponent(PlayerComponent.class).score));

        }
    }



    @Override
    public void dispose() {
        stage.dispose();
    }
}
