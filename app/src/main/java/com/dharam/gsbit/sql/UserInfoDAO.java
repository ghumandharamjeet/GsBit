package com.dharam.gsbit.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dharam.gsbit.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserInfoDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Users.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER_INFO = "USER_INFO";

    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER_INFO + "("
            + COLUMN_PHONE_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_NAME + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER_INFO;

    public UserInfoDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void saveUser(UserInfo userInfo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, userInfo.getPersonName());
        values.put(COLUMN_PASSWORD, userInfo.getPassword());
        values.put(COLUMN_PHONE_NUMBER, userInfo.getPhoneNumber());

        // Inserting Row
        db.insert(TABLE_USER_INFO, null, values);
        db.close();
    }

    public List<UserInfo> getAllUsers()
    {
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        String columnsToFetch[] =  {COLUMN_NAME, COLUMN_PHONE_NUMBER};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =  db.query(TABLE_USER_INFO, columnsToFetch, null, null, null, null, null);

        if(cursor.moveToFirst())
        {
            do {

                UserInfo userInfo = new UserInfo();

                userInfo.setPersonName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                userInfo.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
                userInfoList.add(userInfo);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userInfoList;
    }

    public boolean isUserExisting(String phoneNumber)
    {
        boolean isExisting = false;

        String columnToCheck[] = {COLUMN_PHONE_NUMBER};
        String args[] = {phoneNumber};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER_INFO, columnToCheck,  COLUMN_PHONE_NUMBER+"= ?" , args, null, null, null);

        isExisting = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isExisting;
    }

    public boolean authenticateUser(UserInfo info)
    {
        boolean isUserAuthenticated = false;
        String columnsToCheck[] = {COLUMN_PHONE_NUMBER};
        String args[] = {info.getPhoneNumber() , info.getPassword()};

        SQLiteDatabase db = this.getReadableDatabase();

        /*Cursor cursor = db.query(TABLE_USER_INFO, columnsToCheck, COLUMN_PHONE_NUMBER + "= ? and " + COLUMN_PASSWORD + "= ?", args, null, null, null);
       */

        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_USER_INFO +" WHERE "+ COLUMN_PHONE_NUMBER + "= ? AND " + COLUMN_PASSWORD + "= ?", args);

        isUserAuthenticated = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isUserAuthenticated;
    }

}
