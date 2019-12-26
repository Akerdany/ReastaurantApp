package com.example.reastaurantapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reastaurantapp.Classes.Food;
import com.example.reastaurantapp.Classes.Order;
import com.example.reastaurantapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ChefRecyclerAdapter extends FirestoreRecyclerAdapter<Order, ChefRecyclerAdapter.ChefViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ChefRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Order> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChefViewHolder holder, int position, @NonNull Order model) {

        final TextView orderNo = holder.OrderNO;

        DocumentReference ref = db.collection("reservation").document(model.getOrderNumber());

        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                orderNo.setText(documentSnapshot.get("tableName").toString());
            }
        });

        ArrayList<Food> Items = model.getItems();
        String Desc = "";
        for (int i = 0; i < Items.size(); i++){
            Desc += Items.get(i).getItemName() + "\n";
        }
        holder.OrderDesc.setText(Desc);
    }

    @NonNull
    @Override
    public ChefViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chef_item, parent, false);
        return new ChefViewHolder(view);
    }

    class ChefViewHolder extends RecyclerView.ViewHolder{

        TextView OrderNO;
        TextView OrderDesc;

        public ChefViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderNO = itemView.findViewById(R.id.OrderNO);
            OrderDesc = itemView.findViewById(R.id.OrderDesc);
        }
    }
}
