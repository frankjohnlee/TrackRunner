package com.wordpress.frankhaolunliblog.laprunner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.content.SharedPreferences;

import java.util.ArrayList;


public class FinishWorkout extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String StrDate = "";
    String StrTime = "";
    String StrGoalLap = "";
    String StrLapsPerKM = "";
    String StrStatsPerLap = "";
    boolean DebugMode = true;


    //|Start|Date|Time|GoalLap|LapsPerKM|StatsPerLap[[LapBefore, LapAfter, Seconds], ...]|END
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        String ThisWorkout = pref.getString("ThisWorkout", "");
        if (DebugMode){
            Log.d("ThisWorkout", ThisWorkout);
        }
        this.ExtraFromString(ThisWorkout);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void ExtraFromString (String ThisWorkout){
        int IndicatorCount = 0;
        int counter = 0;

        //|Start|Date|Time|GoalLap|LapsPerKM|StatsPerLap[[LapBefore, LapAfter, Seconds], ...]
        // 1    2     3   4       5         6
        while (counter <= ThisWorkout.length() - 1){
            String CurrentString = ThisWorkout.charAt(counter) + "";
            if (CurrentString.equals("|")){
                IndicatorCount ++;
            }
            else if (IndicatorCount == 2){
                StrDate += CurrentString;
            }
            else if (IndicatorCount == 3){
                StrTime += CurrentString;
            }
            else if (IndicatorCount == 4){
                StrGoalLap += CurrentString;
            }
            else if (IndicatorCount == 5){
                StrLapsPerKM += CurrentString;
            }
            else if (IndicatorCount == 6){
                StrStatsPerLap += CurrentString;
            }

            counter ++;
        }


    }


}
