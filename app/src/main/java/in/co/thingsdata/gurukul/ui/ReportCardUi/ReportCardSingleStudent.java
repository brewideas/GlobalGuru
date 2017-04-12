package in.co.thingsdata.gurukul.ui.ReportCardUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.MarkSheetData;
import in.co.thingsdata.gurukul.data.SubjectWiseMarks;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetResultReq;
import in.co.thingsdata.gurukul.ui.dataUi.CommonAdapter;
import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;
import in.co.thingsdata.gurukul.ui.dataUi.ReportCardModel;
import in.co.thingsdata.gurukul.ui.dataUi.ReportCardStaticData;

public class ReportCardSingleStudent extends AppCompatActivity implements GetResultReq.GetResultCallback {

    private List<DataOfUi> dataList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CommonAdapter mAdapter;

    int mTotalMarksObtained  = 80, mTotalMarks = 100;
    float mFinalPer = 0;

    String[] yearArray , typeOfExamArray;
    TextView mName,mClassSection,mRollNumber;
    Spinner yearTv, typeOfExamTv;
    TextView mMarksObt,mTotal,mPercent;
    AutoCompleteTextView tvYear , tvTypeOfExam;
    int mRolNumber = 0;
    TextView mTitle;
    ReportCardModel mSelectedStudent;
    public static String TAG = "ReportCardSingleStudent";
    int mposInList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reportcard_singlestudent);
        initRes();

        Intent intent = getIntent();
        mposInList = intent.getIntExtra(getResources().getString(R.string.intent_extra_posInList), 1);

        try {
            mSelectedStudent = (ReportCardModel) ReportCardStaticData.dataList.get(mposInList);
            mName.setText(mSelectedStudent.getName());

            mClassSection.setText(getResources().getText(R.string.classOfStudent) +  ReportCardStaticData.getSelectedClass()
                    + " " + ReportCardStaticData.getSelectedSection());

            mRolNumber = mSelectedStudent.getRollNumber();
            mRollNumber.setText(Integer.toString(mRolNumber));
        }catch(NullPointerException e){
            Log.d(TAG,"Data is null");
        }
        mAdapter = new CommonAdapter(dataList, CommonAdapter.SINGLE_STUDENT_REPORTCARD_DETAIL , new CommonAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Log.d("asas", "asasa");
                ///list item was clicked
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);


        initAutotextViewer();

        getResultsOfQuery();

    }


    void initRes(){
        mRecyclerView = (RecyclerView)findViewById(R.id.singleStudentMarks);

        mName = (TextView)findViewById(R.id.enteredNametv);
        mClassSection = (TextView)findViewById(R.id.ClassSection);
        mRollNumber = (TextView)findViewById(R.id.enteredRolNum);


        mMarksObt = (TextView)findViewById(R.id.totalMarksObtained);
        mTotal = (TextView)findViewById(R.id.totalSumOfMarks);
        mPercent = (TextView)findViewById(R.id.percentageOfMarks);

        yearTv =  (Spinner)findViewById(R.id.spinner_year);
        typeOfExamTv =  (Spinner )findViewById(R.id.spinner_type);

        ArrayAdapter<String> spinnerAdapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearTv.setAdapter(spinnerAdapterYear);


        String yrArray[] = {"2016","2017","2018","2019","2020"};

        spinnerAdapterYear.add(yrArray[0]);
        spinnerAdapterYear.add(yrArray[1]);
        spinnerAdapterYear.add(yrArray[2]);
        spinnerAdapterYear.add(yrArray[3]);
        spinnerAdapterYear.add(yrArray[4]);



        ArrayAdapter<String> spinnerAdapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfExamTv.setAdapter(spinnerAdapterType);

        spinnerAdapterType.add(CommonDetails.EXAM_TYPE_YEARLY);
        spinnerAdapterType.add(CommonDetails.EXAM_TYPE_HALF_YEARLY);

        String type = ReportCardStaticData.getSelectedTypeOfExam();
        String year = Integer.toString(ReportCardStaticData.getSelectedYear());
        int siz = yrArray.length;
        int typeIndex = 0,yrIndex = 0;

        for(yrIndex = 0;yrIndex <siz;yrIndex++){
            String yrStr = yrArray[yrIndex];
            if(yrStr.equals(year)== true){
                break;
            }
        }
        yearTv.setSelection(yrIndex);

        if(type.equals(CommonDetails.EXAM_TYPE_YEARLY)) {
            typeIndex = 0;
        }else{
            typeIndex = 1;
        }
        typeOfExamTv.setSelection(typeIndex);

        spinnerAdapterYear.notifyDataSetChanged();
        spinnerAdapterType.notifyDataSetChanged();

    }


    void initAutotextViewer(){

        yearArray = getResources().getStringArray(R.array.YearOfExam);
        typeOfExamArray = getResources().getStringArray(R.array.TypeOfExam);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, yearArray);
        tvYear = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_year);

        String yearsel = Integer.toString(ReportCardStaticData.getSelectedYear());
        tvYear.setText(yearsel);

        tvYear.setThreshold(0);
        tvYear.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, typeOfExamArray);
        tvTypeOfExam = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_type);

        String typOfExam = ReportCardStaticData.getSelectedTypeOfExam();
        tvTypeOfExam.setText(typOfExam);
        tvTypeOfExam.setThreshold(0);
        tvTypeOfExam.setAdapter(adapter2);

   }

    void setFooter(){
        mMarksObt.setText(Integer.toString(mTotalMarksObtained));
        mTotal.setText(Integer.toString(mTotalMarks));
        mPercent.setText(Float.toString(mFinalPer));
    }

    @Override
    public void onResultResponse(CommonRequest.ResponseCode res, MarkSheetData mrData) {

        ReportCardStaticData.dismissProgressBar();

        if(dataList != null) {
            dataList.clear();
        }
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){

            try {
                ArrayList<SubjectWiseMarks> receivedSubjMakrs = mrData.getMarkSheet();
                for (SubjectWiseMarks subjMakrs : receivedSubjMakrs) {

                    int marks = subjMakrs.getMarksObtained();
                    int totalMarks = subjMakrs.getTotalMarks();
                    mTotalMarksObtained += marks;
                    mTotalMarks += totalMarks;
                    float percentage = (marks*100)/ totalMarks;

                    ReportCardModel data = new ReportCardModel(subjMakrs.getSubject().getSubjectId(), Integer.toString(marks),
                            Integer.toString(totalMarks), Float.toString(percentage));
                    dataList.add(data);

                }
            }catch(Exception e){
                Log.d(TAG,"Error in marksheek ");
            }

        }
        mAdapter.notifyDataSetChanged();
        mFinalPer = (mTotalMarksObtained*100)/mTotalMarks;

        setFooter();
     //   prepareMovieData();

    }

    private void getResultsOfQuery(){

        try {
            String token = UserData.getAccessToken();

            String classRoomId = ReportCardStaticData.getSelectedClassRoomId();
            String type = typeOfExamTv.getSelectedItem().toString();//tvTypeOfExam.getText().toString();//ReportCardStaticData.getSelectedTypeOfExam();

            if (type == null) {
                type = ReportCardStaticData.getSelectedTypeOfExam();
            }

            int yr = 0;
            String yrStr = yearTv.getSelectedItem().toString();
            if (yrStr != null) {
                yr = Integer.parseInt(yrStr);//Integer.parseInt(tvYear.getText().toString());//ReportCardStaticData.getSelectedYear();
            } else {
                yr = ReportCardStaticData.getSelectedYear();
            }


            String regId = ReportCardStaticData.mStudentList.get(mposInList).getRegistrationId();//ReportCardStaticData.getRegistrationId();


            MarkSheetData markdata = new MarkSheetData(token, classRoomId, mRolNumber, yr, type, regId);
            GetResultReq reqMarkesheet = new GetResultReq(ReportCardSingleStudent.this, markdata, this);

            reqMarkesheet.executeRequest();
        }catch (Exception e){
            Log.v(TAG,"getResultsOfQuery");
        }
    }



    public void executeResultQuery(View view) {

        getResultsOfQuery();
    }

    void setDataOfViews(){


    }

}






