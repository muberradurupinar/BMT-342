package com.example.english.mesajlasma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.english.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Mesaj extends AppCompatActivity {
    String username;
    FirebaseDatabase db;
    DatabaseReference rf;
    List<String> kullanici_list; //bunu adaptere göndericez
    RecyclerView userRecycler;
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj);

        username =  getIntent().getExtras().getString("kadi");
        //Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
        db =FirebaseDatabase.getInstance();
        rf=db.getReference();

        userRecycler=(RecyclerView) findViewById(R.id.userRecycler);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(Mesaj.this,2);
        userRecycler.setLayoutManager(layoutManager);


        kullanici_list = new ArrayList<>();
        rf.child("Kullanicilar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(!dataSnapshot.getKey().equals(username)) {
                    Log.d("Kullanıcı: ",dataSnapshot.getKey());
                    //listeleme kontrolü için
                    kullanici_list.add(dataSnapshot.getKey()); // mevcut kullanıcı hariç listemizi doldurma
                    adapter.notifyDataSetChanged();    //liste güncellendikçe adapter da günçellenecek
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter= new UserAdapter(Mesaj.this, kullanici_list,Mesaj.this,username);
        userRecycler.setAdapter(adapter);
    }
}
