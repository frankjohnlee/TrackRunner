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
    int SecondsCurrentTime;
    String PastInformation;
    String TempWorkoutInfo;   //|Start|Date|Time|GoalLapInt|LapsPerKM|LapBefore,LapAfter,Seconds,...
    ArrayList TimePerLapArray;
    String StringArray;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.ViewSetup();
        this.GetStoredValues();




    }
    public void CountIncrease (View view) {
        if (ChronometerStarted && !IsPaused) {
            counter++;
            this.UpdateCounter();
            this.UpdateLapsLeft();
            this.UpdateSecondsCurrentTime();
            this.UpdateTimeLeft();
            this.UpdateEstimatedFinishTime();
            this.AddLapSpeedToArray();
            SecondsPreviousLapTime = SecondsCurrentTime;

        }
    }
    public void CountDecrease (View view){
        if (ChronometerStarted && counter > 0 && !IsPaused) {
            counter--;
            this.UpdateCounter();
            this.ClearEstimateViews();
            this.DeleteLastRunTime();
        }
    }
    public void StartPauseResumeButton (View view){
        if (!ChronometerStarted){
            this.StartTimer();
        }
        else if (IsPaused && ChronometerStarted) {
            this.ResumeTimer();
        }
        else if (!IsPaused && ChronometerStarted) {
            this.PauseTimer();
        }

    }
    public void ResetButton (View view) {
        if (ChronometerStarted) {
            this.ResetEverything();
        }
    }
    public void FinishWorkoutButton (View view){
        if (ChronometerStarted) {
            this.LoadStartWorkoutString();
            this.AddLapSpeedToTempWorkout();
            this.StoreThisWorkout();
            this.ResetEverything();
            this.GoToFinishedWorkoutActivity();

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

    // Methods that Set Up
    public void ViewSetup (){
        showValue = (TextView) findViewById(R.id.CountValue);
        EstimatedTimeLeftTextView = (TextView) findViewById(R.id.EstimatedTimeLeftDisplay);
        LapsLeftTextView = (TextView) findViewById(R.id.LapsLeftDisplay);
        EstimatedTotalTimeTextView = (TextView) findViewById(R.id.EstimatedTotalTimeDisplay);
        SimpleChronometer = (Chronometer) findViewById(R.id.TimeValue); // initiate a
    }
    public void GetStoredValues (){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        GoalLapsInt = pref.getInt("GoalLapsInt", 0);
        LapsPerKM = pref.getInt("LapsPerKM", 0);
        PastInformation = pref.getString("PastInformation", "");
        TimePerLapArray = new ArrayList();
        StringArray = "";
    }


    // Reset Button
    public void ResetEverything (){
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


    // Start/Pause/Resume Button
    public void PauseTimer (){
        IsPaused = true;
        TimePaused = SimpleChronometer.getBase() - SystemClock.elapsedRealtime();
        SimpleChronometer.stop();
    }
    public void ResumeTimer (){
        SimpleChronometer.setBase(SystemClock.elapsedRealtime() + TimePaused);
        SimpleChronometer.start();
        IsPaused = false;
    }
    public void StartTimer (){
        this.ResetEverything();
        SimpleChronometer.start();
        ChronometerStarted = true;

    }

    // Count Increase Button
    public void UpdateCounter (){
        // Update Counter
         showValue.setText(Integer.toString(counter));
    }
    public void UpdateLapsLeft (){
        LapsLeft = GoalLapsInt - counter;
        LapsLeftTextView.setText(Integer.toString(LapsLeft));
    }
    public void UpdateSecondsCurrentTime(){
        long CurrentTime = SystemClock.elapsedRealtime() - SimpleChronometer.getBase();
        SecondsCurrentTime = (int) (CurrentTime / 1000);
    }
    public void UpdateTimeLeft (){
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

        EstimatedTimeLeftTextView.setText(hh + ":" + mm + ":" + ss);}

        }
    public void UpdateEstimatedFinishTime () {
        if (SecondsPreviousLapTime != -1) {
            long SecondsLastLapSpeed = SecondsCurrentTime - SecondsPreviousLapTime; // in seconds
            int SecondsEstimatedTimeLeft = (int) SecondsLastLapSpeed * LapsLeft; // in seconds
            int time = (SecondsCurrentTime + SecondsEstimatedTimeLeft) * 1000;

            // Display Estimated Time
            int h = (int) (time / 3600000);
            int m = (int) (time - h * 3600000) / 60000;
            int s = (int) (time - h * 3600000 - m * 60000) / 1000;
            String hh = h < 10 ? "0" + h : h + "";
            String mm = m < 10 ? "0" + m : m + "";
            String ss = s < 10 ? "0" + s : s + "";
            EstimatedTotalTimeTextView.setText(hh + ":" + mm + ":" + ss);
        }
    }
    public void AddLapSpeedToArray(){
        int SecondsLastLapSpeed = (int) (SecondsCurrentTime - SecondsPreviousLapTime);
        String thisLap = Integer.toString(counter) + "," + Integer.toString(SecondsLastLapSpeed);
        TimePerLapArray.add(thisLap);
    }

    // Count Decrease Button
    public void ClearEstimateViews (){
        EstimatedTimeLeftTextView.setText("");
        EstimatedTotalTimeTextView.setText("");
    }
    public void DeleteLastRunTime (){
        EstimatedTimeLeftTextView.setText("");
        EstimatedTotalTimeTextView.setText("");
        TimePerLapArray.remove(TimePerLapArray.size()- 1);
    }

    // Finish Workout Button
    public void LoadStartWorkoutString (){
        String date = new SimpleDateFormat("yyyy-MM-dd|hh:mm:ss").format(new Date());
        TempWorkoutInfo = "";
        TempWorkoutInfo +=
                "|Start|" + date +
                        "|" + Integer.toString(GoalLapsInt) +
                        "|" + Integer.toString(LapsPerKM) +
                        "|";

    }
    public void AddLapSpeedToTempWorkout (){
        int index = 0;
        String StringArray = "";
        while (index <= TimePerLapArray.size()-1){
            StringArray += TimePerLapArray.get(index);
            index ++;
            StringArray += ",";
        }
        TempWorkoutInfo += StringArray;
    }
    public void StoreThisWorkout (){
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
    }
    public void GoToFinishedWorkoutActivity(){
        Intent intent = new Intent(this, FinishWorkout.class);
        startActivity(intent);
    }

}





