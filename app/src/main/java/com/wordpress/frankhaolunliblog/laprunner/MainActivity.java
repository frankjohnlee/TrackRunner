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

import java.util.ArrayList;

import static com.wordpress.frankhaolunliblog.laprunner.R.id.WeightInPounds;

public class MainActivity extends AppCompatActivity {

    TextView StartWorkout;
    TextView GoalLapsTextview;
    TextView LapsPerTextView;
    TextView WeightInPoundsTextView;
    int GoalLapsInt;
    int LapsPerKM;
    int Weight;
    boolean DebugMode = true;
    SharedPreferences pref;
    boolean PreviousPreferences = false;
    SharedPreferences.Editor editor;
    int SavedGoalLapsInt;
    int SavedLapsPerKM;
    int SavedWeight;
    String PastInformation;
    String EmptyString;
    ArrayList<String> Values;
    ArrayList<TextView> TextViews;
    String tabs = "    ";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Values = new ArrayList();
        Values.add("GoalLapsInt");
        Values.add("LapsPerKM");
        Values.add("Weight");

        TextViews = new ArrayList<TextView>();
        TextViews.add(GoalLapsTextview);
        TextViews.add(LapsPerTextView);
        TextViews.add(WeightInPoundsTextView);

        this.SetUp();


        // Check for past values
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        this.GetValueMyPref();

        EmptyString = "";
        PastInformation = pref.getString("PastInformation", EmptyString);

        // Display the previous information
        this.SetToView(SavedGoalLapsInt, GoalLapsTextview);
        this.SetToView(SavedLapsPerKM, LapsPerTextView);
        this.SetToView(SavedWeight, WeightInPoundsTextView);

    }

    public void StartWorkout(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        ArrayList<String> Values = new ArrayList();
        Values.add("GoalLapsInt");
        Values.add("LapsPerKM");
        Values.add("Weight");

        ArrayList<TextView> TextViews = new ArrayList<TextView>();
        TextViews.add(GoalLapsTextview);
        TextViews.add(LapsPerTextView);
        TextViews.add(WeightInPoundsTextView);

        this.SaveValue(Values, TextViews, "MyPref");

        Intent intent = new Intent(this, RunActivity.class);
        startActivity(intent);
    }
    public void GetValueMyPref() {
        Log.d("Current Method", "GetValueMyPref");
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        String InputValuesStr = pref.getString("InputValues", "");
        String[] InputValuesArray = InputValuesStr.split(" ");
        int index = 0;
        for (String s : InputValuesArray) {
            Log.d(tabs + "s String", s);
            if (index == 0) {
                SavedGoalLapsInt = Integer.valueOf(s);
            } else if (index == 1) {
                SavedLapsPerKM = Integer.valueOf(s);
            } else if (index == 2) {
                SavedWeight = Integer.valueOf(s);
            }
            index ++;
        }
    }
    public void SetUp(){
        StartWorkout = (TextView) findViewById(R.id.StartWorkoutButton);
        GoalLapsTextview = (TextView) findViewById(R.id.GoalLapsInput);
        LapsPerTextView = (TextView) findViewById(R.id.LapsPerKMInputKM);
        WeightInPoundsTextView = (TextView) findViewById(WeightInPounds);
    }
    public void SetToView(int number, TextView theTextView){
        if (number != -1){
            theTextView.setText(Integer.toString(number));
        }
    }
    public void SaveValue ( ArrayList<String> Values, ArrayList<TextView> TextViews, String SavedName){
        String SaveString = "";
        int index = 0;
        while (index <= TextViews.size()-1){
            String CurrentString = Values.get(index);
            TextView CurrentView = TextViews.get(index);
        if ((CurrentView != null || !CurrentView.getText().equals(""))){
            SaveString += CurrentView.getText() + " ";
        }
        index ++;
        }
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        editor.putString("InputValues", SaveString);
        editor.apply();
    }
}
