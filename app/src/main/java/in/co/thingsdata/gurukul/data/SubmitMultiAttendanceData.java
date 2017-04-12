package in.co.thingsdata.gurukul.data;

import java.util.ArrayList;

/**
 * Created by Vikas on 1/31/2017.
 */

public class SubmitMultiAttendanceData {
    private String mAccessToken;
    private ArrayList<AttendanceData> mAttendanceList;

    public SubmitMultiAttendanceData (String access_token, ArrayList<AttendanceData> attendance_list)
    {
        mAccessToken = access_token; mAttendanceList = attendance_list;
    }

    public String getAccessToken(){return mAccessToken;}
    public ArrayList<AttendanceData> getAttendanceList(){return mAttendanceList;}
}
