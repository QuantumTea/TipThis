package io.fictional.qa.tipthis;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.text.NumberFormat;
import java.util.Locale;

public class MyActivity extends Activity {

    private TextView tipAmount15percent, totalWith15Percent;
    private TextView tipAmount20percent, totalWith20Percent;
    private TextView tipAmount25percent, totalWith25Percent;
    private String savedMealTotalString;
    private TextView mealTotalField;

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        GetTextFields();
        // Get button from the layout resource, and attach an event to it
        Button btnCalculateTip = (Button) findViewById(R.id.btnCalculate);
        mealTotalField = (TextView) findViewById(R.id.mealTotalInput);

        // TODO
        // stop you putting in three or more decimal places, use Currency Formatter on text field
        // do the calculation when you hit done on the keyboard
        // recalculate after screen rotation

        btnCalculateTip.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CheckIfTotalIsEmpty(mealTotalField.getText().toString());
            }
        });
    }

    private void ClearAllFields() {
        mealTotalField.setText("");
        tipAmount15percent.setText("15%");
        totalWith15Percent.setText("");
        tipAmount20percent.setText("20%");
        totalWith20Percent.setText("");
        tipAmount25percent.setText("25%");
        totalWith25Percent.setText("");
    }

    private void CheckIfTotalIsEmpty(String mealTotalString) {
        if (!mealTotalString.isEmpty())
        {
            CalculateTip(Double.parseDouble(mealTotalString));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
