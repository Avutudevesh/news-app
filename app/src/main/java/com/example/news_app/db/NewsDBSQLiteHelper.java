package com.example.news_app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class NewsDBSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NEWS = "news";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SOURCE_ID = "sourceId";
    public static final String COLUMN_SOURCE_NAME = "sourceName";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_UTL_TO_IMAGE = "url_to_image";
    public static final String COLUMN_PUBLISHED_AT = "publishedAt";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    //     Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NEWS + "( " + COLUMN_ID
            + " text primary key, "
            + COLUMN_SOURCE_ID + "text not null,"
            + COLUMN_SOURCE_NAME + "text not null,"
            + COLUMN_TITLE + "text not null,"
            + COLUMN_DESCRIPTION + "text not null,"
            + COLUMN_URL + "text not null,"
            + COLUMN_UTL_TO_IMAGE + "text not null,"
            + COLUMN_PUBLISHED_AT + "text not null,"
            + " text not null);";

    public NewsDBSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(NewsDBSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        onCreate(db);
    }

}