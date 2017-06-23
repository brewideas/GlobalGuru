package in.co.thingsdata.gurukul.ui.Homework;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.data.common.ClassData;

/**
 * Created by Ritika on 6/20/2017.
 */
public class HomeWorkStaticData {

    public static ArrayList<ClassData> mClassesInSchoolObj = null;
    private static String mSection,mclassRoomId,mSubject;

    public static String getSelectedClassRoomId() {
        return mclassRoomId;
    }

    public static String getSelectedSection() {
        return mSection;
    }

    public static String getSelectedSubject() {
        return mSubject;
    }

    public static void setSelectedClassRoomId(String classRoomId) {
        mclassRoomId = classRoomId;
    }

    public static void setSelectedSection(String section) {
        mSection = section;
    }

    public static void setSelectedSubject(String subject) {
        mclassRoomId = mSubject;
    }
}
