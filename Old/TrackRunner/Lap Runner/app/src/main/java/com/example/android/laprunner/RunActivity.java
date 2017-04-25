package com.example.android.laprunner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import java.util.ArrayList;

public class RunActivity extends MainActivity {
    boolean DebugMode = true;
    boolean ChronometerStarted = false;
    TextView showValue;
    TextView EstimatedTimeLeftTextView;
    TextView LapsLeftTextView;
    Chronometer SimpleChronometer;
    TextView EstimatedTotalTimeTextView;
    ArrayList TimePerLapArray;
    int LapsLeft;
    int counter = -1;
    long SecondsPreviousLapTime = -1;
    int GoalLapsInt;
    int LapsPerKM;
    boolean IsPaused = false;
    long TimePaused = 0;


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
    }


    public void CountIncrease (View view){

        if (ChronometerStarted){
        // On each click this button will increase
            counter ++;
            showValue.setText(Integer.toString(counter));
            // Display Laps Left
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            GoalLapsInt = pref.getInt("GoalLapsInt", 0);
            LapsPerKM = pref.getInt("LapsPerKM", 0);

            LapsLeft = GoalLapsInt - counter;
            LapsLeftTextView.setText(Integer.toString(LapsLeft));

            if (DebugMode){
                Log.d("DEBUG_LapsLeft", Integer.toString(LapsLeft));
                Log.d("DEBUG_GoalLapsInt", Integer.toString(GoalLapsInt));
                Log.d("LapsLeft", Integer.toString(LapsLeft));
            }


            long CurrentTime = SystemClock.elapsedRealtime() - SimpleChronometer.getBase();
            int SecondsCurrentTime = (int) (CurrentTime / 1000);
            long MinutesCurrentTime = (CurrentTime / 1000) / 60;

            if (DebugMode){
                Log.d("DEBUG_CurrentTime", Long.toString(CurrentTime));
                Log.d("SecondsCurrentTime", Long.toString(SecondsCurrentTime));
                Log.d("MinutesCurrentTime", Long.toString(MinutesCurrentTime));
            }

            if (SecondsPreviousLapTime != -1){
                long SecondsLastLapSpeed = SecondsCurrentTime - SecondsPreviousLapTime; // in seconds
                int SecondsEstimatedTimeLeft = (int) SecondsLastLapSpeed * LapsLeft; // in seconds
                int time = SecondsEstimatedTimeLeft * 1000;

                // Display Estimated Time
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";

                if (DebugMode){
                    Log.d("SecondsLastLapSpeed", Long.toString(SecondsLastLapSpeed));
                    Log.d("SecondsEstimatedT", Integer.toString(SecondsEstimatedTimeLeft));
                }
                EstimatedTimeLeftTextView.setText(hh+":"+mm+":"+ss);

                // Display Estimated Total Time
                time = (SecondsCurrentTime + SecondsEstimatedTimeLeft)*1000;

                // Display Estimated Time
                h   = (int)(time /3600000);
                m = (int)(time - h*3600000)/60000;
                s= (int)(time - h*3600000- m*60000)/1000 ;
                hh = h < 10 ? "0"+h: h+"";
                mm = m < 10 ? "0"+m: m+"";
                ss = s < 10 ? "0"+s: s+"";
                EstimatedTotalTimeTextView.setText(hh+":"+mm+":"+ss);

            }
            SecondsPreviousLapTime = SecondsCurrentTime;

        }



    }
    public void CountDecrease (View view){
        if (ChronometerStarted && counter > 0) {
            // On each click this button will increase
            counter--;
            showValue.setText(Integer.toString(counter));
            EstimatedTimeLeftTextView.setText("");
            EstimatedTotalTimeTextView.setText("");
        }
    }
    public void PauseButton (View view){
        if (!ChronometerStarted){
            SimpleChronometer.start();
            ChronometerStarted = true;
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

    public void ResetButton (View view){
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
