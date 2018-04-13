package com.paradise.malariastressfighter.Health;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.paradise.malariastressfighter.Authentication.PhoneAuth;
import com.paradise.malariastressfighter.Authentication.SplashWelcome;
import com.paradise.malariastressfighter.NurseAdminActivity;
import com.paradise.malariastressfighter.Others.InboxFragment;
import com.paradise.malariastressfighter.Others.NursesFragment;
import com.paradise.malariastressfighter.Others.PostActivity;
import com.paradise.malariastressfighter.R;
import com.paradise.malariastressfighter.Others.SearchActivity;
import com.paradise.malariastressfighter.Others.StudentHomeFragment;

public class StudentBoardActivity extends AppCompatActivity {

    RecyclerView mBlogList;
    FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference mdatabase;

final Context context = this;
    private static final String TAG = "StudentBoardActivity";

    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
private FrameLayout frameLayout;
private BottomNavigationView bottomNavigationItemView;
    private FragmentManager fragmentManager=getSupportFragmentManager();
private FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.d(TAG, "onCreate: ");
        initWidgets();
        StudentHomeFragment homeFragment=new StudentHomeFragment();
        fragmentTransaction.replace(R.id.mainframe,homeFragment);
        fragmentTransaction.commit();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        if (user!= null) {

            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            Log.d(TAG, "onCreate: user logged in");
            try {
                if (user.getPhoneNumber() == null) {
                    Log.d(TAG, "onCreate: got no phone");
                    Intent intent= new Intent(StudentBoardActivity.this,NurseAdminActivity.class);
                    startActivity(intent);

                } else {
                    Log.d(TAG, "onCreate: " + user.getPhoneNumber());
                }
            } catch (NullPointerException e) {
                Intent intent= new Intent(StudentBoardActivity.this,NurseAdminActivity.class);
                startActivity(intent);
            }


            // Start of the code for retrieving
        }}

    // End of the code for retrieving
    private void initWidgets() {
        bottomNavigationItemView = findViewById(R.id.bo);
        frameLayout = findViewById(R.id.mainframe);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                Log.d(TAG, "onNavigationItemReselected: ");
                fragmentTransaction = fragmentManager.beginTransaction();
                getSupportActionBar().setTitle("Home");
                if (item.getItemId() == R.id.home) {
                    StudentHomeFragment homeFragment = new StudentHomeFragment();
                    fragmentTransaction.replace(R.id.mainframe, homeFragment);
                    fragmentTransaction.commit();
                } else if (item.getItemId() == R.id.nurse) {
                    getSupportActionBar().setTitle("Nurses");
                    NursesFragment homeFragment = new NursesFragment();

                    fragmentTransaction.replace(R.id.mainframe, homeFragment);
                    fragmentTransaction.commit();
                } else if (item.getItemId() == R.id.inbox) {
                    getSupportActionBar().setTitle("My Inbox");
                    InboxFragment homeFragment = new InboxFragment();

                    fragmentTransaction.replace(R.id.mainframe, homeFragment);
                    fragmentTransaction.commit();
                }


                return true;
            }
        });

    }



    private void sendout() {
        Log.d(TAG, "sendout: ");
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "You need to add your phone", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), PhoneAuth.class);
                Toast.makeText(getBaseContext(), "Signed out successfully", Toast.LENGTH_SHORT).show();
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });    }

//    public void help(View view) {
//        Intent intent = new Intent(StudentBoardActivity.this, UploadInfo.class);
//        startActivity(intent);
//    }
//
//    public void viewHelp(View view) {
//        Intent intent = new Intent(StudentBoardActivity.this, InfoActivity.class);
//        startActivity(intent);
//    }
//
//    public void logout(View view) {
//        FirebaseAuth.getInstance().signOut();
//        sendToStart();
//
//    }
//
//    public void chat(View view) {
//        Intent intent = new Intent(StudentBoardActivity.this, ChatActivity.class);
//        startActivity(intent);
//    }
//
//    public void friends(View view) {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//
//        //setting  The Title
//        alertDialogBuilder.setTitle("Sending message will cost you money");
//
//        //Set dialog message
//        alertDialogBuilder.setMessage("Click Yes to Continue")
//.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(StudentBoardActivity.this, TextMessage.class);
//                startActivity(intent);
//            }
//        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//              dialog.cancel();
//                //StudentBoardActivity.this.finish();
//            }
//        });
//        //create alertdialog
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        //show it
//        alertDialog.show();
//
//    }
//
//    public void Call(View view) {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//
//        //setting  The Title
//        alertDialogBuilder.setTitle("Calling costs you money");
//
//        //Set dialog message
//        alertDialogBuilder.setMessage("Click Yes to Continue")
//                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(StudentBoardActivity.this, CallNurse.class);
//                startActivity(intent);
//            }
//        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//                //StudentBoardActivity.this.finish();
//            }
//        });
//        //create alertdialog
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        //show it
//        alertDialog.show();
//    }

    private void sendToStart() {

        Intent startIntent = new Intent(StudentBoardActivity.this, SplashWelcome.class);
        startActivity(startIntent);
        finish();

    }

    //Android Activity Lifecycle Method
// This is only called once, the first time the options menu is displayed.
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.post) {
            Intent startIntent = new Intent(StudentBoardActivity.this, PostActivity.class);
            startActivity(startIntent);
        }
        if(item.getItemId() == R.id.search_drug) {
            startActivity(new Intent(this, SearchActivity.class));
        }
        if(item.getItemId() == R.id.signout){

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }

        return true;
    }
}
