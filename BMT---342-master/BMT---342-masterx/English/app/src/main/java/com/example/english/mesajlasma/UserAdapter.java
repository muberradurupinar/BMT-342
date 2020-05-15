package com.example.english.mesajlasma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.english.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    List<String> list;
    Activity activity;
    String username;

    public UserAdapter(Context context, List<String> list, Activity activity,String username) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.username=username;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder= LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, final int position) {
        holder.txt.setText(list.get(position).toString());
        holder.anaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, Chat.class);
                intent.putExtra("userName",username);
                intent.putExtra("otherName",list.get(position).toString());
                activity.startActivity(intent);
                activity.finish();


            }
        });
    }

    @Override
    public int getItemCount() {
        return  list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt ;
        LinearLayout anaLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt= itemView.findViewById(R.id.userName);
            anaLayout=itemView.findViewById(R.id.anaLayout);
        }
    }
}
