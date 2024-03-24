package com.example.prediction;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void marketPrices(View view)
    {
        Intent intent= new Intent(this,GetPricesData.class);
        MainActivity.this.startActivity(intent);

    }

    public void forecast(View view)
    {
        Intent intent= new Intent(this,Forecasts.class);
        startActivity(intent);

    }

}
