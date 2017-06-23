package in.co.thingsdata.gurukul.ui.Fees;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.Student;
import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;

/**
 * Created by Ritika on 5/21/2017.
 */
public class FeesDetailsStaticData {


    /*
    * dataList : This list is used to add data in adapter
    * Fill this list with data that is required to be passed in constructor of DataUi class
    * */
    public static List<DataOfUi> dataList = new ArrayList<>();
    public static ArrayList<ClassData> mClassesInSchoolObj = null;

    public enum buttonType{
        viewBtn,
        pendingBtn
    }

    private static String mSection,mclassRoomId;
    private static int mYearSearched = 0,mMonthSearched;

    public static void setYearSearched(int yearSearched){
        mYearSearched = yearSearched;
    }

    public static int getYearSearched(){
        return mYearSearched ;
    }

    public static void setMonthSearched(int monthSearched){
        mMonthSearched = monthSearched;
    }

    public static int getMonthSearched(){
        return mMonthSearched ;
    }

    public static String getSelectedStudentClassRoomId() {
        return mclassRoomId;
    }

    public static String getSelectedStudentSection() {
        return mSection;
    }

    public static void setSelectedStudentClassRoomId(String classRoomId) {
        mclassRoomId = classRoomId;
    }

    public static String getSection(String classSectionFullName) {
        String sectionStr = null;//FeesDetailsStaticData.mClassesInSchoolObj.get(indexValue).getSection();
        int lenOfClassSection = classSectionFullName.length();
        for (int i = 0; i < lenOfClassSection; i++) {
            char ch = classSectionFullName.charAt(i);
            if (Character.isLetter(ch)) {
                sectionStr = classSectionFullName.substring(i);
                break;
            }
        }
        return sectionStr;
    }


    public static void setSelectedStudentSection(String section) {
        mSection = section;
    }

    public static buttonType clickedButton =  buttonType.viewBtn;

    /*
    * Fill with Student list class objects (of Service)as we get in callback
    * */
    public static ArrayList<Student> mFeesStudentList = null;

    private static ProgressDialog progress;

    public static void dismissProgressBar(){

        try {
            progress.dismiss();
        }catch(Exception e){

        }
    }

    public static void showProgressBar(Context contx){

        progress=new ProgressDialog(contx);
        progress.setMessage("Getting Data ..");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime < totalProgressTime) {
                    try {
                        sleep(20);
                        jumpTime += 5;
                        if(jumpTime < 80) {
                            progress.setProgress(jumpTime);
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

}
