package com.example.english;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MetinActivity extends AppCompatActivity {
List<Konular> list=new ArrayList<>();
    Bundle bundle;
    SQLiteHandler db;
    Cursor imlec;
    String gelenID, id, baslik, aciklama, resim, ses, favori, tarih, kategori;
    TextView baslikk, metin;
    RecylerAdapter2 recylerAdapter;
    private RecyclerView recyclerView;
    //bu ikisini kullanarak recyclerview içine listedeki öğeleri koyucaz

    ListViewAdapter adapter;
    SQLiteHandler deneme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metin);
        recyclerView = findViewById(R.id.recyler_view2);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        layout.scrollToPosition(0);
        recyclerView.setLayoutManager(layout);

        db = new SQLiteHandler(getApplicationContext());

        bundle = getIntent().getExtras();
        if (bundle != null) {
            list.clear();
            gelenID = bundle.getString("EmpId");
            imlec = db.getWritableDatabase().rawQuery("SELECT * FROM veriler WHERE id = '" + gelenID + "'", null);
            while (imlec.moveToNext()) {
                id = imlec.getString(imlec.getColumnIndex("id"));
                baslik = imlec.getString(imlec.getColumnIndex("baslik"));
                aciklama = imlec.getString(imlec.getColumnIndex("aciklama"));
                ses = imlec.getString(imlec.getColumnIndex("ses"));
                favori = imlec.getString(imlec.getColumnIndex("favori"));
                tarih = imlec.getString(imlec.getColumnIndex("tarih"));
                kategori = imlec.getString(imlec.getColumnIndex("kategori"));
                list.add(new Konular(id, baslik,aciklama, ses, favori,tarih,kategori));
            }
            recylerAdapter=new RecylerAdapter2(list);
            //list recyclerAdapter içine eklendi
            recyclerView.setHasFixedSize(true); //belirli bir değeri olsun
            recyclerView.setAdapter(recylerAdapter);
            //recylerAdapter de recyclerView içine eklenince bu kayıtlar ekranda görünecek
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            runOnUiThread(new Runnable() { //bazen oluşan geçikmelerin önüne geçmek için
                @Override
                public void run() {
                    recylerAdapter.notifyDataSetChanged();
                    //bunu yapmazsak veriler karışıyor.

                }
            });
        }
    }

}