package com.example.bigbank.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigbank.Model.User;
import com.example.bigbank.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private static ClickListener clickListener;
    private Context context;
    ArrayList<User> list;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewIdentityCard;
        TextView textViewFullName;
        ConstraintLayout constraintLayout;

        UserHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewIdentityCard = itemView.findViewById(R.id.textViewIdentityCardNumber);
            textViewFullName = itemView.findViewById(R.id.textViewFullName);
            constraintLayout = itemView.findViewById(R.id.constraintLayoutUser);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        UserAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    @NonNull
    @Override
    public UserAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_account, parent, false);
        return new UserAdapter.UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserHolder holder, int position) {

        User user = list.get(position);

        holder.textViewIdentityCard.setText(user.getIdentityCard());
        holder.textViewFullName.setText(user.getFullName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
