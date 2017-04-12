package in.co.thingsdata.gurukul.data;

/**
 * Created by Vikas on 2/1/2017.
 */

public class SubmitAttendanceData {
    private String mAccessToken;
    private AttendanceData mAttendance;

    public SubmitAttendanceData (String access_token, AttendanceData attn)
    {
        mAttendance = attn; mAccessToken = access_token;
    }

    public String getAccessToken(){return mAccessToken;}
    public AttendanceData getAttendance(){return mAttendance;}
}
