package com.example.english.metin_ses;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.english.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.concurrent.TimeUnit;

public class DetayActivity extends AppCompatActivity  implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    Bundle bundle;
    SQLiteHandler db;
    Cursor imlec;
    String gelenID, id,baslik,aciklama,resim,ses,favori, tarih,kategori;
    SeekBar seekBar;
    TextView ilk,son;
    Button play,favorim,metin;

    private MediaPlayer mediaPlayer;
    private int toplamSure;

    private final Handler HANDLER =new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //tam sayfa reklam için
       /* MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111");
        final InterstitialAd interstitialAd= new InterstitialAd("ca-app-pub-3940256099942544/6300978111");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public  void onAdLoaded(){
                interstitialAd.show();
            }
        });*/

        db=new SQLiteHandler(getApplicationContext());


        seekBar=findViewById(R.id.seekbarPlay);
        ilk=findViewById(R.id.basla);
        son=findViewById(R.id.bitir);
        play=findViewById(R.id.oynat);
        favorim=findViewById(R.id.like);
        //görsel ögeleri çağırdık

        mediaPlayer= new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);



        //oynatma işlemi
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("baslik","sorun = ");
                try{
                    //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(ses);

                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch (Exception e){
                    //Toast.makeText(DetayActivity.this,"hata oluştu "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    //Log.d("HATA:"," sorun= " +e.getLocalizedMessage());
                    Log.e("HATA:"," sorun= " +e.getMessage());
                }
                toplamSure=mediaPlayer.getDuration();//boyutu getirdi ms cinsinde uzun bir int
                @SuppressWarnings("DefaultLocale") String sure=String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(toplamSure) -
                            TimeUnit.DAYS.toHours(TimeUnit.MICROSECONDS.toDays(toplamSure)),
                        TimeUnit.MILLISECONDS.toMinutes(toplamSure) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(toplamSure)),
                        TimeUnit.MILLISECONDS.toSeconds(toplamSure) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(toplamSure)));
                son.setText(sure);
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    play.setBackgroundResource(R.drawable.play);
                }else{
                    mediaPlayer.start();
                    play.setBackgroundResource(R.drawable.pause);
                }
                //anlık süre için
                seekBarGuncelle();

            }

            private void seekBarGuncelle() {
                final int anliksure = mediaPlayer.getCurrentPosition();
                seekBar.setProgress((int)(((float) mediaPlayer.getCurrentPosition()/anliksure *100)));
                //runnable hareketli bir fonksiyon için
                if(mediaPlayer.isPlaying()){
                    Runnable hareket= new Runnable() {
                        @Override
                        public void run() {
                            //her seferinde seekbar güncellensin
                            seekBarGuncelle();
                            //her saniye olacak şekilde
                            @SuppressWarnings("DefaultLocale") String anliksureyazi=String.format("%02d:%02d:%02d",
                                    TimeUnit.MILLISECONDS.toHours(toplamSure) -
                                            TimeUnit.DAYS.toHours(TimeUnit.MICROSECONDS.toDays(anliksure)),
                                    TimeUnit.MILLISECONDS.toMinutes(toplamSure) -
                                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(anliksure)),
                                    TimeUnit.MILLISECONDS.toSeconds(toplamSure) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(anliksure)));

                            ilk.setText(anliksureyazi);
                        }
                    };
                    HANDLER.postDelayed(hareket,1000);

                }

            }


        });

        bundle=getIntent().getExtras();
        if(bundle!= null){
            gelenID=bundle.getString("EmpId");
            imlec=db.getWritableDatabase().rawQuery("SELECT * FROM veriler WHERE id = '" +gelenID +"'",null);
            while(imlec.moveToNext()){
                id=imlec.getString(imlec.getColumnIndex("id"));
                baslik=imlec.getString(imlec.getColumnIndex("baslik"));
                aciklama=imlec.getString(imlec.getColumnIndex("aciklama"));
                ses=imlec.getString(imlec.getColumnIndex("ses"));
                favori=imlec.getString(imlec.getColumnIndex("favori"));
                tarih=imlec.getString(imlec.getColumnIndex("tarih"));
                kategori=imlec.getString(imlec.getColumnIndex("kategori"));

            }
        }


        if(favori.equals("1")){
            favorim.setBackgroundResource(R.drawable.nonfavori);
        }else{
            favorim.setBackgroundResource(R.drawable.favori);
        }
        favorim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favori.equals("1")){
                    db.favorite_state(id,"0");
                    favorim.setBackgroundResource(R.drawable.favori);
                    Toast.makeText(DetayActivity.this,"removed from favorites",Toast.LENGTH_SHORT).show();
                    favori="0";
                }else{
                    db.favorite_state(id,"1");
                    favorim.setBackgroundResource(R.drawable.nonfavori);
                    Toast.makeText(DetayActivity.this,"added to favorites",Toast.LENGTH_SHORT).show();
                    favori="1";
                }
            }
        });
        //seekbarı elle değiştirince müzik de değişsin
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mediaPlayer.isPlaying()){
                    int surem= (toplamSure/100)*seekBar.getProgress();
                    mediaPlayer.seekTo(surem);
                }
                return false;
            }
        });


    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        //ilerleme oldukça buraya gelecek ilerlemeye göre(percent) hareket ettir
        seekBar.setSecondaryProgress(percent);

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
    play.setBackgroundResource(R.drawable.play);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //öncelikle müzikplayer'ı sustur
        mediaPlayer.stop();
    }
}
