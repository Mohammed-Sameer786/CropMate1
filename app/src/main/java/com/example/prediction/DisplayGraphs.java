package com.example.prediction;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;


public class DisplayGraphs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_graphs);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        BufferedReader reader = null;
        TextView link= (TextView) findViewById(R.id.link1);

        //Please change localhost name accordingly for testing
        String server_name= "http://192.168.249.7:5000/";


        String dataMarket = null;
        if (intent.hasExtra("Market"))
            dataMarket=bundle.getString("Market");

        String dataCommodity = null;
        if (intent.hasExtra("Commodity"))
            dataCommodity=bundle.getString("Commodity");

        String dataState = bundle.getString("State");
        String dataDistrict = bundle.getString("District");

        TextView txt = findViewById(R.id.link1);
        String text = null;


        String url = server_name+"get_val?state="+dataState+"&district="+dataDistrict+"&commodity="+dataCommodity;
        String textUrl= server_name+"info1?state="+dataState+"&district="+dataDistrict+"&commodity="+dataCommodity;
        link.setText(textUrl);
        final ImageView mImageView = (ImageView) findViewById(R.id.image1);

        ImageRequest imgRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mImageView.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // mImageView.setBackgroundColor(Color.parseColor("#ff0000"));
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
                error.printStackTrace();
            }
        });

        imgRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(imgRequest);
    }
}