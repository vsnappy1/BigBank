package com.example.bigbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bigbank.Adapter.UserAdapter;
import com.example.bigbank.Database.DatabaseHelper;
import com.example.bigbank.Model.User;

import java.util.ArrayList;

public class AdminDashboardActivity extends AppCompatActivity {

    Button buttonFaceRecognition;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    UserAdapter adapter;
    ArrayList<User> list;

    DatabaseHelper databaseHelper;

    public static User user = null;
    public static boolean isDatabaseUpdated = false;


    @Override
    protected void onStart() {
        super.onStart();

        if(isDatabaseUpdated){
            isDatabaseUpdated = false;
            progressBar.setVisibility(View.VISIBLE);
            list.clear();
            adapter.notifyDataSetChanged();
            new GetDataFromDatabase().execute();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        databaseHelper = new DatabaseHelper(this);

        buttonFaceRecognition = findViewById(R.id.buttonFaceRecognition);
        recyclerView = findViewById(R.id.recyclerViewAdminDashboard);
        progressBar = findViewById(R.id.progressBarAdminDashboard);

        list = new ArrayList<>();

        adapter = new UserAdapter(this, list);
        adapter.setOnItemClickListener(new UserAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, UserDetailActivity.class);
                user = list.get(position);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        new GetDataFromDatabase().execute();
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
            adapter.notifyDataSetChanged();

            Log.i("TAKA", list.size()+"");
        }
    }
}