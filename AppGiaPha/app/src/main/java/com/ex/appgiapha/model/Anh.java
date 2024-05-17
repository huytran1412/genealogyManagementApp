package com.ex.appgiapha.model;

public class Anh {
    private int id;
    private int idAlbum;
    private String link;

    public Anh() {
    }

    public Anh(int id, int idAlbum, String link) {
        this.id = id;
        this.idAlbum = idAlbum;
        this.link = link;
    }
    public Anh( int idAlbum, String link) {
        this.id = id;
        this.idAlbum = idAlbum;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
