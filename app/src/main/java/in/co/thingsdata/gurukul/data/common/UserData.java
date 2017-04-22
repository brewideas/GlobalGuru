package in.co.thingsdata.gurukul.data.common;

import java.util.ArrayList;


import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_GENDER_MALE;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_TYPE_PRINCIPAL;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_TYPE_STUDENT;

/**
 * Created by Vikas on 2/11/2017.
 */

public final class UserData {
    //TODO: Remove hardcoded data
    private static boolean mIsDataReady = true;

    private static String mAccessToken = "DUMMY";
    private static int mRollNumber = 1;
    private static String mUserId = "Gurukul hardcoded test ID";
    private static String mLoginId = "test_gurukul";
    private static String mClassRoomId = "2-12-A"; //for server string parsing only
    private static int mSchoolCode = 2;
    private static String mFirstName = "Ashutosh";
    private static String mMiddleName;
    private static String mLastName = "Sharma";
    private static String mDOB= "01-Jan-1982";
    private static String mUserType = USER_TYPE_PRINCIPAL;
    private static String mGender = USER_GENDER_MALE;
    private static String mEmailId = "test_gurukul@gmail.com";
    private static String mMobileNumber = "+919891829557";
    private static int mClassId = 12;
    private static String mSectionId = "A";
    private static double mMonthlyFee = 2200;
    private static String mAdmissionDate = "12-Jan-1989";
    private static String mCertificateNumber ;
    private static String mParentName = "Test";
    private static String mDepartment;
    private static String mDesignation;
    private static String mDateOfJoining;
    private static String mRegistrationId;
    private static String mEmployeeId;
    private static String mUniqueId;

    public static boolean isUserDataReady(){return mIsDataReady;}
    public static boolean isUserAlreadyLoggedIn(){return false;} //TODO: Update
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
    public static void setAccessToken(String token){mAccessToken = token;}
    public static void setRollNumber(int rnum){mRollNumber = rnum;}
    public static void setUserId(String uid){mUserId = uid;}
    public static void setLoginId(String lid){mLoginId = lid;}
    public static void setClassRoomId(String clid){mClassRoomId = clid;}
    public static void setSchoolCode(int sc){mSchoolCode = sc;}
    public static void setFirstName(String fname){mFirstName = fname;}
    public static void setMiddleName(String mname){mMiddleName = mname;}
    public static void setLastName(String lname){mLastName = lname;}
    public static void setDOB(String dob){mDOB = dob;}
    public static void setUserType(String type){mUserType = type;}
    public static void setGender(String g){mGender = g;}
    public static void setEmailId(String email){mEmailId = email;}
    public static void setMobileNumber(String mnum){mMobileNumber = mnum;}
    public static void setClassId(int cid){mClassId = cid;}
    public static void setSectionId(String sid){mSectionId = sid;}
    public static void setMonthlyFee(float fee){mMonthlyFee = fee;}
    public static void setAdmissionDate(String adate){mAdmissionDate = adate;}
    public static void setCertificateNumber(String cnum){mCertificateNumber = cnum;}
    public static void setParentName(String pname){mParentName = pname;}
    public static void setDepartment(String dept){mDepartment = dept;}
    public static void setDesignation(String des){mDesignation = des;}
    public static void setDateOfJoining(String doj){mDateOfJoining = doj;}
    public static void setRegistrationId(String id){mRegistrationId = id;}
    public static void setEmployeeId(String id){mEmployeeId = id;}
    public static void setUniqueId(String id){mUniqueId = id;}
}
