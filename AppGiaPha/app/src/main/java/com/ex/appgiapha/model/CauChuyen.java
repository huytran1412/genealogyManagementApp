package com.ex.appgiapha.model;

public class CauChuyen {
    private int id;
    private String content;
    private String ngayTao;
    private int giaPhaId;

    public CauChuyen(int id, String content, String ngayTao, int giaPhaId) {
        this.id = id;
        this.content = content;
        this.ngayTao = ngayTao;
        this.giaPhaId = giaPhaId;
    }
    public CauChuyen( String content, String ngayTao, int giaPhaId) {
        this.content = content;
        this.ngayTao = ngayTao;
        this.giaPhaId = giaPhaId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getGiaPhaId() {
        return giaPhaId;
    }

    public void setGiaPhaId(int giaPhaId) {
        this.giaPhaId = giaPhaId;
    }
}
