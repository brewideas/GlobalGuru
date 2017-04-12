package in.co.thingsdata.gurukul.data;

import android.util.ArrayMap;

/**
 * Created by Vikas on 1/31/2017.
 */

public class GetAttendanceData {
    private String mAccessToken;
    private String mRollNumber;
    private int mYear;
    private int mMonth;

    private ArrayMap<Integer, AttendanceData.AttendanceValue> mAttendanceList;

    public GetAttendanceData (String access_token, String roll_number, int year, int month){
        mAccessToken = access_token; mRollNumber = roll_number; mYear = year;
        mMonth = month;

        mAttendanceList = new ArrayMap<>(31);
    }

    public void setAttendance (int day, AttendanceData.AttendanceValue value)
    {
        mAttendanceList.setValueAt(day, value);
    }

    public ArrayMap<Integer, AttendanceData.AttendanceValue> getAttendanceList(){
        return mAttendanceList;
    }

    public String getAccessToken(){return mAccessToken;}
    public String getRollNumber(){return mRollNumber;}
    public int getYear(){return mYear;}
    public int getMonth(){return mMonth;}
}
