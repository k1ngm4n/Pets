package com.example.android.pets.data;

import com.example.android.pets.data.PetContract.PetEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KingMan on 08-Aug-17.
 */

public class PetDbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "shelter.db";
    private static final int DATABASE_VERSION = 1;

    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + PetEntry.TABLE_NAME + " ("
                + PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetEntry.COLUMN_PET_BREED + " TEXT, "
                + PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + PetEntry.COLUMN_PET_WEIGHT + " REAL NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PETS_TABLE);

//        String sqlStr = "insert into " + PetEntry.TABLE_NAME +
//                " ( " +
//                PetEntry.COLUMN_PET_NAME + ", " +
//                PetEntry.COLUMN_PET_GENDER + ", " +
//                PetEntry.COLUMN_PET_WEIGHT + ", " +
//                PetEntry.COLUMN_PET_BREED + " " +
//                " ) VALUES ( " +
//                "'HHH'" + ", " +
//                "0" + ", " +
//                "25.4" + ", " +
//                "'CHOW'" + " ) ";
//        db.execSQL(sqlStr);
//
//        String sqlStr2 = "insert into pets(fname, fgender, fweight, fgender) values ('asdf', 0, 12.5, 'chow')";
//        db.execSQL(sqlStr2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //donut
    }
}
