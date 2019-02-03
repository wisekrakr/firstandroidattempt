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

    private Integer ballLeft;
    private Integer levelNumber;
    private Integer score;
    private Label scoreCountLabel;
    private Integer worldTimer;
    private Label timeCountLabel;
    private Label timeLabel;
    private Label scoreLabel;
    private Label levelLabel;
    private Label levelNumberLabel;
    private Label ballsLabel;
    private Label numberOfBallsLabel;

    private AndroidGame game;

    private Stage stage;
    private float timeCounter;

    InfoDisplay(AndroidGame game) {
        this.game = game;

        worldTimer = 0;

        stage = new Stage(new FitViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT));

        BitmapFont font = game.assetManager().assetManager.get("font/gamerFont.fnt");
        font.getData().setScale((GameConstants.WORLD_WIDTH/100)/5);

        Color white = new Color(1,1,1,0.3f);
        Color goldenRod = new Color(218, 165, 32, 0.3f);

        levelLabel = new Label("Level", new Label.LabelStyle(font, Color.WHITE));
        levelNumberLabel = new Label(String.format("%06d", levelNumber), new Label.LabelStyle(font, Color.GOLDENROD));
        timeLabel = new Label("TIME", new Label.LabelStyle(font, Color.WHITE));
        timeCountLabel = new Label(String.format("%06d", worldTimer), new Label.LabelStyle(font, Color.GOLDENROD));
        scoreLabel = new Label("Score", new Label.LabelStyle(font, Color.WHITE));
        scoreCountLabel = new Label(String.format("%06d", score), new Label.LabelStyle(font, Color.GOLDENROD));
        ballsLabel = new Label("Balls left", new Label.LabelStyle(font, Color.WHITE));
        numberOfBallsLabel = new Label(String.format("%06d", ballLeft), new Label.LabelStyle(font, Color.GOLDENROD));

        Table tableLeft = new Table();
        tableLeft.setFillParent(true);
        tableLeft.bottom().left().padLeft(20).padBottom(20);

        Table tableRight = new Table();
        tableRight.setFillParent(true);
        tableRight.bottom().right().padRight(20).padBottom(20);


        tableRight.add(levelLabel).padTop(2);
        tableRight.row();
        tableRight.add(levelNumberLabel).padTop(2);
        tableRight.row();
        tableRight.add(ballsLabel).padBottom(2);
        tableRight.row();
        tableRight.add(numberOfBallsLabel).padBottom(2);


        tableLeft.add(timeLabel).padTop(2);
        tableLeft.row();
        tableLeft.add(timeCountLabel).padTop(2);
        tableLeft.row();
        tableLeft.add(scoreLabel).padBottom(2);
        tableLeft.row();
        tableLeft.add(scoreCountLabel).padBottom(2);

        stage.addActor(tableLeft);
        stage.addActor(tableRight);
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
            levelNumberLabel.setText(Integer.toString(game.getGameThread().getLevelGenerationSystem().getMainLevel()));
            numberOfBallsLabel.setText(Integer.toString(game.getGameThread().getEntityCreator().getTotalShapes().size()));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
