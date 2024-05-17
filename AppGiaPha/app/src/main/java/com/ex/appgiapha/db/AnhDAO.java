package com.ex.appgiapha.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.ex.appgiapha.model.Anh;

import java.util.ArrayList;
import java.util.List;

public class AnhDAO {
    private DatabaseHelper dbHelper;

    public AnhDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm ảnh vào database
    public long addAnh(Anh anh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID_ABUM_ANH, anh.getIdAlbum());
        values.put(DatabaseHelper.COLUMN_LINK, anh.getLink());
        long newRowId = db.insert(DatabaseHelper.TABLE_ANH, null, values);
        db.close();
        return newRowId;
    }

    // Xóa ảnh từ database
    public void deleteAnh(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_ANH, DatabaseHelper.COLUMN_ID_ANH + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Lấy danh sách ảnh theo ID của album
    public List<Anh> getAllAnhByAlbumId(int albumId) {
        List<Anh> anhList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ANH +
                " WHERE " + DatabaseHelper.COLUMN_ID_ABUM_ANH + " = ?", new String[]{String.valueOf(albumId)});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_ANH));
                @SuppressLint("Range") String link = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LINK));
                @SuppressLint("Range") int idAlbum = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_ABUM_ANH));
                Anh anh = new Anh(id, idAlbum, link);
                anhList.add(anh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return anhList;
    }
}
