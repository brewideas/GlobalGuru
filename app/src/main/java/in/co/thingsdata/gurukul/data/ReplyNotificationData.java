package in.co.thingsdata.gurukul.data;

import in.co.thingsdata.gurukul.data.common.CommonDetails;

/**
 * Created by Vikas on 2/24/2017.
 */

public class ReplyNotificationData {
    private String mAccessToken;
    private String mNotificationId;
    private CommonDetails.NotificationReplyEnum mReply;

    public ReplyNotificationData (String token, String notif_id, CommonDetails.NotificationReplyEnum reply){
        mAccessToken = token; mNotificationId = notif_id; mReply = reply;
    }

    public String getAccessToken(){return mAccessToken;}
    public String getNotificationId(){return mNotificationId;}
    public CommonDetails.NotificationReplyEnum getReply(){return mReply;}
}
