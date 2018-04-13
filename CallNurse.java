package com.paradise.malariastressfighter.Health;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paradise.malariastressfighter.R;

public class CallNurse extends AppCompatActivity {
    Button b;
    Button c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_nurse);
//        b = (Button)findViewById(R.id.btncalling);
//        c =(Button) findViewById(R.id.btncalling2);
//        b.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("MissingPermission")
//            @Override
//            public void onClick(View view) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:0771424959"));
//                startActivity(callIntent);
//            }
//        });
//        c.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("MissingPermission")
//            @Override
//            public void onClick(View view) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:0705251168"));
//                startActivity(callIntent);
//            }
//        });
    }
}
