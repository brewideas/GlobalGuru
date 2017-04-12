package in.co.thingsdata.gurukul.data.common;

import java.util.ArrayList;

/**
 * Created by Vikas on 3/1/2017.
 */

public class ClassData {
    private String mClassCode;
    private String mName;
    private String mClassRoomId;
    private String mClassTeacherId;
    private String mClassTeacherName;
    private String mSection;
    private ArrayList<Subject> mSubjects = new ArrayList<>();
    private String mUniqueId;

    public ClassData(String class_room_id, String name){
        mClassRoomId = class_room_id; mName = name;
    }

    public String getClassRoomId(){return mClassRoomId;}
    public String getClassTeacherId(){return mClassTeacherId;}
    public String getName(){return mName;}
    public String getClassCode(){return mClassCode;}
    public String getClassTeacherName(){return mClassTeacherName;}
    public String getSection(){return mSection;}
    public String getUniqueId(){return mUniqueId;}
    public ArrayList<Subject> getSubjectList(){return mSubjects;}
    public int getTotalNumberOfSubjectsInClass(){return mSubjects.size();}

    public void setClassRoomId(String id) {mClassRoomId = id;}
    public void setClassTeacherId(String t_id) {mClassTeacherId = t_id;}
    public void setClassName(String name) {mName = name;}
    public void setClassCode(String class_code) {mClassCode = class_code;}
    public void setClassTeacherName(String name) {mClassTeacherName = name;}
    public void setSection(String section) {mSection = section;}
    public void setUniqueId(String u_id) {mUniqueId = u_id;}
    public void addSubjectInClass(Subject s) {mSubjects.add(s);}
}
