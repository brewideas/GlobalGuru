package in.co.thingsdata.gurukul.data;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.data.common.Student;
import in.co.thingsdata.gurukul.data.common.Subject;

/**
 * Created by Vikas on 2/10/2017.
 */

public class GetSubjectListData {
    private String mAccessToken;
    private int mClassId;
    private ArrayList<Subject> mSubjectList = new ArrayList<>();

    public GetSubjectListData(String token, int class_id){
        mAccessToken = token; mClassId = class_id;
    }
    public String getAccessToken(){return mAccessToken;}
    public int getClassId(){return mClassId;}
    public ArrayList<Subject> getSubjectList(){return mSubjectList;}
    public void addSubject(Subject s){mSubjectList.add(s);}
    public int getTotalNumberOfSubjects(){return mSubjectList.size();}
}
