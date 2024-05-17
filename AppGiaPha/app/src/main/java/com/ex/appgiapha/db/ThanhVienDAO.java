package com.ex.appgiapha.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ex.appgiapha.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private DatabaseHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Phương thức để thêm thành viên
    // Phương thức để thêm thành viên
    public long addThanhVien(ThanhVien thanhVien, int giaPhaId) {
        try{
            giaPhaId = 1;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_TEN_THANHVIEN, thanhVien.getTen());
            values.put(DatabaseHelper.COLUMN_NGAYSINH, thanhVien.getNgaySinh());
            values.put(DatabaseHelper.COLUMN_DIACHI, thanhVien.getDiaChi());
            values.put(DatabaseHelper.COLUMN_NGAYMAT, thanhVien.getNgayMat());
            values.put(DatabaseHelper.COLUMN_EMAIL, thanhVien.getEmail());
            values.put(DatabaseHelper.COLUMN_CONCUA, thanhVien.getConCua());
            values.put(DatabaseHelper.COLUMN_LEVELID, thanhVien.getLevelID());
            values.put(DatabaseHelper.COLUMN_GIA_PHA_ID, giaPhaId); // Thêm giá trị giaphaId
            values.put(DatabaseHelper.COLUMN_GIOI_TINH, thanhVien.getGioiTinh());
            long newRowId = db.insert(DatabaseHelper.TABLE_THANHVIEN, null, values);
            db.close();
            return newRowId;
        } catch (Exception e){
            Log.e("Error", e.getMessage());
            return -1;
        }
    }


    // Phương thức để xoá thành viên
    public boolean deleteThanhVien(int thanhVienID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete(DatabaseHelper.TABLE_THANHVIEN, DatabaseHelper.COLUMN_ID_THANHVIEN + " = ?", new String[]{String.valueOf(thanhVienID)});
        db.close();
        return rowsAffected > 0;
    }

    // Phương thức để cập nhật thông tin thành viên
    public List<ThanhVien> getThanhVienByIdLevel(int idLevel) {
        List<ThanhVien> thanhVienList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_TEN_THANHVIEN,
                DatabaseHelper.COLUMN_NGAYSINH,
                DatabaseHelper.COLUMN_DIACHI,
                DatabaseHelper.COLUMN_NGAYMAT,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_CONCUA,
                DatabaseHelper.COLUMN_LEVELID,
                DatabaseHelper.COLUMN_GIA_PHA_ID,
                DatabaseHelper.COLUMN_GIOI_TINH
        };
        String selection = DatabaseHelper.COLUMN_LEVELID + " = ?";
        String[] selectionArgs = {String.valueOf(idLevel)};
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_THANHVIEN,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String ten = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEN_THANHVIEN));
            String ngaySinh = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NGAYSINH));
            String diaChi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIACHI));
            String ngayMat = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NGAYMAT));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
            int conCua = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONCUA));
            int levelID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEVELID));
            int giaphaId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GIA_PHA_ID));
            int gioiTinh = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GIOI_TINH));
            ThanhVien thanhVien = new ThanhVien(id, ten, ngaySinh, diaChi, ngayMat, email, conCua, levelID, giaphaId, gioiTinh);
            thanhVienList.add(thanhVien);
        }
        cursor.close();
        return thanhVienList;
    }
    public List<ThanhVien> getThanhVienByDoiAndGiaPha(int tenDoi, int idGiaPha) {
        List<ThanhVien> thanhVienList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT tv.* FROM " + DatabaseHelper.TABLE_THANHVIEN + " tv " +
                "JOIN " + DatabaseHelper.TABLE_LEVEL + " lv ON tv." + DatabaseHelper.COLUMN_LEVELID + " = lv." + DatabaseHelper.COLUMN_ID_LEVEL + " " +
                "JOIN " + DatabaseHelper.TABLE_GIAPHA + " gp ON lv." + DatabaseHelper.COLUMN_GIA_PHA_ID + " = gp." + DatabaseHelper.COLUMN_ID +
                " WHERE gp." + DatabaseHelper.COLUMN_ID+ " = ? AND lv." + DatabaseHelper.COLUMN_TEN_LEVEL + " = ?";

        String[] selectionArgs = {String.valueOf(idGiaPha), String.valueOf(tenDoi)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_THANHVIEN));
            String ten = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEN_THANHVIEN));
            String ngaySinh = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NGAYSINH));
            String diaChi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIACHI));
            String ngayMat = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NGAYMAT));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
            int conCua = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONCUA));
            int levelID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEVELID));
            int giaphaId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GIA_PHA_ID));
            int gioiTinh = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GIOI_TINH));
            ThanhVien thanhVien = new ThanhVien(id, ten, ngaySinh, diaChi, ngayMat, email, conCua, levelID, giaphaId,gioiTinh);
            thanhVienList.add(thanhVien);
        }
        cursor.close();
        Log.d("ThanhVienDAO", "getThanhVienByDoiAndGiaPha: " + thanhVienList.size());
        return thanhVienList;
    }
    public int updateThanhVien(ThanhVien thanhVien) {
        Log.d("ThanhVienDAO", "updateThanhVien: " + thanhVien.getId());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEN_THANHVIEN, thanhVien.getTen());
        values.put(DatabaseHelper.COLUMN_NGAYSINH, thanhVien.getNgaySinh());
        values.put(DatabaseHelper.COLUMN_DIACHI, thanhVien.getDiaChi());
        values.put(DatabaseHelper.COLUMN_NGAYMAT, thanhVien.getNgayMat());
        values.put(DatabaseHelper.COLUMN_EMAIL, thanhVien.getEmail());
        values.put(DatabaseHelper.COLUMN_CONCUA, thanhVien.getConCua());
        values.put(DatabaseHelper.COLUMN_LEVELID, thanhVien.getLevelID());
        values.put(DatabaseHelper.COLUMN_GIOI_TINH, thanhVien.getGioiTinh());
        // Xác định điều kiện WHERE để chỉ cập nhật thành viên với id tương ứng
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(thanhVien.getId()) };

        int count = db.update(
                DatabaseHelper.TABLE_THANHVIEN,
                values,
                selection,
                selectionArgs);

        db.close();
        return count;
    }

    public int deleteThanhVienById(int thanhVienId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Xác định điều kiện WHERE để chỉ xóa thành viên với id tương ứng
        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(thanhVienId) };

        // Thực hiện xóa
        int count = db.delete(
                DatabaseHelper.TABLE_THANHVIEN,
                selection,
                selectionArgs);

        db.close();
        return count;
    }
    public List<ThanhVien> getAllMembersByGiaphaId(int giaphaId) {
        List<ThanhVien> thanhVienList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_ID_THANHVIEN,
                DatabaseHelper.COLUMN_TEN_THANHVIEN,
                DatabaseHelper.COLUMN_NGAYSINH,
                DatabaseHelper.COLUMN_DIACHI,
                DatabaseHelper.COLUMN_NGAYMAT,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_CONCUA,
                DatabaseHelper.COLUMN_LEVELID,
                DatabaseHelper.COLUMN_GIOI_TINH
        };
        String selection = DatabaseHelper.COLUMN_GIA_PHA_ID + " = ?";
        String[] selectionArgs = {String.valueOf(giaphaId)};
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_THANHVIEN,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_THANHVIEN));
            String ten = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEN_THANHVIEN));
            String ngaySinh = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NGAYSINH));
            String diaChi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIACHI));
            String ngayMat = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NGAYMAT));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
            int conCua = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONCUA));
            int levelID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEVELID));
            int gioiTinh = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GIOI_TINH));
            ThanhVien thanhVien = new ThanhVien(id, ten, ngaySinh, diaChi, ngayMat, email, conCua, levelID, giaphaId, gioiTinh);
            thanhVienList.add(thanhVien);
        }
        cursor.close();
        return thanhVienList;
    }

}
