package com.wordpress.frankhaolunliblog.laprunner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.id.input;


public class FinishWorkout extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String StrDate = "";
    String StrTime = "";
    String StrGoalLap = "";
    String StrLapsPerKM = "";
    String StrStatsPerLap = "";
    boolean DebugMode = true;
    TextView CurrentWorkoutStatsTextView;


    //|Start|Date|Time|GoalLap|LapsPerKM|LapNumber,Seconds, ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        CurrentWorkoutStatsTextView = (TextView) findViewById(R.id.CurrentWorkoutStats);



        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        String ThisWorkout = pref.getString("ThisWorkout", "");
        if (DebugMode){
            Log.d("ThisWorkout", ThisWorkout);
        }
        this.ExtraFromString(ThisWorkout);
        String StrCurrentWorkoutStats = "";
        String Tabs = "     ";
        StrCurrentWorkoutStats += "Date: " + StrDate + "\n";
        StrCurrentWorkoutStats += "Start Time: " + StrTime + "\n";
        StrCurrentWorkoutStats += "Goal: " + StrGoalLap + " laps" + "\n";
        StrCurrentWorkoutStats += "Track Size: " + StrLapsPerKM + " Laps per KM" + "\n";
        StrCurrentWorkoutStats += "Time breakdown for each lap: " + "\n";

        // Print Out Each Lap Time
        int IndexCurrentWorkout = 0;
        int TotalTime = 0;
        int LastLapNumberDigits = 0;
        String[] StatsPerLapArray = StrStatsPerLap.split(",");
        for (String s: StatsPerLapArray) {
            if (IndexCurrentWorkout % 2 == 0 && StatsPerLapArray.length - 1 != IndexCurrentWorkout) {
                LastLapNumberDigits = s.length();
                StrCurrentWorkoutStats += "Lap: " + s;
            } else if (IndexCurrentWorkout % 2 != 0) {
                TotalTime += Integer.valueOf(s);

                if (LastLapNumberDigits == 1) {
                    StrCurrentWorkoutStats += Tabs + "    Seconds: " + s + "\n";
                }
                else if (LastLapNumberDigits == 2) {
                    StrCurrentWorkoutStats += Tabs + "  Seconds: " + s + "\n";
                }
                else if (LastLapNumberDigits == 3) {
                    StrCurrentWorkoutStats += Tabs + " Seconds: " + s + "\n";
                }

            }
            IndexCurrentWorkout ++;
        }
        int h = (int) (TotalTime*1000 / 3600000);
        int m = (int) (TotalTime*1000 - h * 3600000) / 60000;
        int s = (int) (TotalTime*1000 - h * 3600000 - m * 60000) / 1000;
        String hh = h < 10 ? "0" + h : h + "";
        String mm = m < 10 ? "0" + m : m + "";
        String ss = s < 10 ? "0" + s : s + "";

        StrCurrentWorkoutStats += "Total Workout: " + hh + ":" + mm + ":" + ss;
        CurrentWorkoutStatsTextView.setText(StrCurrentWorkoutStats);


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
