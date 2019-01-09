package com.example.shahed.yararch;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class YarDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "YarDatabase";
    public static final int DATABASE_VERSION = 1;
    //public Context mContext;

    public static final String YAR_PROFILE_INFO_TABLE = "tbl_profile";
    public static final String PROFILE_ID = "user_id";
    public static final String PROFILE_LOGIN_ID = "user_login_id";
    public static final String PROFILE_NAME= "fullname";
    public static final String PROFILE_FATHER_NAME = "father_name";
    public static final String PROFILE_PHONE = "phone_no";
    public static final String PROFILE_ADDRESS = "address";
    public static final String PROFILE_REGISTER_DATE = "register_date";
    public static final String PROFILE_USER_DETAILS = "user_details";
    public static final String PROFILE_IMAGE_PATH = "image_path";
    public static final String CREATE_PROFILE_TABLE = "create table " +YAR_PROFILE_INFO_TABLE+"("+
            PROFILE_ID+" integer primary key, "+
            PROFILE_LOGIN_ID+" integer, "+
            PROFILE_NAME+" text, "+
            PROFILE_FATHER_NAME+" text, "+
            PROFILE_PHONE+" text, "+
            PROFILE_ADDRESS+" text, "+
            PROFILE_REGISTER_DATE+" text, "+
            PROFILE_USER_DETAILS+" text, "+
            PROFILE_IMAGE_PATH+" text);";

    public static final String YAR_LOGIN_TABLE = "tbl_login";
    public static final String LOGIN_ID = "login_id";
    public static final String LOGIN_PROFILE_ID = "profile_id";
    public static final String LOGIN_USERNAME= "login_username";
    public static final String LOGIN_PASSWORD = "password";
    public static final String LOGIN_USER_TYPE = "login_type";
    public static final String CREATE_LOGIN_TABLE = "create table " +YAR_LOGIN_TABLE+"("+
            LOGIN_ID+" integer primary key, "+
            LOGIN_PROFILE_ID+" integer, "+
            LOGIN_USERNAME+" text, "+
            LOGIN_USER_TYPE+" text, "+
            LOGIN_PASSWORD+" text);";

//    public static final String INSERT_LOGIN_USERNAME = "yousuf";
//    public static final String INSERT_LOGIN_PASSWORD = "123456";
//    public static final String INSERT_LOGIN_TABLE = "insert into " +YAR_LOGIN_TABLE+"("+
//            LOGIN_USERNAME+","+LOGIN_PASSWORD+") values ("+INSERT_LOGIN_USERNAME+","+INSERT_LOGIN_PASSWORD+");";

    public static final String YAR_DONATION_TABLE = "tbl_donation";
    public static final String PROFILE_USER_ID = "profile_user_id";
    public static final String DONATION_AMOUNT = "donation_amount";
    public static final String DONATION_DATE = "donation_date";
    public static final String DONATION_CAUSE = "donation_cause";
    public static final String CREATE_DONATION_TABLE = "create table " +YAR_DONATION_TABLE+"("+
            PROFILE_USER_ID+" integer primary key, "+
            DONATION_AMOUNT+" numeric, "+
            DONATION_DATE+" text, "+
            DONATION_CAUSE+" text);";

    public YarDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_PROFILE_TABLE);
            db.execSQL(CREATE_LOGIN_TABLE);
            db.execSQL(CREATE_DONATION_TABLE);
           // db.execSQL(INSERT_LOGIN_TABLE);
           //Toast.makeText(mContext, "Oncreate Thanks", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
           // Toast.makeText(mContext, "Exception :"+e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            db.execSQL("drop table if exists " + YAR_PROFILE_INFO_TABLE);
            db.execSQL("drop table if exists " + YAR_LOGIN_TABLE);
            db.execSQL("drop table if exists " + YAR_DONATION_TABLE);
            onCreate(db);
            //Toast.makeText(mContext, "OnUpgrade ! Like", Toast.LENGTH_LONG).show();
        }catch (Exception e){
           // Toast.makeText(mContext, "Exception :"+e, Toast.LENGTH_LONG).show();
        }

    }
}
