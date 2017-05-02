package com.wordpress.frankhaolunliblog.laprunner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.Contacts.SettingsColumns.KEY;

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
    private static final String KEY_AVG_CALORIES = "avg_calories";

    // Steps
    private static final String KEY_EACH_LAP_STEPS = "each_lap_steps";
    private static final String KEY_FLUC_LAP_STEPS = "fluc_lap_steps";
    private static final String KEY_TOTAL_STEPS = "total_steps";
    private static final String KEY_AVG_STEPS = "avg_steps";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_WORKOUTS_TABLE =
                        "CREATE TABLE" + TABLE_WORKOUTS + "{" +
                            KEY_ID + " INTEGER PRIMARY KEY," +
                            KEY_DATE + " TEXT," +
                            KEY_TIME_START + "TEXT," +
                            KEY_LAPS_PER_KM + "INTEGER," +
                            KEY_TOTAL_LAPS + "INTEGER," +
                            KEY_TOTAL_TIME + "INTEGER," +
                            KEY_EACH_LAP_TIME + "TEXT," +
                            KEY_FLUC_LAP_TIME + "TEXT," +
                            KEY_AVG_LAP_TIME + "TEXT," +
                            KEY_EACH_LAP_SPEED + "TEXT," +
                            KEY_FLUC_LAP_SPEED + "TEXT," +
                            KEY_AVG_SPEED + "TEXT," +
                            KEY_EACH_LAP_CALORIES + "TEXT," +
                            KEY_FLUC_LAP_CALORIES + "TEXT," +
                            KEY_TOTAL_CALORIES + "TEXT," +
                            KEY_AVG_CALORIES + "TEXT," +
                            KEY_EACH_LAP_STEPS + "TEXT," +
                            KEY_FLUC_LAP_STEPS + "TEXT," +
                            KEY_TOTAL_STEPS + "TEXT," +
                            KEY_AVG_STEPS + "TEXT," + "}";
        db.execSQL(CREATE_WORKOUTS_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        onCreate(db);
    }

    void addWorkout(Workout workout){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
        values.put("KEY_ID", workout.getId());
        values.put("KEY_DATE", workout.getDate());
        values.put("KEY_TIME_START", workout.getTime_start());
        values.put("KEY_LAPS_PER_KM", workout.getLaps_per_km());
        values.put("KEY_TOTAL_LAPS", workout.getTotal_laps());
        values.put("KEY_EACH_LAP_TIME", workout.getEach_lap_time());
        values.put("KEY_FLUC_LAP_TIME", workout.getFluc_lap_time());
        values.put("KEY_AVG_LAP_TIME", workout.getAvg_lap_time());
        values.put("KEY_EACH_LAP_SPEED", workout.getEach_lap_speed());
        values.put("KEY_FLUC_LAP_SPEED", workout.getFluc_lap_time());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());
        values.put("KEY_ID", workout.getId());


        }



}
