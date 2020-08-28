package com.example.tareeqi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private Button welcomeDriver;
    private Button welcomeCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

            welcomeDriver = findViewById(R.id.welcome_driver_btn);
        welcomeCustomer = findViewById(R.id.welcome_customer_btn);

        welcomeDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginRegisterDriver=new Intent(WelcomeActivity.this,DriverLoginRegistgerActivity.class);
                startActivity(LoginRegisterDriver);
            }
        });

        welcomeCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginRegisterCustomer=new Intent(WelcomeActivity.this,CustomerLoginRegisterActivity.class);
                startActivity(LoginRegisterCustomer);
            }
        });
    }
    }