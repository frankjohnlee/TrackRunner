package com.wordpress.frankhaolunliblog.laprunner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Keep;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RunActivity extends AppCompatActivity {

    boolean DebugMode = true;
    boolean ChronometerStarted = false;
    TextView showValue;
    TextView EstimatedTimeLeftTextView;
    TextView LapsLeftTextView;
    Chronometer SimpleChronometer;
    TextView EstimatedTotalTimeTextView;
    int LapsLeft;
    int counter = -1;
    long SecondsPreviousLapTime = -1;
    int GoalLapsInt;
    int LapsPerKM;
    boolean IsPaused = false;
    long TimePaused = 0;
    String PastInformation;
    String TempWorkoutInfo;
    ArrayList TimePerLapArray;
    String StringArray;
    SharedPreferences.Editor editor;

    //|Start|Date|Time|GoalLapInt|LapsPerKM|LapBefore,LapAfter,Seconds,...|SecondsTotalTime





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showValue = (TextView) findViewById(R.id.CountValue);
        EstimatedTimeLeftTextView = (TextView) findViewById(R.id.EstimatedTimeLeftDisplay);
        LapsLeftTextView = (TextView) findViewById(R.id.LapsLeftDisplay);
        EstimatedTotalTimeTextView = (TextView) findViewById(R.id.EstimatedTotalTimeDisplay);
        SimpleChronometer = (Chronometer) findViewById(R.id.TimeValue); // initiate a

        // Get Shared Preferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        GoalLapsInt = pref.getInt("GoalLapsInt", 0);
        LapsPerKM = pref.getInt("LapsPerKM", 0);
        PastInformation = pref.getString("PastInformation", "");
        TimePerLapArray = new ArrayList();
        StringArray = "";



    }
    public void CountIncrease (View view) {

        if (ChronometerStarted && !IsPaused) {
            // On each click this button will increase
            counter++;
            showValue.setText(Integer.toString(counter));
            // Display Laps Left
            LapsLeft = GoalLapsInt - counter;
            LapsLeftTextView.setText(Integer.toString(LapsLeft));

            if (DebugMode) {
                Log.d("DEBUG_LapsLeft", Integer.toString(LapsLeft));
                Log.d("DEBUG_GoalLapsInt", Integer.toString(GoalLapsInt));
                Log.d("LapsLeft", Integer.toString(LapsLeft));
            }


            long CurrentTime = SystemClock.elapsedRealtime() - SimpleChronometer.getBase();
            int SecondsCurrentTime = (int) (CurrentTime / 1000);
            long MinutesCurrentTime = (CurrentTime / 1000) / 60;

            if (DebugMode) {
                Log.d("DEBUG_CurrentTime", Long.toString(CurrentTime));
                Log.d("SecondsCurrentTime", Long.toString(SecondsCurrentTime));
                Log.d("MinutesCurrentTime", Long.toString(MinutesCurrentTime));
            }

            if (SecondsPreviousLapTime != -1) {
                long SecondsLastLapSpeed = SecondsCurrentTime - SecondsPreviousLapTime; // in seconds
                int SecondsEstimatedTimeLeft = (int) SecondsLastLapSpeed * LapsLeft; // in seconds
                int time = SecondsEstimatedTimeLeft * 1000;

                // Display Estimated Time
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String hh = h < 10 ? "0" + h : h + "";
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";

                if (DebugMode) {
                    Log.d("SecondsLastLapSpeed", Long.toString(SecondsLastLapSpeed));
                    Log.d("SecondsEstimatedT", Integer.toString(SecondsEstimatedTimeLeft));
                }
                EstimatedTimeLeftTextView.setText(hh + ":" + mm + ":" + ss);

                // Display Estimated Total Time
                time = (SecondsCurrentTime + SecondsEstimatedTimeLeft) * 1000;

                // Display Estimated Time
                h = (int) (time / 3600000);
                m = (int) (time - h * 3600000) / 60000;
                s = (int) (time - h * 3600000 - m * 60000) / 1000;
                hh = h < 10 ? "0" + h : h + "";
                mm = m < 10 ? "0" + m : m + "";
                ss = s < 10 ? "0" + s : s + "";
                EstimatedTotalTimeTextView.setText(hh + ":" + mm + ":" + ss);

            }
            int SecondsLastLapSpeed = (int) (SecondsCurrentTime - SecondsPreviousLapTime);
            String thisLap = Integer.toString(counter) + "," + Integer.toString(SecondsLastLapSpeed);
            TimePerLapArray.add(thisLap);
            if (DebugMode) {
                int index = 0;
                StringArray = "";
                while (index <= TimePerLapArray.size() - 1) {
                    StringArray += TimePerLapArray.get(index);
                    index++;
                    StringArray += ",";

                    Log.d("DebugountIncreaseArray", StringArray);
                }
            }
            SecondsPreviousLapTime = SecondsCurrentTime;

        }
    }
    public void CountDecrease (View view){
        if (ChronometerStarted && counter > 0 && !IsPaused) {
            // On each click this button will increase
            counter--;
            showValue.setText(Integer.toString(counter));
            EstimatedTimeLeftTextView.setText("");
            EstimatedTotalTimeTextView.setText("");
            TimePerLapArray.remove(TimePerLapArray.size()- 1);
        }
    }
    public void StartPauseResumeButton (View view){
        if (!ChronometerStarted){
            SimpleChronometer.start();
            ChronometerStarted = true;
            String date = new SimpleDateFormat("yyyy-MM-dd|hh:mm:ss").format(new Date());
            TempWorkoutInfo = "";
            TempWorkoutInfo +=
                    "|Start|" + date +
                    "|" + Integer.toString(GoalLapsInt) +
                    "|" + Integer.toString(LapsPerKM) +
                    "|";
        }
        else if (IsPaused && ChronometerStarted) {
            SimpleChronometer.setBase(SystemClock.elapsedRealtime() + TimePaused);
            SimpleChronometer.start();
            IsPaused = false;
        }
        else if (!IsPaused && ChronometerStarted) {
            IsPaused = true;
            TimePaused = SimpleChronometer.getBase() - SystemClock.elapsedRealtime();
            SimpleChronometer.stop();
        }

    }
    public void ResetButton (View view) {
        if (ChronometerStarted) {
            // On each click this button will increase
            IsPaused = false;
            TimePaused = 0;
            counter = 0;
            showValue.setText(Integer.toString(counter));
            SimpleChronometer.stop();
            SimpleChronometer.setBase(SystemClock.elapsedRealtime());
            LapsLeft = GoalLapsInt;
            LapsLeftTextView.setText(Integer.toString(GoalLapsInt));
            ChronometerStarted = false;
            EstimatedTimeLeftTextView.setText("");
            EstimatedTotalTimeTextView.setText("");

        }
    }
    public void FinishWorkoutButton (View view){
        if (ChronometerStarted) {
            int index = 0;
            StringArray = "";
            while (index <= TimePerLapArray.size()-1){
                StringArray += TimePerLapArray.get(index);
                index ++;
                StringArray += ",";
        }
            StringArray = StringArray.substring(0, StringArray.length()-2);
            TempWorkoutInfo += StringArray;
            SimpleChronometer.stop();

            // Save workout in storage
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            editor = pref.edit();
            PastInformation = pref.getString("PastInformation", "");
            PastInformation += TempWorkoutInfo;
            editor.putString("PastInformation", PastInformation);
            editor.apply();

            //Keep this particular workout
            editor.putString("ThisWorkout", TempWorkoutInfo);
            editor.apply();
            Intent intent = new Intent(this, FinishWorkout.class);
            startActivity(intent);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
