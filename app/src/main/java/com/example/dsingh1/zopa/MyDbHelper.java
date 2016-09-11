package com.example.dsingh1.zopa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by DSingh1 on 5/1/2016.
 */
public class MyDbHelper extends SQLiteOpenHelper {  public static final String DATABASE_NAME = "MyDBName.db";
    public static final String SITE_TABLE_NAME = "site";
    public static final String KEY_TABLE_NAME = "keyValues";
   public static final String SITE_SITE_NAME="site_name";
    public static final String SITE_JSON_CODE="jsonCode";
    public static final String SITE_LOGO="logo";
    public static final String KEY_COL_KEY= "key";
    public static final String KEY_COL_VALUE = "value";



    private HashMap hp;

    public MyDbHelper(Context context)
    {

        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(
                "create table IF NOT EXISTS site " +
                        "(site_name  text  primary key, jsonCode  text,logo  blob)"
        );
        db.execSQL(
                "create table IF NOT EXISTS keyValues   " +
                        "(site_name  text, key  text,value  text, primary key (site_name,key,value))"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS site");
        onCreate(db);
    }

    public boolean deleteData(){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from  site");
            db.execSQL("delete from  keyValues");
        }catch (Exception e){
         Log.d("delete exception",e.getMessage());
        }
        return true;
    }
    public boolean insertSite  (String name, String jsonCode, Bitmap logo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("site_name", name);
        contentValues.put("jsonCode", jsonCode);
        contentValues.put("logo", Utility.getBytes(logo));
        db.insert("site", null, contentValues);
        return true;
    }

    public boolean insertKeyValues  (String name, LinkedHashMap<String,String> map)
    {

try {
    for (String key : map.keySet()) {
        String value = map.get(key);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("site_name", name);
        contentValues.put("key", key);
        contentValues.put("value", value);
       Log.d("insertionQ",key);
        db.insert(KEY_TABLE_NAME, null, contentValues);
    }
}catch (SQLiteConstraintException e){
    Log.d("inside exception","");
}


        return true;
    }




  public ArrayList<String> getSiteList(){
      ArrayList<String> array_list = new ArrayList<String>();

      //hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select site_name from site", null );
      res.moveToFirst();

      while(res.isAfterLast() == false){
          array_list.add(res.getString(res.getColumnIndex(SITE_SITE_NAME)));
          res.moveToNext();
      }
      return array_list;
  }

    public HashMap<String,Object> getSiteDetials(String site){
        List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select *  from site where site_name = '"+site+"'", null );
        res.moveToFirst();
        HashMap<String,Object> hashMap=new HashMap<>();
        while(res.isAfterLast() == false){

            hashMap.put("site_name", res.getString(res.getColumnIndex(SITE_SITE_NAME)));
            hashMap.put("jsonCode",res.getString(res.getColumnIndex(SITE_JSON_CODE)));
            hashMap.put("logo",res.getBlob(res.getColumnIndex(SITE_LOGO)));
            res.moveToNext();
            data.add(hashMap);
        }
        return hashMap;
    }

    public List<LinkedHashMap<String,String>> getKeyValuesData(String site_name){
        List<LinkedHashMap<String,String>> data=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from keyValues where site_name = '"+site_name+"' order by rowid", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            LinkedHashMap<String,String> hashMap=new LinkedHashMap<>();
            hashMap.put("key", res.getString(res.getColumnIndex(KEY_COL_KEY)));
            hashMap.put("value",res.getString(res.getColumnIndex(KEY_COL_VALUE)));
            res.moveToNext();
            Log.d("deepak",hashMap.toString());
            data.add(hashMap);
        }


        return data;

    }
}