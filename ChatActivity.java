package com.paradise.malariastressfighter.Chat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


/*import com.crycetruly.a4app.utils.GetShortTimeAgo;

import com.crycetruly.a4app.utils.ImageManager;*/

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.paradise.malariastressfighter.R;
import com.paradise.malariastressfighter.utils.GetShortTimeAgo;
import com.paradise.malariastressfighter.utils.ImageManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.media.MediaRecorder.VideoSource.CAMERA;


public class ChatActivity extends AppCompatActivity {
    public static final int READ_EXTERNAL_STORAGE = 0;
    private static final int TOTAL_ITEMS_TO_LOAD = 15;
    private static final String TAG = "ChatActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION_ACCESS = ACCESS_COARSE_LOCATION;
    private static final String STORAGE_PERMISSIN = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int CAMERA_REQUEST = 4;
    private static final int GALLERY_INTENT = 2;
    private static final int PERMISSION_INT = 1234;
    private static final int PLACE_PICKER_REQUEST = 888;
    private static int mCurrentPage = 1;
    private final List<Message> messagesList = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    LocationManager locationManager;
    Location loc;
    //---------------------------------------images------------------//
    Button select_image, upload_button;
    ImageView user_image;
    TextView title;
    //Add Emojicon
   EditText emojiconEditText;
    ImageView emojiButton, submitButton;
    RelativeLayout chating;
    private double mProgress = 0;
    private ProgressDialog dialog;
    private StorageReference storageReference;
    private LinearLayout mRevealView;
    private boolean hidden = true;
    private ImageButton gallery_btn, photo_btn, video_btn, audio_btn, location_btn, contact_btn;
    //new solution
    private int itemPosition = 0;
    private String mChatUser;
    private DatabaseReference mRootRef;
    private TextView mTitleView;
    private TextView mLastSeenView;
    private CircleImageView mProfileImage;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;
    private DatabaseReference mUserRf, userPoints;
    private ImageButton mChatAddBtn;
    private ImageButton mChatSendBtn;
    private EditText mChatMessageView;
    private RecyclerView mMessagesList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLinearLayout;
    private MessagesTestAdapter mAdapter;
    private ImageButton add;
    private Context mContext;
    private ProgressDialog progressDialog;
    private StorageReference mStorage;
    private String lastKey;
    private String mPrevKey;
    private ImageButton pickimage;
    private boolean mPermissionGranted = false;
    private String currentUserDb;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;
    private double points;

    //----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        mContext = ChatActivity.this;
        linearLayout = findViewById(R.id.linearLayout);
        getLocationPersion();
        progressDialog = new ProgressDialog(mContext);
        Toolbar mChatToolbar =(Toolbar) findViewById(R.id.apptoolbar);
        setSupportActionBar(mChatToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        chating = findViewById(R.id.chating);
        submitButton = findViewById(R.id.chat_send_btn);
        emojiconEditText = findViewById(R.id.emojicon_edit_text);

        mRootRef = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        mUserRf = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUserId);
        mUserRf.child("online").setValue("true");
        final String user_id = getIntent().getStringExtra("user_id");
        mChatUser = user_id;
//--------------CHANGE THE READ STATUS------------------
        mRootRef.child("Chats").child(mCurrentUserId).child(mChatUser).child("seen").setValue(true);
        currentUserDb = "Users";

        mStorage = FirebaseStorage.getInstance().getReference();


