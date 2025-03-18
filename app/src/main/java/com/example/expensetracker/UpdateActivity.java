package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    Button back, update;
    EditText txtUpName, txtUpAge, txtUpMonthInc, txtUpTotalSaving;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_info);

        db = new Database(this);
        back = findViewById(R.id.btnUpdateGoBack);
        update = findViewById(R.id.btnUpdate);
        txtUpName = findViewById(R.id.txtUpName);
        txtUpAge = findViewById(R.id.txtUpAge);
        txtUpMonthInc = findViewById(R.id.txtUpMonthInc);
        txtUpTotalSaving = findViewById(R.id.txtUpTotalSaving);

        // Populate fields with current user data
        loadUserData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtUpName.getText().toString();
                String income = txtUpMonthInc.getText().toString();
                String savings = txtUpTotalSaving.getText().toString();

                // Update user information without the age parameter
                boolean isUpdated = db.updateUserData(name, income, savings);
                if (isUpdated) {
                    Toast.makeText(UpdateActivity.this, "Information Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadUserData() {
        Cursor cursor = db.getUserData();

        // Check if the cursor contains any data
        if (cursor != null && cursor.moveToFirst()) {
            // Safely fetch the column indexes
            int nameIndex = cursor.getColumnIndex("username");
            int incomeIndex = cursor.getColumnIndex("monthlyIncome");
            int savingsIndex = cursor.getColumnIndex("currentBalance");

            // Check if column indexes are valid (they should not return -1)
            if (nameIndex != -1 && incomeIndex != -1 && savingsIndex != -1) {
                String name = cursor.getString(nameIndex);
                String income = cursor.getString(incomeIndex);
                String savings = cursor.getString(savingsIndex);

                // Populate the fields with the data
                txtUpName.setText(name);
                txtUpMonthInc.setText(income);
                txtUpTotalSaving.setText(savings);
            } else {
                // Handle error if column indexes are invalid
                Toast.makeText(UpdateActivity.this, "Error: Invalid column names in the database", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle case where no data is found in the cursor
            Toast.makeText(UpdateActivity.this, "No user data found", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }


}
