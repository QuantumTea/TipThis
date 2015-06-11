package io.fictional.qa.tipthis;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MyActivity extends Activity {

    private TextView tipAmount15percent, totalWith15Percent;
    private TextView tipAmount20percent, totalWith20Percent;
    private TextView tipAmount25percent, totalWith25Percent;
    private TextView tipAmount30percent, totalWith30Percent;
    private String savedMealTotalString ="";
    private TextView mealTotalField;

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);

        GetLabelTextFields();
        mealTotalField = (EditText) findViewById(R.id.mealTotalInput);

        // TODO
        // stop you putting in three or more decimal places, use Currency Formatter on text field

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
                String currentText = mealTotalField.getText().toString();

                if (currentText.isEmpty())
                {
                    ClearAllLabelFields();
                }
                else  {
                    // check if there are more than 2 decimal places, if so,
                    // disconnect the watcher, format the text,
                    // put the cursor at the end and, reconnect the watcher

                    if (currentText.startsWith("."))
                    {
                        currentText = "0" + currentText;
                    }

                    CalculateTip(Double.parseDouble(currentText));
                }
            }
        });

        // Check if recreating a previously destroyed instance
        if (savedInstanceState != null) {
            CheckIfTotalIsEmpty(savedInstanceState.getString(savedMealTotalString));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current state
        savedInstanceState.putString(savedMealTotalString, mealTotalField.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    private void ClearAllLabelFields() {
        tipAmount15percent.setText("15%");
        totalWith15Percent.setText(" ");
        tipAmount20percent.setText("20%");
        totalWith20Percent.setText(" ");
        tipAmount25percent.setText("25%");
        totalWith25Percent.setText(" ");
        tipAmount30percent.setText("30%");
        totalWith30Percent.setText(" ");
    }

    private void CheckIfTotalIsEmpty(String mealTotalString) {
        if (mealTotalString.isEmpty())
        {
            ClearAllLabelFields();
        }
        else  {
            CalculateTip(Double.parseDouble(mealTotalString));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        tipAmount30percent.setText(String.format("%s", "30%: " + currencyFormatter.format((mealTotal * .3))));
        totalWith30Percent.setText(String.format("%s", "Total: " + currencyFormatter.format((mealTotal *1.3))));
    }

    private void GetLabelTextFields()
    {
        tipAmount15percent = (TextView) findViewById(R.id.txt15percenttip);
        tipAmount20percent = (TextView) findViewById(R.id.txt20percenttip);
        tipAmount25percent = (TextView) findViewById(R.id.txt25percenttip);
        tipAmount30percent = (TextView) findViewById(R.id.txt30percenttip);
        totalWith15Percent = (TextView) findViewById(R.id.txt15percenttotal);
        totalWith20Percent = (TextView) findViewById(R.id.txt20percenttotal);
        totalWith25Percent = (TextView) findViewById(R.id.txt25percenttotal);
        totalWith30Percent = (TextView) findViewById(R.id.txt30percenttotal);
    }
}
