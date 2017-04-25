package com.example.android.laprunner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wordpress.frankhaolunliblog.laprunner.R;



public class MainActivity extends AppCompatActivity {
    TextView StartWorkout;
    TextView GoalLapsTextview;
    TextView LapsPerTextView;
    int GoalLapsInt;
    int LapsPerKM;
    boolean DebugMode = true;
    SharedPreferences pref;
    boolean PreviousPreferences = false;
    SharedPreferences.Editor editor;
    int SavedGoalLapsInt;
    int SavedLapsPerKM;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StartWorkout = (TextView) findViewById(R.id.StartWorkoutButton);
        GoalLapsTextview = (TextView) findViewById(R.id.GoalLapsInput);
        LapsPerTextView = (TextView) findViewById(R.id.LapsPerKMInputKM);

        // Check for past values
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        SavedGoalLapsInt = pref.getInt("GoalLapsInt", -1);
        SavedLapsPerKM = pref.getInt("LapsPerKM", -1);
        if (SavedGoalLapsInt != -1 && SavedLapsPerKM != -1){
            PreviousPreferences = true;
            if (PreviousPreferences && DebugMode){
                Log.d("PreviousPreferences", "True");
            }
            else if (DebugMode) {
                Log.d("PreviousPreferences", "False");
            }
            GoalLapsTextview.setText(Integer.toString(SavedGoalLapsInt));
            LapsPerTextView.setText(Integer.toString(SavedLapsPerKM));
        }

    }

    public void StartWorkout(View view) {


        // Save these values
        GoalLapsInt = Integer.parseInt(GoalLapsTextview.getText().toString());
        LapsPerKM = Integer.parseInt(LapsPerTextView.getText().toString());
        if (DebugMode) {
            Log.d("GoalLapsInt", Integer.toString(GoalLapsInt));
            Log.d("LapsPerKM", Integer.toString(LapsPerKM));
        }
        if ((GoalLapsTextview != null || !GoalLapsTextview.getText().equals(""))){
            editor.putInt("GoalLapsInt", GoalLapsInt);
            editor.apply();
        }

        if (LapsPerTextView == null || !LapsPerTextView.getText().equals("")){
            editor.putInt("LapsPerKM", LapsPerKM);
            editor.apply();
        }

        Intent intent = new Intent(this, com.example.android.laprunner.RunActivity.class);
        startActivity(intent);
    }
}
