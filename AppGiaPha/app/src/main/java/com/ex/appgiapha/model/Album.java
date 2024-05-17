package com.ex.appgiapha.model;

public class Album {
    private int id;
    private String tenAlbum;
    private int giaPhaId;

    public Album() {
    }

    public Album(int id, String tenAlbum, int giaPhaId) {
        this.id = id;
        this.tenAlbum = tenAlbum;
        this.giaPhaId = giaPhaId;
    }
    public Album( String tenAlbum, int giaPhaId) {
        this.id = id;
        this.tenAlbum = tenAlbum;
        this.giaPhaId = giaPhaId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenAlbum() {
        return tenAlbum;
    }

    public void setTenAlbum(String tenAlbum) {
        this.tenAlbum = tenAlbum;
    }

    public int getGiaPhaId() {
        return giaPhaId;
    }

    public void setGiaPhaId(int giaPhaId) {
        this.giaPhaId = giaPhaId;
    }
}
