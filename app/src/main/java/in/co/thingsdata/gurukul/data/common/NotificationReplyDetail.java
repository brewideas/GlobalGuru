package in.co.thingsdata.gurukul.data.common;

/**
 * Created by Vikas on 2/24/2017.
 */

public class NotificationReplyDetail {
    private String mUserId;
    private String mUserName;
    private String mUserType;
    private CommonDetails.NotificationTypeEnum mType;
    private CommonDetails.NotificationReplyEnum mReply;
    private String mClassId;
    private String mSectionId;
    private int mRollNumber;
    private String mRegistrationNumber;
    private String mEmpCode;

    public NotificationReplyDetail (String id, CommonDetails.NotificationTypeEnum type, CommonDetails.NotificationReplyEnum reply)
    {
        mUserId = id; mType = type; mReply = reply;
    }

    public void setStudentDetails(String name, String class_id, String section, int roll_number,
                                  String reg_number)
    {
        mUserType = CommonDetails.USER_TYPE_STUDENT;
        mUserName = name; mClassId = class_id; mSectionId = section; mRollNumber = roll_number;
        mRegistrationNumber = reg_number;
        mEmpCode = null;
    }

    public void setTeacherDetails(String name, String emp_code){
        mUserType = CommonDetails.USER_TYPE_TEACHER;
        mUserName = name; mEmpCode = emp_code; mClassId = null; mSectionId = null;
        mRegistrationNumber = null; mRollNumber = -1;
    }

    public String getUserId(){return mUserId;}
    public String getUserName(){return mUserName;}
    public String getUserType(){return mUserType;}
    public CommonDetails.NotificationTypeEnum getNotificationType(){return mType;}
    public CommonDetails.NotificationReplyEnum getNotificationReply(){return mReply;}
    public String getClassId(){return mClassId;}
    public String getClassName(){String name;
        String[] parts = mClassId.split("-");
        name = parts[1];
        return name;
    }
    public String getSection(){return mSectionId;}
    public int getRollNumber(){return mRollNumber;}
    public String getRegistrationNumber(){return mRegistrationNumber;}
    public String getEmployeeCode(){return mEmpCode;}
}
