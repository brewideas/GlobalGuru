package in.co.thingsdata.gurukul.ui.Fees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import in.co.thingsdata.gurukul.Models.FeesListModel;
import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.common.FeesProfileData;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetStudentFeesProfileRequest;

public class FeesProfileNonEditable extends AppCompatActivity implements GetStudentFeesProfileRequest.GetFeesProfileCallback {

    TextView nameTV,
             classTV,
             regIdTv,
             fatherNameTv,
             monthTv,
             lateFeesTv,
             otherChargeTv,
             totalTv;

    int mposInList = 0;
    FeesListModel mSelectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fees_profile_non_editabe);
        FeesDetailsStaticData.showProgressBar(this);


        Intent intent = getIntent();
        mposInList = intent.getIntExtra(getResources().getString(R.string.intent_extra_Fees_studentposInList), 1);

        initRes();

        mSelectedStudent = (FeesListModel) FeesDetailsStaticData.dataList.get(mposInList);

        String regId = mSelectedStudent.getRegIdFromPos(mposInList);
        regIdTv.setText(regId);
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

    void initRes(){

     nameTV = (TextView)findViewById(R.id.nameFees);
     classTV = (TextView)findViewById(R.id.classfees);
     regIdTv = (TextView)findViewById(R.id.regId);
     fatherNameTv = (TextView)findViewById(R.id.fathernameTv);
     monthTv = (TextView)findViewById(R.id.monthTv);
     lateFeesTv = (TextView)findViewById(R.id.lateFeesTv);
     otherChargeTv = (TextView)findViewById(R.id.otherChargesTv);
     totalTv = (TextView)findViewById(R.id.feesTotalTv);

    }

    int mStatus = 0;
    int mMonth = 0 , mYear = 0, mPaidFees = 0, mRemainingFees = 0;
    String lastPaidDate = null;

    @Override
    public void onGetStudentFeesProfileResponse(CommonRequest.ResponseCode res, int status, int month,
                                                int year, int paidFees, int remainingFees, String lastPaidDate,String lastFeeCollector,
                                                int lateFees,int otherCharges) {

             FeesDetailsStaticData.dismissProgressBar();

            if(CommonRequest.ResponseCode.COMMON_RES_SUCCESS == res){
                mMonth = month ; mYear = year; mPaidFees = paidFees; mRemainingFees = remainingFees;
                try {
                    totalTv.setText(Integer.toString(paidFees));
                    monthTv.setText(Integer.toString(month));
                    lateFeesTv.setText(Integer.toString(lateFees));
                    otherChargeTv.setText(Integer.toString(otherCharges));
                }catch (NullPointerException e){
                    Toast.makeText(FeesProfileNonEditable.this,"Error in getting data . Try later",Toast.LENGTH_LONG);
                    finish();

                }

            }else{
                Toast.makeText(FeesProfileNonEditable.this,"Error please try some other time",Toast.LENGTH_LONG);
                finish();
            }

    }

    public void feesProfilefinish(View view) {
        finish();
    }
}