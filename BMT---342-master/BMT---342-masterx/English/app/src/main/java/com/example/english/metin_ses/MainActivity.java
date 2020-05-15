package com.example.english.metin_ses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.english.R;
import com.example.english.mesajlasma.Mesaj;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//08.04.2020 14.45 şuan uygulama çalıştığında url içindeki veriler log ekranında görünecek. --göründü

public class MainActivity extends AppCompatActivity {
    List<Konular> list=new ArrayList<>();
    private RecyclerView recyclerView;
    //bu ikisini kullanarak recyclerview içine listedeki öğeleri koyucaz
    String username;
    RecylerAdapter recylerAdapter;
    DrawerLayout menum;
    Button menu_bttn,refresh,ask;
    ListView liste;
    //verilerin yer aldığı url için
    String url = "https://raw.githubusercontent.com/Sukriye26/BMT---342/master/index2.json";
    //https://raw.githubusercontent.com/Sukriye26/BMT---342/master/index2.json

    RequestQueue queue; //bu kuyrugu networkController a göndericez url deki verileri getiricek
    SQLiteHandler db;

    String[] basliklar= new String[]{
            "Recently Added", "Favorites", "Stories","Beginner - A1","Elementary - A2","Intermediate - B1"
    };
    String[] linkler = new  String[]{"0","00","1","2","3","4"};

    ListViewAdapter adapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username =getIntent().getExtras().getString("kadi");
        //reklamlar için
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //uygulama açılır açılmaz çalışsın diye
        queue= NetworkController.getInstance(this).getRequestQueue();
        queue.add(new JsonObjectRequest(0,url, null ,new listener(),new error()));

        db=new SQLiteHandler(getApplicationContext());

