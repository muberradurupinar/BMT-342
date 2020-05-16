package com.example.chatlingg;

public class Users {

    public String kadi;
    public String sifre;

    public Users(){

    }

    public String getKadi() {
        return kadi;
    }

    public void setKadi(String kadi) {
        this.kadi = kadi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public  Users(String kadi, String sifre){
        this.kadi=kadi;
        this.sifre=sifre;
    }
}
