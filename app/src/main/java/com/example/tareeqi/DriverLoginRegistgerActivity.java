package com.example.tareeqi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginRegistgerActivity extends AppCompatActivity {

    private Button DriverLoginButton;
    private Button DriverRegisterButton;
    private TextView DriverStatus;
    private TextView DriverRegisterLink;
    private EditText EmailDriver;
    private EditText PasswordDriver;
    private ProgressBar DriverBar;
    private DatabaseReference DriverDatabaseRef;
    private String onlineDriverID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_registger);

        mAuth=FirebaseAuth.getInstance();


        DriverLoginButton =findViewById(R.id.driver_login_btn);
        DriverRegisterButton=findViewById(R.id.driver_register_btn);
        DriverStatus =findViewById(R.id.driver_status);
        DriverRegisterLink =findViewById(R.id.register_driver_link);
        EmailDriver=findViewById(R.id.email_driver);
        PasswordDriver=findViewById(R.id.password_driver);
        DriverBar=findViewById(R.id.driver_bar);

        DriverRegisterButton.setVisibility(View.INVISIBLE);
        DriverRegisterButton.setEnabled(false);

        DriverRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DriverLoginButton.setVisibility(View.INVISIBLE);
                DriverRegisterLink.setVisibility(View.INVISIBLE);
                DriverStatus.setText("Register driver");

                DriverRegisterButton.setVisibility(View.VISIBLE);
                DriverRegisterButton.setEnabled(true);
            }
        });

        DriverRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=EmailDriver.getText().toString();
                String password=PasswordDriver.getText().toString();

                RegisterDriver(email,password);
            }
        });

        DriverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=EmailDriver.getText().toString();
                String password=PasswordDriver.getText().toString();

                SignInDriver(email,password);
            }
        });
    }



    private void SignInDriver(String email, String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginRegistgerActivity.this,"Please Enter Email...",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginRegistgerActivity.this,"Please Enter Password...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            DriverBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        DriverBar.setVisibility(View.INVISIBLE);

                        Intent driverIntent=new Intent(DriverLoginRegistgerActivity.this, placesActivity.class);
                        driverIntent.putExtra("type","Drivers");
                        startActivity(driverIntent);
                        Toast.makeText(DriverLoginRegistgerActivity.this,"Driver Signed In successfully",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(DriverLoginRegistgerActivity.this,"signing in Unsuccessful, please try again...",Toast.LENGTH_SHORT).show();
                        DriverBar.setVisibility(View.INVISIBLE);

                        Intent intent=new Intent(DriverLoginRegistgerActivity.this,placesActivity.class);
                        intent.putExtra("type","Drivers");
                        startActivity(intent);
                    }

                }
            });
    }}



    private void RegisterDriver(String email, String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginRegistgerActivity.this,"Please Enter Email...",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginRegistgerActivity.this,"Please Enter Password...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            DriverBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        onlineDriverID=mAuth.getCurrentUser().getUid();
                        DriverDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(onlineDriverID);
                        DriverDatabaseRef.setValue(true);

                        DriverBar.setVisibility(View.INVISIBLE);

                        Intent intent=new Intent(DriverLoginRegistgerActivity.this,placesActivity.class);
                        intent.putExtra("type","Drivers");
                        startActivity(intent);
                        Toast.makeText(DriverLoginRegistgerActivity.this,"Driver registered successfully",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(DriverLoginRegistgerActivity.this,"Registration Unsuccessful, please try again...",Toast.LENGTH_SHORT).show();
                        DriverBar.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }
    }
}