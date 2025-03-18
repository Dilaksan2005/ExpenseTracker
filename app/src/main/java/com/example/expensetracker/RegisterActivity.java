package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    TextView tv1;
    EditText edUsername;
    EditText edPassword;
    EditText edMonthlyIncome;
    EditText edCurrentBalance;

    EditText edConfirmPass;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tv1 = findViewById(R.id.textViewExistingUser);
        edUsername = findViewById(R.id.RegUsername);
        edPassword = findViewById(R.id.RegPassword);
        edMonthlyIncome = findViewById(R.id.RegMonthlyIncome);
        edCurrentBalance = findViewById(R.id.RegCurrentBalance);
        edConfirmPass = findViewById(R.id.regConfirmPassword);
        btn = findViewById(R.id.btnRegister);


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                String confirmPass = edConfirmPass.getText().toString();
                String monthlyIn = edMonthlyIncome.getText().toString();
                String curBalance = edCurrentBalance.getText().toString();

                Database db = new Database(getApplicationContext(), "ExpenseTracker", null,1);

                if(username.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || monthlyIn.isEmpty() || curBalance.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "please fill all details", Toast.LENGTH_SHORT).show();
                } else if (password.compareTo(confirmPass)==0) {
                    db.register(username, password, monthlyIn, curBalance);
                    Toast.makeText(getApplicationContext(), "Record inserted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(), "password didn't match confirm password", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}