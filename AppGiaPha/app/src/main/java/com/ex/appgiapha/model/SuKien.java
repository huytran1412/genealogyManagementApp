package com.ex.appgiapha.model;

public class SuKien {
    private int id;
    private String tenSuKien;
    private String ngay;

    public SuKien() {
    }

    public SuKien(int id, String tenSuKien, String ngay) {
        this.id = id;
        this.tenSuKien = tenSuKien;
        this.ngay = ngay;
    }
    public SuKien( String tenSuKien, String ngay) {
        this.id = id;
        this.tenSuKien = tenSuKien;
        this.ngay = ngay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSuKien() {
        return tenSuKien;
    }

    public void setTenSuKien(String tenSuKien) {
        this.tenSuKien = tenSuKien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    @Override
    public String toString() {
        return "SuKien{" +
                "id=" + id +
                ", tenSuKien='" + tenSuKien + '\'' +
                ", ngay='" + ngay + '\'' +
                '}';
    }
}
