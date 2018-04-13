package com.paradise.malariastressfighter.Authentication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.paradise.malariastressfighter.Health.StudentBoardActivity;
import com.paradise.malariastressfighter.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PhoneAuth extends AppCompatActivity {
    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.PhoneBuilder().build()
                            ))
                            .build(),
                    RC_SIGN_IN);
        }else {
            if (user.getEmail().length()>0){
                AuthUI.getInstance().signOut(this);
                Intent i=new Intent(this,UserTypeActivity.class);
                startActivity(i);
            }else {
                Intent i=new Intent(this,StudentBoardActivity.class);
                startActivity(i);
            }


        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Map map=new HashMap();
                map.put("name",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                map.put("image","default");
                map.put("device_token", FirebaseInstanceId.getInstance().getToken());

                ref.updateChildren(map);

                Intent i=new Intent(this,StudentBoardActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this, "User cancelled sign in", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No network connection", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, "un known errpr", Toast.LENGTH_SHORT).show();
                    return;
                }
            }


        }
    }
}