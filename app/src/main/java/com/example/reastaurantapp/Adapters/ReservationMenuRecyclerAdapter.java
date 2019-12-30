package com.example.reastaurantapp.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reastaurantapp.Classes.Food;
import com.example.reastaurantapp.R;
import com.example.reastaurantapp.ReservationMenu;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ReservationMenuRecyclerAdapter extends FirestoreRecyclerAdapter<Food, ReservationMenuRecyclerAdapter.ReservationMenuHolder> {

    ViewGroup mParent;

    public ReservationMenuRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Food> options){
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull ReservationMenuHolder holder, int position, @NonNull Food model) {
        holder.FoodID.setText(model.getItemID());
        holder.ItemName.setText(model.getItemName());
        holder.ItemDesc.setText(model.getItemDesc());
        holder.ItemPrice.setText(String.valueOf(model.getItemPrice()));

        Glide.with(mParent.getContext()).load(Uri.parse(model.getItemPhoto())).into(holder.ItemImage);
    }

    @NonNull
    @Override
    public ReservationMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservationmenu_item, parent, false);
        mParent = parent;
        final ReservationMenuRecyclerAdapter.ReservationMenuHolder mHolder = new ReservationMenuRecyclerAdapter.ReservationMenuHolder(view);
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

        mHolder.MinusItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(String.valueOf(mHolder.ItemCountTxt.getText()));
                ReservationMenu.itemsCount -= 1;
                count = count - 1;
                mHolder.ItemCountTxt.setText(String.valueOf(count));
                ReservationMenu.CartNoTxt.setText(String.valueOf(ReservationMenu.itemsCount));

                for(Food foodItem: ReservationMenu.mFood){
                    if (foodItem.getItemName().equals(mHolder.ItemName.getText().toString()) && foodItem.getItemPrice() == Integer.parseInt(mHolder.ItemPrice.getText().toString())){
                        ReservationMenu.mFood.remove(foodItem);
                        break;
                    }
                }
            }
        });

        mHolder.AddItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(String.valueOf(mHolder.ItemCountTxt.getText()));
                ReservationMenu.itemsCount += 1;
                count = count + 1;
                mHolder.ItemCountTxt.setText(String.valueOf(count));
                ReservationMenu.CartNoTxt.setText(String.valueOf(ReservationMenu.itemsCount));

                Food Fooditem = new Food(mHolder.FoodID.getText().toString(),
                        mHolder.ItemName.getText().toString(),
                        mHolder.ItemDesc.getText().toString(),
                        Integer.parseInt(mHolder.ItemPrice.getText().toString()));

                ReservationMenu.mFood.add(Fooditem);
            }
        });
        return mHolder;
    }

    class ReservationMenuHolder extends RecyclerView.ViewHolder {

        ConstraintLayout food_item;

        TextView FoodID;
        TextView ItemName;
        TextView ItemPrice;
        TextView ItemDesc;
        TextView ItemCountTxt;

        Button MinusItemBtn;
        Button AddItemBtn;

        ImageView ItemImage;

        public ReservationMenuHolder(@NonNull View itemView) {
            super(itemView);

            food_item = itemView.findViewById(R.id.item_layout);

            FoodID = itemView.findViewById(R.id.FoodID);
            ItemName = itemView.findViewById(R.id.ItemName);
            ItemPrice = itemView.findViewById(R.id.ItemPrice);
            ItemDesc = itemView.findViewById(R.id.ItemDesc);
            ItemCountTxt = itemView.findViewById(R.id.ItemCountTxt);

            MinusItemBtn = itemView.findViewById(R.id.MinusItemBtn);
            AddItemBtn = itemView.findViewById(R.id.AddItemBtn);

            ItemImage = itemView.findViewById(R.id.ItemImage);
        }
    }
}
