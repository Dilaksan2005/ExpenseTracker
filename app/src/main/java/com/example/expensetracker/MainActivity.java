package com.example.expensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button btnAddEx, btnExpList, btnSum, btnGra, btnSet, btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddEx = findViewById(R.id.btnAddExpense);
        btnExpList = findViewById(R.id.btnExpenseList);
        btnSum = findViewById(R.id.btnSummary);
        btnGra = findViewById(R.id.btnGraph);
        btnSet = findViewById(R.id.btnSettings);
        btnLog = findViewById(R.id.btnLogout);

        btnAddEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddExpense.class));
            }
        });

        btnExpList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExpenseList.class));
            }
        });

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SummaryActivity.class));
            }
        });

        btnGra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GraphActivity.class));
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(); // Call the logout method
            }
        });

    }

    // In your current Activity (for example, MainActivity or HomeActivity)
    public void logout() {
        // Get shared preferences (session data storage)
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Clear all session data (like the login status)
        editor.clear(); // This clears all the data in "UserSession"
        editor.apply(); // Apply the changes

        // Optionally, you can also logout from any authentication systems like FirebaseAuth
        // FirebaseAuth.getInstance().signOut();

        // Redirect the user to the Login Activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

        // Finish the current activity so that user cannot go back to it
        finish();
    }

}