package com.example.bigbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bigbank.Database.DatabaseHelper;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button buttonUser;
    Button buttonAdmin;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUser = findViewById(R.id.buttonUser);
        buttonAdmin = findViewById(R.id.buttonAdmin);
        databaseHelper = new DatabaseHelper(this);

        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserLoginActivity.class));
            }
        });

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
            }
        });

    }


}