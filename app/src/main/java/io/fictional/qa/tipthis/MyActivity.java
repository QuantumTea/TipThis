package io.fictional.qa.tipthis;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MyActivity extends AppCompatActivity {

    private TextView tipAmount15percent, totalWith15Percent;
    private TextView tipAmount20percent, totalWith20Percent;
    private TextView tipAmount25percent, totalWith25Percent;
    private TextView tipAmount30percent, totalWith30Percent;
    private TextView mealTotalField;

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private String format_15pc;
    private String format_20pc;
    private String format_25pc;
    private String format_30pc;
    private String format_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        format_15pc = getString(R.string.format_15pc);
        format_20pc = getString(R.string.format_20pc);
        format_25pc = getString(R.string.format_25pc);
        format_30pc = getString(R.string.format_30pc);
        format_total = getString(R.string.format_total);

        GetLabelTextFields();

        mealTotalField.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String fieldValue = mealTotalField.getText().toString();
                if (fieldValue.length() == 0 && ".".equals(source)) {
                    return "";
                }

                try {
                    if (Double.valueOf(fieldValue) > 1000) {
                        return "";
                    }
                } catch(Exception ignored) {}

                int length = fieldValue.length();
                if (length - dstart > 2 && source.length() > 0 && source.charAt(0) == '.') {
                    return "";
                }

                int dot = fieldValue.indexOf('.');
                if (dot > 0 && length - dot > 2) {
                    return "";
                }
                return null;
            }
        }});

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
                CheckIfTotalIsEmpty(currentText);
            }
        });

        if (savedInstanceState != null) {
            CheckIfTotalIsEmpty(savedInstanceState.getString("total"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("total", mealTotalField.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    private void ClearAllLabelFields() {
        tipAmount15percent.setText(R.string.fifteen_pc);
        totalWith15Percent.setText(" ");
        tipAmount20percent.setText(R.string.twenty_pc);
        totalWith20Percent.setText(" ");
        tipAmount25percent.setText(R.string.twenty_five_pc);
        totalWith25Percent.setText(" ");
        tipAmount30percent.setText(R.string.thirty_pc);
        totalWith30Percent.setText(" ");
    }

    private void CheckIfTotalIsEmpty(String mealTotalString) {
        if (mealTotalString.isEmpty()){
            ClearAllLabelFields();
        } else {
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
        setColoredLabel(tipAmount15percent, String.format(format_15pc, currencyFormatter.format(mealTotal * .15)), 4, false);
        setColoredLabel(totalWith15Percent, String.format(format_total, currencyFormatter.format(mealTotal * 1.15)), 6, true);

        setColoredLabel(tipAmount20percent, String.format(format_20pc, currencyFormatter.format(mealTotal * .2)), 4, false);
        setColoredLabel(totalWith20Percent, String.format(format_total, currencyFormatter.format(mealTotal * 1.2)), 6, true);

        setColoredLabel(tipAmount25percent, String.format(format_25pc, currencyFormatter.format(mealTotal * .25)), 4, false);
        setColoredLabel(totalWith25Percent, String.format(format_total, currencyFormatter.format(mealTotal * 1.25)), 6, true);

        setColoredLabel(tipAmount30percent, String.format(format_30pc, currencyFormatter.format(mealTotal * .3)), 4, false);
        setColoredLabel(totalWith30Percent, String.format(format_total, currencyFormatter.format(mealTotal * 1.3)), 6, true);
    }

    private void setColoredLabel(TextView tv, String text, int dataStart, boolean bold) {
        Spannable x = Spannable.Factory.getInstance().newSpannable(text);
        x.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary_text)), 0, dataStart, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        x.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary)), dataStart + 1, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if (bold) {
            x.setSpan(new StyleSpan(Typeface.BOLD), dataStart + 1, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        tv.setText(x);
    }

    private void GetLabelTextFields()
    {
        mealTotalField = (EditText) findViewById(R.id.mealTotalInput);
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
