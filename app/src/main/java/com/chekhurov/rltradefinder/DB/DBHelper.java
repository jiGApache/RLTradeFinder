package com.chekhurov.rltradefinder.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "rlItems";

    public static final String TABLE_ITEMS = "items";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PAINTABLE = "paintable";
    public static final String KEY_PRICE = "price";
    public static final String KEY_BASE_IMAGE_PATH = "baseImage";

    public static final String TABLE_COLORED_IMAGES = "coloredImages";

    public static final String KEY_PAINTED_ITEM_ID = "paintedItemID";
    public static final String KEY_BLACK = "black";
    public static final String KEY_WHITE = "white";
    public static final String KEY_GREY = "grey";
    public static final String KEY_CRIMSON = "crimson";
    public static final String KEY_PINK = "pink";
    public static final String KEY_COBALT = "cobalt";
    public static final String KEY_SK_BLUE = "skyBlue";
    public static final String KEY_B_SIENA = "burntSiena";
    public static final String KEY_SAFFRON = "saffron";
    public static final String KEY_LIME = "lime";
    public static final String KEY_F_GREEN = "forestGreen";
    public static final String KEY_ORANGE = "orange";
    public static final String KEY_PURPLE = "purple";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ITEMS +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_TYPE + " TEXT, " +
                KEY_PAINTABLE + " NUMERIC, " +
                KEY_PRICE + " TEXT, " +
                KEY_BASE_IMAGE_PATH + " TEXT" +
                ");"
        );

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_COLORED_IMAGES +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_PAINTED_ITEM_ID + " integer, " +
                KEY_BLACK + " TEXT, " +
                KEY_WHITE + " TEXT, " +
                KEY_GREY + " TEXT, " +
                KEY_CRIMSON + " TEXT, " +
                KEY_PINK + " TEXT, " +
                KEY_COBALT + " TEXT, " +
                KEY_SK_BLUE + " TEXT, " +
                KEY_B_SIENA + " TEXT, " +
                KEY_SAFFRON + " TEXT, " +
                KEY_LIME + " TEXT, " +
                KEY_F_GREEN + " TEXT, " +
                KEY_ORANGE + " TEXT, " +
                KEY_PURPLE + " TEXT, " +
                "FOREIGN KEY (" + KEY_PAINTED_ITEM_ID + ") " +
                "REFERENCES " + TABLE_ITEMS + "(" + KEY_ID + ")" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
