package com.paradise.malariastressfighter.Health;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.paradise.malariastressfighter.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.BitSet;
import java.util.jar.Attributes;

public class UploadInfo extends AppCompatActivity {

Button call;
    ImageView imageView;
    EditText email, name, desc;

    private  StorageReference storageReference;
    private DatabaseReference databaseReference;

    public static final String STORAGE_PATH ="images/";
    public static final String DATABASE_PATH ="info";
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_info);

        imageView =(ImageView)findViewById(R.id.insertImages);
        email=(EditText)findViewById(R.id.insertEmail);
        name=(EditText)findViewById(R.id.insertName);
        desc=(EditText)findViewById(R.id.insertDesc);
call=(Button)findViewById(R.id.call);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent= new Intent(UploadInfo.this, CallNurse.class);
        startActivity(intent);
    }
});
    }
    //method for browsing the image
    public void browseImages(View view){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK){
            imageUri = data.getData();
            try{
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        public String getActualImage(URI uri){
            ContentResolver  contentResolver=getContentResolver();
            MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
        }

// method for sending data to database
    public void uploadData(View view){
        if (imageUri!=null){
            //insert data

         final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading...");
            progressDialog.show();

            StorageReference reference =storageReference.child(STORAGE_PATH + System.currentTimeMillis()+"."+imageUri);
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String NAME = name.getText().toString();
                    String EMAIL =email.getText().toString();
                    String DESC =desc.getText().toString();

                    Person person = new Person(NAME,EMAIL,DESC,taskSnapshot.getDownloadUrl().toString());

                    String id =databaseReference.push().getKey();
                    databaseReference.child(id).setValue(person);

                   progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Data Uploaded", Toast.LENGTH_LONG).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double totalProgress = (100*taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                progressDialog.setMessage("Uploading... "+ (int)totalProgress);
                }
            });
        }else {
            Toast .makeText(getApplicationContext(), "Select the image first",Toast.LENGTH_LONG).show();
        }
    }


    public void viewAllData(View view){
Intent intent = new Intent(UploadInfo.this, InfoActivity.class);
startActivity(intent);
    }
}
