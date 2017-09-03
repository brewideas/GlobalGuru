package in.co.thingsdata.gurukul.ui.Fees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import in.co.thingsdata.gurukul.Models.FeesListModel;
import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.FeesProfileData;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetStudentFeesProfileRequest;
import in.co.thingsdata.gurukul.services.request.SubmitStudentFeesReq;

public class FeesProfile extends AppCompatActivity implements GetStudentFeesProfileRequest.GetFeesProfileCallback  , SubmitStudentFeesReq.SubmitStudenFeesCallback{

    TextView nameTV,classTV,LpaidAmountTV,LPaidDateTV,previousRemainingAmountTV,textViewDateTV;
//feeCollectorTv,
    EditText balanceRemaingEt , amluntPaidEt;
    Spinner monthSv;
    int mposInList = 0;
    FeesListModel mSelectedStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fees_profile);
        FeesDetailsStaticData.showProgressBar(this);
        Intent intent = getIntent();
        mposInList = intent.getIntExtra(getResources().getString(R.string.intent_extra_Fees_studentposInList), 1);

        initRes();
        fillDropDownData();

        mSelectedStudent = (FeesListModel) FeesDetailsStaticData.dataList.get(mposInList);

        String regId = mSelectedStudent.getRegIdFromPos(mposInList);
        nameTV.setText(mSelectedStudent.getName());
        showIndividualFeesProfile();
    }

    void showIndividualFeesProfile(){

        String registrationId = mSelectedStudent.getRegId();
        int month = 0;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String addTime = df.format(c.getTime());

        int pos = addTime.indexOf('-');


        if(month == 0){
            String Strmonth = addTime.substring(pos+1,pos+3);
            month = Integer.parseInt(Strmonth);
        }

        addTime = addTime.substring(0,pos);
        int year = Integer.parseInt(addTime);

        String classRoomId = FeesDetailsStaticData.getSelectedStudentClassRoomId();
        String section =  FeesDetailsStaticData.getSelectedStudentSection();
        String feesSubmittedBy = "Teacher";//String.valueOf(feesSubmittedByTv.getText());
        String ReceiptNumber = "121";

        int feesPaid  = 0 ;
        int remainingFees  = 0;
        String lastPaidDate  = "Not Updated";

        FeesProfileData data = new FeesProfileData(registrationId, UserData.getAccessToken(), month,classRoomId,section,
                feesSubmittedBy,ReceiptNumber ,year,feesPaid, remainingFees,
                lastPaidDate);

        GetStudentFeesProfileRequest reqFees = new GetStudentFeesProfileRequest(this,this,data);
        reqFees.executeRequest();



    }

    public void feesProfile(View view) {
        executeResultQuery();

    }

    void setViewGoneIfNotPrinciple(){

        if(UserData.getUserType() == CommonDetails.USER_TYPE_PRINCIPAL) {
            balanceRemaingEt.setVisibility(View.GONE);
            amluntPaidEt.setVisibility(View.GONE);
            monthSv.setVisibility(View.GONE);
        }

    }


    void initRes(){

        balanceRemaingEt = (EditText)findViewById(R.id.balanceEV);
        amluntPaidEt = (EditText)findViewById(R.id.actualFeesEV);

        nameTV = (TextView)findViewById(R.id.profileNameTV);
//        feeCollectorTv = (TextView)findViewById(R.id.feeCollectorTv);


        LpaidAmountTV = (TextView)findViewById(R.id.lastPaidAmountTV);
        LPaidDateTV = (TextView)findViewById(R.id.lastPaidDateTV);
        previousRemainingAmountTV  = (TextView)findViewById(R.id.previousRemainingAmountTV);
        textViewDateTV =  (TextView)findViewById(R.id.textViewDate);
        monthSv = (Spinner)findViewById(R.id.spinner_month);

        setViewGoneIfNotPrinciple();
    }

    void fillDropDownData(){

        ArrayAdapter<String> spinnerAdapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSv.setAdapter(spinnerAdapterMonth);

        spinnerAdapterMonth.add("January");
        spinnerAdapterMonth.add("Feb");
        spinnerAdapterMonth.add("March");
        spinnerAdapterMonth.add("April");
        spinnerAdapterMonth.add("May");
        spinnerAdapterMonth.add("June");
        spinnerAdapterMonth.add("July");
        spinnerAdapterMonth.add("August");
        spinnerAdapterMonth.add("September");
        spinnerAdapterMonth.add("October");
        spinnerAdapterMonth.add("November");
        spinnerAdapterMonth.add("December");

        spinnerAdapterMonth.notifyDataSetChanged();
    }


    void getDataInputWhileFeesUpdate(){

        if(UserData.getUserType() == CommonDetails.USER_TYPE_PRINCIPAL) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss.SSS");

            String addTime = df2.format(c.getTime());
            String currDate = df.format(c.getTime());

            String dateOfPayment = currDate + "T" + addTime;

            String monthStr = monthSv.getSelectedItem().toString();
            String amountPaid = amluntPaidEt.getText().toString();
            String balanceRemaing = balanceRemaingEt.getText().toString();
        }
    }


    public void executeResultQuery() {

        String registrationId = mSelectedStudent.getRegId();
        int month = monthSv.getSelectedItemPosition() + 1;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String addTime = df.format(c.getTime());

        int pos = addTime.indexOf('-');


//        if(month == 0){
//          String Strmonth = addTime.substring(pos,2);
//          month = Integer.parseInt(Strmonth);
//        }

        addTime = addTime.substring(0,pos);
        int year = Integer.parseInt(addTime);
        String classRoomId = FeesDetailsStaticData.getSelectedStudentClassRoomId();
        String section =  FeesDetailsStaticData.getSelectedStudentSection();

        String feesSubmittedBy = UserData.getFirstName();
        String ReceiptNumber = "121";

        String fePaid = amluntPaidEt.getText().toString();
        int feesPaid  = Integer.parseInt(fePaid);

        String feeremaining = balanceRemaingEt.getText().toString();

        int remainingFees = Integer.parseInt(feeremaining);
        String lastPaidDate  = "0";

        FeesProfileData data = new FeesProfileData(registrationId, UserData.getAccessToken(), month,classRoomId,section,
                feesSubmittedBy,ReceiptNumber ,year,feesPaid, remainingFees,
                lastPaidDate);

        SubmitStudentFeesReq reqFees = new SubmitStudentFeesReq(this,data,this);
        reqFees.executeRequest();
        FeesDetailsStaticData.showProgressBar(this);


    }

    int mStatus = 0;
    int mMonth = 0 , mYear = 0, mPaidFees = 0, mRemainingFees = 0;
    String lastPaidDate = null;
    @Override
    public void onGetStudentFeesProfileResponse(CommonRequest.ResponseCode res, int status, int month,
                                                int year, int paidFees, int remainingFees,
                                                String lastPaidDate,String lastFeeCollector,
                                                int lateFees,int otherCharges) {

             FeesDetailsStaticData.dismissProgressBar();

            if(CommonRequest.ResponseCode.COMMON_RES_SUCCESS == res){
                mMonth = month ; mYear = year; mPaidFees = paidFees; mRemainingFees = remainingFees;

                LpaidAmountTV.setText(Integer.toString(paidFees));
                LPaidDateTV.setText(Integer.toString(month));
//                feeCollectorTv.setText(lastFeeCollector);
                previousRemainingAmountTV.setText(Integer.toString(mRemainingFees));
                textViewDateTV.setText(lastPaidDate);

            }else{
               // Toast.makeText(FeesProfile.this,"Error please try some other time",Toast.LENGTH_LONG);
          //      finish();
            }

    }

    @Override
    public void onSubmitFeesResponse(CommonRequest.ResponseCode res, FeesProfileData data) {

        FeesDetailsStaticData.dismissProgressBar();

        if(CommonRequest.ResponseCode.COMMON_RES_SUCCESS == res) {
            Toast.makeText(FeesProfile.this, "Fees paid successfully",Toast.LENGTH_LONG);
            finish();

        }else{
            Toast.makeText(FeesProfile.this,"Error in submitting fees",Toast.LENGTH_LONG);
            //      finish();
        }

    }
}