package com.example.tareeqi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class placesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String getType;
    private String place;
    private FirebaseAuth mAuth;

    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        getType=getIntent().getStringExtra("type");


        mAuth=FirebaseAuth.getInstance();
        UID=mAuth.getCurrentUser().getUid();


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Button button=(Button)findViewById(R.id.location_btn);

        spinner.setOnItemSelectedListener(this);




        List<String> places = new ArrayList<String>();
        places.add("Amman/North Park");
        places.add("Amman/7th Circle");
        places.add("Amman/Abdoun");
        places.add("Irbid/Just University");
        places.add("Irbid/University Street");
        places.add("Irbid/Aydoun");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, places);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String,Object> placeMap=new HashMap<>();
                placeMap.put("place",place);
                if(getType.equals("Drivers")) {
                    FirebaseDatabase.getInstance().getReference().child("Drivers").child(UID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(UID).updateChildren(placeMap);
                                Intent intent = new Intent(placesActivity.this, DriversMapActivity.class);
                                intent.putExtra("type","Drivers");
                                startActivity(intent);
                            }
                            else{
                                FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(UID).updateChildren(placeMap);

                                Intent intent = new Intent(placesActivity.this, SettingsActivity.class);
                                intent.putExtra("type","Drivers");
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                   /* FirebaseDatabase.getInstance().getReference().child("Drivers").child(UID).updateChildren(placeMap);
                    Intent intent = new Intent(placesActivity.this, DriversMapActivity.class);
                    startActivity(intent);*/
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Customers").child(UID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(UID).updateChildren(placeMap);
                                Intent intent = new Intent(placesActivity.this, CustomersMapActivity.class);
                                intent.putExtra("type","Customers");
                                startActivity(intent);
                            }
                            else{
                                FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(UID).updateChildren(placeMap);

                                Intent intent = new Intent(placesActivity.this, SettingsActivity.class);
                                intent.putExtra("type","Customers");
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                   /* FirebaseDatabase.getInstance().getReference().child("Customers").child(UID).updateChildren(placeMap);
                    Intent intent = new Intent(placesActivity.this, CustomersMapActivity.class);
                    startActivity(intent);*/
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        place=item;
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}