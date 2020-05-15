package com.example.english.mesajlasma;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.english.R;

import java.util.List;

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.ViewHolder> {
    Context context;
    List<MesajModel> list;
    Activity activity;
    String username;
    Boolean state =false;
    int sent =1, reciever=2;

    public MesajAdapter(Context context, List<MesajModel> list, Activity activity, String username) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.username=username;
        state =false;
    }

    @NonNull
    @Override
    public MesajAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder;
        if(viewType ==sent){
            viewHolder=LayoutInflater.from(context).inflate(R.layout.send_layout,parent,false);
            return new ViewHolder(viewHolder);
        }
        else{
            viewHolder=LayoutInflater.from(context).inflate(R.layout.receiver_layout,parent,false);
            return new ViewHolder(viewHolder);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MesajAdapter.ViewHolder holder, final int position) {
        holder.txt.setText(list.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return  list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if(state){
                txt= itemView.findViewById(R.id.sendtext);
            }
            else{
                txt= itemView.findViewById(R.id.recievertext);
            }


        }
    }

    @Override
    public int getItemViewType(int position) {
        // if (user.getPassword() != null && password.getText() != null && user.getPassword().equals(password.getText().toString())) {
        //
        if(list.get(position).getFrom()!= null){
            state =true;
            return sent;
        }
        else {
            state=false;
            return reciever;
        }
    }
}
