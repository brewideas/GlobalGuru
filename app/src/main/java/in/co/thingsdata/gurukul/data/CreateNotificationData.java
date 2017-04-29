package in.co.thingsdata.gurukul.data;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;

/**
 * Created by Vikas on 2/24/2017.
 */

public class CreateNotificationData {
    private CommonDetails.NotificationTypeEnum mNotificationType;
    private String mTitle;
    private String mDescription;
    private String mCreateDate;
    private String mAccessToken;
    private String mExpiryDate;
    private String mClassName;
    private boolean mIsSMS;
    private ArrayList<String> mClassList;

    public CreateNotificationData (String access_token, String createDate, String expiryDate,
                                   String desc, String title, String className,
                                   CommonDetails.NotificationTypeEnum type, boolean isSMS){
        mTitle = title;
        mNotificationType = type;
        mAccessToken = access_token;
        mDescription = desc;
        mCreateDate = createDate;
        mExpiryDate = expiryDate;
        mClassName = className; mIsSMS = isSMS;
    }

    public CreateNotificationData (String access_token, String createDate, String expiryDate,
                                   String desc, String title, ArrayList<String> classList,
                                   CommonDetails.NotificationTypeEnum type, boolean isSMS){
        mTitle = title;
        mNotificationType = type;
        mAccessToken = access_token;
        mDescription = desc;
        mCreateDate = createDate;
        mExpiryDate = expiryDate;
        mClassList = classList; mIsSMS = isSMS;
    }



    public String getTitle(){return mTitle;}
    public String getDescription(){return mDescription;}
    public String getAccessToken(){return mAccessToken;}
    public CommonDetails.NotificationTypeEnum getNotificationType(){return mNotificationType;}
    public String getCreateDate(){return mCreateDate;}
    public String getExpiryDate(){return mExpiryDate;}

    public String getClassName(){
        if (mClassList == null || mClassList.isEmpty()) {
            return (mClassName.isEmpty())? null: mClassName;
        }
        else
        {
            String class_name = mClassList.get(0);
            for (int i = 1; i < mClassList.size(); i++){
                class_name = "," + mClassList.get(i);
            }
            return class_name;
        }
    }
    public String getSection(){return "A,B,C,D";}
    public boolean IsSMS(){return mIsSMS;}
}
