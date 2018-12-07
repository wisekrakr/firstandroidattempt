package com.wisekrakr.androidmain.levels;

import com.wisekrakr.androidmain.EntityCreator;

public interface LevelCreation {


    void setLevelNumber(int levelNumber);

    boolean lockedLevel();
    void setLevelLocked(int levelNumber, boolean levelLocked);

    void setRowsAndColumns(int rows, int columns, EntityCreator entityCreator);

}
