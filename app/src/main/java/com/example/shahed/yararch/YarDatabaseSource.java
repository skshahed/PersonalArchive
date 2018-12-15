package com.example.shahed.yararch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class YarDatabaseSource {
    private YarDatabaseHelper yarDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ProfileModel profileModel;
    private LoginModel loginModel;
    private DonationModel donationModel;

    public YarDatabaseSource(Context context){
        yarDatabaseHelper = new YarDatabaseHelper(context);
    }

    public void open(){
        sqLiteDatabase = yarDatabaseHelper.getWritableDatabase();
    }
    public void close(){
        sqLiteDatabase.close();
    }

    public boolean addDoctorInfo(ProfileModel profileModel){
        this.open();
        ContentValues values = new ContentValues();
        values.put(YarDatabaseHelper.PROFILE_NAME, profileModel.getName());
        values.put(YarDatabaseHelper.PROFILE_FATHER_NAME, profileModel.getFatherName());
        values.put(YarDatabaseHelper.PROFILE_PHONE, profileModel.getPhoneNo());
        values.put(YarDatabaseHelper.PROFILE_ADDRESS, profileModel.getAddress());
        values.put(YarDatabaseHelper.PROFILE_USER_DETAILS, profileModel.getUserDetails());
        values.put(YarDatabaseHelper.PROFILE_IMAGE_PATH, profileModel.getImagePath());
        long id = sqLiteDatabase.insert(YarDatabaseHelper.YAR_PROFILE_INFO_TABLE,null,values);

        this.close();
        if (id > 0){
            return true;
        }
        else {
            return false;
        }

    }

    public  boolean loginUser(LoginModel loginModel){
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_LOGIN_TABLE+" where "+YarDatabaseHelper.LOGIN_USERNAME+" = '"+loginModel.getUserName()+"'"+" and  "+YarDatabaseHelper.LOGIN_PASSWORD+" = '"+loginModel.getPassword()+"'",null);
        //cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }


}
