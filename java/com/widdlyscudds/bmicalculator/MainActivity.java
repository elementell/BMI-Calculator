package com.widdlyscudds.bmicalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    public boolean metricOn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void changeUnits(View v) {
        if (!metricOn) {
            findViewById(R.id.weightImp).setVisibility(View.GONE);
            findViewById(R.id.weightMetr).setVisibility(View.VISIBLE);

            findViewById(R.id.impText).setVisibility(View.GONE);
            findViewById(R.id.metrText).setVisibility(View.VISIBLE);

            findViewById(R.id.heightImp).setVisibility(View.GONE);
            findViewById(R.id.heightMetr).setVisibility(View.VISIBLE);

            metricOn = true;
        } else {
            findViewById(R.id.weightImp).setVisibility(View.VISIBLE);
            findViewById(R.id.weightMetr).setVisibility(View.GONE);

            findViewById(R.id.impText).setVisibility(View.VISIBLE);
            findViewById(R.id.metrText).setVisibility(View.GONE);

            findViewById(R.id.heightImp).setVisibility(View.VISIBLE);
            findViewById(R.id.heightMetr).setVisibility(View.GONE);

            metricOn = false;
        }
    }
    public void calculate(View v) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        if (!metricOn && ((TextView)findViewById(R.id.weightEdit)).getText().length() > 0 && ((TextView)findViewById(R.id.heightFt)).getText().length() > 0 && ((TextView)findViewById(R.id.heightIn)).getText().length() > 0) {
            float inches = (Float.parseFloat(((TextView)findViewById(R.id.heightFt)).getText().toString()) * 12) + Float.parseFloat(((TextView)findViewById(R.id.heightIn)).getText().toString());
            float BMI = 703 * (Float.parseFloat(((TextView)findViewById(R.id.weightEdit)).getText().toString()) / (inches * inches));
            findViewById(R.id.output).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.output)).setText("Your BMI is: " + fixFloat(BMI) + "\n" + "You are " + checkBMI(BMI));
        } else if (metricOn && ((TextView)findViewById(R.id.weightEdit)).getText().length() > 0 && ((TextView)findViewById(R.id.heightCm)).getText().length() > 0) {
            float meters = Float.parseFloat(((TextView)findViewById(R.id.heightCm)).getText().toString()) / 100;
            float BMI = (Float.parseFloat(((TextView)findViewById(R.id.weightEdit)).getText().toString()) / (meters * meters));
            findViewById(R.id.output).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.output)).setText("Your BMI is: " + fixFloat(BMI) + "\n" + "You are " + checkBMI(BMI));
        } else {
            Toast toast = Toast.makeText(this, "Please fill out all required text fields!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    public String checkBMI(float BMI) {
        if (BMI < 18.5)
            return "underweight";
        else if (BMI < 24.9)
            return "normal weight";
        else if (BMI < 29.9)
            return "overweight";
        else if (BMI > 30)
            return "obese";
        return "";
    }
    public float fixFloat(float in) { in = Math.round(in * 10); return in/10; }
}
