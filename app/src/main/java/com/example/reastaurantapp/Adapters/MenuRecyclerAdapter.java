package com.example.reastaurantapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reastaurantapp.Classes.Food;
import com.example.reastaurantapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MenuRecyclerAdapter extends FirestoreRecyclerAdapter<Food, MenuRecyclerAdapter.MenuHolder> {

    public MenuRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MenuHolder holder, int position, @NonNull Food model) {
        holder.ItemName.setText(model.getItemName());
        holder.ItemDesc.setText(model.getItemDesc());
        holder.ItemPrice.setText(String.valueOf(model.getItemPrice()));
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        final MenuHolder mHolder = new MenuHolder(view);
        mHolder.food_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHolder.ItemDesc.getVisibility() == View.GONE){
                    mHolder.ItemDesc.setVisibility(View.VISIBLE);
                }else {
                    mHolder.ItemDesc.setVisibility(View.GONE);
                }
            }
        });
        return mHolder;
    }

    class MenuHolder extends RecyclerView.ViewHolder {

        ConstraintLayout food_item;

        TextView ItemName;
        TextView ItemPrice;
        TextView ItemDesc;

        ImageView ItemImage;

        public MenuHolder(@NonNull View itemView) {
            super(itemView);

            food_item = itemView.findViewById(R.id.item_layout);

            ItemName = itemView.findViewById(R.id.ItemName);
            ItemPrice = itemView.findViewById(R.id.ItemPrice);
            ItemDesc = itemView.findViewById(R.id.ItemDesc);

            ItemImage = itemView.findViewById(R.id.ItemImage);
        }
    }
}
