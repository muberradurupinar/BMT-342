package com.example.english.metin_ses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper {
    //hata durumunda yazdırmak için
    //logda hata olursa bunu kullanıcaz sınıfın adını getiriyor.
    private  static final String TAG=SQLiteHandler.class.getSimpleName();
    private static final int DBVERSİON = 1;
    private  static final String DBNAME="listEnglish";
    private static final String TBNAME= "veriler";
    //tablonun sütunları için
    private static final String KEY_ID="id";
    public static final String KEY_BASLIK="baslik";
    public static final String KEY_ACIKLAMA="aciklama";
    private static final String KEY_SES="ses";
    private static final String KEY_FAVORI="favori";
    private static final String KEY_TARIH="tarih";
    private static final String KEY_KATEGORI="kategori";
    private static final String KEY_ANAHTAR="anahtar";


    public SQLiteHandler(Context context) {
        super(context,DBNAME,null,DBVERSİON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQLİTE BURDA OLUŞTURUYORUZZ
        //SÜTUN İÇİNDEKİ VERİLERİ TANIMLAMA
        String CREATETABLE="CREATE TABLE " + TBNAME + "("+
                KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_BASLIK +" TEXT, " + KEY_ACIKLAMA + " TEXT, " +
                " TEXT UNIQUE, " + KEY_SES +" TEXT UNIQUE, "+
                KEY_FAVORI + " INTEGER DEFAULT 0, " + KEY_TARIH +" TEXT, "+
                KEY_KATEGORI + "  INTEGER DEFAULT 0, " + KEY_ANAHTAR + " INTEGER DEFAULT 0);";
        db.execSQL(CREATETABLE);
        Log.d("Veritabanı kuruldu ", ", tablo oluşturuldu");


    }
    //veritabanı komple güncellendi
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TBNAME);
        onCreate(db);
    }
    //veri ekleme
    public void add_data(String baslik, String aciklama,  String ses,String tarih, String kategori, String anahtar){
        //en son eklenenin anahtarı en büyük olacak oda en üstte gösterilecek
        SQLiteDatabase new_db =this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(KEY_BASLIK,baslik);
        values.put(KEY_ACIKLAMA,aciklama);
        values.put(KEY_SES,ses);
        values.put(KEY_TARIH,tarih);
        values.put(KEY_KATEGORI,kategori);
        values.put(KEY_ANAHTAR  ,anahtar);
        //HER İŞLEM İÇİN VERİTABANINDA BİR SATIR OLUŞTURACAK BU SATIRLARIN SÜTUNLARINA AİT BİLGİLERİ NASIL KAYDEDECEĞİ OLUŞTURULUYOR.
        //satıra yazdırmak için
        //TBNAME tablosuna bir satır oluştur ve values içindeki değerleri ilgili yerlere yerleştir
        long yazi=new_db.insert(TBNAME, null, values);
        new_db.close();
        Log.d("yeni veri eklendi " , String.valueOf(yazi));
    }
    //favorilere ekle - çıkar
    public void favorite_state(String id, String state){

        SQLiteDatabase new_db=this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(KEY_FAVORI,state);
        //FAVORİ DEĞERİNİ GÖNDERİLEN DEĞER YAP
        new_db.update(TBNAME,values,"id = "+ id,null);
        //hangi satırın?  id'si gönderilen id olan satırın
        new_db.close();
        Log.d(TAG," favori durumu güncellendi");
    }
    //bütün verileri sil
    public void delete(){
        SQLiteDatabase new_db=this.getWritableDatabase();
        new_db.delete(TBNAME,null, null);
        new_db.close();
        Log.d(TAG,"kayıtlar silindi");
        //logda yazmada 23 karakter sınırı var!
    }

    public List<Konular> getir(){
        SQLiteDatabase db= this.getReadableDatabase();
        String [] deneme = new String []{KEY_ID, KEY_BASLIK, KEY_ACIKLAMA,null,null,null,null,null,null};
        Cursor c= db.query(TBNAME, deneme,null,null,null,null,null);
        int basliksirano=c.getColumnIndex(KEY_BASLIK);
        int aciklamasirano=c.getColumnIndex(KEY_ACIKLAMA);
        List<Konular> konularList = new ArrayList<Konular>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            Konular konular = new Konular();
            konular.setBaslik(c.getString(basliksirano));
            konular.setAciklama(c.getString(aciklamasirano));
            konularList.add(konular);
        }
        db.close();
        return konularList;
    }
}
