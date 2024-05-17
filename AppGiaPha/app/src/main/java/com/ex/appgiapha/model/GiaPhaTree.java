package com.ex.appgiapha.model;
import java.util.List;
import com.google.gson.Gson;

public class GiaPhaTree {
    int Level;
    List<ThanhVien> thanhVien;
    public GiaPhaTree(int level, List<ThanhVien> thanhVien) {
        Level = level;
        this.thanhVien = thanhVien;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public List<ThanhVien> getThanhVien() {
        return thanhVien;
    }

    public void setThanhVien(List<ThanhVien> thanhVien) {
        this.thanhVien = thanhVien;
    }

}
