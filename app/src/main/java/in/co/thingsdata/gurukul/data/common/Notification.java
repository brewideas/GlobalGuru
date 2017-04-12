package in.co.thingsdata.gurukul.data.common;

/**
 * Created by Vikas on 2/24/2017.
 */

public class Notification {
    private String mId;
    private String mTitle;
    private String mDescription;
    private String mCreationDate;
    private String mExpiryDate;
    private CommonDetails.NotificationTypeEnum mType;

    public Notification (String id, CommonDetails.NotificationTypeEnum type, String title,
                         String desc, String createDate, String expiry){
        mId = id; mCreationDate = createDate; mDescription = desc; mExpiryDate= expiry;
        mTitle = title; mType = type;
    }

    public String getId(){return mId;}
    public String getTitle(){return mTitle;}
    public String getDescription(){return mDescription;}
    public String getCreatedDate(){return mCreationDate;}
    public String getExpiryDate(){return mExpiryDate;}
    public CommonDetails.NotificationTypeEnum getType(){return mType;}
}
