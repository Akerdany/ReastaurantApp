package com.example.reastaurantapp.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reastaurantapp.Classes.Food;
import com.example.reastaurantapp.FoodEditDelete;
import com.example.reastaurantapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FoodPanelRecyclerAdapter extends FirestoreRecyclerAdapter<Food, FoodPanelRecyclerAdapter.FoodViewHolder> {

    private FirebaseStorage storageRef = FirebaseStorage.getInstance();

    ViewGroup mParent;

    public FoodPanelRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final FoodViewHolder holder, int position, @NonNull Food model) {
        holder.FoodID.setText(model.getItemID());
        holder.ItemName.setText(model.getItemName());
        holder.ItemDesc.setText(model.getItemDesc());
        holder.ItemPrice.setText(String.valueOf(model.getItemPrice()));

        Glide.with(mParent.getContext()).load(Uri.parse(model.getItemPhoto())).into(holder.ItemImage);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_panel_item, parent, false);
        mParent = parent;
        final FoodViewHolder mHolder = new FoodViewHolder(view);

        mHolder.food_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), FoodEditDelete.class);

                intent.putExtra("FoodID", mHolder.FoodID.getText().toString());

                parent.getContext().startActivity(intent);
            }
        });

        return mHolder;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout food_item;

        TextView FoodID;
        TextView ItemName;
        TextView ItemPrice;
        TextView ItemDesc;

        ImageView ItemImage;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            food_item = itemView.findViewById(R.id.main_layout);

            FoodID = itemView.findViewById(R.id.FoodID);
            ItemName = itemView.findViewById(R.id.ItemName);
            ItemPrice = itemView.findViewById(R.id.ItemPrice);
            ItemDesc = itemView.findViewById(R.id.ItemDesc);

            ItemImage = itemView.findViewById(R.id.ItemImage);
        }
    }
}