        //-------------FIX BACK----------------------//
        pickimage = findViewById(R.id.action_clip);
        pickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: trying to choose media");
                AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
                CharSequence[] options = {"Gallery", "Camera"};
                ad.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            //Check for Runtime Permission
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                                }
                            } else {
                                callgalary();
                            }
                        }

                        if (i == 1) {
                            //Check for camera Runtime Permission
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA);
                                }
                            } else {
                                callCamera();


                            }
                        }

                        if (i == 2) {
                            //Check for camera Runtime Permission
                            getLocationPersion();
//                            getDeviceLocation();
                            //todo might need this


                        }

                    }
                }).show();


            }
        });

        mChatSendBtn = findViewById(R.id.chat_send_btn);
        mChatMessageView = findViewById(R.id.emojicon_edit_text);
        mLastSeenView = findViewById(R.id.custom_bar_seen);
        mProfileImage = findViewById(R.id.custom_bar_image);


        mAdapter = new MessagesTestAdapter(mContext, messagesList);

        mMessagesList = findViewById(R.id.messages_list);
        // swipeRefreshLayout = findViewById(R.id.swipe_layout);
        mLinearLayout = new LinearLayoutManager(this);

        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);

        mMessagesList.setAdapter(mAdapter);

        loadMessages();
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance().getReference().child("Users").child(mChatUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String name = dataSnapshot.child("name").getValue().toString();
                } catch (NullPointerException eh) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//-------------------------------CREATION OF THE CHAT NODE TO QUERY FOR THE CHATS ACTIVITY--------------------------//
        mRootRef.child("Chat").child(mCurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChild(mChatUser)) {

                    Map<String, Object> chatAddMap = new HashMap<>();
                    chatAddMap.put("seen", false);
                    chatAddMap.put("timestamp", ServerValue.TIMESTAMP);
                    Map<String, Object> chatUserMap = new HashMap<String, Object>();
                    chatUserMap.put("Chat/" + mCurrentUserId + "/" + mChatUser, chatAddMap);
                    chatUserMap.put("Chat/" + mChatUser + "/" + mCurrentUserId, chatAddMap);

                    mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if (databaseError != null) {

                                Log.d("CHAT_LOG", databaseError.getMessage());

                            }

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mChatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = emojiconEditText.getText().toString();
                if (!message.equals("")) {
                    sendMessage(message, "text");
                }


            }
        });


    }

    //--------------------LOOKING FOR THE CAMERA--------------------------//
    private void callCamera() {
        Log.d(TAG, "onClick: starting camera");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);


    }

    //----------------GALLERY FOR IMG
    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri mImageUri = data.getData();
            StorageReference filePath = mStorage.child("MessageImageShares").child(mAuth.getCurrentUser().getUid()
            ).child(mChatUser).child(String.valueOf(ServerValue.TIMESTAMP));

            progressDialog.setMessage("Uploading Image....");
            progressDialog.show();
            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();  //Ignore This error
                    sendMessage(downloadUri.toString(), "image");

                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double currentProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    if (currentProgress > (mProgress + 15)) {
                        mProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Log.d(TAG, "onProgress: upload is " + mProgress + "& done");
                        Toast.makeText(mContext, mProgress + "%", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            if (requestCode == CAMERA_REQUEST) {
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "onActivityResult: Getting from camera");
                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    byte[] bytes = ImageManager.getBytesFromBitmap(bitmap, 100);

                    StorageReference filePath = mStorage.child("MessageImageShares").child(mAuth.getCurrentUser().getUid()
                    ).child(mChatUser).child(String.valueOf(ServerValue.TIMESTAMP));

                    progressDialog.setMessage("Uploading Image....");
                    progressDialog.show();
                    filePath.putBytes(bytes).addOnSuccessListener(new OnSuccessListener <UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUri = taskSnapshot.getDownloadUrl();  //Ignore This error
                            sendMessage(downloadUri.toString(), "image");
                            progressDialog.dismiss();
                        }
                    });
                }

            }
        }
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
//todo location
            }

        }
    }




    private void loadMessages() {

        Query mMessageQuery = mRootRef.child("messages").child(mCurrentUserId).child(mChatUser);
       mMessageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Message message = dataSnapshot.getValue(Message.class);
                String messageKeey = dataSnapshot.getKey();
                messagesList.add(message);
                itemPosition++;
                if (itemPosition == 1) {

                    lastKey = messageKeey;
                    mPrevKey = messageKeey;
                }
                mAdapter.notifyDataSetChanged();
                mMessagesList.scrollToPosition(messagesList.size() - 1);
//                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void sendMessage(String message, String type) {
        Log.d(TAG, "sendMessage: sending " + message + " of type " + type);
        String mes = message.trim();
        if (!TextUtils.isEmpty(mes)) {
            String currentDate = DateFormat.getDateTimeInstance().format(new Date());
            String current_user_ref = "messages/" + mCurrentUserId + "/" + mChatUser;
            String chat_user_ref = "messages/" + mChatUser + "/" + mCurrentUserId;

            DatabaseReference user_message_push = mRootRef.child("messages")
                    .child(mCurrentUserId).child(mChatUser).push();
            DatabaseReference newNotificationref = mRootRef.child("messageNotifications")
                    .child(mCurrentUserId).child(mChatUser).push();
            String newNotificationId = newNotificationref.getKey();

            HashMap<String, String> notificationData = new HashMap<>();
            notificationData.put("from", mCurrentUserId);
            notificationData.put("message", mes);
            String push_id = user_message_push.getKey();
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("message", mes);
            messageMap.put("seen", false);
            messageMap.put("type", type);
            messageMap.put("sendDate", currentDate);
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", mCurrentUserId);
            messageMap.put("reversedTime",GetCurTime.getReversedNow());

            final Map<String, Object> messageUserMap = new HashMap<String, Object>();
            messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
            messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);
            messageUserMap.put("MessageNotifications/" + mChatUser + "/" + newNotificationId, notificationData);


            mChatMessageView.setText("");
            //-------------------------------CREATION OF THE CHAT NODE TO QUERY FOR THE CHATS ACTIVITY--------------------------//
            Map<String, Object> chatAddMap = new HashMap<>();
            chatAddMap.put("message", mes);
            chatAddMap.put("seen", false);
            chatAddMap.put("type", type);
            chatAddMap.put("sendDate", currentDate);
            chatAddMap.put("timestamp", ServerValue.TIMESTAMP);
            chatAddMap.put("from", mCurrentUserId);
            chatAddMap.put("userName", "My display name");
            //todo find aaway to enhance loading,possibly update the chat nodde with last info
            Map<String, Object> chatUserMap = new HashMap<String, Object>();
            chatUserMap.put("Chat/" + mCurrentUserId + "/" + mChatUser, chatAddMap);
            chatUserMap.put("Chat/" + mChatUser + "/" + mCurrentUserId, chatAddMap);
            mRootRef.child("Chat").child(mCurrentUserId).child(mChatUser).updateChildren(chatAddMap);

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if (databaseError != null) {

                        Log.d("CHAT_LOG", databaseError.getMessage());


                    } else {

                        //---------------update seen-----------------------//
                        mRootRef.child("Chat").child(mCurrentUserId).child(mChatUser).child("seen").setValue(true);
                        mRootRef.child("Chat").child(mCurrentUserId).child(mChatUser).child("timestamp").setValue(ServerValue.TIMESTAMP);
                        mRootRef.child("Chat").child(mChatUser).child(mCurrentUserId).child("seen").setValue(false);
                        mRootRef.child("Chat").child(mChatUser).child(mCurrentUserId).child("timestamp").setValue(ServerValue.TIMESTAMP);

                    }

                }
            });


        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
        } else {
            reference.child(currentUser.getUid()).child("online").setValue("true");
            //--------------------------------------------
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
        } else {
            reference.child(currentUser.getUid()).child("online").setValue(ServerValue.TIMESTAMP);
            //--------------------------------------------
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    //----------------LOCATION PERMISIIONS-------------------//

    public void getLocationPersion() {
        Log.d(TAG, "getLocationPersion: getting permissions");
        String[] permissions = {ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(mContext, COURSE_LOCATION_ACCESS) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(mContext, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "getLocationPersion: Permissions granted");
                mPermissionGranted = true;
            } else {
                Log.d(TAG, "getLocationPersion: Permissions not .Now requesting");
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_INT);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionGranted = false;
        switch (requestCode) {
            case PERMISSION_INT:
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        mPermissionGranted = grantResult == PackageManager.PERMISSION_GRANTED;
                    }
                }
        }
    }

    @SuppressLint("WrongConstant")
    public int getTask() {
        Log.d(TAG, "getTask: " + getIntent().getFlags());
        return getIntent().getFlags();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
