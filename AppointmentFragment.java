package com.paradise.malariastressfighter.Others;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paradise.malariastressfighter.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentFragment extends Fragment {


    DatabaseReference reference;
    private CircleImageView circleImageView;
    private String phone;
    private TextView name,description;
    String id;
    Button chat,msg,call;
    private static final String TAG = "AppointmentFragment";

    public AppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nurses, container, false);
        chat = v.findViewById(R.id.chat);
        Log.d(TAG, "onCreateView: ");
        msg = v.findViewById(R.id.text);
        call = v.findViewById(R.id.call);
        circleImageView = v.findViewById(R.id.ci);
        name = v.findViewById(R.id.name);
        description = v.findViewById(R.id.desc);
        reference = FirebaseDatabase.getInstance().getReference().child("doctors");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot);

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Picasso.get().load(dataSnapshot1.child("image").getValue().toString()).into(circleImageView);
                    description.setText(dataSnapshot1.child("description").getValue().toString());
                    name.setText(dataSnapshot1.child("name").getValue().toString());
                    phone = dataSnapshot1.child("phone").getValue().toString();
                    id = dataSnapshot1.getKey();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }
    }
