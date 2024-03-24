package com.example.prediction;






import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;


import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.prediction.ml.SoilAnalysis;

import org.tensorflow.lite.DataType;

import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class HomeActivity extends AppCompatActivity {
    TextView result, confidence,recommendTextview;
    ImageView imageView;
    Button picture,upload,recommend;
    int imageSize = 224;
    int PICK_IMAGE_REQUEST=2;
    String soiltype;
    String state,seasonSeleted;
    Spinner seasonSpinner;

    private Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        result = findViewById(R.id.result);
        confidence = findViewById(R.id.confidence);
        imageView = findViewById(R.id.imageView);
        picture = findViewById(R.id.button);
        upload=findViewById(R.id.button2);
        recommend=findViewById(R.id.recommend);
        recommendTextview=findViewById(R.id.recommendedText);

        picture.setOnClickListener(view -> {
            // Launch camera if we have permission
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            } else {
                //Request camera permission if we don't have it.
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
            private void selectImage() {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
            }
        });
        seasonSpinner=findViewById(R.id.spinnerSeason);

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(
                this,
                R.array.season,
                android.R.layout.simple_spinner_item
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonSpinner.setAdapter(adapter1);
        seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seasonSeleted=parent.getItemAtPosition(position).toString();
                Toast.makeText(HomeActivity.this,seasonSeleted,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans="";
                state="Telangana";
                soiltype=result.getText().toString();
                seasonSeleted=seasonSeleted;
                recommendTextview.setText(state+soiltype+seasonSeleted);
                if(soiltype.equals("Red soil")){
                    if(seasonSeleted.equals("Rabi")){
                        ans="Cotton "+" "+"Wheat"+" "+"Rice";
                        recommendTextview.setText(ans);
                    }
                    else if(seasonSeleted.equals("Kharif")){
                        ans="Red gram"+" "+"cotton"+" "+"maize"+" "+"Groundnut";
                        recommendTextview.setText(ans);
                    }
                    else if(seasonSeleted.equals("Summer")){
                        ans="watermelon"+" "+"cucumber"+" "+"vegetables";
                        recommendTextview.setText(ans);

                    }
                }
                if(soiltype.equals("Alluvail Soil")){
                    if(seasonSeleted.equals("Rabi")){
                        ans="Cotton "+" "+"Black Pepper"+" "+"Rice";
                        recommendTextview.setText(ans);
                    }
                    else if(seasonSeleted.equals("Kharif")){
                        ans="Maize"+" "+"cotton"+" "+"sugarcane"+" "+"oilseeds" +
                                "Rice";
                        recommendTextview.setText(ans);
                    }
                    else if(seasonSeleted.equals("Summer")){
                        ans="Rice"+" "+"Wheat"+" "+"Sugarcane"+" "+"cotton"+" "+"Tobacco";
                        recommendTextview.setText(ans);

                    }
                }
                if(soiltype.equals("Black soil")){
                    if(seasonSeleted.equals("Rabi")){
                        ans="Cotton "+" "+"Black Gram"+" "+"Wheat"+" "+"Sunflower"+" "+"Groundnut";
                        recommendTextview.setText(ans);
                    }
                    else if(seasonSeleted.equals("Kharif")){
                        ans="Groundnut"+" "+"Castor Seed"+" "+"Oil palm seeds";
                        recommendTextview.setText(ans);
                    }
                    else if(seasonSeleted.equals("Summer")){
                        ans="watermelon"+" "+"cucumber"+" "+"vegetables";
                        recommendTextview.setText(ans);

                    }
                }
                if(soiltype.equals("Clay soil")){
                    if(seasonSeleted.equals("Rabi")){
                        ans="Cotton "+" "+"Wheat"+" "+"Rice";
                        recommendTextview.setText(ans);
                    }
                    else if(seasonSeleted.equals("Kharif")){
                        ans="Rice"+" "+"Gram"+" "+"Groundnut";
                        recommendTextview.setText(ans);
                    }
                    else if(seasonSeleted.equals("Summer")){
                        ans="Green chilles"+" "+"vegetables";
                        recommendTextview.setText(ans);

                    }
                }
                if(soiltype.equals("Latterite")){
                    if(seasonSeleted.equals("Rabi")){
                        ans="Black Gram"+" "+"Wheat";
                        recommendTextview.setText(ans);
                    }
                    else if(seasonSeleted.equals("Kharif")){
                        ans="oil seeds"+" "+"spices";
                        recommendTextview.setText(ans);
                    }
                    else if(seasonSeleted.equals("Summer")){
                        ans="vegetables"+" "+"spices";
                        recommendTextview.setText(ans);

                    }
                }

            }
        });

    }
    public void classifyImage(Bitmap image){
        try {
            SoilAnalysis model = SoilAnalysis.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            // get 1D array of 224 * 224 pixels in image
            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);


            SoilAnalysis.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Alluvail Soil", "Black soil", "Clay soil", "Red soil","Latterite"};

            int c=0;
            String s = "";
            for(int i = 0; i < classes.length; i++){
                if(confidences[i]*100<50){
                    c++;
                }
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }
            confidence.setText(s);
            if(c==4){
                result.setText(classes[maxPos]);
            }else {
                result.setText("error");
            }


            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ( resultCode == RESULT_OK) {
            if(requestCode==1 && data!=null){
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }else if(requestCode==2 && data!=null ){
                Uri uri=data.getData();
                try {
                    selectedBitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    int dimension=Math.min(selectedBitmap.getWidth(),selectedBitmap.getHeight());
                    selectedBitmap=ThumbnailUtils.extractThumbnail(selectedBitmap,dimension,dimension);

                    imageView.setImageBitmap(selectedBitmap);
                    selectedBitmap=Bitmap.createScaledBitmap(selectedBitmap,imageSize,imageSize,false);

                    classifyImage(selectedBitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}