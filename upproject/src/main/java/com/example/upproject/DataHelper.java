package com.example.upproject;

/**
 * Created by myalien on 2016/7/18.
 */
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataHelper {
    // 数据库名称
    private static String DB_NAME = "information.db";
    // 数据库版本
    private static int DB_VERSION = 3;
    private SQLiteDatabase db;
    private SqilteHelper dbHelper;

    public DataHelper(Context context) {
        dbHelper = new SqilteHelper(context, DB_NAME, null, DB_VERSION );
        db = dbHelper.getWritableDatabase();
    }

    public void Close() {
        db.close();
        dbHelper.close();
    }

    // 设置电器的的TIME、POWER、STATE、TOLERANCE的记录,从数据库中的得到数据然后返回一个类列表用于更新客户端的值
    public List<UserInfo> GetAppList() {
        List<UserInfo> userList = new ArrayList<UserInfo>();
        Cursor cursor = db.query(SqilteHelper.TB_NAME,null,null,null,null,null,
                UserInfo.APPLIANCES + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null )) {
            UserInfo user = new UserInfo();
            user.setdbAppliance(cursor.getString(0));
            user.setdbTime(cursor.getString(1));
            user.setdbPower(cursor.getString(2));
            user.setdbPower(cursor.getString(3));
            user.setdbTolerance(cursor.getString(4));
            userList.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }


    // 判断users表中的是否包含某个Appliance的记录
    public Boolean HaveAppInfo(String Appliances) {
        Boolean b = false;
        Cursor cursor = db.query(SqilteHelper.TB_NAME, null, UserInfo.APPLIANCES
                + "=?", new String[]{Appliances}, null, null, null );
        b = cursor.moveToFirst();
        Log. e("HaveAppInfo", b.toString());
        cursor.close();
        return b;
    }

    // 更新users表的记录，根据Appliance更新其余各项

    /**
     *
     * @param app 表项的键值
     * @param column 将要更新的表的属性值
     * @param newvalues 属性对应的新值
     * @return 返回操作id
     */
    public int UpdateAppInfo(String app, String column,String newvalues) {
        ContentValues values = new ContentValues();
        values.put(column, newvalues);
        int id = db.update(SqilteHelper.TB_NAME, values, UserInfo.APPLIANCES + "=?" , new String[]{app});
        Log. e("UpdateAppInfo2", id + "");
        return id;
    }

    // 添加app表的记录
    public Long SaveAppInfo(UserInfo app) {
        ContentValues values = new ContentValues();
        values.put(UserInfo.APPLIANCES, app.getdbAppliance());
        values.put(UserInfo.TIME,app.getdbTime());
        values.put(UserInfo.POWER,app.getdbPower());
        values.put(UserInfo.STATE,app.getdbState());
        values.put(UserInfo.TOLERANCE,app.getdbTolerance());
        Long uid = db.insert(SqilteHelper.TB_NAME, UserInfo.APPLIANCES, values);
        Log. e("SaveAppInfo", uid + "");
        return uid;
    }

    // 删除app表的记录
    public int DelAppInfo(String app) {
        int id = db.delete(SqilteHelper.TB_NAME,
                UserInfo.APPLIANCES + "=?", new String[]{app});
        Log. e("DelAppInfo", id + "");
        return id;
    }

    //得到某个app的信息
    public static UserInfo getUserByName(String app,List<UserInfo> userList){
        UserInfo userInfo = null;
        int size = userList.size();
        for( int i=0;i<size;i++){
            if(app.equals(userList.get(i).getdbAppliance())){
                userInfo = userList.get(i);
                break;
            }
        }
        return userInfo;
    }
}