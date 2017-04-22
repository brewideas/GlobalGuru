package in.co.thingsdata.gurukul.data;

/**
 * Created by Vikas on 4/22/2017.
 */

public class GetUserDetailsData {
    private String mAccessToken;
    private String mName;
    private String mUserType;
    private String mEmail;
    private String mUserId;
    private String mClassRoomId;
    private String mSection;
    private String mMobileNumber;
    private int mSchoolCode;
    private String mReferenceCode;

    public GetUserDetailsData (String access_token){
        mAccessToken = access_token;
    }

    public String getAccessToken (){return mAccessToken;}
    public String getName(){return mName;}
    public String getUserType(){return mUserType;}
    public String getEmail(){return mEmail;}
    public String getUserId(){return mUserId;}
    public String getClassRoomId(){return mClassRoomId;}
    public String getSection(){return mSection;}
    public String getMobileNumber(){return mMobileNumber;}
    public int getSchoolCode(){return mSchoolCode;}
    public String getReferenceCode(){return mReferenceCode;}

    public void setAccessToken (String token){mAccessToken = token;}
    public void setName(String name){mName = name;}
    public void setUserType(String type){mUserType = type;}
    public void setEmail(String email){mEmail = email;}
    public void setUserId(String uid){mUserId = uid;}
    public void setClassRoomId(String rid){mClassRoomId = rid;}
    public void setSection(String sec){mSection = sec;}
    public void setMobileNumber(String m_num){mMobileNumber = m_num;}
    public void setSchoolCode(int s_code){mSchoolCode = s_code;}
    public void setReferenceCode(String r_code){mReferenceCode = r_code;}
}
