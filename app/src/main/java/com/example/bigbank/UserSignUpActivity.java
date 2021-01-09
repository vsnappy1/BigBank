package com.example.bigbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bigbank.Database.DatabaseHelper;
import com.example.bigbank.Model.User;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UserSignUpActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextFullName;
    EditText editTextIdentityCard;
    EditText editTextPhoneNumber;
    EditText editTextOccupation;
    EditText editTextAddress;
    EditText editTextCity;
    EditText editTextPostCode;

    Spinner spinnerGender;
    Spinner spinnerNation;
    Spinner spinnerState;

    Button buttonSignup;
    ProgressBar progressBar;

    ArrayList<User> list;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        databaseHelper = new DatabaseHelper(this);
        list = new ArrayList<>();

        new GetDataFromDatabase().execute();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextIdentityCard = findViewById(R.id.editTextIdentityCard);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextOccupation = findViewById(R.id.editTextOccupation);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextCity = findViewById(R.id.editTextCity);
        editTextPostCode = findViewById(R.id.editTextPostCode);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerNation = findViewById(R.id.spinnerNation);
        spinnerState = findViewById(R.id.spinnerState);

        buttonSignup = findViewById(R.id.buttonSignup);
        progressBar = findViewById(R.id.progressBar);


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(
                        editTextEmail.getText().toString().equals("") ||
                                editTextPassword.getText().toString().equals("") ||
                                editTextFullName.getText().toString().equals("") ||
                                editTextIdentityCard.getText().toString().equals("") ||
                                editTextPhoneNumber.getText().toString().equals("") ||
                                editTextOccupation.getText().toString().equals("") ||
                                editTextAddress.getText().toString().equals("") ||
                                editTextCity.getText().toString().equals("") ||
                                editTextPostCode.getText().toString().equals("") ||
                        spinnerGender.getSelectedItem().equals("Select Gender") ||
                        spinnerNation.getSelectedItem().equals("Select Nation") ||
                        spinnerState.getSelectedItem().equals("Select State")
                ){

                    Toast.makeText(UserSignUpActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = editTextEmail.getText().toString();


                if(isEmailUsed(email)){
                    Toast.makeText(UserSignUpActivity.this, "Try different email address, this email address already registered", Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        String email = editTextEmail.getText().toString();
                        String password = editTextPassword.getText().toString();
                        String fullName = editTextFullName.getText().toString();
                        String identityCard = editTextIdentityCard.getText().toString();
                        String phoneNumber = editTextPhoneNumber.getText().toString();
                        String occupation = editTextOccupation.getText().toString();
                        String address = editTextAddress.getText().toString();
                        String postCode = editTextPostCode.getText().toString();
                        String city = editTextCity.getText().toString();
                        String gender = spinnerGender.getSelectedItem().toString();
                        String nation = spinnerNation.getSelectedItem().toString();
                        String state = spinnerState.getSelectedItem().toString();
                        String cardNumber = "_";
                        String cvc = "_";
                        int usingFirstTime = 1;
                        int receivedCard = 0;

                        User user = new User();
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setFullName(fullName);
                        user.setIdentityCard(identityCard);
                        user.setPhoneNumber(phoneNumber);
                        user.setOccupation(occupation);
                        user.setAddress(address);
                        user.setPostCode(postCode);
                        user.setCity(city);
                        user.setGender(gender);
                        user.setNation(nation);
                        user.setState(state);
                        user.setCardNumber(cardNumber);
                        user.setCvc(cvc);
                        user.setUsingFirstTime(usingFirstTime);
                        user.setReceivedCard(receivedCard);

                        finish();
                        Intent intent = new Intent(UserSignUpActivity.this, InfoSubmissionActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);

                    }
                },MyConstant.FAKE_DELAY);


            }
        });
    }

    boolean isEmailUsed(String email){

        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getEmail().equals(email)){
                return true;
            }
        }

        return false;
    }

    private class GetDataFromDatabase extends AsyncTask<Void, Void, ArrayList<User>> {

        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            return databaseHelper.getAllUsers();
        }

        @Override
        protected void onPostExecute(ArrayList<User> users) {
            super.onPostExecute(users);
            progressBar.setVisibility(View.GONE);
            list.clear();
            list.addAll(users);

            Log.i("TAKA", list.size()+"");
        }
    }
}