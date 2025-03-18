package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class AddExpense extends AppCompatActivity {

    Button back, btnAdd;
    Spinner spinner;
    EditText am, des;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        back = findViewById(R.id.btnAddExpenseGoBack);
        spinner = findViewById(R.id.spinnerType);
        am = findViewById(R.id.addExpenseAmount);
        des = findViewById(R.id.addExpenseDescription);
        btnAdd = findViewById(R.id.btnAddEx);
        db = new Database(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedType = spinner.getSelectedItem().toString();
                String amount = am.getText().toString();
                String description = des.getText().toString();

                Boolean saveData = db.addExp(selectedType, amount, description);
                if (TextUtils.isEmpty(selectedType) || TextUtils.isEmpty(amount)|| TextUtils.isEmpty(description))
                {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (saveData)
                    {
                        Toast.makeText(getApplicationContext(), "Expense Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddExpense.this, ExpenseList.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddExpense.this, MainActivity.class));
            }
        });

    }
}