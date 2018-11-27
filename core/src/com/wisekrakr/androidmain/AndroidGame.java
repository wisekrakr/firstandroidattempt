package com.wisekrakr.androidmain;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.wisekrakr.androidmain.screens.EndScreen;
import com.wisekrakr.androidmain.screens.LoadingScreen;
import com.wisekrakr.androidmain.screens.MenuScreen;
import com.wisekrakr.androidmain.screens.PlayScreen;
import com.wisekrakr.androidmain.screens.PreferencesScreen;

public class AndroidGame extends Game {

	private MyAssetManager myAssetManager;
	private GamePreferences gamePreferences;

	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private PlayScreen playScreen;
	private EndScreen endScreen;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;

	@Override
	public void create() {
		start();
	}

	private void start(){
		gamePreferences = new GamePreferences();

		myAssetManager = new MyAssetManager();

		setScreen(new LoadingScreen(this));
	}

	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(playScreen == null) playScreen = new PlayScreen(this);
				this.setScreen(playScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}

	public GamePreferences getGamePreferences() {
		return gamePreferences;
	}

	public MyAssetManager assetManager() {
		return myAssetManager;
	}
}
