package com.creolin.shop_design;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
//        database = FirebaseDatabase.getInstance().getReference("Avatars").child("CreoAV");

        btnBuy = findViewById(R.id.btn_buy);
        btnSetDp = findViewById(R.id.btn_dp);
        rvAvatar = findViewById(R.id.collectionRV);

        rvAvatar.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Avatars> options
                = new FirebaseRecyclerOptions.Builder<Avatars>()
                .setQuery(database, Avatars.class)
                .build();
        sAdapter = new ShopAdapter(options);
        rvAvatar.setAdapter(sAdapter);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertMtd();
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
}