package in.co.thingsdata.gurukul.ui.Homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.CreateNotificationData;
import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.CreateNotificationRequest;
import in.co.thingsdata.gurukul.ui.NoticeBoard.NoticeBoardStatics;

import static in.co.thingsdata.gurukul.data.common.CommonDetails.NotificationTypeEnum.NOTIFICATION_TYPE_NORMAL;

public class CreateHomework extends AppCompatActivity implements AdapterView.OnItemSelectedListener
        , CreateNotificationRequest.CreateNotificationCallback {

    Spinner classSpinner, subjectSpinner;
    EditText title,detail,editSubject;

    public void createHomeWork(){

        try {
            String setSubject = editSubject.getText().toString();
            if (setSubject == null || setSubject.length() == 0) {
                setSubject = subjectSpinner.getSelectedItem().toString();
            }

            String setHomeWorkSubject = NoticeBoardStatics.homeworkNotification.concat(setSubject);
            // setSubject += "\n" + detail.getText().toString();

            int indexValue = classSpinner.getSelectedItemPosition();
            String classRoomId = HomeWorkStaticData.mClassesInSchoolObj.get(indexValue).getClassRoomId();
            String classCode = HomeWorkStaticData.mClassesInSchoolObj.get(indexValue).getClassCode();

            HomeWorkStaticData.setSelectedClassRoomId(classRoomId);

            selClassesList = null;
            selClassesList = new ArrayList<String>();
            selClassesList.add(0, classCode);

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss.SSS");

            String addTime = df2.format(c.getTime());
            String currDate = df.format(c.getTime());
//        String createDate = startdate.getText().toString();// + "T" + currTime;

            String createDate = currDate + "T" + addTime;

            c.add(c.DATE, 31);
            addTime = df2.format(c.getTime());
            currDate = df.format(c.getTime());

            String expiryDate = currDate + "T" + addTime;

            CommonDetails.NotificationTypeEnum type = NOTIFICATION_TYPE_NORMAL;

            CreateNotificationData nd = null;
            String text = detail.getText().toString();
            String classOfHomeWork = HomeWorkStaticData.getSelectedClassRoomId();

            String classNum = "Class :";
            
            String description = classNum.concat(HomeWorkStaticData.getSelectedClassRoomId());
            description = description.concat("\n");
            description = description.concat(text);
            description = description.concat("\n");
            nd = new CreateNotificationData(
                    UserData.getAccessToken(),
                    createDate,
                    expiryDate,
                    description,
                    setHomeWorkSubject,
                    selClassesList, type, false);

            CreateNotificationRequest request = new CreateNotificationRequest(this, nd, this);
            request.executeRequest();
        }catch(Exception e){
            Toast.makeText(CreateHomework.this,"Error creating homework .Please try later",Toast.LENGTH_LONG);
        }
    }


    public void sendHomeWork(View view) {
        createHomeWork();
    }

    @Override
    public void onCreateNotificationResponse(CommonRequest.ResponseCode res, CreateNotificationData data) {

        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            Toast.makeText(this, "Homework created successfully", Toast.LENGTH_SHORT).show();
            Intent launchFeature = new Intent(this, Showhomework.class);
            startActivity(launchFeature);
        }else{
            Toast.makeText(this, "Failed to create notification", Toast.LENGTH_SHORT).show();
        }
     }

    enum classLevel{
        Primary,
        Secondary,
        All
    }
    classLevel mClassLevel = classLevel.All;
    ArrayList<String> selClassesList;
    void executeRequest(String textOfHomework ,String title){

        CreateNotificationData nd = null;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss.SSS");

        String addTime = df2.format(c.getTime());
        String currDate = df.format(c.getTime());

        String createDate =currDate + "T" + addTime;

        c.add(Calendar.DAY_OF_YEAR, 300);

        df = new SimpleDateFormat("yyyy-MM-dd");
        df2 = new SimpleDateFormat("HH:mm:ss.SSS");

        addTime = df2.format(c.getTime());
        currDate = df.format(c.getTime());

        String expiryDate =currDate + "T" + addTime;

        CommonDetails.NotificationTypeEnum type = CommonDetails.NotificationTypeEnum.NOTIFICATION_TYPE_HOMEWORK;
        if(selClassesList != null && selClassesList.size()>0) {
            nd = new CreateNotificationData(
                    UserData.getAccessToken(),
                    createDate,
                    expiryDate,
                    textOfHomework,
                    title,
                    selClassesList,
                    type,false);
        }
        CreateNotificationRequest request = new CreateNotificationRequest(this, nd, this);
        request.executeRequest();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selClass = parent.getSelectedItem().toString();
        int len  = selClass.length();

        if(len > 3){
            selClass = selClass.substring(0,3);
            len = 3;
        }

        String intOnly = selClass.substring(0,len-1);

        int selClassIs = Integer.parseInt(intOnly);
        if(selClassIs > 10){
            mClassLevel = classLevel.Secondary;

            ArrayAdapter<String> spinnerAdapterSubject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
            spinnerAdapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subjectSpinner.setAdapter(spinnerAdapterSubject);

            spinnerAdapterSubject.add("Chemistry");
            spinnerAdapterSubject.add("Biology");
            spinnerAdapterSubject.add("Commerce");
            spinnerAdapterSubject.add("Computers");
            spinnerAdapterSubject.add("Physics");
            spinnerAdapterSubject.add("Accounts");
            spinnerAdapterSubject.add("Economics");
            spinnerAdapterSubject.add("HomeScience");
            spinnerAdapterSubject.add("English");
            spinnerAdapterSubject.add("Physical Edu");

            spinnerAdapterSubject.notifyDataSetChanged();

            spinnerAdapterSubject.notifyDataSetChanged();
        } else {
            mClassLevel = classLevel.Primary;

            ArrayAdapter<String> spinnerAdapterSubject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
            spinnerAdapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subjectSpinner.setAdapter(spinnerAdapterSubject);

            spinnerAdapterSubject.add("Hindi");
            spinnerAdapterSubject.add("English");
            spinnerAdapterSubject.add("Sanskrit");
            spinnerAdapterSubject.add("Maths");
            spinnerAdapterSubject.add("History");
            spinnerAdapterSubject.add("Science");
            spinnerAdapterSubject.add("Social Science");
            spinnerAdapterSubject.add("Physical Edu");
            spinnerAdapterSubject.add("Sociology");
            spinnerAdapterSubject.add("Computers");
            spinnerAdapterSubject.notifyDataSetChanged();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_homework);

        try {
            if ((UserData.getUserType().equals(CommonDetails.USER_TYPE_STUDENT)) ||
                    (UserData.getUserType().equals(CommonDetails.USER_TYPE_PARENT))) {
                finish();
            }

            initRes();
            fillDropDownData();
        }catch(Exception e){
            Toast.makeText(CreateHomework.this, "Error this time , try later", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    void initRes(){
        classSpinner = (Spinner) findViewById(R.id.spinner_class);
        classSpinner.setOnItemSelectedListener(this);
        subjectSpinner = (Spinner) findViewById(R.id.spinner_subject);



        title = (EditText) findViewById(R.id.addtitle);
        detail = (EditText) findViewById(R.id.detail);
        editSubject = (EditText) findViewById(R.id.editSubject);
    }

    void fillDropDownData() {

        ArrayAdapter<String> spinnerAdapterClass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(spinnerAdapterClass);
        HomeWorkStaticData.mClassesInSchoolObj = CommonDetails.getAllClassesInSchool();

        try {
            for (ClassData obj : HomeWorkStaticData.mClassesInSchoolObj) {
                String classsName = obj.getName();
                String section = obj.getSection();
                spinnerAdapterClass.add(classsName);

            }
        } catch (NullPointerException e) {
            Toast.makeText(CreateHomework.this,"Please try later",Toast.LENGTH_LONG);
            finish();
        }
        spinnerAdapterClass.notifyDataSetChanged();


        ArrayAdapter<String> spinnerAdapterSubject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(spinnerAdapterSubject);

        spinnerAdapterSubject.add("Hindi");
        spinnerAdapterSubject.add("English");
        spinnerAdapterSubject.add("Sanskrit");
        spinnerAdapterSubject.add("Maths");
        spinnerAdapterSubject.add("History");
        spinnerAdapterSubject.add("Science");
        spinnerAdapterSubject.add("Social Science");
        spinnerAdapterSubject.add("Physical Edu");
        spinnerAdapterSubject.add("Sociology");

        spinnerAdapterSubject.add("Chemistry");
        spinnerAdapterSubject.add("Biology");
        spinnerAdapterSubject.add("Commerce");
        spinnerAdapterSubject.add("Computers");
        spinnerAdapterSubject.add("Physics");
        spinnerAdapterSubject.add("Accounts");
        spinnerAdapterSubject.add("Economics");
        spinnerAdapterSubject.add("HomeScience");

        spinnerAdapterSubject.notifyDataSetChanged();
    }

}
