package in.co.thingsdata.gurukul.data.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;


import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_GENDER_MALE;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_TYPE_PRINCIPAL;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_TYPE_STUDENT;

/**
 * Created by Vikas on 2/11/2017.
 */

public final class UserData {
    private static Context mContext;
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String FULL_NAME = "FULL_NAME";
    public static final String CLASS_ROOM_ID = "CLASS_ROOM_ID";
    public static final String SECTION_ID = "SECTION_ID";
    public static final String MOBIL_NUMBER = "MOBIL_NUMBER";
    public static final String EMAIL_ID = "EMAIL_ID";
    public static final String SCHOOL_CODE = "SCHOOL_CODE";
    public static final String ROLE = "ROLE";
    public static final String USER_UNIQUE_ID = "USER_UNIQUE_ID";
    public static final String LOGIN_ID = "LOGIN_ID";
    public static final String KEY = "in.co.thingsdata.gurukul.USERDATA_FILE_KEY";
    
    private static boolean mIsDataReady = true;

    private static String mAccessToken;
    private static int mRollNumber;
    private static String mUserId;
    private static String mLoginId;
    private static String mClassRoomId;
    private static int mSchoolCode;
    private static String mFirstName;
    private static String mMiddleName;
    private static String mLastName;
    private static String mDOB;
    private static String mUserType;
    private static String mGender;
    private static String mEmailId;
    private static String mMobileNumber;
    private static int mClassId;
    private static String mSectionId;
    private static double mMonthlyFee;
    private static String mAdmissionDate;
    private static String mCertificateNumber ;
    private static String mParentName;
    private static String mDepartment;
    private static String mDesignation;
    private static String mDateOfJoining;
    private static String mRegistrationId;
    private static String mEmployeeId;
    private static String mUniqueId;

    public static boolean isUserDataReady(){return mIsDataReady;}
    public static boolean isUserAlreadyLoggedIn(){
        SharedPreferences sharedPref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String access_token = sharedPref.getString(ACCESS_TOKEN, null);
        return (access_token != null);
    }
    public static String getAccessToken(){return mAccessToken;}
    public static int getRollNumber(){return mRollNumber;}
    public static String getUserId(){return mUserId;}
    public static String getLoginId(){return mLoginId;}
    public static String getClassRoomId(){return mClassRoomId;}
    public static int getSchoolCode(){return mSchoolCode;}
    public static String getFirstName(){return mFirstName;}
    public static String getMiddleName(){return mMiddleName;}
    public static String getLastName(){return mLastName;}
    public static String getDOB(){return mDOB;}
    public static String getUserType(){return mUserType;}
    public static String getGender(){return mGender;}
    public static String getEmailId(){return mEmailId;}
    public static String getMobileNumber(){return mMobileNumber;}
    public static int getClassId(){return mClassId;}
    public static String getSectionId(){return mSectionId;}
    public static double getMonthlyFee(){return mMonthlyFee;}
    public static String getAdmissionDate(){return mAdmissionDate;}
    public static String getCertificateNumber(){return mCertificateNumber;}
    public static String getParentName(){return mParentName;}
    public static String getDepartment(){return mDepartment;}
    public static String getDesignation(){return mDesignation;}
    public static String getDateOfJoining(){return mDateOfJoining;}
    public static String getRegistrationId(){return mRegistrationId;}
    public static String getEmployeeId(){return mEmployeeId;}
    public static String getUniqueId(){return mUniqueId;}



    //TODO: All Setter must write in Shared preference
    public static void setUserDataReady(boolean isReady){mIsDataReady = isReady;}
    public static void setAccessToken(String token){
        mAccessToken = token;
        writeToSharedPreference (ACCESS_TOKEN, mAccessToken, -1);
    }
    public static void setRollNumber(int rnum){mRollNumber = rnum;}
    public static void setUserId(String uid){mUserId = uid;}
    public static void setLoginId(String lid){
        mLoginId = lid;
        writeToSharedPreference (LOGIN_ID, lid, -1);
    }
    public static void setClassRoomId(String clid){
        mClassRoomId = clid;
        writeToSharedPreference (CLASS_ROOM_ID, clid, -1);
    }
    public static void setSchoolCode(int sc){
        mSchoolCode = sc;
        writeToSharedPreference (SCHOOL_CODE, null, sc);
    }
    public static void setFirstName(String fname){
        mFirstName = fname;
        writeToSharedPreference (FULL_NAME, fname, -1);
    }
    public static void setMiddleName(String mname){mMiddleName = mname;}
    public static void setLastName(String lname){mLastName = lname;}
    public static void setDOB(String dob){mDOB = dob;}
    public static void setUserType(String type){
        mUserType = type;
        writeToSharedPreference (ROLE, type, -1);
    }
    public static void setGender(String g){mGender = g;}
    public static void setEmailId(String email){
        mEmailId = email;
        writeToSharedPreference (EMAIL_ID, email, -1);
    }
    public static void setMobileNumber(String mnum){
        mMobileNumber = mnum;
        writeToSharedPreference (MOBIL_NUMBER, mnum, -1);
    }
    public static void setClassId(int cid){mClassId = cid;}
    public static void setSectionId(String sid){
        mSectionId = sid;
        writeToSharedPreference (SECTION_ID, sid, -1);
    }
    public static void setMonthlyFee(float fee){mMonthlyFee = fee;}
    public static void setAdmissionDate(String adate){mAdmissionDate = adate;}
    public static void setCertificateNumber(String cnum){mCertificateNumber = cnum;}
    public static void setParentName(String pname){mParentName = pname;}
    public static void setDepartment(String dept){mDepartment = dept;}
    public static void setDesignation(String des){mDesignation = des;}
    public static void setDateOfJoining(String doj){mDateOfJoining = doj;}
    public static void setRegistrationId(String id){mRegistrationId = id;}
    public static void setEmployeeId(String id){mEmployeeId = id;}
    public static void setUniqueId(String id){
        mUniqueId = id;
        writeToSharedPreference (USER_UNIQUE_ID, id, -1);
    }
    public static void setContext(Context c){mContext = c;}

    private static void readAllDataFromSharedPreference(){
        SharedPreferences sharedPref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        mAccessToken = sharedPref.getString(ACCESS_TOKEN, null);
        mFirstName = sharedPref.getString(FULL_NAME, null);
        mUniqueId = sharedPref.getString(USER_UNIQUE_ID, null);
        mClassRoomId = sharedPref.getString(CLASS_ROOM_ID, null);
        mLoginId = sharedPref.getString(LOGIN_ID, null);
        mEmailId = sharedPref.getString(EMAIL_ID, null);
        mMobileNumber = sharedPref.getString(MOBIL_NUMBER, null);
        mUserType = sharedPref.getString(ROLE, null);
        mSchoolCode = sharedPref.getInt(SCHOOL_CODE, -1);
        mSectionId = sharedPref.getString(SECTION_ID, null);
    }

    private static void writeToSharedPreference (String key, String value, int int_value){
        SharedPreferences sharedPref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (int_value < 0)
        {
            editor.putString(key, value);
        }
        else {
            editor.putInt(key, int_value);
        }
        editor.commit();
    }

    public static void init (){
        readAllDataFromSharedPreference();
        setUserDataReady(true);
    }

    public static void clearAll() {
        SharedPreferences sharedPref = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }
}
