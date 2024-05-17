package com.ex.appgiapha.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ex.appgiapha.model.CauChuyen;

import java.util.ArrayList;
import java.util.List;

public class CauChuyenDAO {
    private DatabaseHelper dbHelper;

    public CauChuyenDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addCauChuyen(CauChuyen cauChuyen) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CONTENT_CAU_CHUYEN, cauChuyen.getContent());
        values.put(DatabaseHelper.COLUMN_NGAY_TAO_CAU_CHUYEN, cauChuyen.getNgayTao());
        values.put(DatabaseHelper.COLUMN_GIA_PHA_ID_CAU_CHUYEN, cauChuyen.getGiaPhaId());

        long newRowId = db.insert(DatabaseHelper.TABLE_CAU_CHUYEN, null, values);
        db.close();
        return newRowId;
    }

    public List<CauChuyen> getAllCauChuyen() {
        List<CauChuyen> cauChuyenList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_CAU_CHUYEN, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID_CAU_CHUYEN));
                @SuppressLint("Range")  String content = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENT_CAU_CHUYEN));
                @SuppressLint("Range")  String ngayTao = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NGAY_TAO_CAU_CHUYEN));
                @SuppressLint("Range")  int giaPhaId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_GIA_PHA_ID_CAU_CHUYEN));
                CauChuyen cauChuyen = new CauChuyen(id, content, ngayTao, giaPhaId);
                cauChuyenList.add(cauChuyen);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cauChuyenList;
    }
    public int updateCauChuyen(CauChuyen cauChuyen) {
        Log.d("updateCauChuyen", cauChuyen.getId()+"");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CONTENT_CAU_CHUYEN, cauChuyen.getContent());
        values.put(DatabaseHelper.COLUMN_NGAY_TAO_CAU_CHUYEN, cauChuyen.getNgayTao());
        values.put(DatabaseHelper.COLUMN_GIA_PHA_ID_CAU_CHUYEN, cauChuyen.getGiaPhaId());

        // updating row
        return db.update(DatabaseHelper.TABLE_CAU_CHUYEN, values, DatabaseHelper.COLUMN_ID_CAU_CHUYEN + " = ?",
                new String[]{String.valueOf(cauChuyen.getId())});
    }

    public void deleteCauChuyen(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_CAU_CHUYEN, DatabaseHelper.COLUMN_ID_CAU_CHUYEN + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
