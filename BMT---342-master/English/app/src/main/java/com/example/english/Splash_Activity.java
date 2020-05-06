package com.example.english;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class Splash_Activity extends AppCompatActivity {
    public boolean internet(final Context context){
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnected();
    } //internet kontrolü
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);
        //eğer internet varsa main activity e git
        if(internet(this)){
            Thread splashThread;
            splashThread =new Thread(){
                @Override public void run(){
                    try{
                        synchronized (this){
                            wait(2000);
                        }
                    }catch (InterruptedException ex){

                    }finally {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }
            };
            splashThread.start();
        }
        //eğer internet yoksa uyarı ver ve uygulamayı kapat

        else{
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("Bağlantı Hatası");
            alert.setMessage("Lütfen internet bağlantınızı kontrol edip tekrar deneyiniz.");
            alert.setButton(DialogInterface.BUTTON_NEUTRAL, "Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int pid =android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    dialog.dismiss();
                }
            });
            alert.show();
        }
    }
}
