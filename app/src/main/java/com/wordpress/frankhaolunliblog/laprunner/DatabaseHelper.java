package com.wordpress.frankhaolunliblog.laprunner;

import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Frank2 on 4/29/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WorkoutSaveManager";
    private static final String TABLE_WORKOUTS = "workouts";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME_START = "time_start";
    private static final String KEY_LAPS_PER_KM = "laps_per_km";
    private static final String KEY_TOTAL_LAPS = "total_laps";

    // Time
    private static final String KEY_TOTAL_TIME = "total_time";
    private static final String KEY_EACH_LAP_TIME = "each_lap_time";
    private static final String KEY_FLUC_LAP_TIME = "fluc_lap_time";
    private static final String KEY_AVG_LAP_TIME = "avg_lap_time";

    // Speed
    private static final String KEY_EACH_LAP_SPEED = "each_lap_speed";
    private static final String KEY_FLUC_LAP_SPEED = "fluc_lap_speed";
    private static final String KEY_AVG_SPEED = "avg_speed";

    // Calories
    private static final String KEY_EACH_LAP_CALORIES = "each_lap_calories";
    private static final String KEY_FLUC_LAP_CALORIES = "fluc_lap_calories";
    private static final String KEY_TOTAL_CALORIES = "total_calories";
    private static final String avg_calories;

    // Steps
    private static final String each_lap_steps;
    private static final String fluc_lap_steps;
    private static final String total_steps;
    private static final String avg_steps;
}
