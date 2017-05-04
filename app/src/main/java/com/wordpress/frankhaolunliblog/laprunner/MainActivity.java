package com.wordpress.frankhaolunliblog.laprunner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView StartWorkout;
    TextView GoalLapsTextview;
    TextView LapsPerTextView;
    TextView WeightInPoundsTextView;
    int GoalLapsInt;
    int LapsPerKM;
    double WeightInPounds;
    boolean DebugMode = true;
    SharedPreferences pref;
    boolean PreviousPreferences = false;
    SharedPreferences.Editor editor;
    int SavedGoalLapsInt;
    int SavedLapsPerKM;
    double SavedWeightInPoundsDouble;
    String PastInformation;
    String EmptyString;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StartWorkout = (TextView) findViewById(R.id.StartWorkoutButton);
        GoalLapsTextview = (TextView) findViewById(R.id.GoalLapsInput);
        LapsPerTextView = (TextView) findViewById(R.id.LapsPerKMInputKM);
        WeightInPoundsTextView = (TextView) findViewById(R.id.WeightInPounds);

        // Check for past values
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        SavedGoalLapsInt = pref.getInt("GoalLapsInt", -1);
        SavedLapsPerKM = pref.getInt("LapsPerKM", -1);
        SavedWeightInPoundsDouble = Double.valueOf(pref.getString("LapsPerKM", "-1"));

        EmptyString = "";
        PastInformation = pref.getString("PastInformation", EmptyString);

        // Display the previous information
        if (SavedGoalLapsInt != -1){
            GoalLapsTextview.setText(Integer.toString(SavedGoalLapsInt));
        }
        if (SavedLapsPerKM != -1){
            LapsPerTextView.setText(Integer.toString(SavedLapsPerKM));
        }
        if (SavedWeightInPoundsDouble != -1){
            WeightInPoundsTextView.setText(Double.toString(SavedWeightInPoundsDouble));
        }



    }

    public void StartWorkout(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        // Save these values
        GoalLapsInt = Integer.parseInt(GoalLapsTextview.getText().toString());
        LapsPerKM = Integer.parseInt(LapsPerTextView.getText().toString());
        WeightInPounds = Double.parseDouble(WeightInPoundsTextView.getText().toString());
        if (DebugMode) {
            Log.d("GoalLapsInt", Integer.toString(GoalLapsInt));
            Log.d("LapsPerKM", Integer.toString(LapsPerKM));
            Log.d("WeightInPounds", Double.toString(WeightInPounds));
        }
        if ((GoalLapsTextview != null || !GoalLapsTextview.getText().equals(""))){
            editor.putInt("GoalLapsInt", GoalLapsInt);
            editor.apply();
        }

        if (LapsPerTextView == null || !LapsPerTextView.getText().equals("")){
            editor.putInt("LapsPerKM", LapsPerKM);
            editor.apply();
        }

        if (WeightInPoundsTextView == null || !WeightInPoundsTextView.getText().equals("")){
            editor.putString("WeightInPounds", Double.toString(WeightInPounds));
            editor.apply();
        }

        Intent intent = new Intent(this, RunActivity.class);
        startActivity(intent);
    }
}
