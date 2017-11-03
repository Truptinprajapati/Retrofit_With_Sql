package com.trupti.mensfashiontipsone.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vaksys-android-52 on 25/7/17.
 */

public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "manfashiontips";

    public static final String TABLE_NAME = "manfashion";

    public static final String COL_ID = "COL_ID";
    public static final String TOPIC_ID = "TOPIC_ID";
    public static final String COLUMN_TITLE = "topic";
    public static final String COLUMN_DESCRIPTION = "description";

    SQLiteDatabase db;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = " CREATE TABLE " + TABLE_NAME +
                " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TOPIC_ID + " TEXT, "
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT" + " ) ";
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addData(String topicId, String title,String description) {
        db = this.getWritableDatabase();
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOPIC_ID,topicId);
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DESCRIPTION, description);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }
}
