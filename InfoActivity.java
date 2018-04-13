package com.paradise.malariastressfighter.Health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paradise.malariastressfighter.R;
import com.squareup.picasso.Picasso;
public class InfoActivity extends AppCompatActivity {
    RecyclerView mBlogList;
    FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference mdatabase;
    private static final String TAG = "PhoneAuthActivity";
    private FirebaseAuth mAuth;
    Button mSignOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mBlogList = (RecyclerView)findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<RetrieveInfo> options=new FirebaseRecyclerOptions.Builder<RetrieveInfo>().setQuery(
                mdatabase,
                RetrieveInfo.class

        ).build();

        mAdapter= new FirebaseRecyclerAdapter<RetrieveInfo,BlogViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position, @NonNull RetrieveInfo model) {

                model.setImageView(model.getImageView());
                holder.setName(model.getName());
                holder.setId(model.getEmail());

            }


            @Override
            public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row,parent,false);
                return new BlogViewHolder(v);
            }
        };

        mBlogList.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
//
mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        mAdapter.stopListening();
        super.onStop();
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView= itemView;

        }
        public void setName(String firstName){
            TextView sp_name = mView.findViewById(R.id.nameTxt);
            sp_name.setText(firstName);
        }
        public void setId(String email) {
            TextView sp_Id = mView.findViewById(R.id.insertEmail);
            sp_Id.setText(email);
        }
//        public void setImage(Context cxt ,String imageView){
//            ImageView sp_image = mView.findViewById(R.id.doctorImages);
//            Picasso.with(cxt).load(imageView).placeholder(R.drawable.place_holder).into(sp_image);
//
//
//
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(InfoActivity.this, UploadInfo.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}