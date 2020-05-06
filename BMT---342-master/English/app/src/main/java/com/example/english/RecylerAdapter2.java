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

import java.util.List;

public class RecylerAdapter2 extends RecyclerView.Adapter<RecylerAdapter.ViewHolder>{
    private  List<Konular> liste2;
    public RecylerAdapter2(List<Konular> list){
        liste2=list;
    }
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public RecylerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecylerAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view2,parent,false));

    }

    @Override
    public void onBindViewHolder(RecylerAdapter.ViewHolder holder, int position) {
        holder.aciklama_txt.setText(liste2.get(position).getAciklama());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView2;
        public TextView aciklama_metin;
        public ViewHolder(View view){
            super(view);
            this.cardView2=view.findViewById(R.id.card_view3);
            this.aciklama_metin=view.findViewById(R.id.aciklama_metin);

        }
    }
}
