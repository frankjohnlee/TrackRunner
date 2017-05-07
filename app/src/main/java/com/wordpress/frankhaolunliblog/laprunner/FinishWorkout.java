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
    boolean DebugMode = true;
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

    // Saved Values from MainActivity

    int GoalLapsInt;
    int LapsPerKM;
    int Weight;

    TextView CurrentWorkoutStatsTextView;

    // For Database
    Workout ThisWorkout;
    ArrayList EachTimeArray;
    String id;
    String date;
    String time_start;
    Integer laps_per_km;
    Integer total_laps;
    String StrWeightInPounds;

    // Time
    Integer total_time;
    String each_lap_time;
    String fluc_lap_time;
    double avg_lap_time;

    // TimePerKM
    String each_lap_TimePerKM;
    String fluc_lap_TimePerKM;
    Double avg_TimePerKM;

    // MetersPerSecond
    String each_lap_MetersPerSecond;
    String fluc_lap_MetersPerSecond;
    Double avg_MetersPerSecond;

    // Calories
    ArrayList CalorieChartMetersPerKm;
    ArrayList CalorieChartMETS;
    String each_lap_calories;
    String fluc_lap_calories;
    Double total_calories;
    Double avg_calories;

    // Steps
    String each_lap_steps;
    String fluc_lap_steps;
    Double total_steps;
    Double avg_steps;


    //|Start|Date|Time|GoalLap|LapsPerKM|WeightInPoundsDouble|LapNumber,Seconds, ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.GetValueMyPref();
        EachTimeArray = new ArrayList();
        CurrentWorkoutStatsTextView = (TextView) findViewById(R.id.CurrentWorkoutStats);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        String ThisWorkoutString = pref.getString("ThisWorkout", "");
        if (DebugMode){
            ThisWorkoutString = "|Start|2017-05-02|12:43:14|70|7|160|1,11,2,9,3,11,4,4,5,2,";
        }

        this.ExtraFromString(ThisWorkoutString);
        this.SetCurrentWorkoutStatsTextView();
        this.GetLapValues();
        this.DataIntoDatabase();

        CurrentWorkoutStatsTextView.setText(StrCurrentWorkoutStats);




    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void ExtraFromString (String ThisWorkout){
        Boolean DebugExtraFromString = false;
        String tabs = "     ";
        if (DebugExtraFromString){
            Log.d("Current Method", "GetValueMyPref");
        }

        int IndicatorCount = 0;
        int counter = 0;

        //|Start|Date|Time|GoalLap|LapsPerKM|Weight|StatsPerLap[[LapBefore, LapAfter, Seconds], ...]
        // 1    2     3   4       5         6       7

        while (counter <= ThisWorkout.length() - 1){

            String CurrentString = ThisWorkout.charAt(counter) + "";
            if (DebugExtraFromString){
                Log.d(tabs + "While Loop Count: ", Integer.toString(counter));
                Log.d(tabs + "CurrentString: ", CurrentString);
            };

            if (CurrentString.equals("|")){
                IndicatorCount ++;
                if (DebugExtraFromString){
                    Log.d(tabs + tabs+ "CurrentString.equals(|): ", "IndicatorCount ++;");
                };

            }
            else if (IndicatorCount == 2){
                StrDate += CurrentString;
                if (DebugExtraFromString){
                    Log.d(tabs + tabs+ "StrDate", StrDate);
                };

            }
            else if (IndicatorCount == 3){
                StrTime += CurrentString;
                if (DebugExtraFromString){
                    Log.d(tabs + tabs+ "StrTime", StrTime);
                };

            }
            else if (IndicatorCount == 4){
                StrGoalLap += CurrentString;
                if (DebugExtraFromString){
                    Log.d(tabs + tabs+ "StrGoalLap", StrGoalLap);
                };

            }
            else if (IndicatorCount == 5){
                StrLapsPerKM += CurrentString;
                if (DebugExtraFromString){
                    Log.d(tabs + tabs+ "StrLapsPerKM", StrLapsPerKM);
                };

            }
            else if (IndicatorCount == 6){
                StrWeightInPounds = Integer.toString(Weight);
                if (DebugExtraFromString){
                    Log.d(tabs + tabs+ "StrWeightInPounds", StrWeightInPounds);
                };

            }
            else if (IndicatorCount == 7){
                StrStatsPerLap += CurrentString;
                if (DebugExtraFromString){
                    Log.d(tabs + tabs+ "StrStatsPerLap", StrStatsPerLap);
                };

            }

            counter ++;
        }


    }
    public void SetCurrentWorkoutStatsTextView(){
        StrCurrentWorkoutStats += "Date: " + StrDate + "\n";
        StrCurrentWorkoutStats += "Start Time: " + StrTime + "\n";
        StrCurrentWorkoutStats += "Goal: " + StrGoalLap + " laps" + "\n";
        StrCurrentWorkoutStats += "Track Size: " + StrLapsPerKM + " Laps per KM" + "\n";
        StrCurrentWorkoutStats += "Weight: " + StrWeightInPounds + " lbs" + "\n";
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
    public void GetValueMyPref() {
        String tabs = "     ";
        Log.d("Current Method", "GetValueMyPref");
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        String InputValuesStr = pref.getString("InputValues", "");
        String[] InputValuesArray = InputValuesStr.split(" ");
        int index = 0;
        for (String s : InputValuesArray) {
            Log.d(tabs + "s String", s);
            if (index == 0) {
                GoalLapsInt = Integer.valueOf(s);
            } else if (index == 1) {
                LapsPerKM = Integer.valueOf(s);
            } else if (index == 2) {
                Weight = Integer.valueOf(s);
            }
            index ++;
        }
    }


    public void DataIntoDatabase(){
        EachTimeArray = new ArrayList();
        this.EachTimeArrayHelper();

        // "|Start|2017-05-02|12:43:14|70|7|160.0|1,11,2,9,3,11,4,4,5,2,";

        // Get all the values I want
        id = StrDate + ", " + StrTime;

        // General Info
        date = StrDate; // 2017-05-02
        time_start = StrTime; // 12:43:14
        laps_per_km = Integer.valueOf(StrLapsPerKM); // 7
        total_laps = EachTimeArray.size(); // 5
        total_time = TotalTime; // 37

        // Time for each lap
        each_lap_time = this.EachLapTimeHelper(EachTimeArray); // 11  9  11  4  2
        fluc_lap_time = this.FlucLapTimeHelper(EachTimeArray); //   -   +   -  -
        avg_lap_time = this.AvgLapTimeHelper(EachTimeArray); // 7.4

        // min/km
        each_lap_TimePerKM = this.EachLapTimePerKMHelper(EachTimeArray); // 1.28 1.05 1.28 0.46 0.23
        fluc_lap_TimePerKM = this.FlucLapTimePerKMHelper(each_lap_TimePerKM); //    -    +    -    -
        avg_TimePerKM = this.AvgTimePerKMHelper(each_lap_TimePerKM); // 0.86

        // m/s (meters per second)
        each_lap_MetersPerSecond = this.EachLapMetersPerSecondHelper(EachTimeArray); // 12.9  15.8   12.9   35.7   71.4
        fluc_lap_MetersPerSecond = this.FlucGeneralHelper(each_lap_MetersPerSecond); //      -     -      +      +
        avg_MetersPerSecond = this.AvgGeneralHelper(each_lap_MetersPerSecond); // 29.740000000000002



        this.SetCaloriesMETS();



        if (DebugMode) {
            Log.d("id", id);
            Log.d("date", date);
            Log.d("time_start", time_start);
            Log.d("laps_per_km ", Integer.toString(laps_per_km));
            Log.d("total_laps", Integer.toString(total_laps));
            Log.d("total_time", Integer.toString(total_time));
            Log.d("each_lap_time", each_lap_time);
            Log.d("fluc_lap_time", fluc_lap_time);
            Log.d("avg_lap_time", Double.toString(avg_lap_time));
            Log.d("each_lap_TimePerKM", each_lap_TimePerKM);
            Log.d("fluc_lap_TimePerKM", fluc_lap_TimePerKM);
            Log.d("avg_TimePerKM", Double.toString(avg_TimePerKM));
            Log.d("MPerSecond", each_lap_MetersPerSecond);
            Log.d("fluc_lap_m/s", fluc_lap_MetersPerSecond);
            Log.d("avg_MetersPerSecond", Double.toString(avg_MetersPerSecond));
        }


        // Store values inside Workout variable
        ThisWorkout = new Workout(id);
        ThisWorkout.setId(id);
        ThisWorkout.setDate(date);
        ThisWorkout.setTime_start(time_start);
        ThisWorkout.setLaps_per_km(laps_per_km);
        ThisWorkout.setTotal_laps(total_laps);
        ThisWorkout.setTotal_time(TotalTime);
        ThisWorkout.setEach_lap_time(each_lap_time);
        ThisWorkout.setFluc_lap_time(fluc_lap_time);
        ThisWorkout.setAvg_lap_time(avg_lap_time);
        ThisWorkout.setEach_lap_TimePerKM(each_lap_TimePerKM);
        ThisWorkout.setFluc_lap_TimePerKM(fluc_lap_TimePerKM);
        ThisWorkout.setAvg_TimePerKM(avg_TimePerKM);
        ThisWorkout.setEach_lap_MetersPerSecond(each_lap_MetersPerSecond);
        ThisWorkout.setFluc_lap_MetersPerSecond(fluc_lap_MetersPerSecond);
        ThisWorkout.setAvg_MetersPerSecond(avg_MetersPerSecond);


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
    public String EachLapTimePerKMHelper(ArrayList<String> theArray){
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
    public String FlucLapTimePerKMHelper(String theString){
        /* String -> String
         * Method takes a string of lap TimePerKMs, in min/sec and outputs a string of +, -, = which tells of fluctuations.
         */
        String[] theStringArray = theString.split(" ");
        String returnString = "";
        double LastTimePerKM = -999999;
        for (String TimePerKM: theStringArray){
            double TimePerKMDouble = Double.valueOf(TimePerKM);
            if (LastTimePerKM != -999999 && LastTimePerKM < TimePerKMDouble){
                returnString += "+ ";
            }
            else if (LastTimePerKM != -999999 && LastTimePerKM > TimePerKMDouble){
                returnString += "- ";
            }
            else if (LastTimePerKM != -999999 && LastTimePerKM > TimePerKMDouble){
                returnString += "= ";
            }
            LastTimePerKM = TimePerKMDouble;
        }
        return returnString;

    }
    public double AvgTimePerKMHelper(String theString){
        String[] theStringArray = theString.split(" ");
        double returnDouble = 0;
        double TimePerKMLength = 0;

        for (String TimePerKM: theStringArray){

            double TimePerKMDouble = Double.valueOf(TimePerKM);
            returnDouble += TimePerKMDouble;
            TimePerKMLength ++;
        }
        String StringReturnDouble = Double.toString(returnDouble/TimePerKMLength);

        if (Double.toString(returnDouble/TimePerKMLength).length() > 4){
            StringReturnDouble = StringReturnDouble.substring(0, 4);
        }

        return Double.valueOf(StringReturnDouble);
    }
    public String EachLapMetersPerSecondHelper(ArrayList<String> theArray) {
        String ReturnString = "";
        for (String time: theArray){
            double SecondsDifference = Double.valueOf(time);
            double MetersPerLap = ((double) 1 / (double) laps_per_km) * 1000;
            double MetersPerSecond = MetersPerLap / SecondsDifference;
            String StringMetersPerSecond = Double.toString(MetersPerSecond);
            if (StringMetersPerSecond.length() > 4){
                StringMetersPerSecond = String.valueOf(StringMetersPerSecond).substring(0, 4);
            }
            ReturnString += StringMetersPerSecond + " ";
        }
        return ReturnString;
    }
    public String FlucGeneralHelper(String theString){
        /* String -> String
         * Method takes a string of values each seperated by a space and outputs a string of +, -, = which tells of fluctuations.
         */
        String[] theStringArray = theString.split(" ");
        String returnString = "";
        double PreviousValue = -999999;
        for (String TimePerKM: theStringArray){
            double CurrentValue = Double.valueOf(TimePerKM);
            if (PreviousValue != -999999 && PreviousValue < CurrentValue){
                returnString += "+ ";
            }
            else if (PreviousValue != -999999 && PreviousValue > CurrentValue){
                returnString += "- ";
            }
            else if (PreviousValue != -999999 && PreviousValue > CurrentValue){
                returnString += "= ";
            }
            PreviousValue = CurrentValue;
        }
        return returnString;

    }
    public double AvgGeneralHelper(String theString){
        String[] theStringArray = theString.split(" ");
        double count = 0;
        double sum = 0;
        for (String s: theStringArray){
            double CurrentValue = Double.valueOf(s);
            sum += CurrentValue;
            count ++;
        }
        return sum / count;

    }
    public void SetCaloriesMETS(){
        CalorieChartMetersPerKm = new ArrayList();
        CalorieChartMetersPerKm.add(8.078);
        CalorieChartMetersPerKm.add(7.456);
        CalorieChartMetersPerKm.add(7.146);
        CalorieChartMetersPerKm.add(6.214);
        CalorieChartMetersPerKm.add(5.592);
        CalorieChartMetersPerKm.add(5.28);
        CalorieChartMetersPerKm.add(4.971);
        CalorieChartMetersPerKm.add(4.66);
        CalorieChartMetersPerKm.add(4.35);
        CalorieChartMetersPerKm.add(4.04);
        CalorieChartMetersPerKm.add(3.728);
        CalorieChartMetersPerKm.add(3.42);
        CalorieChartMetersPerKm.add(3.107);
        CalorieChartMetersPerKm.add(2.86);
        CalorieChartMetersPerKm.add(2.67);

        CalorieChartMETS = new ArrayList();
        CalorieChartMETS.add(6.0);
        CalorieChartMETS.add(8.3);
        CalorieChartMETS.add(9.0);
        CalorieChartMETS.add(9.8);
        CalorieChartMETS.add(10.5);
        CalorieChartMETS.add(11.0);
        CalorieChartMETS.add(11.5);
        CalorieChartMETS.add(11.8);
        CalorieChartMETS.add(12.3);
        CalorieChartMETS.add(12.8);
        CalorieChartMETS.add(14.5);
        CalorieChartMETS.add(16.0);
        CalorieChartMETS.add(19.0);
        CalorieChartMETS.add(19.8);
        CalorieChartMETS.add(23.0);



    }



    }


