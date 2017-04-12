package in.co.thingsdata.gurukul.ui.dataUi;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.Student;

/**
 * Created by Ritika on 2/18/2017.
 */
public class ReportCardStaticData {

    /*
    * dataList : This list is used to add data in adapter
    * Fill this list with data that is required to be passed in constructor of DataUi class
    * */
    public static List<DataOfUi> dataList = new ArrayList<>();

    /*
    * Fill with Student list class objects (of Service)as we get in callback
    * */
    public static ArrayList<Student> mStudentList = null;
    public static ArrayList<ClassData> mClassesInSchoolObj = null;

    private static String classOfStudent ,section,typeOfExam,regId,classRoomId;
    private static int rolNum =0;

    public enum buttonType{
        viewBtn,
        uploadBtn
    }

    public static buttonType clickedButton =  buttonType.viewBtn;
    public static int year;

    public static void setSelectedClass(String data){
        classOfStudent = data;
    }

    public static void setRegistrationId(String data){
        regId = data;
    }

    public static void setRollNumber(int data){
        rolNum = data;
    }

    public static  void setSelectedSection(String data){
        section = data;
    }

    public static  void setSelectedYear(int data){
        year = data;
    }

    public static  void setSelectedClassRoomId(String data){
        classRoomId = data;
    }

    public static  void setSelectedTypeOfExam(String data){
        typeOfExam = data;
    }

    public static  String  getSelectedClass(){
        return classOfStudent;
    }

    public static  String  getSelectedClassRoomId(){
        return classRoomId;
    }

    public  static int  getSelectedYear(){
        return year;
    }

    public  static int  getSelectedRollNumbber(){
        return rolNum;
    }

    public static String  getSelectedSection(){
        return section;
    }

    public static String  getSelectedTypeOfExam(){
        return typeOfExam;
    }

    public static String getRegistrationId(){
        return regId;
    }


    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in {@link # setId(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public static List<DataOfUi> getDataList(){
        return dataList;
    }

    public static void setDataList(List<DataOfUi> data){
       dataList = data;
    }

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
