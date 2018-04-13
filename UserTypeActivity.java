package com.paradise.malariastressfighter.Authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.paradise.malariastressfighter.R;

public class UserTypeActivity extends AppCompatActivity {
    private Button   btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.chooseemail);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserTypeActivity.this, AdminEmailLoginActivity.class);
                startActivity(intent);

            }
        });
    }
    public void loginStudent(View view) {
        Intent intent = new Intent(UserTypeActivity.this, PhoneAuth.class);
        startActivity(intent);
        finish();
    }

}
