package com.paradise.malariastressfighter.Others;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paradise.malariastressfighter.Health.StudentBoardActivity;
import com.paradise.malariastressfighter.R;

import java.util.HashMap;

public class Doctors extends AppCompatActivity {
    private Button mFirebaseBtn;
    private DatabaseReference mDatabase;
    private EditText mPhoneNumber;
    private EditText mNameField;

    private EditText mHospital;

    private EditText mSpeciality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        mFirebaseBtn = (Button) findViewById(R.id.sellticket);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        mPhoneNumber = (EditText) findViewById(R.id.code);
        mNameField = (EditText) findViewById(R.id.name);

        mHospital = (EditText) findViewById(R.id.contact);
        mSpeciality = (EditText) findViewById(R.id.amount);




        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the input from the user and pursing them to strings
                String phone = mPhoneNumber.getText().toString().trim();
                String name = mNameField.getText().toString().trim();

                String hospital = mHospital.getText().toString().trim();
                String speciality = mSpeciality.getText().toString().trim();



                //putting data data into database
                HashMap<String, String> datamap = new HashMap<String, String>();
                datamap.put("doctor_phone", phone);
                datamap.put("doctor_name", name);

                datamap.put("doctor_hospital", hospital);
                datamap.put("doctor_speciality", speciality);




                // ending to the firebase database
                mDatabase.child("doctors").push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Doctors.this, "Data Submittion sucecssfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Doctors.this, "Data Submittion failed", Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });
    }

    // oncreate class ends here
    public void Backs(View view) {
        Intent bac = new Intent(Doctors.this, StudentBoardActivity.class);
        startActivity(bac);
    }

    public void cancl(View view) {
        Intent Cancel = new Intent(Doctors.this, StudentBoardActivity.class);
        startActivity(Cancel);
    }

    // public void sellTicket(View view) {
    //  Intent selling = new Intent(SellTicket.this, SellTicket.class);
    //  startActivity(selling);

    //  Toast.makeText(SellTicket.this, "Data Submitted", Toast.LENGTH_LONG).show();
    // }
}
