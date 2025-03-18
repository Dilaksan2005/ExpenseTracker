package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {
    Button back;
    TextView totalExpenseView, monthlyIncomeView, balanceView;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        back = findViewById(R.id.btnSumGoBack);

        db = new Database(this);

        // Initialize TextViews
        totalExpenseView = findViewById(R.id.sumTotalExpView);
        monthlyIncomeView = findViewById(R.id.sumMonIncView);
        balanceView = findViewById(R.id.sumBalanceView);

        double totalExpense = db.getTotalExpenses();
        String monthlyIncome = db.getMonthlyIncome();
        String currentBalance = db.getCurrentBalance();



        totalExpenseView.setText(String.valueOf(totalExpense));
        monthlyIncomeView.setText(monthlyIncome);
        balanceView.setText(currentBalance);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SummaryActivity.this, MainActivity.class));
            }
        });
    }
}