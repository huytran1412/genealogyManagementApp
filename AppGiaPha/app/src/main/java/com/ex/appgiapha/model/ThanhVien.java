package com.ex.appgiapha.model;

public class ThanhVien implements java.io.Serializable {
    private int id;
    private String ten;
    private String ngaySinh;
    private int gioiTinh;
    private String diaChi;
    private String ngayMat;
    private String email;
    private int conCua;
    private int levelID;
    private int giaphaId; // Thêm trường giaphaId

    public ThanhVien() {
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public ThanhVien(int id, String ten, String ngaySinh, String diaChi, String ngayMat, String email, int conCua, int levelID, int giaphaId, int gioiTinh) {
        this.id = id;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.ngayMat = ngayMat;
        this.email = email;
        this.conCua = conCua;
        this.levelID = levelID;
        this.giaphaId = giaphaId; // Gán giá trị giaphaId
        this.gioiTinh = gioiTinh;
    }

    public ThanhVien(String ten, String ngaySinh, String diaChi, String ngayMat, String email, int conCua, int levelID, int giaphaId, int gioiTinh) {
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.ngayMat = ngayMat;
        this.email = email;
        this.conCua = conCua;
        this.levelID = levelID;
        this.giaphaId = giaphaId; // Gán giá trị giaphaId
        this.gioiTinh = gioiTinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayMat() {
        return ngayMat;
    }

    public void setNgayMat(String ngayMat) {
        this.ngayMat = ngayMat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getConCua() {
        return conCua;
    }

    public void setConCua(int conCua) {
        this.conCua = conCua;
    }

    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public int getGiaphaId() {
        return giaphaId;
    }

    public void setGiaphaId(int giaphaId) {
        this.giaphaId = giaphaId;
    }
}
