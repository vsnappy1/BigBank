package com.example.bigbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bigbank.Database.DatabaseHelper;
import com.example.bigbank.Model.User;

public class BankActivity extends AppCompatActivity {


    User currentUser = null;
    boolean isVerified = false;
    DatabaseHelper databaseHelper;
    AlertDialog cvcDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        databaseHelper = new DatabaseHelper(this);

        currentUser = UserLoginActivity.LoggedInUser;

        if(currentUser.getUsingFirstTime() == 1){
            firstTimeUsing();
        }
    }

    void firstTimeUsing(){


        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.dialog_cvc, null);
        EditText editTextCvc =    view.findViewById(R.id.editTextCvc);
        Button buttonCvcConfirm = view.findViewById(R.id.buttonConfrimCvc);

        buttonCvcConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cvc = editTextCvc.getText().toString().trim();

                if(cvc.equals(currentUser.getCvc())){
                    Toast.makeText(BankActivity.this, "Card activated", Toast.LENGTH_SHORT).show();
                    buttonCvcConfirm.setEnabled(false);
                    isVerified = true;
                    new updateUser().execute();
                }else {
                    Toast.makeText(BankActivity.this, "Incorrect cvc", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cvcDialog = new AlertDialog.Builder(this).create();
        cvcDialog.setView(view);
        cvcDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(!isVerified){
                    onBackPressed();
                }
            }
        });
        cvcDialog.show();
    }

    private class updateUser extends AsyncTask<String, Void, Long> {


        @Override
        protected Long doInBackground(String... strings) {
            return databaseHelper.cardReceived(currentUser.getId());
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);

            if (result == -1) {
                //Toast.makeText(getApplicationContext(), "Operation Failed", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getApplicationContext(), "Operation Successful", Toast.LENGTH_SHORT).show();
                cvcDialog.dismiss();
            }
        }
    }
}