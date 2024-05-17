package com.ex.appgiapha.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ex.appgiapha.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumDAO {
    private DatabaseHelper dbHelper;

    public AlbumDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addAlbum(Album album) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEN_ALBUM, album.getTenAlbum());
        values.put(DatabaseHelper.COLUMN_GIA_PHA_ID_ALBUM, album.getGiaPhaId());
        long newRowId = db.insert(DatabaseHelper.TABLE_ALBUM, null, values);
        db.close();
        return newRowId;
    }

    public List<Album> getAllAlbum() {
        List<Album> albumList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ALBUM, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_ALBUM));
                @SuppressLint("Range") String tenAlbum = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEN_ALBUM));
                @SuppressLint("Range") int giaPhaId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_GIA_PHA_ID_ALBUM));
                Album album = new Album(id, tenAlbum, giaPhaId);
                albumList.add(album);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return albumList;
    }

    public void deleteAlbum(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_ALBUM, DatabaseHelper.COLUMN_ID_ALBUM + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    public void updateAlbum(Album album) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEN_ALBUM, album.getTenAlbum());
        values.put(DatabaseHelper.COLUMN_GIA_PHA_ID_ALBUM, album.getGiaPhaId());

        // Xác định điều kiện cập nhật dựa trên ID của album
        String selection = DatabaseHelper.COLUMN_ID_ALBUM + " = ?";
        String[] selectionArgs = {String.valueOf(album.getId())};

        // Thực hiện cập nhật
        int count = db.update(
                DatabaseHelper.TABLE_ALBUM,
                values,
                selection,
                selectionArgs);

        // Kiểm tra xem có bao nhiêu hàng được cập nhật
        if (count == 0) {
            Log.d("AlbumDAO", "Không có album nào được cập nhật");
        } else {
            Log.d("AlbumDAO", "Cập nhật " + count + " album");
        }

        db.close();
    }

}
