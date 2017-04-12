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

    public CreateNotificationData (String access_token, String createDate, String expiryDate,
                                   String desc, String title, CommonDetails.NotificationTypeEnum type){
        mTitle = title;
        mNotificationType = type;
        mAccessToken = access_token;
        mDescription = desc;
        mCreateDate = createDate;
        mExpiryDate = expiryDate;
    }

    public String getTitle(){return mTitle;}
    public String getDescription(){return mDescription;}
    public String getAccessToken(){return mAccessToken;}
    public CommonDetails.NotificationTypeEnum getNotificationType(){return mNotificationType;}
    public String getCreateDate(){return mCreateDate;}
    public String getExpiryDate(){return mExpiryDate;}
}
