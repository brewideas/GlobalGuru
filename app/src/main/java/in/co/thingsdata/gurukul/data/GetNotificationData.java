package in.co.thingsdata.gurukul.data;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.data.common.Notification;

/**
 * Created by Vikas on 2/24/2017.
 */

public class GetNotificationData {
    private String mAccessToken;
    ArrayList<Notification> mNotificationList = new ArrayList<>();

    public GetNotificationData(String token){
        mAccessToken = token;
    }
    public void addNotification(Notification n){mNotificationList.add(n);}
    public ArrayList<Notification> getNotificationList(){return mNotificationList;}
    public int getNumberOfNotification(){return mNotificationList.size();}
}
