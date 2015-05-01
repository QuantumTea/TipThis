package io.fictional.qa.tipthis;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;

public class MyActivity extends Activity {

    private TextView tipAmount15percent, totalWith15Percent;
    private TextView tipAmount20percent, totalWith20Percent;
    private TextView tipAmount25percent, totalWith25Percent;
    private String savedMealTotalString ="";
    private String wasThisClearedYet = "";
    private boolean alreadyCleared = true;
    private TextView mealTotalField;

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
    //DecimalFormat twoDecimalPlaces = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);

        GetTextFields();
        Button btnCalculateTip = (Button) findViewById(R.id.btnCalculate);
        Button btnClear = (Button) findViewById(R.id.btnClear);
        mealTotalField = (TextView) findViewById(R.id.mealTotalInput);

        // TODO
        // call the clear method when the text field is empty without an infinite loop
        // stop you putting in three or more decimal places, use Currency Formatter on text field

        btnCalculateTip.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CheckIfTotalIsEmpty(mealTotalField.getText().toString());
            }
        });

        btnClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ClearAllFields();
            }
        });

        mealTotalField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                CheckIfTotalIsEmpty(mealTotalField.getText().toString());
            }
        });

        // Check if recreating a previously destroyed instance
        if (savedInstanceState != null) {
            alreadyCleared = savedInstanceState.getBoolean(wasThisClearedYet);
            CheckIfTotalIsEmpty(savedInstanceState.getString(savedMealTotalString));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current state
        savedInstanceState.putString(savedMealTotalString, mealTotalField.getText().toString());
        savedInstanceState.putBoolean(wasThisClearedYet, alreadyCleared);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    private void ClearAllFields() {
        if (!alreadyCleared)
        {
            mealTotalField.setText("");
            tipAmount15percent.setText("15%");
            totalWith15Percent.setText(" ");
            tipAmount20percent.setText("20%");
            totalWith20Percent.setText(" ");
            tipAmount25percent.setText("25%");
            totalWith25Percent.setText(" ");
            alreadyCleared = true;
        }
    }

    private void CheckIfTotalIsEmpty(String mealTotalString) {
        if (!mealTotalString.isEmpty())
        {
            CalculateTip(Double.parseDouble(mealTotalString));
            alreadyCleared = false;
        }
 /*       if (mealTotalString.isEmpty()) {
            Log.d("EMPTY", "Nil, nothing, no points");
            // goes into an infinite loop spitting out log statements when you try ot delete the last character
            // ClearAllFields();
        }
        else
        {
            CalculateTip(Double.parseDouble(mealTotalString));
            alreadyCleared = false;
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        return id == R.id.action_about || super.onOptionsItemSelected(item);
    }

    private void CalculateTip(double mealTotal)
    {
        tipAmount15percent.setText(String.format("%s", "15%: " + currencyFormatter.format((mealTotal * .15))));
        totalWith15Percent.setText(String.format("%s", "Total: " + currencyFormatter.format((mealTotal * 1.15))));

        tipAmount20percent.setText(String.format("%s", "20%: " + currencyFormatter.format((mealTotal * .2))));
        totalWith20Percent.setText(String.format("%s", "Total: " + currencyFormatter.format((mealTotal *1.2))));

        tipAmount25percent.setText(String.format("%s", "25%: " + currencyFormatter.format((mealTotal * .25))));
        totalWith25Percent.setText(String.format("%s", "Total: " + currencyFormatter.format((mealTotal *1.25))));
    }

    private void GetTextFields()
    {
        tipAmount15percent = (TextView) findViewById(R.id.txt15percenttip);
        tipAmount20percent = (TextView) findViewById(R.id.txt20percenttip);
        tipAmount25percent = (TextView) findViewById(R.id.txt25percenttip);
        totalWith15Percent = (TextView) findViewById(R.id.txt15percenttotal);
        totalWith20Percent = (TextView) findViewById(R.id.txt20percenttotal);
        totalWith25Percent = (TextView) findViewById(R.id.txt25percenttotal);
    }
}
