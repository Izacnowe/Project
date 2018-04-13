package com.paradise.malariastressfighter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paradise.malariastressfighter.Authentication.AdminEmailLoginActivity;
import com.paradise.malariastressfighter.Authentication.UserTypeActivity;
import com.paradise.malariastressfighter.Health.InfoActivity;
import com.paradise.malariastressfighter.Health.RetrieveInfo;
import com.paradise.malariastressfighter.Health.StudentBoardActivity;
import com.paradise.malariastressfighter.Others.AddDoctorFragment;
import com.paradise.malariastressfighter.Others.AddInformationFragment;
import com.paradise.malariastressfighter.Others.AppointmentFragment;
import com.paradise.malariastressfighter.Others.ChatFragment;
import com.paradise.malariastressfighter.Others.HomeFragment;

public class NurseAdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
FragmentManager fragmentManager=getSupportFragmentManager();
FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_admin3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Queries");
        HomeFragment homeFragment = new HomeFragment();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user.getPhoneNumber().length()>0){
            AuthUI.getInstance().signOut(this);
            Intent i=new Intent(this,UserTypeActivity.class);
            startActivity(i);
        }else {
            Intent i=new Intent(this,StudentBoardActivity.class);
            startActivity(i);
        }

        fragmentTransaction.replace(R.id.mainframe, homeFragment);
        fragmentTransaction.commit();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nurse_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handles navigation view item clicks here.
        int id = item.getItemId();
switch (id){
            case R.id.nav_camera:
                getSupportActionBar().setTitle("Home");
                getSupportActionBar().setSubtitle("All Queries");
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainframe, homeFragment);
                fragmentTransaction.commit();
                break;

            case R.id.nav_slideshow:
                getSupportActionBar().setTitle("Add Information");
                AddInformationFragment addInformationFragment = new AddInformationFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainframe, addInformationFragment);
                fragmentTransaction.commit();

                break;
            case R.id.appoint:
                getSupportActionBar().setTitle("Add Appointment");
                AppointmentFragment appointmentFragment = new AppointmentFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainframe, appointmentFragment);
                fragmentTransaction.commit();

                break;


            case R.id.doc:
                getSupportActionBar().setTitle("Add Doctor");
                AddDoctorFragment addDoctorFragment = new AddDoctorFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainframe, addDoctorFragment);
                fragmentTransaction.commit();

                break;
    case R.id.chat:
        getSupportActionBar().setTitle("Chat");
       ChatFragment chatFragment = new ChatFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainframe, chatFragment);
        fragmentTransaction.commit();

        break;

            case R.id.logout:

                logout();

                break;


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

        }

private void logout() {
    AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            Intent i = new Intent(NurseAdminActivity.this, AdminEmailLoginActivity.class);
            Toast.makeText(NurseAdminActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    });



    }


    }



