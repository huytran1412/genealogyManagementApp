package com.ex.appgiapha.db;

import static com.ex.appgiapha.db.DatabaseHelper.COLUMN_GIA_PHA_ID;
import static com.ex.appgiapha.db.DatabaseHelper.COLUMN_ID;
import static com.ex.appgiapha.db.DatabaseHelper.COLUMN_TENGIAPHA;
import static com.ex.appgiapha.db.DatabaseHelper.TABLE_GIAPHA;
import static com.ex.appgiapha.db.DatabaseHelper.TABLE_LEVEL;
import static com.ex.appgiapha.db.DatabaseHelper.TABLE_THANHVIEN;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ex.appgiapha.model.GiaPha;
import com.ex.appgiapha.model.GiaPhaInfo;

import java.util.ArrayList;
import java.util.List;

public class GiaPhaDAO {

    private DatabaseHelper dbHelper;

    public GiaPhaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm gia phả mới
    public long addGiaPha(String tenGiaPha) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENGIAPHA, tenGiaPha);
        long result = db.insert(TABLE_GIAPHA, null, values);
        db.close();
        return result;
    }

    // Xoá gia phả
    public int deleteGiaPha(long giaPhaId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_GIAPHA, COLUMN_ID + "=?", new String[]{String.valueOf(giaPhaId)});
        db.close();
        return result;
    }

    // Sửa tên gia phả
    public int updateGiaPha(long giaPhaId, String newTenGiaPha) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENGIAPHA, newTenGiaPha);
        int result = db.update(TABLE_GIAPHA, values, COLUMN_ID + "=?", new String[]{String.valueOf(giaPhaId)});
        db.close();
        return result;
    }

    // Lấy tất cả các gia phả
    public List<GiaPha> getAllGiaPha() {
        List<GiaPha> giaPhaList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TENGIAPHA};

        // Câu truy vấn SQL để lấy ra số lượng đời của gia phả
        String query = "SELECT " + TABLE_GIAPHA + "." + COLUMN_ID + ", " +
                TABLE_GIAPHA + "." + COLUMN_TENGIAPHA + ", " +
                "COUNT(" + TABLE_LEVEL + "." + DatabaseHelper.COLUMN_ID_LEVEL + ") AS SoLuongDoi " +
                "FROM " + TABLE_GIAPHA +
                " LEFT JOIN " + TABLE_LEVEL +
                " ON " + TABLE_GIAPHA + "." + COLUMN_ID +
                " = " + TABLE_LEVEL + "." + COLUMN_GIA_PHA_ID +
                " GROUP BY " + TABLE_GIAPHA + "." + COLUMN_ID +
                ", " + TABLE_GIAPHA + "." + COLUMN_TENGIAPHA;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String tenGiaPha = cursor.getString(cursor.getColumnIndex(COLUMN_TENGIAPHA));
                @SuppressLint("Range") int soLuongDoi = cursor.getInt(cursor.getColumnIndex("SoLuongDoi"));
                GiaPha giaPha = new GiaPha(id, tenGiaPha, soLuongDoi);
                giaPhaList.add(giaPha);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return giaPhaList;
    }
    @SuppressLint("Range")
    public GiaPhaInfo getGiaPhaInfoById(int giaPhaId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        GiaPhaInfo giaPhaInfo = new GiaPhaInfo();
        // Query để lấy ra thông tin về tên gia phả
        String queryTenGiaPha = "SELECT " + COLUMN_TENGIAPHA + " FROM " + TABLE_GIAPHA + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursorTenGiaPha = db.rawQuery(queryTenGiaPha, new String[]{String.valueOf(giaPhaId)});
        if (cursorTenGiaPha.moveToFirst()) {
            giaPhaInfo.setTenGiaPha(cursorTenGiaPha.getString(cursorTenGiaPha.getColumnIndex(COLUMN_TENGIAPHA)));
        }
        cursorTenGiaPha.close();

        // Query để đếm số lượng thành viên của gia phả
        String querySoLuongThanhVien = "SELECT COUNT(*) AS count FROM " + TABLE_THANHVIEN + " WHERE " + COLUMN_GIA_PHA_ID + " = ?";
        Cursor cursorSoLuongThanhVien = db.rawQuery(querySoLuongThanhVien, new String[]{String.valueOf(giaPhaId)});
        if (cursorSoLuongThanhVien.moveToFirst()) {
            giaPhaInfo.setSoLuongThanhVien(cursorSoLuongThanhVien.getInt(cursorSoLuongThanhVien.getColumnIndex("count")));
        }
        cursorSoLuongThanhVien.close();

        // Query để đếm số lượng level của gia phả
        String querySoLuongLevel = "SELECT COUNT(*) AS count FROM " + TABLE_LEVEL + " WHERE " + COLUMN_GIA_PHA_ID + " = ?";
        Cursor cursorSoLuongLevel = db.rawQuery(querySoLuongLevel, new String[]{String.valueOf(giaPhaId)});
        if (cursorSoLuongLevel.moveToFirst()) {
            giaPhaInfo.setSoLuongLevel(cursorSoLuongLevel.getInt(cursorSoLuongLevel.getColumnIndex("count")));
        }
        cursorSoLuongLevel.close();

        db.close();
        return giaPhaInfo;
    }

    // Hàm cập nhật tên gia phả có id là 1
    public void updateTenGiaPha(int giaPhaId, String tenGiaPhaMoi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENGIAPHA, tenGiaPhaMoi);
        // Cập nhật tên gia phả với id là 1
        db.update(TABLE_GIAPHA, values, COLUMN_ID + " = ?", new String[]{String.valueOf(giaPhaId)});
        db.close();
    }
}
