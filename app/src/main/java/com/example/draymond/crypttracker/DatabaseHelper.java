package com.example.draymond.crypttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="ownedCoins.db";

    public static final String TABLE_NAME ="coinTable";

    public static final int VERSION = 3;
    public static final String COL_1 ="ID";
    public static final String COL_2 ="COIN_ID";


    public static final String COL_3 ="SYMBOL";
    public static final String COL_4 ="AMOUNT";
    public static final String COL_5 ="TRADE_PRICE";

    private static DatabaseHelper instance = null;





    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    public static DatabaseHelper getInstance(Context context){

        if(instance== null){
            instance = new DatabaseHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,COIN_ID TEXT,SYMBOL TEXT, AMOUNT REAL, TRADE_PRICE REAL )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insert(String id,String symbol, double amount, double price){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,id);
        contentValues.put(COL_3,symbol);
        contentValues.put(COL_4,amount);
        contentValues.put(COL_5,price);


        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result==-1){
            return false;

        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME,null);
        return result;

    }


    public Integer delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"COIN_ID = ?", new String[] {id});
    }
}
