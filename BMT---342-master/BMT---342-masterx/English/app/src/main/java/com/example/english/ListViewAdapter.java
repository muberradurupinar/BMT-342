package com.example.english;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private String[] baslik;                //menü öğeleri kullanıcının gördüğü yer
    private String[] link;                 //menüye basınca gideceği yer menüye basınca oluşan link
    private LayoutInflater inflater;      //menü satırındaki görseller için
    public ListViewAdapter(Context context2,String[] baslik2, String[] link2){
        this.context=context2;
        this.baslik=baslik2;
        this.link=link2;

    }
    @Override                         //menü ögesinde kaç tane eleman onu buluyor
    public int getCount() {
        return baslik.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override                      //bütün işlemler burda yapılacak
    public View getView(int position, View convertView, ViewGroup parent) {
        //menu_icerik xml e  ulaşılacak (bir textview birde imageview var)
        TextView ad;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //böylece menu_icerik xml deki layout buraya çekiliyor.
        View item_view=inflater.inflate(R.layout.menu_icerik,parent,false); //view'i çağırdık
        ad= (TextView) item_view.findViewById(R.id.txt);
        ad.setText(baslik[position]);

        return item_view;
    }
}

