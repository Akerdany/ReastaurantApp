package com.example.reastaurantapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reastaurantapp.Classes.Food;
import com.example.reastaurantapp.FoodEditDelete;
import com.example.reastaurantapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FoodPanelRecyclerAdapter extends FirestoreRecyclerAdapter<Food, FoodPanelRecyclerAdapter.FoodViewHolder> {

    public FoodPanelRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
        holder.FoodID.setText(model.getItemID());
        holder.ItemName.setText(model.getItemName());
        holder.ItemDesc.setText(model.getItemDesc());
        holder.ItemPrice.setText(String.valueOf(model.getItemPrice()));
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_panel_item, parent, false);
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
