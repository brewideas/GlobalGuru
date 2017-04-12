package in.co.thingsdata.gurukul.data.common;

/**
 * Created by Vikas on 2/10/2017.
 */

public final class Student {
    private String mStudentId;
    private String mRegistrationId;
    private String mStudentName;
    private int mRollNumber;

    public Student (String id, String name, int roll_num, String reg_id){
        mStudentId = id; mStudentName = name; mRollNumber = roll_num; mRegistrationId = reg_id;
    }

    public String getStudentId(){return mStudentId;}
    public int getRollNumber(){return mRollNumber;}
    public String getName(){return mStudentName;}
    public String getRegistrationId(){return mRegistrationId;}
}
