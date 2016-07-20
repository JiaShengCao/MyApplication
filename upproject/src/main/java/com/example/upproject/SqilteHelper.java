package com.example.upproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by myalien on 2016/7/17.
 */
public class SqilteHelper extends SQLiteOpenHelper{
    public static final String TB_NAME="app";

    public SqilteHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE IF NOT EXISTS "+
                TB_NAME+ "("+
                UserInfo.APPLIANCES+ " varchar primary key,"+
                UserInfo.TIME+ "varchar,"+
                UserInfo.POWER+ "varchar,"+
                UserInfo.STATE+ " varchar,"+
                UserInfo.TOLERANCE+ " varchar,"+
                ")"
        );
        Log. e("Database" ,"onCreate" );
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TB_NAME );
        onCreate(db);
        Log. e("Database" ,"onUpgrade" );
    }
//    public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn){
//        try{
//            db.execSQL( "ALTER TABLE " +
//                    TB_NAME + " CHANGE " +
//                    oldColumn + " "+ newColumn +
//                    " " + typeColumn
//            );
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public void insertRow(SQLiteDatabase db,String app,String tm,String po,String sta,String tol)
//    {
//        try {
//            db.execSQL("insert into " + TB_NAME + " values" + "("
//                    + app + "," + tm + "," + po + "," + sta + ',' + tol + ");");
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteRow(SQLiteDatabase db,String app)
//    {
//        try {
//            db.execSQL("delete from " + TB_NAME + " where _appliances = "+app);
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public void updateTable(SQLiteDatabase db,String app,String column,String newvalues)
//    {
//        try {
//            db.execSQL("update " + TB_NAME +" set "+column+"="+newvalues+" where _appliances ="+app);
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
}
