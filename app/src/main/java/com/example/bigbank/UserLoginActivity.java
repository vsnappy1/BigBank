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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bigbank.Database.DatabaseHelper;
import com.example.bigbank.Model.User;

import java.util.ArrayList;

public class UserLoginActivity extends AppCompatActivity {

    public static User LoggedInUser = null;

    EditText editTextEmail, editTextPassword;
    TextView textViewSignup;
    Button buttonLogin;
    ProgressBar progressBar;

    ArrayList<User> list;
    DatabaseHelper databaseHelper;

    boolean hasDataFetched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        list = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewSignup = findViewById(R.id.textViewSignup);
        buttonLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressBar);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String pass = editTextPassword.getText().toString().trim();

                if(email.isEmpty()){
                    Toast.makeText(UserLoginActivity.this, "Enter email please", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pass.isEmpty()){
                    Toast.makeText(UserLoginActivity.this, "Enter password please", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                login(email,pass);
            }
        });


        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this, UserSignUpActivity.class));
            }
        });

        new GetDataFromDatabase().execute();
    }

    private void login(String email, String pass) {
        boolean isValid = false;
        boolean isCardSent = false;
        int userId = -1;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getEmail().equals(email) && list.get(i).getPassword().equals(pass)){
                isValid = true;
                LoggedInUser = list.get(i);
                isCardSent = list.get(i).getCvc().equals("_");
                break;
            }
        }

        if(isCardSent){
            Toast.makeText(this, "Admin have not sent you card yet", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(isValid){
            startActivity(new Intent(getApplicationContext(),BankActivity.class));
            finish();
        }
        else {
            Toast.makeText(this, "Invalid Email & Password", Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.GONE);
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
            hasDataFetched = true;
            list.clear();
            list.addAll(users);

        }
    }
}