package com.example.prediction;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;

public class GetPricesData extends AppCompatActivity {


    EditText stateText,districtText,marketText,commodityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_prices_data);

        stateText= (EditText) findViewById(R.id.state);
        districtText= (EditText) findViewById(R.id.district);
        marketText= (EditText) findViewById(R.id.market);
       // commodityText= (EditText) findViewById(R.id.commodity);
    }

    public void submit(View view)
    {
        String state = stateText.getText().toString();
        String district = districtText.getText().toString();
        String market = null;
        market=marketText.getText().toString();
        String commodity= null;
       // commodity=commodityText.getText().toString();


        Intent intent= new Intent(this,DisplayGraphs.class);
        intent.putExtra("State",state);
        intent.putExtra("District",district);
        if(market!=null && market!="")
            intent.putExtra("Market",market);

        startActivity(intent);
    }
    public void submit1(View view)
    {
        String state = stateText.getText().toString();
        String district = districtText.getText().toString();
        String market = null;
        market=marketText.getText().toString();
        String commodity= null;



        Intent intent= new Intent(this,DisplayGraphs2.class);
        intent.putExtra("State",state);
        intent.putExtra("District",district);
        if(market!=null && market!="")
            intent.putExtra("Market",market);

        startActivity(intent);
    }
    public void submit2(View view)
    {
        String state = stateText.getText().toString();
        String district = districtText.getText().toString();
        String market = null;
        market=marketText.getText().toString();



        Intent intent= new Intent(this,DisplayGraphs.class);
        intent.putExtra("State",state);
        intent.putExtra("District",district);
        if(market!=null && market!="")
            intent.putExtra("Market",market);

        startActivity(intent);
    }
}
