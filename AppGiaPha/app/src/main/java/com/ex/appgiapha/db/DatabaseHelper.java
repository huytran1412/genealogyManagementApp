package com.ex.appgiapha.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Tên bảng và các cột trong bảng Album
    public static final String TABLE_ALBUM = "Album";
    public static final String COLUMN_ID_ALBUM = "ID";
    public static final String COLUMN_TEN_ALBUM = "Ten";
    public static final String COLUMN_GIA_PHA_ID_ALBUM = "GiaPhaID";

    // Tên bảng và các cột trong bảng Anh
    public static final String TABLE_ANH = "Anh";
    public static final String COLUMN_ID_ANH = "ID";
    public static final String COLUMN_ID_ABUM_ANH = "IDAlbum";
    public static final String COLUMN_LINK = "Link";
    // Tên bảng và các cột trong bảng Sự kiện
    public static final String TABLE_SU_KIEN = "SuKien";
    public static final String COLUMN_ID_SU_KIEN = "ID";
    public static final String COLUMN_TEN_SU_KIEN = "Ten";
    public static final String COLUMN_NGAY_SU_KIEN = "Ngay";
    public static final String COLUMN_GIA_PHA_ID_SU_KIEN = "GiaPhaID";

    // Tên bảng và các cột trong bảng Câu chuyện
    public static final String TABLE_CAU_CHUYEN = "CauChuyen";
    public static final String COLUMN_ID_CAU_CHUYEN = "ID";
    public static final String COLUMN_CONTENT_CAU_CHUYEN = "Content";
    public static final String COLUMN_NGAY_TAO_CAU_CHUYEN = "NgayTao";
    public static final String COLUMN_GIA_PHA_ID_CAU_CHUYEN = "GiaPhaID";


    // Tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "GiaPha2.db";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_GIOI_TINH = "GioiTinh";

    // Tên bảng và các cột trong bảng GiaPha
    public static final String TABLE_GIAPHA = "GiaPha";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TENGIAPHA = "TENGIAPHA";

    // Câu lệnh tạo bảng GiaPha
    private static final String CREATE_TABLE_GIAPHA = "CREATE TABLE IF NOT EXISTS " + TABLE_GIAPHA + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TENGIAPHA + " VARCHAR(255) NOT NULL" +
            ")";

    // Tên bảng và các cột trong bảng ThanhVien
    public static final String TABLE_THANHVIEN = "ThanhVien";
    public static final String COLUMN_ID_THANHVIEN = "ID";
    public static final String COLUMN_TEN_THANHVIEN = "Ten";
    public static final String COLUMN_NGAYSINH = "NgaySinh";
    public static final String COLUMN_DIACHI = "DiaChi";
    public static final String COLUMN_NGAYMAT = "NgayMat";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_CONCUA = "ConCua";
    public static final String COLUMN_LEVELID = "LevelID";
    public static final String COLUMN_GIA_PHA_ID= "GIAPHAID";

    // Tên bảng và các cột trong bảng Level
    public static final String TABLE_LEVEL = "Level";
    public static final String COLUMN_ID_LEVEL = "ID";
    public static final String COLUMN_TEN_LEVEL = "TENLEVEL";

    // Câu lệnh tạo bảng ThanhVien
    private static final String CREATE_TABLE_THANHVIEN = "CREATE TABLE " + TABLE_THANHVIEN + " (" +
            COLUMN_ID_THANHVIEN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TEN_THANHVIEN + " VARCHAR(255) NOT NULL," +
            COLUMN_NGAYSINH + " DATE," +
            COLUMN_DIACHI + " VARCHAR(255)," +
            COLUMN_NGAYMAT + " DATE," +
            COLUMN_EMAIL + " VARCHAR(255)," +
            COLUMN_CONCUA + " INTEGER," +
            COLUMN_LEVELID + " INTEGER," +
            COLUMN_GIOI_TINH + " INTEGER ,"+
            COLUMN_GIA_PHA_ID + " INTEGER," + // Thêm trường giapha_id
            "FOREIGN KEY (" + COLUMN_CONCUA + ") REFERENCES " + TABLE_THANHVIEN + "(" + COLUMN_ID_THANHVIEN + ") ON DELETE CASCADE," +
            "FOREIGN KEY (" + COLUMN_LEVELID + ") REFERENCES " + TABLE_LEVEL + "(" + COLUMN_ID_LEVEL + ") ON DELETE CASCADE," +
            "FOREIGN KEY (" + COLUMN_GIA_PHA_ID + ") REFERENCES " + TABLE_GIAPHA + "(" + COLUMN_ID + ") ON DELETE CASCADE" + // Khóa ngoại tham chiếu đến bảng GiaPha
            ")";


    // Câu lệnh tạo bảng Level
    private static final String CREATE_TABLE_LEVEL = "CREATE TABLE " + TABLE_LEVEL + " (" +
            COLUMN_ID_LEVEL + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TEN_LEVEL + " INTEGER NOT NULL," +
            COLUMN_GIA_PHA_ID+ " INTEGER," + // Thêm cột khóa ngoại
            "FOREIGN KEY (" + COLUMN_ID + ") REFERENCES " + TABLE_GIAPHA + "(" + COLUMN_ID + ") ON DELETE CASCADE" +
            ")";
    private static final String CREATE_TABLE_SU_KIEN = "CREATE TABLE " + TABLE_SU_KIEN + " (" +
            COLUMN_ID_SU_KIEN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TEN_SU_KIEN + " VARCHAR(255) NOT NULL," +
            COLUMN_NGAY_SU_KIEN + " DATE," +
            COLUMN_GIA_PHA_ID_SU_KIEN + " INTEGER," + // Thêm trường gia pha id
            "FOREIGN KEY (" + COLUMN_GIA_PHA_ID_SU_KIEN + ") REFERENCES " + TABLE_GIAPHA + "(" + COLUMN_ID + ") ON DELETE CASCADE" + // Khóa ngoại tham chiếu đến bảng GiaPha
            ")";

    // Câu lệnh tạo bảng Câu chuyện
    private static final String CREATE_TABLE_CAU_CHUYEN = "CREATE TABLE " + TABLE_CAU_CHUYEN + " (" +
            COLUMN_ID_CAU_CHUYEN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_CONTENT_CAU_CHUYEN + " TEXT," +
            COLUMN_NGAY_TAO_CAU_CHUYEN + " DATE," +
            COLUMN_GIA_PHA_ID_CAU_CHUYEN + " INTEGER," + // Thêm trường gia pha id
            "FOREIGN KEY (" + COLUMN_GIA_PHA_ID_CAU_CHUYEN + ") REFERENCES " + TABLE_GIAPHA + "(" + COLUMN_ID + ") ON DELETE CASCADE" + // Khóa ngoại tham chiếu đến bảng GiaPha
            ")";
    // Câu lệnh tạo bảng Album
    private static final String CREATE_TABLE_ALBUM = "CREATE TABLE " + TABLE_ALBUM + " (" +
            COLUMN_ID_ALBUM + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TEN_ALBUM + " VARCHAR(255) NOT NULL," +
            COLUMN_GIA_PHA_ID_ALBUM + " INTEGER," + // Thêm trường gia pha id
            "FOREIGN KEY (" + COLUMN_GIA_PHA_ID_ALBUM + ") REFERENCES " + TABLE_GIAPHA + "(" + COLUMN_ID + ") ON DELETE CASCADE" + // Khóa ngoại tham chiếu đến bảng GiaPha
            ")";

    // Câu lệnh tạo bảng Anh
    private static final String CREATE_TABLE_ANH = "CREATE TABLE " + TABLE_ANH + " (" +
            COLUMN_ID_ANH + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ID_ABUM_ANH + " INTEGER," +
            COLUMN_LINK + " TEXT," +
            "FOREIGN KEY (" + COLUMN_ID_ABUM_ANH + ") REFERENCES " + TABLE_ALBUM + "(" + COLUMN_ID_ALBUM + ") ON DELETE CASCADE" + // Khóa ngoại tham chiếu đến bảng Album
            ")";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng GiaPha, ThanhVien và Level
        db.execSQL(CREATE_TABLE_GIAPHA);
        db.execSQL(CREATE_TABLE_LEVEL);
        db.execSQL(CREATE_TABLE_THANHVIEN);
        db.execSQL(CREATE_TABLE_SU_KIEN);
        db.execSQL(CREATE_TABLE_CAU_CHUYEN);
        db.execSQL(CREATE_TABLE_ALBUM);
        db.execSQL(CREATE_TABLE_ANH);
        // create default gia pha
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TENGIAPHA, ""); // Tên rỗng
        db.insert(TABLE_GIAPHA, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại và tạo lại bảng mới
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIAPHA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THANHVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVEL);
        onCreate(db);
    }
}
