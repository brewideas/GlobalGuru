package in.co.thingsdata.gurukul.data.common;

/**
 * Created by Vikas on 2/10/2017.
 */

public final class Subject {
    private String mId;
    private String mName;

    public Subject (String id, String name){
        mId = id;
        mName = name;
    }

    public String getSubjectId(){return mId;}
    public String getSubjectName(){return mName;}
}
