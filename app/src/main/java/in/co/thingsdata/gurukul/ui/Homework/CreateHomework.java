package in.co.thingsdata.gurukul.ui.Homework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
import in.co.thingsdata.gurukul.ui.Fees.FeesDetailsStaticData;

import static in.co.thingsdata.gurukul.data.common.CommonDetails.NotificationTypeEnum.NOTIFICATION_TYPE_NORMAL;

public class CreateHomework extends AppCompatActivity implements AdapterView.OnItemSelectedListener
        , CreateNotificationRequest.CreateNotificationCallback {

    Spinner classSpinner, subjectSpinner;
    EditText title,detail,editSubject;
    public void sendHomeWork(View view) {
        String setText = editSubject.getText().toString();
        if(setText == null) {
            setText = subjectSpinner.getSelectedItem().toString();
        }
        setText += "\n" + detail.getText().toString();

        selClassesList = new ArrayList<String>();

        int indexValue = classSpinner.getSelectedItemPosition();
        String classCode = HomeWorkStaticData.mClassesInSchoolObj.get(indexValue).getClassCode();
        HomeWorkStaticData.setSelectedClassRoomId(classCode);

        selClassesList.add(0, classCode);

    }

    @Override
    public void onCreateNotificationResponse(CommonRequest.ResponseCode res, CreateNotificationData data) {

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

        CommonDetails.NotificationTypeEnum type = NOTIFICATION_TYPE_NORMAL;
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

        String selClass = classSpinner.getSelectedItem().toString();

        int selClassIs = Integer.getInteger(selClass);
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

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
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
            for (ClassData obj : FeesDetailsStaticData.mClassesInSchoolObj) {
                String classsName = obj.getName();
                String section = obj.getSection();
                spinnerAdapterClass.add(classsName);

            }
        } catch (NullPointerException e) {

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
