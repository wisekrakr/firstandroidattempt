package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;
import com.wisekrakr.androidmain.retainers.TimeKeeper;


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

        stage = new Stage(new FitViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT));

        BitmapFont font = game.assetManager().assetManager.get("font/gamerFont.fnt");
        font.getData().setScale((GameConstants.WORLD_WIDTH/100)/3);

        Color white = new Color(1,1,1,0.3f);
        Color goldenRod = new Color(218, 165, 32, 0.3f);

        timeLabel = new Label("TIME", new Label.LabelStyle(font, white));
        timeCountLabel = new Label(String.format("%06d", worldTimer), new Label.LabelStyle(font, goldenRod));
        scoreLabel = new Label("Score", new Label.LabelStyle(font, white));
        scoreCountLabel = new Label(String.format("%06d", score), new Label.LabelStyle(font, goldenRod));

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(timeLabel).expandX().padTop(2);
        table.row();
        table.add(timeCountLabel).expandX().padTop(2);
        table.row().pad(30,0,30,0);
        table.add(scoreLabel).expandX().padBottom(2);
        table.row();
        table.add(scoreCountLabel).expandX().padBottom(2);

        stage.addActor(table);
    }

    void renderDisplay(TimeKeeper timer, float delta){

        stage.act();
        stage.draw();

        timeCounter += delta;
        if (timeCounter >= 1) {
            timeCounter = 0;
            worldTimer = (int) timer.time;

            timeCountLabel.setText(String.format("%s",worldTimer));
            scoreCountLabel.setText(Float.toString(ScoreKeeper.getScore()));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
