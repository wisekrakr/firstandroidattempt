package com.wisekrakr.androidmain.levels;

import java.util.HashMap;
import java.util.Map;

public enum LevelNumber {
    ONE(1), TWO(2), THREE(3);

    private int value;
    private static Map<Integer, LevelNumber> map = new HashMap<Integer, LevelNumber>();

    LevelNumber(int value){
        this.value = value;
    }

    static {
        for (LevelNumber levelNumber : LevelNumber.values()) {
            map.put(levelNumber.value, levelNumber);
        }
    }

    public static LevelNumber valueOf(int numberOfLevel) {
        return map.get(numberOfLevel);
    }

    public int getValue() {
        return value;
    }

}
