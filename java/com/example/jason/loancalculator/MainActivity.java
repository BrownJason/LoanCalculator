package com.example.jason.loancalculator;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    /**
     *
     *  Here we will declare and initialize our local variables for our loan calculator
     *
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button homeAndAuto = (Button) findViewById(R.id.home_auto);
        homeAndAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoanCalculator.class));
                finish();
            }
        });

        Button rothIRA = (Button) findViewById(R.id.rothIRA);
        rothIRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RothIRAInvestment.class));
                finish();
            }
        });


    }
}
