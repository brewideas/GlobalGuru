package in.co.thingsdata.gurukul.data;

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
    private String mSection;
    private boolean mIsSMS;

    public CreateNotificationData (String access_token, String createDate, String expiryDate,
                                   String desc, String title, String className, String section,
                                   CommonDetails.NotificationTypeEnum type, boolean isSMS){
        mTitle = title;
        mNotificationType = type;
        mAccessToken = access_token;
        mDescription = desc;
        mCreateDate = createDate;
        mExpiryDate = expiryDate;
        mClassName = className; mSection = section; mIsSMS = isSMS;
    }

    public String getTitle(){return mTitle;}
    public String getDescription(){return mDescription;}
    public String getAccessToken(){return mAccessToken;}
    public CommonDetails.NotificationTypeEnum getNotificationType(){return mNotificationType;}
    public String getCreateDate(){return mCreateDate;}
    public String getExpiryDate(){return mExpiryDate;}
    public String getClassName(){return mClassName;}
    public String getSection(){return mSection;}
}
