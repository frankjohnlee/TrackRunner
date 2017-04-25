package com.example.android.trackrunner;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Counter extends AppCompatActivity {

    TextView countValueDisplay;
    Button incrementButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    AlertDialog.Builder builder;
    EditText inputNewValue;

    LinearLayout AlertDialogLayout;
    LinearLayout.LayoutParams layoutParameters




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
    }
    public void defineValues(){
        countValueDisplay = (TextView) findViewById(R.id.count_value_display);
        incrementButton = (Button) findViewById(R.id.increment_button);
        sharedPreferences = getSharedPreferences("count", Context.MODE_PRIVATE)
    }
}
