package com.example.english.mesajlasma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.english.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat extends AppCompatActivity {
    String username, otherName;
    TextView txt;
    ImageView back,send;
    EditText msj;
    RecyclerView listele;
    FirebaseDatabase db;
    DatabaseReference rf;
    MesajAdapter mesajAdapter;
    List<MesajModel> yeni_liste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        username=getIntent().getExtras().getString("userName");
        otherName=getIntent().getExtras().getString("otherName");
        txt=findViewById(R.id.chatuserName);
        back=findViewById(R.id.backimg);
        send= findViewById(R.id.sendimg);
        msj=findViewById(R.id.message);
        txt.setText(otherName);

        db=FirebaseDatabase.getInstance();
        rf=db.getReference();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeni= new Intent(Chat.this,Mesaj.class);
                yeni.putExtra("kadi",username);
                startActivity(yeni);
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edittext= msj.getText().toString();

                mesajGonder(edittext);
                msj.setText("");
            }
        });


        yeni_liste=new ArrayList<>();
        listele=findViewById(R.id.listele);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Chat.this,1);
        listele.setLayoutManager(layoutManager);
        mesajAdapter =new MesajAdapter(Chat.this,yeni_liste,Chat.this,username);
        listele.setAdapter(mesajAdapter);
        loadMessage();
    }
    public void mesajGonder(String text){
        final String id=rf.child("Mesajlar").child(username).child(otherName).push().getKey();

        //mesajlar kaybolmasın diye de bir id veriyoruz
        //çift taraflı mesaj kaydetmek gerekiyor çünkü A kullanıcısı B ye attığı mesajları silse bile B kullanıcısında bu mesajlar durmalı
        final Map messageMap= new HashMap();
        messageMap.put("text",text);
        messageMap.put("from",username);

        rf.child("Mesajlar").child(username).child(otherName).child(id).push().setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    rf.child("Mesajlar").child(otherName).child(username).child(id).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }
        });
    }

    public void loadMessage(){
        rf.child("Mesajlar").child(username).child(otherName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MesajModel mesajModel =dataSnapshot.getValue(MesajModel.class);
                Log.d("Mesajlar",mesajModel.toString());
                yeni_liste.add(mesajModel);
                mesajAdapter.notifyDataSetChanged();
                listele.scrollToPosition(yeni_liste.size()-1);
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
    }
}
