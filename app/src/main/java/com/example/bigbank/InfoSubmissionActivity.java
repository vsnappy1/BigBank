package com.example.bigbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbank.Model.User;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class InfoSubmissionActivity extends AppCompatActivity {

    ImageView imageViewFront;
    ImageView imageViewBack;

    TextView textViewFront;
    TextView textViewBack;

    ProgressBar progressBar;

    Button buttonSubmit;

    int RESULT_IMAGE_FRONT = 100, RESULT_IMAGE_BACK = 101;

    public static String frontImage;
    public static String backImage;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_submission);

        user = (User) getIntent().getSerializableExtra("user");

        imageViewFront = findViewById(R.id.imageViewFront);
        imageViewBack = findViewById(R.id.imageViewBack);
        textViewFront = findViewById(R.id.textViewTitleFront);
        textViewBack = findViewById(R.id.textViewTitleBack);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        progressBar = findViewById(R.id.progressBarIdentityCardSubmission);


        imageViewFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_IMAGE_FRONT);
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_IMAGE_BACK);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(frontImage == null){
                    Toast.makeText(InfoSubmissionActivity.this, "Please select front image", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(backImage == null){
                    Toast.makeText(InfoSubmissionActivity.this, "Please select back image", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        finish();
                        Intent intent = new Intent(InfoSubmissionActivity.this, FaceRecognitionActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                },MyConstant.FAKE_DELAY);


            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {


            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                if (reqCode == RESULT_IMAGE_FRONT) {
                    imageViewFront.setImageBitmap(selectedImage);
                    textViewFront.setVisibility(View.GONE);
                    frontImage = bitmapToString(selectedImage);
                } else if (reqCode == RESULT_IMAGE_BACK) {
                    imageViewBack.setImageBitmap(selectedImage);
                    textViewBack.setVisibility(View.GONE);
                    backImage = bitmapToString(selectedImage);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(InfoSubmissionActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(InfoSubmissionActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    String bitmapToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    Bitmap StringToBitmap(String encodedImage){
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}