        menum =findViewById(R.id.drawer_layout);
        menu_bttn=findViewById(R.id.menuBtn);
        refresh=findViewById(R.id.refreshBtn);
        ask=findViewById(R.id.ask);
        liste =findViewById(R.id.leftdrawer_child);

        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gecis = new Intent(MainActivity.this, Mesaj.class);
                gecis.putExtra("kadi",username);
                startActivity(gecis);
            }
        });


        refresh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete();
                queue.add(new JsonObjectRequest(0,url, null ,new listener(),new error()));
                Toast.makeText(MainActivity.this,"syncing",Toast.LENGTH_SHORT).show();
            }
        });
        menum.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //menü hareket ederken

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                //menü açıkken
                menu_bttn.setBackgroundResource(R.drawable.menux);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                //menü kapalıyken
                menu_bttn.setBackgroundResource(R.drawable.menu);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                //menünün durumu değiştiğinde
            }
        });
        adapter=new ListViewAdapter(this,basliklar,linkler);
        liste.setAdapter(adapter); //menü elemanlarını yani string arrayleri listview içine yerleştirdim.
        //şimdi tıklanınca ne olacak onu yazıyoruz
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //position ile hangi menü öğesine tıklandğı bulunacak
                menum.closeDrawer(GravityCompat.START); //menü açıksa kapattık
                //Toast.makeText(MainActivity.this,linkler[position] +" menüsüne tıkladınız",Toast.LENGTH_SHORT).show();
                show(linkler[position]);

            }
        });
        menu_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menum.isDrawerOpen(GravityCompat.START)){
                    menum.closeDrawer(GravityCompat.START);
                }
                else{
                    menum.openDrawer(GravityCompat.START);
                }
            }
        });
        recyclerView=findViewById(R.id.recyler_view);
        LinearLayoutManager lay=new LinearLayoutManager(this);
        lay.setOrientation(LinearLayoutManager.VERTICAL);
        lay.scrollToPosition(0); //başlarken en üstte
        recyclerView.setLayoutManager(lay); //recylerview içinde layoutmanager kullanıyorum
        show("0"); //uygulama ilk açıldığında kategori 0 olanları yani hepsini göster
    }
    //CEVAP OLARAK GELECEK JSON için
    private class listener implements Response.Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {
            //JSON PARSE ETME VE SQLİTE KAYDETME
            try{
                //json array yakalama
                JSONArray icerik=response.getJSONArray("Hikayeler"); // (burdaki yani php deki listenin) ismi php içeriği değişince hata yapmamak için onunla aynı olmak zorunda
                int uzunluk =icerik.length();
                //her birinin içindeki objeyi alıp veritabanına kaydediyoruz
                for (int i=0; i<uzunluk; i++){
                    try{
                        JSONObject yeni=icerik.getJSONObject(i);//sırasıyla tüm objeleri al
                        //veritabanındaki kayıtları kontrol
                        //sunucudaki her bir veriyi json object yaptık
                        //anahtar sunucudan aldığımız id
                        Cursor cursor = db.getWritableDatabase().rawQuery("SELECT count(*) FROM veriler WHERE anahtar ='"
                                +yeni.getString("id")+ "'",null);
                        cursor.moveToFirst();
                        int sayi=cursor.getInt(0);
                        if (sayi==0){
                            db.add_data(yeni.getString("baslik"),yeni.getString("aciklama"),
                                    yeni.getString("sesdosyasi"),
                                    yeni.getString("tarih"),yeni.getString("kategori"),
                                    yeni.getString("id"));
                        }
                        else{
                            Log.d("Eklenmedi ","Veri zaten kayıtlı");
                        }
                            show("0");
                    }catch (Exception exception){
                        Toast.makeText(MainActivity.this,"Hata Oluştu!"+exception.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        Log.d("Hata",exception.toString());
                    }
                }
                show("0");
            }catch(Exception exception){
                Toast.makeText(MainActivity.this,"Hata Oluştu!"+exception.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                Log.d("Hata",exception.toString());
            }
            // burası okunan json dosyasının içeriğini logcat e yazdırıyor.
            Log.i("Gelen Cevap",response.toString());
        }



    }
    //hata durumunda hata derleyiciden implement ediliyor
    private class error implements Response.ErrorListener{

        @Override
        //cevap alınırken bir hata oluştu
        public void onErrorResponse(VolleyError error) {
            //hata mesajını kullanıcının dilinde bastır.
            Toast.makeText(MainActivity.this, "Hata Oluştu: "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            Log.i("Hata: ",error.toString());
        }
    }
    public void show(String cat){
        //içine kategorileri gönderiyorum
        //herseferinde içini temizleyerek başlıyor
        list.clear();
        Cursor cur ;
        switch(cat){

            case "0":
                cur=db.getWritableDatabase().rawQuery("SELECT * FROM veriler ORDER BY anahtar DESC",null);
                break;
            case "00":
                cur=db.getWritableDatabase().rawQuery("SELECT * FROM veriler WHERE favori=1  ORDER BY anahtar DESC",null);
                break;

            default:
                cur=db.getWritableDatabase().rawQuery("SELECT * FROM veriler WHERE kategori="+ cat +" ORDER BY anahtar DESC",null);
                break;
        }
        while(cur.moveToNext()){
            //ileri gittikçe tablodaki her değeri string yap
            String id= cur.getString(cur.getColumnIndex("id"));
            String baslik= cur.getString(cur.getColumnIndex("baslik"));
            String aciklama= cur.getString(cur.getColumnIndex("aciklama"));
            String ses= cur.getString(cur.getColumnIndex("ses"));
            String favori= cur.getString(cur.getColumnIndex("favori"));
            String tarih= cur.getString(cur.getColumnIndex("tarih"));
            String kategori= cur.getString(cur.getColumnIndex("kategori"));
            list.add(new Konular(id,baslik,aciklama,ses,favori,tarih,kategori));
        }//oluşturulan her yeni nesne list e eklendi
        recylerAdapter=new RecylerAdapter(list);
        //list recyclerAdapter içine eklendi
        recyclerView.setHasFixedSize(true); //belirli bir değeri olsun
        recyclerView.setAdapter(recylerAdapter);
        //recylerAdapter de recyclerView içine eklenince bu kayıtlar ekranda görünecek
        recyclerView.setItemAnimator(new DefaultItemAnimator()); //hareket ekleme
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent pass = new Intent(MainActivity.this, DetayActivity.class);
                pass.putExtra("EmpId", (list.get(position)).getId());
                startActivity(pass);
            }
        }));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recylerAdapter.notifyDataSetChanged();
                //bunu yapmazsak veriler karışıyor.

            }
        });
    }
    @Override
    public  void onBackPressed(){
        if(menum.isDrawerOpen(GravityCompat.START)){
            //önce menüyü kapatıp sonra uygulamadan çıkıyoruz
            //menü açıkken uygulamadan çıkmamak için
            menum.closeDrawer(GravityCompat.START);
        }
        else{//menü kapalıysa
            super.onBackPressed();
        }
    }
}
