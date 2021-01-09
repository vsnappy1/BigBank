package com.example.bigbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeliveryActivity extends AppCompatActivity {

    Button buttonSelfCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        buttonSelfCollect = findViewById(R.id.buttonSelfCollect);


        buttonSelfCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DeliveryActivity.this)
                        .setTitle("Information")
                        .setMessage("We will send you a message when your bank card is ready")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Ok", null)
                        .show();
            }
        });
    }
}