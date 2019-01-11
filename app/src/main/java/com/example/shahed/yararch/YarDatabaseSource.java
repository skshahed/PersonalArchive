package com.example.shahed.yararch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class YarDatabaseSource {
    private YarDatabaseHelper yarDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ProfileModel profileModel;
    private LoginModel loginModel;
    private DonationModel donationModel;
    private YarDatabaseSource yarDatabaseSource;
    private double totalDonation;

    public YarDatabaseSource(Context context){
        this.yarDatabaseHelper = new YarDatabaseHelper(context);
    }

    public void open(){
        sqLiteDatabase = yarDatabaseHelper.getWritableDatabase();
    }
    public void close(){
        sqLiteDatabase.close();
    }

    public  boolean loginUser(LoginModel loginModel){
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_LOGIN_TABLE+" where "+YarDatabaseHelper.LOGIN_USERNAME+" = '"+loginModel.getUserName()+"'"+" and "+YarDatabaseHelper.LOGIN_PASSWORD+" = '"+loginModel.getPassword()+"'",null);
        //cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public int addSignUp(LoginModel loginModel){
        this.open();
        //Integer profile_id = 1;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_LOGIN_TABLE+" where "+YarDatabaseHelper.LOGIN_USERNAME+" = '"+loginModel.getUserName()+"'",null);
        if(cursor != null && cursor.getCount() > 0){
            return 00;
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            ContentValues profileValues = new ContentValues();
            profileValues.put(YarDatabaseHelper.PROFILE_NAME, loginModel.getFullName());
            profileValues.put(YarDatabaseHelper.PROFILE_REGISTER_DATE, dateFormat.format(date));
            long profile_id = sqLiteDatabase.insertWithOnConflict(YarDatabaseHelper.YAR_PROFILE_INFO_TABLE,null, profileValues, SQLiteDatabase.CONFLICT_IGNORE);

            if(profile_id > 0){
                ContentValues values = new ContentValues();
                values.put(YarDatabaseHelper.LOGIN_PROFILE_ID, profile_id);
                values.put(YarDatabaseHelper.LOGIN_USERNAME, loginModel.getUserName());
                values.put(YarDatabaseHelper.LOGIN_PASSWORD, loginModel.getPassword());
                values.put(YarDatabaseHelper.LOGIN_USER_TYPE, loginModel.getUserType());
                long id = sqLiteDatabase.insert(YarDatabaseHelper.YAR_LOGIN_TABLE, null, values);

                this.close();
                if (id > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
            else {
                return 0;
            }
        }
    }

    public int addPeople(ProfileModel profileModel, String userName){
        this.open();
        long uId;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_PROFILE_INFO_TABLE+" where "+YarDatabaseHelper.PROFILE_PHONE+" = '"+profileModel.getPhoneNo()+"'",null);
        if(cursor != null && cursor.getCount() > 0){
            return 00;
        }
        else {
            // find for user integer ID
            Cursor cursor2 = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_LOGIN_TABLE+" where "+YarDatabaseHelper.LOGIN_USERNAME+" = '"+userName+"' ",null);
            cursor2.moveToFirst();
            if(cursor2 != null && cursor2.getCount() > 0){
                uId = cursor2.getInt(cursor2.getColumnIndex(YarDatabaseHelper.LOGIN_ID));
            }
            else{
                return 0;
            }

            ContentValues values = new ContentValues();
            values.put(YarDatabaseHelper.PROFILE_LOGIN_ID, uId);
            values.put(YarDatabaseHelper.PROFILE_ISVIP, profileModel.getVipPerson());
            values.put(YarDatabaseHelper.PROFILE_NAME, profileModel.getName());
            values.put(YarDatabaseHelper.PROFILE_FATHER_NAME, profileModel.getFatherName());
            values.put(YarDatabaseHelper.PROFILE_PHONE, profileModel.getPhoneNo());
            values.put(YarDatabaseHelper.PROFILE_ADDRESS, profileModel.getAddress());
            values.put(YarDatabaseHelper.PROFILE_USER_DETAILS, profileModel.getUserDetails());
            values.put(YarDatabaseHelper.PROFILE_REGISTER_DATE, String.valueOf(profileModel.getRegisterDate()));
            values.put(YarDatabaseHelper.PROFILE_IMAGE_PATH, profileModel.getImagePath());
            long id = sqLiteDatabase.insert(YarDatabaseHelper.YAR_PROFILE_INFO_TABLE, null, values);

            this.close();
            if (id > 0) {
                return 1;
            } else {
                return 0;
            }
        }

    }

    public boolean addDonation(DonationModel donationModel){
        this.open();
        long uId;
        Cursor cursor2 = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_LOGIN_TABLE+" where "+YarDatabaseHelper.LOGIN_USERNAME+" = '"+donationModel.getUserName()+"' ",null);
        cursor2.moveToFirst();
        if(cursor2 != null && cursor2.getCount() > 0){
            uId = cursor2.getInt(cursor2.getColumnIndex(YarDatabaseHelper.LOGIN_ID));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            ContentValues donationValues = new ContentValues();
            donationValues.put(YarDatabaseHelper.DONATION_LOGIN_ID, uId);
            donationValues.put(YarDatabaseHelper.DONATION_PROFILE_ID, donationModel.getDonationProfileId());
            donationValues.put(YarDatabaseHelper.DONATION_AMOUNT, donationModel.getDonationAmount());
            donationValues.put(YarDatabaseHelper.DONATION_CAUSE, donationModel.getDonationCause());
            donationValues.put(YarDatabaseHelper.DONATION_DATE, dateFormat.format(date));
            long donation_id = sqLiteDatabase.insert(YarDatabaseHelper.YAR_DONATION_TABLE, null, donationValues);

            if (donation_id > 0) {
                return true;
            } else {
                return false;
            }
        }
        else{
            return false;
        }

    }

    public ProfileModel getSinglePeople(int pId){
        //ArrayList<User> users = new ArrayList<>();
        //ProfileModel profileModel = new ProfileModel();
        this.open();
        // Cursor cursor = sqLiteDatabase.query(TourDatabaseHelper.TOUR_USER_INFO,null,null,null,null,null,null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_PROFILE_INFO_TABLE+" where "+YarDatabaseHelper.PROFILE_ID+" = "+pId+" ",null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            for (int i = 0;i < cursor.getCount();i++){
                int vip = cursor.getInt(cursor.getColumnIndex(YarDatabaseHelper.PROFILE_ISVIP));
                String fullName = cursor.getString(cursor.getColumnIndex(YarDatabaseHelper.PROFILE_NAME));
                String fatherName = cursor.getString(cursor.getColumnIndex(YarDatabaseHelper.PROFILE_FATHER_NAME));
                String phoneNo=cursor.getString(cursor.getColumnIndex(YarDatabaseHelper.PROFILE_PHONE));
                String address = cursor.getString(cursor.getColumnIndex(YarDatabaseHelper.PROFILE_ADDRESS));
                String details = cursor.getString(cursor.getColumnIndex(YarDatabaseHelper.PROFILE_USER_DETAILS));
                String registerDate = cursor.getString(cursor.getColumnIndex(YarDatabaseHelper.PROFILE_REGISTER_DATE));
                //Date dateregister = registerDate;
                String imagePath = cursor.getString(cursor.getColumnIndex(YarDatabaseHelper.PROFILE_IMAGE_PATH));

                profileModel = new ProfileModel(vip,fullName,fatherName,phoneNo,address,details,registerDate,imagePath);
                //users.add(user);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.close();
        return profileModel;
    }


    public ArrayList<ProfileModel> getVipPeople(String userName){
        ArrayList<ProfileModel> profileModels = new ArrayList<>();
        this.open();
        // find for user integer ID
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_LOGIN_TABLE+" where "+YarDatabaseHelper.LOGIN_USERNAME+" = '"+userName+"' ",null);
        cursor.moveToFirst();
        int uId = cursor.getInt(cursor.getColumnIndex(YarDatabaseHelper.LOGIN_ID));
        // end find for user integer ID

        //Cursor cursor = sqLiteDatabase.query(TourDatabaseHelper.TOUR_TRAVEL_EVENT,null,null,null,null,null,null);

        Cursor cursor2 = sqLiteDatabase.rawQuery("select * from "+yarDatabaseHelper.YAR_PROFILE_INFO_TABLE+" where "+YarDatabaseHelper.PROFILE_LOGIN_ID+" = "+uId+" and "+YarDatabaseHelper.PROFILE_ISVIP+" = 1 order by "+YarDatabaseHelper.PROFILE_NAME+" ASC ",null);

        cursor2.moveToFirst();
        if (cursor2 != null && cursor2.getCount() > 0){
            for (int i = 0;i < cursor2.getCount();i++){
                int id = cursor2.getInt(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_ID));
                String fullName= cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_NAME));
                String fatherName= cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_FATHER_NAME));
                String phoneNo= cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_PHONE));
                // userName passedby address in adapter
                String address = userName;
                //String address=cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_ADDRESS));
                String details=cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_USER_DETAILS));
                String imagePath = cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_IMAGE_PATH));

                profileModel = new ProfileModel(id,fullName,fatherName,phoneNo,address,details,imagePath);

                profileModels.add(profileModel);
                cursor2.moveToNext();
            }
        }
        cursor2.close();
        this.close();
        return profileModels;
    }

    public ArrayList<ProfileModel> getAllPeople(String userName,String nameOrPhone,String addressSearch){
        ArrayList<ProfileModel> profileModels = new ArrayList<>();
        this.open();
        // find for user integer ID
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_LOGIN_TABLE+" where "+YarDatabaseHelper.LOGIN_USERNAME+" = '"+userName+"' ",null);
        cursor.moveToFirst();
        int uId = cursor.getInt(cursor.getColumnIndex(YarDatabaseHelper.LOGIN_ID));
        // end find for user integer ID

        //Cursor cursor = sqLiteDatabase.query(TourDatabaseHelper.TOUR_TRAVEL_EVENT,null,null,null,null,null,null);
        Cursor cursor2;
        if(nameOrPhone != null){
            cursor2 = sqLiteDatabase.rawQuery("select * from "+yarDatabaseHelper.YAR_PROFILE_INFO_TABLE+" where "+YarDatabaseHelper.PROFILE_LOGIN_ID+" = "+uId+" and "+YarDatabaseHelper.PROFILE_NAME+" like '%"+nameOrPhone+"%' order by "+YarDatabaseHelper.PROFILE_NAME+" ASC ",null);
        }
        else if(addressSearch != null){
            cursor2 = sqLiteDatabase.rawQuery("select * from "+yarDatabaseHelper.YAR_PROFILE_INFO_TABLE+" where "+YarDatabaseHelper.PROFILE_LOGIN_ID+" = "+uId+" and "+YarDatabaseHelper.PROFILE_ADDRESS+" like '%"+addressSearch+"%' order by "+YarDatabaseHelper.PROFILE_NAME+" ASC ",null);
        }
        else {
            cursor2 = sqLiteDatabase.rawQuery("select * from " + yarDatabaseHelper.YAR_PROFILE_INFO_TABLE + " where " + YarDatabaseHelper.PROFILE_LOGIN_ID + " = " + uId + " order by " + YarDatabaseHelper.PROFILE_NAME + " ASC ", null);
        }
        cursor2.moveToFirst();
        if (cursor2 != null && cursor2.getCount() > 0){
            for (int i = 0;i < cursor2.getCount();i++){
                int id = cursor2.getInt(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_ID));
                String fullName= cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_NAME));
                String fatherName= cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_FATHER_NAME));
                String phoneNo= cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_PHONE));
               // userName passedby address in adapter
                String address = userName;
               // String address=cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_ADDRESS));
                String details=cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_USER_DETAILS));
                String imagePath = cursor2.getString(cursor2.getColumnIndex(YarDatabaseHelper.PROFILE_IMAGE_PATH));

                profileModel = new ProfileModel(id,fullName,fatherName,phoneNo,address,details,imagePath);

                // start nested loop for calculate per event expense amt
                /*Cursor cursor3 = sqLiteDatabase.rawQuery("select * from "+TourDatabaseHelper.TOUR_TRAVEL_EXPENSE_TRACKER+" where "+TourDatabaseHelper.EXPENSE_EVENT_ID+" = '"+id+"' ",null);
                cursor3.moveToFirst();
                if (cursor3 != null && cursor3.getCount() > 0) {
                    totalExpense = 0;
                    for (int i3 = 0; i3 < cursor3.getCount(); i3++) {
                        int exDeAmount=cursor3.getInt(cursor3.getColumnIndex(TourDatabaseHelper.EXPENSE_AMOUNT));
                        totalExpense = totalExpense + exDeAmount;
                        //events.add(event);
                        cursor3.moveToNext();
                    }

                }*/
                /*totalExpense = 0;
                for(int k= 1;k<=3;k++){
                    totalExpense = totalExpense +k;
                }*/
                // end start nested loop for calculate per event expense amt//else {
                /*if(totalExpense>0){
                    event = new Event(id,userIdn,destination,budjget,fromdate,todate,String.valueOf(totalExpense));
                }else{
                    event = new Event(id,userIdn,destination,budjget,fromdate,todate);
                }*/
                profileModels.add(profileModel);
                cursor2.moveToNext();
            }
        }
        cursor2.close();
        this.close();
        return profileModels;
    }

    public  Double getTotalDonation(String userName){
        this.open();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_LOGIN_TABLE+" where "+YarDatabaseHelper.LOGIN_USERNAME+" = '"+userName+"' ",null);
        cursor.moveToFirst();
        long uId = cursor.getInt(cursor.getColumnIndex(YarDatabaseHelper.LOGIN_ID));

        Cursor cursor3 = sqLiteDatabase.rawQuery("select * from "+YarDatabaseHelper.YAR_DONATION_TABLE+" where "+YarDatabaseHelper.DONATION_LOGIN_ID+" = '"+uId+"' ",null);
        cursor3.moveToFirst();

        if (cursor3 != null && cursor3.getCount() > 0) {
            totalDonation = 0.00;
            for (int i3 = 0; i3 < cursor3.getCount(); i3++) {
                double exDeAmount=cursor3.getDouble(cursor3.getColumnIndex(YarDatabaseHelper.DONATION_AMOUNT));
                totalDonation = totalDonation + exDeAmount;
                //events.add(event);
                cursor3.moveToNext();
            }

        }
        return totalDonation;
    }
 /*
    public LoginModel loginUser (String username){
    String query = "Select userId, password from logins where username ='"+username+"'";
    LoginModel myUser = new LoginModel(0,username,"");
    this.open();
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if (cursor.moveToFirst()){
        do {
            int id = cursor.getInt(cursor.getColumnIndex(YarDatabaseHelper.LOGIN_ID));

            String userPasswprd = cursor.getString(cursor.getColumnIndex(YarDatabaseHelper.LOGIN_PASSWORD));
            myUser = new LoginModel(id,userPasswprd);
            //myUser.add(user);
            //myUser.userId=cursor.getInt(0);
            //myUser.password=cursor.getString(1);
        } while (cursor.moveToNext());
    }
    return myUser;
}
*/
 public boolean deletePeople(int profileId){
     this.open();
     int deleteId    =   sqLiteDatabase.delete(YarDatabaseHelper.YAR_PROFILE_INFO_TABLE,
             YarDatabaseHelper.PROFILE_ID+"=?",new String[]{Integer.toString(profileId)});
     this.close();
     if(deleteId>0){
         return true;
     }else{
         return false;
     }
 }

}
