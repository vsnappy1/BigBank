package com.example.bigbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbank.Database.DatabaseHelper;
import com.example.bigbank.Model.User;

import java.util.Random;


public class UserDetailActivity extends AppCompatActivity {

    TextView textViewFullName;
    TextView textViewEmail;
    TextView textViewIdentityCard;
    TextView textViewPhoneNumber;
    TextView textViewGender;
    TextView textViewNation;
    TextView textViewOccupation;
    TextView textViewAddress;
    TextView textViewPostalCode;
    TextView textViewState;
    TextView textViewCity;

    ImageView imageViewFront;
    ImageView imageViewBack;

    Button buttonGiveCard;

    ProgressBar progressBar;

    User user;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        databaseHelper = new DatabaseHelper(this);
        initViews();

        user = AdminDashboardActivity.user;

        textViewFullName.setText(user.getFullName());
        textViewEmail.setText(user.getEmail());
        textViewIdentityCard.setText(user.getIdentityCard());
        textViewPhoneNumber.setText(user.getPhoneNumber());
        textViewGender.setText(user.getGender());
        textViewNation.setText(user.getNation());
        textViewOccupation.setText(user.getOccupation());
        textViewAddress.setText(user.getAddress());
        textViewPostalCode.setText(user.getPostCode());
        textViewState.setText(user.getState());
        textViewCity.setText(user.getCity());

        imageViewFront.setImageBitmap(stringToBitmap(user.getIdentityCardPictureFront()));
        imageViewBack.setImageBitmap(stringToBitmap(user.getIdentityCardPictureBack()));

        if(user.getCvc().equals("_")){
            buttonGiveCard.setText("Send card");
        }
        else {
            buttonGiveCard.setText("Check status");
        }

        buttonGiveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user.getCvc().equals("_")){
                    showConfirmDialog();
                }
                else {
                    showCardDetails();
                }

            }
        });
    }

    void initViews() {
        textViewFullName = findViewById(R.id.textViewDetailFullName);
        textViewEmail = findViewById(R.id.textViewDetailEmail);
        textViewIdentityCard = findViewById(R.id.textViewDetailIdentityCard);
        textViewPhoneNumber = findViewById(R.id.textViewDetailPhoneNumber);
        textViewGender = findViewById(R.id.textViewDetailGender);
        textViewNation = findViewById(R.id.textViewDetailNation);
        textViewOccupation = findViewById(R.id.textViewDetailOccupation);
        textViewAddress = findViewById(R.id.textViewDetailAddress);
        textViewPostalCode = findViewById(R.id.textViewDetailPostalCode);
        textViewState = findViewById(R.id.textViewDetailState);
        textViewCity = findViewById(R.id.textViewDetailCity);
        imageViewFront = findViewById(R.id.imageViewFront);
        imageViewBack = findViewById(R.id.imageViewBack);
        buttonGiveCard = findViewById(R.id.buttonGiveCard);
        progressBar = findViewById(R.id.progressBarUserDetail);
    }

    Bitmap stringToBitmap(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.NO_WRAP);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    void showConfirmDialog() {

        int firstFourFigures = new Random().nextInt(9000) + 1000;
        int secondFourFigures = new Random().nextInt(9000) + 1000;
        int thirdFourFigures = new Random().nextInt(9000) + 1000;
        int lastFourFigures = new Random().nextInt(9000) + 1000;


        int cvc = new Random().nextInt(900) + 100;

        String cardNumber = firstFourFigures + "-" + secondFourFigures + "-" + thirdFourFigures + "-" + lastFourFigures;
        String cvcNumber = String.valueOf(cvc);
        Log.i("TAKA", "card " + cardNumber + "\n" + "cvc " + cvcNumber);


        new AlertDialog.Builder(UserDetailActivity.this)
                .setTitle("Information")
                .setMessage(
                        "Card Number: "+cardNumber+"\n"+
                        "CVC: "+ cvcNumber)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);
                        new updateUser().execute(cardNumber, cvcNumber);
                    }
                })
                .show();
    }

    void showCardDetails(){

        String cardStatus;

        if(user.getReceivedCard() == 1){
            cardStatus = "Received";
        }
        else {
            cardStatus = "Sent";
        }

        new AlertDialog.Builder(UserDetailActivity.this)
                .setTitle("Information")
                .setMessage(
                        "Card Number: "+user.getCardNumber()+"\n"+
                                "CVC: "+ user.getCvc()+"\n"+
                                "Status: "+cardStatus)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("OK", null)
                .show();

    }

    private class updateUser extends AsyncTask<String, Void, Long> {


        @Override
        protected Long doInBackground(String... strings) {
            String cardNumber = strings[0];
            String cvcNumber = strings[1];
            return databaseHelper.addCardNumberAndCvc(user.getId(), cardNumber, cvcNumber);
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);

            if (result == -1) {
                Toast.makeText(UserDetailActivity.this, "Operation Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UserDetailActivity.this, "Operation Successful", Toast.LENGTH_SHORT).show();
                AdminDashboardActivity.isDatabaseUpdated = true;
                onBackPressed();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
}