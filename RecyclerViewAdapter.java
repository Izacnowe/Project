package com.paradise.malariastressfighter.Health;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paradise.malariastressfighter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dell on 2/16/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    ArrayList<String> mImage = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();


    Context mContext;

    public RecyclerViewAdapter(Context mContext,ArrayList<String> mImage, ArrayList<String> name,
                               ArrayList<String> email) {
        this.mImage = mImage;
        this.name = name;
        this.email = email;


        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder:called.");
      /*  Picasso.with(mContext)
                .load(mImage.get(position))
                .placeholder(R.drawable.tels)
                .into(holder.imageView);
        holder.firstName.setText(name.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick:clicked on."+name.get(position));
                Toast.makeText(mContext, name.get(position), Toast.LENGTH_SHORT).show();

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView email,firstName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.doctorImages);
            email = itemView.findViewById(R.id.insertEmail);
            firstName = itemView.findViewById(R.id.nameTxt);


        }
    }
}
