package com.example.english.metin_ses;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
//bu class kullanılarak json verisi mobil uygulamaya aktarılacak

public class NetworkController {
    private static Context context1;
    private static NetworkController netwrk;
    private RequestQueue req;
    private NetworkController(Context context){
        context1=context;
        this.req=getRequestQueue();

    }
    public static synchronized NetworkController getInstance(MainActivity conntext){
        NetworkController networkController ;
        synchronized (NetworkController.class){
            if(netwrk == null){
                netwrk = new NetworkController(conntext);
            }
            networkController=netwrk;
        }
        return networkController;
    }
    RequestQueue getRequestQueue() {
        if(this.req==null){
            req= Volley.newRequestQueue(context1.getApplicationContext());
        }
        return req;
    }
    //dışardan istek kuyrugu oluşturmak için requestqueue kısmı
    public <T> void addToRequestQueue(Request<T> requ){
        getRequestQueue().add(requ); //talep kuyruguna yeni bir istek ekleme

    }
}
