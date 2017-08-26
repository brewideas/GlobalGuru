package in.co.thingsdata.gurukul.ui.Homework;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;

/**
 * Created by Ritika on 6/20/2017.
 */
public class HomeWorkStaticData {

    /*
   * dataList : This list is used to add data in adapter
   * Fill this list with data that is required to be passed in constructor of DataUi class
   * */
    public static List<DataOfUi> dataList = new ArrayList<>();

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
