package com.example.prediction;




import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class PredictActivity extends AppCompatActivity {
    Spinner statesSpinner,seasonSpinner;

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
        textView=findViewById(R.id.textRecommendation);
        statesSpinner=findViewById(R.id.spinnerState);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(
                this,
                R.array.states_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statesSpinner.setAdapter(adapter);


        seasonSpinner=findViewById(R.id.spinnerSeason);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(
                this,
                R.array.states_array,
                android.R.layout.simple_spinner_item
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



    }
}