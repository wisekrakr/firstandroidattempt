package com.wisekrakr.androidmain.levels;

import com.badlogic.gdx.Gdx;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.EntityCreator;

public class LevelCreationTools {

    public LevelCreationTools() {
    }

    public LevelCreation levelCreation(){return levelCreation; }



    private LevelCreation levelCreation = new LevelCreation() {

        private boolean lockedLevel;

        @Override
        public void setLevelNumber(int levelNumber) {
            LevelNumber.valueOf(levelNumber);
        }

        @Override
        public boolean lockedLevel() {
            return lockedLevel;
        }

        @Override
        public void setLevelLocked(int levelNumber, boolean levelLocked) {
            if (levelNumber != 0) {
                LevelNumber.valueOf(levelNumber);
                lockedLevel = levelLocked;
            }
        }

        @Override
        public void setRowsAndColumns(int rows, int columns, EntityCreator entityCreator) {
            if (rows != 0 && columns != 0){
                for (int j = 1; j < columns; j++) {
                    for (int k = 1; k < rows; k++) {
                        entityCreator.createRowBall(j * GameUtilities.BALL_RADIUS,
                                Gdx.graphics.getHeight() - k * GameUtilities.BALL_RADIUS
                        );
                    }
                }
            }
        }
    };
}
