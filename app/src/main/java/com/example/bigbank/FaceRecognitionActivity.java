package com.example.bigbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bigbank.Database.DatabaseHelper;
import com.example.bigbank.Model.User;

import java.util.Timer;
import java.util.TimerTask;

public class FaceRecognitionActivity extends AppCompatActivity {

    Button buttonCapture;
    User user;

    DatabaseHelper databaseHelper;

    ProgressBar progressBar;

    String frontImage, backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);

        databaseHelper = new DatabaseHelper(this);
        user = (User) getIntent().getSerializableExtra("user");

        buttonCapture = findViewById(R.id.buttonCapture);
        progressBar = findViewById(R.id.progressFaceRecognition);

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                user.setFacePicture("facePicture");
                user.setIdentityCardPictureFront(InfoSubmissionActivity.frontImage);
                user.setIdentityCardPictureBack(InfoSubmissionActivity.backImage);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {


                        if(databaseHelper.insertUser(getApplicationContext(),user) == -1){
                            //Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(FaceRecognitionActivity.this, UserSignUpActivity.class);
                            startActivity(intent);
                        }
                        else {
                            //Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(FaceRecognitionActivity.this, DeliveryActivity.class);
                            startActivity(intent);
                        }
                    }
                },MyConstant.FAKE_DELAY);
            }
        });
    }
}