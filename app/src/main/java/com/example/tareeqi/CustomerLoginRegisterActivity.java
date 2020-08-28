package com.example.tareeqi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class CustomerLoginRegisterActivity extends AppCompatActivity {

    private Button CustomerLoginButton;
    private Button CustomerRegisterButton;
    private TextView CustomerStatus;
    private TextView CustomerRegisterLink;
    private EditText EmailCustomer;
    private EditText PasswordCustomer;
    private ProgressBar CustomerBar;
    private String OnlineCustomerID;


    private FirebaseAuth mAuth;
    private DatabaseReference CustomerDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);

        mAuth=FirebaseAuth.getInstance();
        //OnlineCustomerID=mAuth.getCurrentUser().getUid();
        //CustomerDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(OnlineCustomerID);

        CustomerLoginButton =findViewById(R.id.customer_login_btn);
        CustomerRegisterButton =findViewById(R.id.customer_register_btn);
        CustomerStatus =findViewById(R.id.customer_status);
        CustomerRegisterLink =findViewById(R.id.register_customer_link);
        EmailCustomer=findViewById(R.id.email_customer);
        PasswordCustomer=findViewById(R.id.password_customer);
        CustomerBar=findViewById(R.id.customer_bar);



        CustomerRegisterButton.setVisibility(View.INVISIBLE);
        CustomerRegisterButton.setEnabled(false);

        CustomerRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerLoginButton.setVisibility(View.INVISIBLE);
                CustomerRegisterLink.setVisibility(View.INVISIBLE);
                CustomerStatus.setText("Register customer");

                CustomerRegisterButton.setVisibility(View.VISIBLE);
                CustomerRegisterButton.setEnabled(true);


            }
        });

        CustomerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=EmailCustomer.getText().toString();
                String password=PasswordCustomer.getText().toString();

                RegisterCustomer(email,password);
            }
        });


        CustomerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=EmailCustomer.getText().toString();
                String password=PasswordCustomer.getText().toString();

                SignInCustomer(email,password);
            }
        });


    }



    private void SignInCustomer(String email, String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this,"Please Enter Email...",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this,"Please Enter Password...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            CustomerBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {

                        Intent intent=new Intent(CustomerLoginRegisterActivity.this,placesActivity.class);
                        intent.putExtra("type","Customers");
                        startActivity(intent);
                        Toast.makeText(CustomerLoginRegisterActivity.this,"Customer Signed In successful",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this,"Signing In Unsuccessful, please try again...",Toast.LENGTH_SHORT).show();
                    }
                    CustomerBar.setVisibility(View.INVISIBLE);
                }
            });
        }

    }



    private void RegisterCustomer(String email, String password) {

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this,"Please Enter Email...",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(CustomerLoginRegisterActivity.this,"Please Enter Password...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            CustomerBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        OnlineCustomerID=mAuth.getCurrentUser().getUid();
                        CustomerDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(OnlineCustomerID);
                        CustomerDatabaseRef.setValue(true);


                        Intent intent=new Intent(CustomerLoginRegisterActivity.this,placesActivity.class);
                        intent.putExtra("type","Customers");
                        startActivity(intent);
                        Toast.makeText(CustomerLoginRegisterActivity.this,"Customer registered successful",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(CustomerLoginRegisterActivity.this,"Registration Unsuccessful, please try again...",Toast.LENGTH_SHORT).show();
                    }
                    CustomerBar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}