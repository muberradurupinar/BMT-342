package com.example.english;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.english.metin_ses.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Giris extends AppCompatActivity {
    EditText ad;
    Button buton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        ad= findViewById(R.id.k_adi);
        buton=findViewById(R.id.kayit);
        firebaseDatabase =FirebaseDatabase.getInstance();
        reference =firebaseDatabase.getReference();
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=ad.getText().toString();
                ekle(username);
                ad.setText("");

            }
        });
    }
    //authentication işlemi yok sanki yapıyormuşçasına
    //veritabanına ekleyerek
    //varsa giriş yapacak yoksa yeni oluşturacak
    public void ekle(final String kadi){
        reference.child("Kullanicilar").child(kadi).child("kullaniciadi").setValue(kadi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Giris.this, MainActivity.class);
                    intent.putExtra("kadi",kadi);
                    startActivity(intent);
                    finish();
                }
            }
        });
        //şimdi burda kullanıcı adını alabilmek için
    }
}
