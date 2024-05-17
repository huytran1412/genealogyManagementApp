package com.ex.appgiapha.model;

public class GiaPhaInfo {
    private String tenGiaPha;
    private int soLuongThanhVien;
    private int soLuongLevel;

    // Getter và Setter

    public String getTenGiaPha() {
        return tenGiaPha;
    }

    public void setTenGiaPha(String tenGiaPha) {
        this.tenGiaPha = tenGiaPha;
    }

    public int getSoLuongThanhVien() {
        return soLuongThanhVien;
    }

    public void setSoLuongThanhVien(int soLuongThanhVien) {
        this.soLuongThanhVien = soLuongThanhVien;
    }

    public int getSoLuongLevel() {
        return soLuongLevel;
    }

    public void setSoLuongLevel(int soLuongLevel) {
        this.soLuongLevel = soLuongLevel;
    }

    public GiaPhaInfo(String tenGiaPha, int soLuongThanhVien, int soLuongLevel) {
        this.tenGiaPha = tenGiaPha;
        this.soLuongThanhVien = soLuongThanhVien;
        this.soLuongLevel = soLuongLevel;
    }
    public GiaPhaInfo() {

    }

    @Override
    public String toString() {
        return "GiaPhaInfo{" +
                "tenGiaPha='" + tenGiaPha + '\'' +
                ", soLuongThanhVien=" + soLuongThanhVien +
                ", soLuongLevel=" + soLuongLevel +
                '}';
    }
}
