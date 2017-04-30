package in.co.thingsdata.gurukul.data;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.NotificationReplyDetail;

/**
 * Created by Vikas on 2/24/2017.
 */

public class GetNotificationStatsData {
    private String mAccessToken;
    private String mNotificationId;
    private String mErrorMessage;

    private ArrayList<NotificationReplyDetail> mReplyList = new ArrayList<>();

    public GetNotificationStatsData(String token, String n_id){
        mAccessToken = token; mNotificationId = n_id;
    }
    public String getNotificationId(){return mNotificationId;}
    public String getAccessToken(){return mAccessToken;}
    public void setErrorMessage(String s){mErrorMessage = s;}
    public String getErrorMessage(){return mErrorMessage;}

    public void addReply(NotificationReplyDetail r){mReplyList.add(r);}

    public ArrayList<NotificationReplyDetail> getAllReplies(){return mReplyList;}

}
