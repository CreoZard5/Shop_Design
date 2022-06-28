package com.creolin.shop_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    static final int PICK_IMAGE_REQUEST = 1;
    Button add,upload;
    RecyclerView rvAvatar;
    ShopAdapter sAdapter;
    DatabaseReference database;
    EditText name;
    ImageView imgvProfilePic;
    private StorageTask mUploadTask;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference("Avatars");

//        database = FirebaseDatabase.getInstance().getReference("Avatars").child("CreoAV");

        mStorageRef = FirebaseStorage.getInstance().getReference("DisplayImagesShop");//the folder name
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Avatars");

        imgvProfilePic = findViewById(R.id.image);
        upload = findViewById(R.id.btn_upload);
        add = findViewById(R.id.btn_test);
        name = findViewById(R.id.edt_test);
        rvAvatar = findViewById(R.id.shop_rv);


        /*  rvAvatar.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Avatars> options
                = new FirebaseRecyclerOptions.Builder<Avatars>()
                .setQuery(database.getRoot(), Avatars.class)
                .build();
        sAdapter = new ShopAdapter(options);
        rvAvatar.setAdapter(sAdapter);*/



add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        uploadProfilePicture();

    }
});

upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        uploadFile();
    }
});
    }


    public void AlertMtd(){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        //deduct points
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to purchase?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    //uploading a profile picture
    private void uploadProfilePicture() {
        try {
            Intent image = new Intent();
            image.setType("image/*");
            image.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(image, "Select a profile picture"), PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Picture cannot be found, consider trying again or use another image. \nError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //intent to move the the uploading image activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(imgvProfilePic);
            //displaying chosen image
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }//getting file type
    //Method to upload profile picture
    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));//getting a reference of the file

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            try {


                                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                                        Toast.makeText(MainActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                                    } else {

                                        String Name =  name.getText().toString();
                                        Avatars A= new Avatars(mStorageRef.getDownloadUrl().toString(),Name,false,false);


                                        mDatabaseRef.child(Name).setValue(A);

                                        Toast.makeText(MainActivity.this, "User information has been captured", Toast.LENGTH_SHORT).show();

                                        Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                                    }


                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Sign up was unsuccessful, please try again \nError:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }


    }
}