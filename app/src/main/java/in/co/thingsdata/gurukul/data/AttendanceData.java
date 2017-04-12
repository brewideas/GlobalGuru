package in.co.thingsdata.gurukul.data;

/**
 * Created by Vikas on 1/31/2017.
 */

public class AttendanceData {

    public enum AttendanceValue  {
        ATTENDANCE_INVALID_DATE, // Used for the day which don't exist like 30 FEB.
        ATTENDANCE_PRESENT,
        ATTENDANCE_ABSENT,
        ATTENDANCE_LEAVE,
        ATTENDANCE_HOLIDAY,

        ATTENDANCE_END // WARNING: Add all attendance value above this line only
    }
    private int mDay;
    private int mMonth;
    private int mYear;
    private AttendanceValue mAttendance;

    public AttendanceData (int day, int mon, int year, AttendanceValue attendance)
    {
        mDay = day; mMonth = mon; mYear = year; mAttendance = attendance;
    }

    public int getDay(){return mDay;}
    public int getMonth(){return mMonth;}
    public int getYear(){return mYear;}
    public AttendanceValue getAttendance(){return mAttendance;}
}
