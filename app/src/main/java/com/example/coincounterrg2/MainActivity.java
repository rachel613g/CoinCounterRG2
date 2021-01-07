package com.example.coincounterrg2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CoinCounter mCoinCounter;

    private EditText et_Penny;
    private EditText et_Nickel;
    private EditText et_Dime;
    private EditText et_Quarter;
    private TextView tv_StatusBar;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("COIN_COUNT", mCoinCounter.getJSONStringFromThis());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCoinCounter = CoinCounter.getCoinCounterObjectFromJSONString(savedInstanceState.getString("COIN_COUNT"));
        updateUI();
    }

    private void updateUI() {

        //if user never entered anything
        if (et_Penny.getText().equals("0") && et_Nickel.getText().equals("0")
                && et_Dime.getText().equals("0") && et_Quarter.getText().equals("0")) {
            tv_StatusBar.setText(R.string.status_bar_initial_string);
        } else {
            tv_StatusBar.setText("Total in cents: ");
        }
        et_Penny.setText(String.format("%d", mCoinCounter.getCountOfPennies()));
        et_Nickel.setText(String.format("%d", mCoinCounter.getCountOfNickels()));
        et_Dime.setText(String.format("%d", mCoinCounter.getCountOfDimes()));
        et_Quarter.setText(String.format("%d", mCoinCounter.getCountOfQuarters()));
        tv_StatusBar.setText(String.format("Total in cents: %d", mCoinCounter.getCentsValueTotal()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setUpFAB();
        setUpFields();

    }

    private void setUpFields() {
        //model coin counter
        mCoinCounter = new CoinCounter();

        //res views
        et_Penny = findViewById(R.id.pennyEditText);
        et_Nickel = findViewById(R.id.nickelEditText);
        et_Dime = findViewById(R.id.dimeEditText);
        et_Quarter = findViewById(R.id.quarterEditText);
        tv_StatusBar = findViewById(R.id.statusBarTV);
    }

    private void setUpFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCoinCounter.setCountOfPennies(et_Penny.getText().toString());
                mCoinCounter.setCountOfNickels(et_Nickel.getText().toString());
                mCoinCounter.setCountOfDimes(et_Dime.getText().toString());
                mCoinCounter.setCountOfQuarters(et_Quarter.getText().toString());

                tv_StatusBar.setText(String.format("Total in cents: %,d", mCoinCounter.getCentsValueTotal()));
            }
        });
    }


    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        if (id == R.id.clear_all) {
            clearAll();
            return true;
        }

        if (id == R.id.about) {
            showAbout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        Utils.showInfoDialog(MainActivity.this,
                R.string.about_title, R.string.about_text);
    }

    private void clearAll() {
        mCoinCounter = new CoinCounter();

        //if user never entered anything
        if (et_Penny.getText().equals("0") && et_Nickel.getText().equals("0")
                && et_Dime.getText().equals("0") && et_Quarter.getText().equals("0")) {
            tv_StatusBar.setText(R.string.status_bar_initial_string);
        } else {
            tv_StatusBar.setText("Total in cents: ");
        }
        et_Penny.setText("0");
        et_Nickel.setText("0");
        et_Dime.setText("0");
        et_Quarter.setText("0");

    }
}