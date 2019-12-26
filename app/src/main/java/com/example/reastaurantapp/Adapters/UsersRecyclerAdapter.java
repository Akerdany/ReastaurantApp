package com.example.reastaurantapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reastaurantapp.Classes.User;
import com.example.reastaurantapp.R;
import com.example.reastaurantapp.UserEditDelete;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class UsersRecyclerAdapter extends FirestoreRecyclerAdapter<User, UsersRecyclerAdapter.UserViewHolder> {

    public UsersRecyclerAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
        holder.UserID.setText(model.getId());
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_panel_item, parent, false);
        final UsersRecyclerAdapter.UserViewHolder mHolder = new UsersRecyclerAdapter.UserViewHolder(view);

        mHolder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), UserEditDelete.class);

                intent.putExtra("UserID", mHolder.UserID.getText().toString());

                parent.getContext().startActivity(intent);
            }
        });

        return mHolder;
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout main_layout;

        TextView UserID;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            main_layout = itemView.findViewById(R.id.main_layout);

            UserID = itemView.findViewById(R.id.UserID);
        }
    }
}
