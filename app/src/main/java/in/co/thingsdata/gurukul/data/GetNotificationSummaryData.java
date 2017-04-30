package in.co.thingsdata.gurukul.data;

/**
 * Created by Vikas on 4/30/2017.
 */

public class GetNotificationSummaryData {
    private String mErrorMessage;
    private String mNotificationId;
    private int mTotalYES;
    private int mTotalNo;
    private int mTotal;

    public GetNotificationSummaryData(String n_id){mNotificationId = n_id;}
    public void setErrorMessage(String s){mErrorMessage = s;}
    public String getErrorMessage(){return mErrorMessage;}
    public String getNotificationId(){return mNotificationId;}

    public void setTotalNotificationCount(int c){mTotal = c;}
    public void setTotalYesNotificationCount(int c){mTotalYES = c;}
    public void setTotalNoNotificationCount(int c){mTotalNo = c;}

    public int getTotalNotificationCount(){return mTotal;}
    public int getTotalYesNotificationCount(){return mTotalYES;}
    public int getTotalNoNotificationCount(){return mTotalNo;}
}
