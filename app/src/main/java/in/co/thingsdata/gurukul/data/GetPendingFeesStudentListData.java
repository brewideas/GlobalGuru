package in.co.thingsdata.gurukul.data;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.data.common.Student;

/**
 * Created by Vikas on 2/10/2017.
 */

public class GetPendingFeesStudentListData {
    private String mAccessToken;
    private String mClass;
    private String mSection;
    int mMonth , mYear;

    private ArrayList<Student> mStudentList = new ArrayList<>();

    public GetPendingFeesStudentListData(String token, String classarg, String sec , int year,
                                         int month){
        mAccessToken = token; mClass = classarg; mSection = sec;
        mMonth = month;
        mYear = year;
    }

    public String getAccessToken(){return mAccessToken;}
    public String getClassRoomId(){return mClass;}
    public String getSectionId(){return mSection;}
    public int getYear(){return mYear;}
    public int getMonth(){return mMonth;}

    public void addStudent(Student s){
        mStudentList.add(s);
    }
    public int getTotalNumberOfStudent(){return  mStudentList.size();}
    public ArrayList<Student> getStudentListInClass(){

        return mStudentList;}
}
