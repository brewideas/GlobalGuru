package in.co.thingsdata.gurukul.data;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.data.common.Student;

/**
 * Created by Vikas on 2/10/2017.
 */

public class GetStudentListInClassData {
    private String mAccessToken;
    private String mClass;
    private String mSection;

    private ArrayList<Student> mStudentList = new ArrayList<>();

    public GetStudentListInClassData(String token, String c, String sec){
        mAccessToken = token; mClass = c; mSection = sec;
    }

    public String getAccessToken(){return mAccessToken;}
    public String getClassId(){return mClass;}
    public String getSectionId(){return mSection;}

    public void addStudent(Student s){
        mStudentList.add(s);
    }
    public int getTotalNumberOfStudent(){return  mStudentList.size();}
    public ArrayList<Student> getStudentListInClass(){

        return mStudentList;}
}
