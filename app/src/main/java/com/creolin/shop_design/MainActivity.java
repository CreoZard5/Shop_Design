package com.creolin.shop_design;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageButton btnBuy, btnSetDp;
    RecyclerView rvAvatar;
    ShopAdapter sAdapter;
    DatabaseReference database;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       database = FirebaseDatabase.getInstance().getReference("Avatars");
//       database = FirebaseDatabase.getInstance().getReference("Avatars").child("creolin avatar");




       btnBuy = findViewById(R.id.btn_buy);
       btnSetDp = findViewById(R.id.btn_dp);
       rvAvatar = findViewById(R.id.collectionRV);

       database.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
//               if (snapshot.exists()){
//                   snapshot.getValue();
//               }
//               snapshot.getRef();


               Query query = FirebaseDatabase.getInstance().getReference("Avatars");


               rvAvatar.setLayoutManager(new LinearLayoutManager(MainActivity.this));
               FirebaseRecyclerOptions<Avatars> options
                       = new FirebaseRecyclerOptions.Builder<Avatars>().setQuery(snapshot.getRef(), Avatars.class).build();
               sAdapter = new ShopAdapter(options);
               rvAvatar.setAdapter(sAdapter);



           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });



//        btnBuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertMtd();
//            }
//        });
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
}