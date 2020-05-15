package com.example.english.metin_ses;

public class Konular {
    private String id;
    private String baslik;
    private String aciklama;

    private String ses;
    private String favori;
    private String kategori;
    private String tarih;

    public Konular(String id, String baslik, String aciklama,  String ses, String favori, String kategori, String tarih) {
        this.id = id;
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.ses = ses;
        this.favori = favori;
        this.kategori = kategori;
        this.tarih = tarih;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }



    public String getSes() {
        return ses;
    }

    public void setSes(String ses) {
        this.ses = ses;
    }

    public String getFavori() {
        return favori;
    }

    public void setFavori(String favori) {
        this.favori = favori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public Konular() {

    }
    public Konular(String m_aciklama){
        this.aciklama=m_aciklama;
    }
}
