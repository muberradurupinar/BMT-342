package com.example.english.metin_ses;

import android.content.ContentValues;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//normalde onclicklistener geliyor diğer objeler için ama recyclerviewde yok o yüzden elle tanımlıyoruz.
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    //dokunma için
    GestureDetector gestureDetector;
    private OnItemClickListener listener;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener1){
        this.listener=listener1;
        this.gestureDetector =new GestureDetector(context, (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener(){
            public boolean onSingleTapUp(MotionEvent e){
                return true;
            }
        });
    }



    @Override
    public boolean onInterceptTouchEvent( RecyclerView rv, MotionEvent event) {
        View view=rv.findChildViewUnder(event.getX(),event.getY()); //dokunulan yerin konumunu tespit etme
        if(view!=null && this.listener!=null && this.gestureDetector.onTouchEvent(event)){
            this.listener.onItemClick(view,rv.getChildAdapterPosition(view));
        }
        return false;
    }

    @Override
    public void onTouchEvent( RecyclerView rv,  MotionEvent e) {

    }


    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    public  static interface  OnItemClickListener{
        public void onItemClick(View view1,int position);
    }
}
