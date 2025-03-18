package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditExpense extends AppCompatActivity {

    EditText  amount, description;
    Spinner type;
    ImageView delete, edit;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        type = findViewById(R.id.spinnertypeEd);
        amount = findViewById(R.id.editTextNumberDecimal);
        description = findViewById(R.id.editTextTextMultiLine);
        delete = findViewById(R.id.imageView2);
        edit = findViewById(R.id.imageView3);
        db = new Database(this);

        Intent intent = getIntent();

// Get the passed values
        String selectedType = intent.getStringExtra("type");
        String selectedAmount = intent.getStringExtra("amount");
        String selectedDescription = intent.getStringExtra("description");

// Set Spinner value for type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.add_expense_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

// Find the position of the type in the Spinner and set it
        if (selectedType != null) {
            int spinnerPosition = adapter.getPosition(selectedType);
            type.setSelection(spinnerPosition);
        }

// Set values for amount and description
        amount.setText(selectedAmount);
        description.setText(selectedDescription);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String des = description.getText().toString();

                Boolean DeleteData = db.DeleteExp(des);
                    if (DeleteData)
                    {
                        Toast.makeText(getApplicationContext(), "Expense Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditExpense.this, ExpenseList.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "Not Deleted", Toast.LENGTH_SHORT).show();
                    }

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedType = type.getSelectedItem().toString();
                String am = amount.getText().toString();
                String des = description.getText().toString();

                Boolean updateData = db.UpdateExp(selectedType, am, des);

                    if (updateData)
                    {
                        Toast.makeText(getApplicationContext(), "Expense Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditExpense.this, ExpenseList.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();
                    }
                }

        });

    }
}