package com.wordpress.frankhaolunliblog.laprunner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import java.util.ArrayList;


public class FinishWorkout extends AppCompatActivity {
    boolean DebugMode = false;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String StrDate = "";
    String StrTime = "";
    String StrGoalLap = "";
    String StrLapsPerKM = "";
    String StrStatsPerLap = "";
    int TotalTime = 0;
    String Tabs = "     ";
    String StrCurrentWorkoutStats = "";

    TextView CurrentWorkoutStatsTextView;

    // For Database
    Workout ThisWorkout;
    ArrayList EachTimeArray;
    String id;
    String date;
    String time_start;
    Integer laps_per_km;
    Integer total_laps;

    // Time
    Integer total_time;
    String each_lap_time;
    String fluc_lap_time;
    double avg_lap_time;

    // Speed
    String each_lap_speed;
    String fluc_lap_speed;
    Integer avg_speed;

    // Calories
    String each_lap_calories;
    String fluc_lap_calories;
    Integer total_calories;
    Integer avg_calories;

    // Steps
    String each_lap_steps;
    String fluc_lap_steps;
    Integer total_steps;
    Integer avg_steps;


    //|Start|Date|Time|GoalLap|LapsPerKM|LapNumber,Seconds, ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.FindBreakPoint("1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.FindBreakPoint("2");
        EachTimeArray = new ArrayList();
        CurrentWorkoutStatsTextView = (TextView) findViewById(R.id.CurrentWorkoutStats);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        String ThisWorkoutString = pref.getString("ThisWorkout", "");
//        if (DebugMode){
//            ThisWorkoutString = "|Start|2017-05-02|12:43:14|70|7|1,11,2,9,3,11,4,4,5,2,";
//        }

        this.FindBreakPoint("3");
        this.ExtraFromString(ThisWorkoutString);
        this.FindBreakPoint("4");
        this.SetCurrentWorkoutStatsTextView();
        this.FindBreakPoint("5");
        this.GetLapValues();
        this.FindBreakPoint("6");
        this.DataIntoDatabase();

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
    public void SetCurrentWorkoutStatsTextView(){
        StrCurrentWorkoutStats += "Date: " + StrDate + "\n";
        StrCurrentWorkoutStats += "Start Time: " + StrTime + "\n";
        StrCurrentWorkoutStats += "Goal: " + StrGoalLap + " laps" + "\n";
        StrCurrentWorkoutStats += "Track Size: " + StrLapsPerKM + " Laps per KM" + "\n";
        StrCurrentWorkoutStats += "Time breakdown for each lap: " + "\n";
    }
    public void GetLapValues(){
        int IndexCurrentWorkout = 0;
        TotalTime = 0;
        int LastLapNumberDigits = 0;
        String[] StatsPerLapArray = StrStatsPerLap.split(",");
        for (String s: StatsPerLapArray) {
            if (IndexCurrentWorkout % 2 == 0 && StatsPerLapArray.length - 1 != IndexCurrentWorkout) {
                LastLapNumberDigits = s.length();
                StrCurrentWorkoutStats += "Lap: " + s;
            } else if (IndexCurrentWorkout % 2 != 0) {
                TotalTime += Integer.valueOf(s);
                EachTimeArray.add(s);


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

    }


    public void FindBreakPoint(String string){
        if (DebugMode){
            Log.d("Find Break Point", string);
        }
    }
    public void DataIntoDatabase(){
        EachTimeArray = new ArrayList();
        this.EachTimeArrayHelper();

        // "|Start|2017-05-02|12:43:14|70|7|1,11,2,9,3,11,4,4,5,2,";

        id = StrDate + ", " + StrTime;
        ThisWorkout = new Workout(id);
        date = StrDate; // 2017-05-02
        time_start = StrTime; // 12:43:14
        laps_per_km = Integer.valueOf(StrLapsPerKM); // 7
        total_laps = EachTimeArray.size(); // 5
        total_time = TotalTime; // 37
        each_lap_time = this.EachLapTimeHelper(EachTimeArray); // 11  9  11  4  2
        fluc_lap_time = this.FlucLapTimeHelper(EachTimeArray); //   -   +   -  -
        avg_lap_time = this.AvgLapTimeHelper(EachTimeArray); // 7.4
        each_lap_speed = this.EachLapSpeedHelper(EachTimeArray); // 1.28 1.05 1.28 0.46 0.23

        if (DebugMode) {
            Log.d("StrDate", StrDate);
            Log.d("date", date);
            Log.d("time_start", time_start);
            Log.d("laps_per_km ", Integer.toString(laps_per_km));
            Log.d("total_laps", Integer.toString(total_laps));
            Log.d("total_time", Integer.toString(total_time));
            Log.d("each_lap_time", each_lap_time);
            Log.d("fluc_lap_time", fluc_lap_time);
            Log.d("avg_lap_time", Double.toString(avg_lap_time));
            Log.d("each_lap_speed", each_lap_speed);
        }



    }
    public void EachTimeArrayHelper(){
            int IndexCurrentWorkout = 0;
            TotalTime = 0;
            int LastLapNumberDigits = 0;
            String[] StatsPerLapArray = StrStatsPerLap.split(",");
            for (String s: StatsPerLapArray) {
                if (IndexCurrentWorkout % 2 == 0 && StatsPerLapArray.length - 1 != IndexCurrentWorkout) {
                } else if (IndexCurrentWorkout % 2 != 0) {
                    EachTimeArray.add(s);
                    TotalTime += Integer.valueOf(s);
                }
                IndexCurrentWorkout ++;
            }

        }
    public String EachLapTimeHelper(ArrayList theArray){
        int index = 0;
        String returnString = "";
        while (index <= theArray.size() - 1){
            returnString += theArray.get(index) + " ";
            index ++;
        }
        returnString.substring(0, returnString.length() - 2);
        return returnString;

    }
    public String FlucLapTimeHelper(ArrayList<String> theArray){
        int index = 0;
        String returnString = "";
        int last_time = -1;
        while (index <= theArray.size() - 1){
            int currentTime = Integer.valueOf(theArray.get(index));
            if (last_time != -1){

                if (currentTime == last_time){
                    returnString += "= ";
                    last_time = currentTime;
                }
                else if (currentTime > last_time){
                    returnString += "+ ";
                    last_time = currentTime;
                }
                else if (currentTime < last_time){
                    returnString += "- ";
                    last_time = currentTime;
                }
            }
            else {
                last_time = currentTime;
            }
            index ++;
        }
        return returnString;
    }
    public double AvgLapTimeHelper(ArrayList<String> theArray){
        // [11, 9, 11, 4, 2]
        // The return value should be 7.4, but when I print double.toString() I get 7.0.
        // if I make this a Long it prints 7
        int index = 0;
        String returnString = "";
        double alltimes = 0;
        while (index <= theArray.size() - 1){
            alltimes += Long.valueOf(theArray.get(index));
            index ++;
        }
        return alltimes/theArray.size();

    }
    public String EachLapSpeedHelper(ArrayList<String> theArray){
        int index = 0;
        String returnString = "";
        while (index <= theArray.size() - 1){
            double CurrentTime = Double.parseDouble(theArray.get(index));
            double MinPerKm = (CurrentTime * laps_per_km)/60;
            String StringMinPerKm = String.valueOf(MinPerKm);
            if (StringMinPerKm.length() > 4){
                StringMinPerKm = String.valueOf(MinPerKm).substring(0, 4);
            }
            returnString += StringMinPerKm + " ";
            index ++;
        }
        return returnString;
    }




    }


