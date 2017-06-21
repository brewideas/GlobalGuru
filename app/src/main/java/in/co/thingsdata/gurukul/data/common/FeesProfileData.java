package in.co.thingsdata.gurukul.data.common;

/**
 * Created by Ritika on 6/13/2017.
 */
public class FeesProfileData {

    private String mAccessToken;
    private int mMonth;
    private String mRegistrationId;
    private int mYear;
    private String mClassRoomId,mSectionId;
    private int mFeesPaid,mFeesRemaining;
    private String mFeesSubmittedBy,mReceiptNumber , mLastPaidDate;

    public FeesProfileData(String registrationId,String argtoken, int argMonth,String classRoomId,String sectionId,
                          String FeesSubmittedBy,String ReceiptNumber ,int argYear, int argFeesPaid, int argremainingFees,
                           String lastPaidDate){

        mAccessToken = argtoken; mMonth = argMonth; mYear = argYear;
        mRegistrationId = registrationId;
        mFeesRemaining = argremainingFees;
        mClassRoomId = classRoomId ;
        mSectionId = sectionId;
        mLastPaidDate = lastPaidDate;
    }

    public FeesProfileData(String registrationId,String argtoken, int argMonth,
                           int argYear){

        mAccessToken = argtoken; mMonth = argMonth; mYear = argYear;
        mRegistrationId = registrationId;

    }

    public int getYear(){
        return mYear;
    }

    public int getMonth(){
        return mMonth;
    }

    public String getRegistrationId(){
        return mRegistrationId;
    }

    public String getAccessToken(){
        return mAccessToken;
    }

    public String getClassRoomId(){
        return mClassRoomId;
    }


     public String getPaidDate() {
       return  mLastPaidDate;
     }
    public String getSectionId(){
        return mSectionId;
    }

    public String getReceiptNumber(){
        return mReceiptNumber;
    }

    public int getFeesPaid(){
        return mFeesPaid;
    }

    public int getFeesRemaining(){
        return mFeesRemaining;
    }

    public String getFeesSubmittedBy(){
        return mFeesSubmittedBy;
    }

}
