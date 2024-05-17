package com.ex.appgiapha.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ex.appgiapha.model.SuKien;
import java.util.ArrayList;
import java.util.List;

public class SuKienDAO {
    private DatabaseHelper dbHelper;

    public SuKienDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Phương thức để thêm sự kiện
    public long addSuKien(int giaPhaID, String tenSuKien, String ngay) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_TEN_SU_KIEN, tenSuKien);
            values.put(DatabaseHelper.COLUMN_NGAY_SU_KIEN, ngay);
            values.put(DatabaseHelper.COLUMN_GIA_PHA_ID_SU_KIEN, giaPhaID);
            long newRowId = db.insert(DatabaseHelper.TABLE_SU_KIEN, null, values);
            db.close();
            return newRowId;
        } catch (Exception e) {
            return -1;
        }
    }

    // Phương thức để xem tất cả các sự kiện của một gia phả
    public List<SuKien> getAllSuKienByGiaPhaID(int giaPhaID) {
        List<SuKien> suKienList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.COLUMN_ID_SU_KIEN,
                DatabaseHelper.COLUMN_TEN_SU_KIEN,
                DatabaseHelper.COLUMN_NGAY_SU_KIEN
        };
        String selection = DatabaseHelper.COLUMN_GIA_PHA_ID_SU_KIEN + " = ?";
        String[] selectionArgs = {String.valueOf(giaPhaID)};
        Cursor cursor = db.query(DatabaseHelper.TABLE_SU_KIEN, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")     int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_SU_KIEN));
                @SuppressLint("Range")    String tenSuKien = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEN_SU_KIEN));
                @SuppressLint("Range")   String ngay = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NGAY_SU_KIEN));
                SuKien suKien = new SuKien(id, tenSuKien, ngay);
                suKienList.add(suKien);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return suKienList;
    }

    // Phương thức để cập nhật thông tin sự kiện
    public int updateSuKien(int suKienID, String tenSuKien, String ngay) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_TEN_SU_KIEN, tenSuKien);
            values.put(DatabaseHelper.COLUMN_NGAY_SU_KIEN, ngay);
            String selection = DatabaseHelper.COLUMN_ID_SU_KIEN + " = ?";
            String[] selectionArgs = {String.valueOf(suKienID)};
            int count = db.update(DatabaseHelper.TABLE_SU_KIEN, values, selection, selectionArgs);
            db.close();
            return count;
        } catch (Exception e) {
            Log.e("SuKienDAO", "updateSuKien: ", e);
            return -1;
        }
    }

    // Phương thức để xóa sự kiện
    public int deleteSuKien(int suKienID) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String selection = DatabaseHelper.COLUMN_ID_SU_KIEN + " = ?";
            String[] selectionArgs = {String.valueOf(suKienID)};
            int count = db.delete(DatabaseHelper.TABLE_SU_KIEN, selection, selectionArgs);
            db.close();
            return count;
        } catch (Exception e) {
            Log.e("SuKienDAO", "deleteSuKien: ", e);
            return -1;
        }
    }

    // Phương thức để tìm sự kiện theo ngày
    public List<SuKien> getSuKienByNgay(int giaPhaID, String ngay) {
        List<SuKien> suKienList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.COLUMN_ID_SU_KIEN,
                DatabaseHelper.COLUMN_TEN_SU_KIEN,
                DatabaseHelper.COLUMN_NGAY_SU_KIEN
        };
        String selection = DatabaseHelper.COLUMN_GIA_PHA_ID_SU_KIEN + " = ? AND " + DatabaseHelper.COLUMN_NGAY_SU_KIEN + " = ?";
        String[] selectionArgs = {String.valueOf(giaPhaID), ngay};
        Cursor cursor = db.query(DatabaseHelper.TABLE_SU_KIEN, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_SU_KIEN));
                @SuppressLint("Range")   String tenSuKien = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEN_SU_KIEN));
                @SuppressLint("Range") String ngaySuKien = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NGAY_SU_KIEN));
                SuKien suKien = new SuKien(id, tenSuKien, ngaySuKien);
                suKienList.add(suKien);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return suKienList;
    }
}
