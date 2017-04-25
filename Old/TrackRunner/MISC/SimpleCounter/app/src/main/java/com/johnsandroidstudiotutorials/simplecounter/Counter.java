package com.johnsandroidstudiotutorials.simplecounter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Counter extends AppCompatActivity {

    // The values that are defined for the class
    int count; // Set the variable count
    TextView countValueDisplay;
    Button incrementButton;
    SharedPreferences sharedPreferences;

    AlertDialog.Builder builder;
    EditText inputNewValue;
    EditText changeAmountInput;

    LinearLayout AlertDialogLayout;
    LinearLayout.LayoutParams layoutParameters;

    @Override
    // This is where all the methods of the class is defined.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        defineValues();
        loadValues();
        setCount();
        buttonHandler();
    }

    public void defineValues() {
        countValueDisplay = (TextView) findViewById(R.id.count_value_display);
        incrementButton = (Button) findViewById(R.id.increment_button);
        sharedPreferences = getSharedPreferences("count", Context.MODE_PRIVATE);
        builder = new AlertDialog.Builder(this);
        inputNewValue = new EditText(this);
    }

    public void loadValues() {
        sharedPreferences = getSharedPreferences("count", Context.MODE_PRIVATE);
    }

    // this sets the numbers that are displayed
    private void setCount() {
        count = sharedPreferences.getInt("count", 0);
        if (count == 0) { // If the count is 0 then nothing is displayed
            countValueDisplay.setText("Count");

        } else {
            countValueDisplay.setText(Integer.toString(count)); // otherwise display the text count

        }

    }

    // Count Up Button
    public void buttonHandler() {
        incrementButton.setOnClickListener(new View.OnClickListener() { // Listen for a click
            @Override
            public void onClick(View view) { // if click is in region
                if (count != 0) { // and count is not 0 then increamenet up
                    count = sharedPreferences.getInt("count", 0); // get the text that is currrently displayed
                    count += 1; // increase count
                    countValueDisplay.setText(Integer.toString(count)); // display the count
                } else {
                    count += 1; // increase count from 0 to 1
                    countValueDisplay.setText(Integer.toString(count)); // displayh the count
                }
                commitToSharedPreferences(); // save result
            }
        });
    }

    // Updates the global variable count
    public void commitToSharedPreferences() {
        sharedPreferences.edit().putInt("count", count).apply();
    }

    public void createAlertDialog() {
        builder = new AlertDialog.Builder(this);

        AlertDialogLayout = new LinearLayout(this);
        layoutParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        AlertDialogLayout.setOrientation(LinearLayout.VERTICAL);
        AlertDialogLayout.setLayoutParams(layoutParameters);

        AlertDialogLayout.setGravity(Gravity.CENTER);
        AlertDialogLayout.setPadding(2, 2, 2, 2);

        changeAmountInput = new EditText(this);
        changeAmountInput.setHint("example: 232");
        changeAmountInput.setInputType(InputType.TYPE_CLASS_DATETIME);

        AlertDialogLayout.addView(changeAmountInput, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        builder.setView(AlertDialogLayout);
        builder.setCancelable(true);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        // Setting Positive "Yes" Button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                count = Integer.parseInt(changeAmountInput.getText().toString());
                countValueDisplay.setText(Integer.toString(count));
                commitToSharedPreferences();
            }
        });

        builder.setIcon(R.drawable.ic_info_black_24dp);
        builder.setTitle("Input the correct value");

        AlertDialog alertDialog = builder.create();

        try {
            alertDialog.show();
        } catch (Exception e) {
            // WindowManager$BadTokenException will be caught and the app would
            // not display the 'Force Close' message
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_counter_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_value:
                createAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

}
