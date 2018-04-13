package com.paradise.malariastressfighter.Health;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paradise.malariastressfighter.R;

public class Home extends AppCompatActivity {

    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("UpdateInfo");
        mDatabase.keepSynced(true);

        mBlogList=(RecyclerView)findViewById(R.id.recyclerView);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager( new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        //FirebaseRecyclerAdapter<Blog, BlogViewHolders>firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Blog, BlogViewHolders>(){
        //Popular
       // }
    }
}
