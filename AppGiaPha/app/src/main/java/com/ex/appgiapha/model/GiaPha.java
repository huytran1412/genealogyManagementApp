package com.ex.appgiapha.model;

public class GiaPha {
    private int id;
    private String tenGiaPha;
    private int soLuongDoi; // Số đời của gia phả

    public GiaPha() {
    }

    public GiaPha(String tenGiaPha) {
        this.tenGiaPha = tenGiaPha;
    }

    public GiaPha(int id, String tenGiaPha) {
        this.id = id;
        this.tenGiaPha = tenGiaPha;
    }

    public GiaPha(int id, String tenGiaPha, int soLuongDoi) {
        this.id = id;
        this.tenGiaPha = tenGiaPha;
        this.soLuongDoi = soLuongDoi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenGiaPha() {
        return tenGiaPha;
    }

    public void setTenGiaPha(String tenGiaPha) {
        this.tenGiaPha = tenGiaPha;
    }

    public int getSoLuongDoi() {
        return soLuongDoi;
    }

    public void setSoLuongDoi(int soLuongDoi) {
        this.soLuongDoi = soLuongDoi;
    }
}
