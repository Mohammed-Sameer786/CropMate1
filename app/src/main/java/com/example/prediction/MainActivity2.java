package com.example.prediction;





import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    CardView crop_recommendation,crop_price_predictor,crop_descriptor;


    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent=getIntent();
        if(intent.hasExtra("username")){
            String username=intent.getStringExtra("username");
            textView=findViewById(R.id.welcome);
            String s="Welcome, "+username+"!";
            textView.setText(s);
        }
        crop_recommendation=findViewById(R.id.cardCropRecommendation);
        crop_descriptor=findViewById(R.id.cardViewCropPrice);


        crop_recommendation.setOnClickListener(v -> startActivity(new Intent(MainActivity2.this,HomeActivity.class)));
        crop_descriptor.setOnClickListener(v -> startActivity(new Intent(MainActivity2.this,MainActivity.class)));

    }
}