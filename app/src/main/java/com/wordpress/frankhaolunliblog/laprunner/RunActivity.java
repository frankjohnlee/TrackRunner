package com.wordpress.frankhaolunliblog.laprunner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    Button StartPauseResumeButton;
    TextView LastLapTimeTextview;
    TextView LastLapSpeedTextview;
    int LapsLeft;
    int counter = -1;
    long SecondsPreviousLapTime = -1;
    int GoalLapsInt;
    int LapsPerKM;
    boolean IsPaused = false;
    long TimePaused = 0;
    int SecondsCurrentTime;
    String PastInformation;
    String PastTotalTimes;
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
        SimpleChronometer.setText("00:00:00");
        SimpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                // Display Estimated Time
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String hh = h < 10 ? "0" + h : h + "";
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                cArg.setText(hh + ":" + mm + ":" + ss);
            }
        });
    }
    public void CountIncrease (View view) {
        if (ChronometerStarted && !IsPaused) {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            counter++;
            this.UpdateCounter();
            this.UpdateLapsLeft();
            this.UpdateSecondsCurrentTime();
            this.UpdateLastLapSpeed();
            this.UpdateTimeLeft();
            this.UpdateEstimatedFinishTime();
            this.AddLapSpeedToArray();
            this.UpdateLastLapTimeSeconds();
            SecondsPreviousLapTime = SecondsCurrentTime;

        }
    }
    public void CountDecrease (View view){
        if (ChronometerStarted && counter > 0 && !IsPaused) {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            counter--;
            this.UpdateCounter();
            this.ClearEstimateViews();
            this.DeleteLastRunTime();
        }
    }
    public void StartPauseResumeButton (View view){
        if (!ChronometerStarted){
            StartPauseResumeButton.setText("PAUSE");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            this.StartTimer();
        }
        else if (IsPaused && ChronometerStarted) {
            StartPauseResumeButton.setText("PAUSE");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            this.ResumeTimer();
        }
        else if (!IsPaused && ChronometerStarted) {
            StartPauseResumeButton.setText("RESUME");
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            this.PauseTimer();
        }

    }
    public void ResetButton (View view) {
        if (ChronometerStarted) {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            this.AlertConfirmMessageReset("Confirm Reset", "Please confirm that you would like to reset all workout values.", this, view);
        }
    }
    public void FinishWorkoutButton (View view){
        if (ChronometerStarted) {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            this.AlertConfirmMessageFinishWorkout("Confirm Finish Workout", "Please confirm that that you would like to complete workout.", this, view);
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
        StartPauseResumeButton = (Button) findViewById(R.id.StartPauseResumeButton);
        LastLapTimeTextview = (TextView) findViewById(R.id.PreviousLapTime);
        LastLapSpeedTextview = (TextView) findViewById(R.id.PreviousLapSpeed);
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
        showValue.setText("00");
        SimpleChronometer.stop();
        SimpleChronometer.setBase(SystemClock.elapsedRealtime());
        SimpleChronometer.setText("00:00:00");
        LapsLeft = GoalLapsInt;
        LapsLeftTextView.setText(Integer.toString(GoalLapsInt));
        ChronometerStarted = false;
        EstimatedTimeLeftTextView.setText("");
        EstimatedTotalTimeTextView.setText("");
        TempWorkoutInfo = "";
        TimePerLapArray = new ArrayList();
        StringArray = "";
        LastLapSpeedTextview.setText("00");
        LastLapTimeTextview.setText("00");
        SecondsPreviousLapTime = -1;

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
        String CounterString = "";
        if (Integer.toString(counter).length() < 2) {
            CounterString += "0";
        }

        // Update Counter
         showValue.setText(CounterString + Integer.toString(counter));
    }
    public void UpdateLapsLeft (){
        LapsLeft = GoalLapsInt - counter;
        LapsLeftTextView.setText(Integer.toString(LapsLeft));
    }

    public void UpdateLastLapTimeSeconds (){
        if (SecondsPreviousLapTime != -1) {
            int SecondsDifference = (int) (SecondsCurrentTime - SecondsPreviousLapTime);
            String CounterString = "";
            if (Integer.toString(SecondsDifference).length() < 2) {
                CounterString += "0";
            }
            LastLapTimeTextview.setText(CounterString + Integer.toString(SecondsDifference) + "  Secs");
        }
    }
    public void UpdateLastLapSpeed (){
        if (SecondsPreviousLapTime != -1) {
            double SecondsDifference = (double) (SecondsCurrentTime - SecondsPreviousLapTime);

            double MinutesPerKM = (SecondsDifference * LapsPerKM)/60;
            String CounterString = "";
            if (Double.toString(MinutesPerKM).length() < 2) {
                CounterString += "0";
            }
            String StringMinPerKm = Double.toString(MinutesPerKM);
            if (StringMinPerKm.length() > 4){
                StringMinPerKm = StringMinPerKm.substring(0, 4);
            }
            LastLapSpeedTextview.setText(CounterString + StringMinPerKm + "  Min/KM");
        }
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

        if (    (h >= 0) &&
                (m >= 0) &&
                (s >= 0)){
            EstimatedTimeLeftTextView.setText(hh + ":" + mm + ":" + ss);}
        }



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
            if (    (h >= 0) &&
                    (m >= 0) &&
                    (s >= 0)){
                EstimatedTotalTimeTextView.setText(hh + ":" + mm + ":" + ss);}
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


    // Confirm Messages
    public void AlertConfirmMessageReset (String Title, String Message, Context context, final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                            this.ResetEverything();
                    }
                    public void ResetEverything (){
                        IsPaused = false;
                        TimePaused = 0;
                        counter = 0;
                        showValue.setText("00");
                        SimpleChronometer.stop();
                        SimpleChronometer.setBase(SystemClock.elapsedRealtime());
                        SimpleChronometer.setText("00:00:00");
                        LapsLeft = GoalLapsInt;
                        LapsLeftTextView.setText(Integer.toString(GoalLapsInt));
                        ChronometerStarted = false;
                        EstimatedTimeLeftTextView.setText("");
                        EstimatedTotalTimeTextView.setText("");
                        TempWorkoutInfo = "";
                        TimePerLapArray = new ArrayList();
                        StringArray = "";
                        StartPauseResumeButton.setText("START");
                        LastLapSpeedTextview.setText("00");
                        LastLapTimeTextview.setText("00");
                        SecondsPreviousLapTime = -1;

                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void AlertConfirmMessageFinishWorkout (String Title, String Message, final Context context, final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        this.FindBreakPoint("1");
                        this.LoadStartWorkoutString();
                        this.FindBreakPoint("2");
                        this.AddLapSpeedToTempWorkout();
                        this.FindBreakPoint("3");
                        this.StoreThisWorkout();
                        this.FindBreakPoint("4");
                        this.ResetEverything();
                        this.FindBreakPoint("5");
                        this.GoToFinishedWorkoutActivity();
                        this.FindBreakPoint("6");
                    }
                    public void ResetEverything (){
                        IsPaused = false;
                        TimePaused = 0;
                        counter = 0;
                        showValue.setText("00");
                        SimpleChronometer.stop();
                        SimpleChronometer.setBase(SystemClock.elapsedRealtime());
                        SimpleChronometer.setText("00:00:00");
                        LapsLeft = GoalLapsInt;
                        LapsLeftTextView.setText(Integer.toString(GoalLapsInt));
                        ChronometerStarted = false;
                        EstimatedTimeLeftTextView.setText("");
                        EstimatedTotalTimeTextView.setText("");
                        TempWorkoutInfo = "";
                        TimePerLapArray = new ArrayList();
                        StringArray = "";
                        StartPauseResumeButton.setText("START");
                        LastLapSpeedTextview.setText("00");
                        LastLapTimeTextview.setText("00");
                        SecondsPreviousLapTime = -1;

                    }
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
                        Intent intent = new Intent(context, FinishWorkout.class);
                        startActivity(intent);
                    }
                    public void FindBreakPoint(String string){
                        if (DebugMode){
                            Log.d("Find Break Point", string);
                        }
                    }
                });


        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Get Data and Put Them Into A Table


}





