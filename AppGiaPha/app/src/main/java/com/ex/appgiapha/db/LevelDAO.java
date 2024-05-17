package com.ex.appgiapha.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ex.appgiapha.model.Level;

import java.util.ArrayList;
import java.util.List;

public class LevelDAO {
    private DatabaseHelper dbHelper;

    public LevelDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Phương thức để xem level của một gia phả
    public List<Level> getLevelsByGiaPhaID(int giaPhaID) {
        List<Level> levelList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_ID_LEVEL, DatabaseHelper.COLUMN_TEN_LEVEL};
        String selection = DatabaseHelper.COLUMN_GIA_PHA_ID + " = ?";
        String[] selectionArgs = {String.valueOf(giaPhaID)};
        Cursor cursor = db.query(DatabaseHelper.TABLE_LEVEL, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_LEVEL));
                @SuppressLint("Range")  int tenLevel = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEN_LEVEL));
                // Thêm số lượng thành viên của mỗi cấp độ
                int soLuongThanhVien = getSoLuongThanhVienByLevelID(id);
                Level level = new Level(id, tenLevel, soLuongThanhVien);
                levelList.add(level);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return levelList;
    }

    // Phương thức để thêm level cho một gia phả
    public long addLevelForGiaPha(int giaPhaID) {
        try {
            List<Level> currentLevels = getLevelsByGiaPhaID(giaPhaID);
            String newLevelName = String.valueOf(currentLevels.size() + 1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_TEN_LEVEL, newLevelName);
            values.put(DatabaseHelper.COLUMN_GIA_PHA_ID, giaPhaID);
            long newRowId = db.insert(DatabaseHelper.TABLE_LEVEL, null, values);
            db.close();
            Log.d("LevelDAO", "addLevelForGiaPha: " + newRowId);
            return newRowId;
        } catch (Exception e) {
            Log.e("LevelDAO", "addLevelForGiaPha: ", e);
            return -1;
        }
    }

    // Phương thức để lấy số lượng thành viên của từng cấp độ
    private int getSoLuongThanhVienByLevelID(int levelID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {"COUNT(*)"};
        String selection = DatabaseHelper.COLUMN_LEVELID + " = ?";
        String[] selectionArgs = {String.valueOf(levelID)};
        Cursor cursor = db.query(DatabaseHelper.TABLE_THANHVIEN, columns, selection, selectionArgs, null, null, null);
        int soLuongThanhVien = 0;
        if (cursor != null && cursor.moveToFirst()) {
            soLuongThanhVien = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return soLuongThanhVien;
    }
}
