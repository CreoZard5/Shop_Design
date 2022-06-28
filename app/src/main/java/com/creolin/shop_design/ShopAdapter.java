package com.creolin.shop_design;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ShopAdapter extends FirebaseRecyclerAdapter<Avatars, ShopAdapter.ShopViewHolder> {

    public ShopAdapter(@NonNull FirebaseRecyclerOptions<Avatars> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull ShopAdapter.ShopViewHolder holder, int position, @NonNull Avatars model) {
        holder.name.setText(model.getImageName());
        Picasso.with(holder.ImageURL.getContext()).load(String.valueOf(model.getImageURL())).resize(300, 300).into(holder.ImageURL);
//        Glide
    }

    @NonNull
    @Override
    public ShopAdapter.ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop, parent, false);
        return new ShopAdapter.ShopViewHolder(v);
    }


    //Method to declare the view for the recycler
    public static class ShopViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView ImageURL;
        ImageButton bought, DP;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            ImageURL = itemView.findViewById(R.id.item_image);
            bought = itemView.findViewById(R.id.btn_buy);
            DP = itemView.findViewById(R.id.btn_dp);
        }
    }
}
