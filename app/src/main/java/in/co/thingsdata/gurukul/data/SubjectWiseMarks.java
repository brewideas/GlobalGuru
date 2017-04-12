package in.co.thingsdata.gurukul.data;

import in.co.thingsdata.gurukul.data.common.Subject;

/**
 * Created by Vikas on 2/10/2017.
 */

public class SubjectWiseMarks {
    private Subject mSubject;
    private int mTotalMarks;
    private int mMarksObtained;

    public SubjectWiseMarks(Subject sub, int total, int marks){
        mSubject = sub; mMarksObtained = marks; mTotalMarks = total;
    }

    public Subject getSubject(){return mSubject;}
    public int getTotalMarks(){return mTotalMarks;}
    public int getMarksObtained(){return mMarksObtained;}
}
