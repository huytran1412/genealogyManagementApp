package com.ex.appgiapha.model;

public class Level {
    private int id;
    private int name;
    private int soLuongThanhVien; // Thêm trường số lượng thành viên

    public Level() {
    }

    public Level(int id, int name, int soLuongThanhVien) {
        this.id = id;
        this.name = name;
        this.soLuongThanhVien = soLuongThanhVien; // Khởi tạo số lượng thành viên
    }

    public Level(int name, int soLuongThanhVien) {
        this.name = name;
        this.soLuongThanhVien = soLuongThanhVien; // Khởi tạo số lượng thành viên
    }
    public Level(int name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getSoLuongThanhVien() {
        return soLuongThanhVien;
    }

    public void setSoLuongThanhVien(int soLuongThanhVien) {
        this.soLuongThanhVien = soLuongThanhVien;
    }
}
