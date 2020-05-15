package com.example.english;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.ViewHolder> {
    private List<Konular> listem;

    public RecylerAdapter(List<Konular> list){
        listem=list;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecylerAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false));
    }

    @Override
    public void onBindViewHolder( RecylerAdapter.ViewHolder holder, int position) {
        holder.baslik_txt.setText(listem.get(position).getBaslik());
        holder.aciklama_txt.setText(listem.get(position).getAciklama());
        //Picasso.get().load(listem.get(position).getResim()).into(holder.resim);
    }

    @Override
    public int getItemCount() {
        return listem.size();
    }
    public static  class ViewHolder extends RecyclerView.ViewHolder{
        public CardView card_view;
        public TextView baslik_txt;
        public TextView aciklama_txt;
        public ImageView resim;
        public ViewHolder(View view) {
            super(view);
            this.card_view =view.findViewById(R.id.card_view);
            this.baslik_txt=view.findViewById(R.id.resim_title);
            this.aciklama_txt=view.findViewById(R.id.resim_aciklama);
            //this.resim=view.findViewById(R.id.resim);
        }
    }
}
