package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ExpenseList extends AppCompatActivity {

    Button back;
    RecyclerView recyclerView;
    FloatingActionButton btnGotoAdd;
    Database db;
    ArrayList<String> uType, uAmount, uDescription;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_list);

        back = findViewById(R.id.btnExpListGoBack);
        recyclerView = findViewById(R.id.expensesRecyclerView);
        btnGotoAdd = findViewById(R.id.floatingActionButton);
        db = new Database(this);

        btnGotoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpenseList.this, AddExpense.class));
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpenseList.this, MainActivity.class));
            }
        });

        uType = new ArrayList<>();
        uAmount = new ArrayList<>();
        uDescription = new ArrayList<>();

        myAdapter = new MyAdapter(ExpenseList.this, uType, uAmount, uDescription);
        recyclerView.setAdapter(myAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(ExpenseList.this));

        displayData();

    }

    public void displayData()
    {
        Cursor cursor = db.getData();
        if (cursor.getCount()==0)
        {
            Toast.makeText(getApplicationContext(), "No Expense Yet!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            while (cursor.moveToNext())
            {
                uType.add(cursor.getString(0));
                uAmount.add(cursor.getString(1));
                uDescription.add(cursor.getString(2));
            }
        }
    }

}