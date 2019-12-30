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
        holder.FullName.setText(model.getFirstName() + " " + model.getLastName());
        switch (model.getUserType()) {
            case "1":
                holder.UserType.setText("Admin");
                break;
            case "2":
                holder.UserType.setText("Client");
                break;
            case "3":
                holder.UserType.setText("Chef");
                break;
        }

        if (model.getIsDeleted()){
            holder.Status.setText("Deleted");
        }else {
            holder.Status.setText("Active");
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_panel_item, parent, false);
        final UsersRecyclerAdapter.UserViewHolder mHolder = new UsersRecyclerAdapter.UserViewHolder(view);

        mHolder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(parent.getContext(), mHolder.UserID.getText().toString() + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(parent.getContext(), UserEditDelete.class);

                intent.putExtra("UserID", mHolder.UserID.getText().toString());

                parent.getContext().startActivity(intent);
            }
        });

        return mHolder;
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout main_layout;

        TextView UserID, FullName, UserType, Status;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            main_layout = itemView.findViewById(R.id.main_layout);

            UserID = itemView.findViewById(R.id.UserID);
            FullName = itemView.findViewById(R.id.fullname_usersPanel);
            UserType = itemView.findViewById(R.id.userType_usersPanel);
            Status = itemView.findViewById(R.id.status);
        }
    }
}
