package com.example.chatlingg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class GirisActivity extends AppCompatActivity {

    EditText kullaniciAdi,sifre;
    Button girisYap,kayitOl;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        tanimla();
    }

    public void tanimla(){

        kullaniciAdi = (EditText)findViewById(R.id.kullaniciAdi);
        sifre = (EditText)findViewById(R.id.sifre);
        girisYap = (Button)findViewById(R.id.girisYap);
        kayitOl = (Button)findViewById(R.id.kayitOl);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = kullaniciAdi.getText().toString();
                String pass = sifre.getText().toString();
                Toast.makeText(getApplicationContext(), "Başarı ile giriş yapıldı", Toast.LENGTH_SHORT).show();
                kullaniciAdi.setText("");
                sifre.setText("");
                kayit_ekle(username,pass);
            }
        });

        kayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = kullaniciAdi.getText().toString();
                String pass = sifre.getText().toString();
                Toast.makeText(getApplicationContext(), "Başarı ile kayıt olundu", Toast.LENGTH_SHORT).show();
                kullaniciAdi.setText("");
                sifre.setText("");
                kayit_ekle(username,pass);
            }
        });

    }
    public void kayit_ekle(final String kadi,final String pass){
        reference.child("Kullanıcılar").child(kadi).child("Sifre").setValue(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    firebaseDatabase =FirebaseDatabase.getInstance();
                    reference=firebaseDatabase.getReference().child("Kullanıcı Özellikleri").child("ozellik");
                    Map map = new HashMap();
                    map.put("resim","null");
                    map.put("isim","null");
                    map.put("dogum","null");
                    map.put("meslek","null");
                    map.put("egitim","null");

                    reference.setValue(map);

                    Intent intent = new Intent(GirisActivity.this,MainActivity.class);
                    intent.putExtra("kadi",kadi);
                    startActivity(intent);
                }
            }
        });
    }
    public void giris(final String kadi,final String pass){
        String username = kullaniciAdi.getText().toString();
        String password = sifre.getText().toString();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();

     //  DatabaseReference oku = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(kadi);

         myRef.child("Kullanıcılar").child(kadi).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                     Users users = new Users();
                     users=dataSnapshot.getValue(Users.class);
                     Log.i("oooo",pass);
                 }


             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

    }
}
