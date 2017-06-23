package in.co.thingsdata.gurukul.ui.Fees;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import in.co.thingsdata.gurukul.Models.FeesListModel;
import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetPendingFeesStudentListData;
import in.co.thingsdata.gurukul.data.GetStudentListInClassData;
import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.Student;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetPendingFeesStudentListReq;
import in.co.thingsdata.gurukul.services.request.GetStudentListInClassReq;
import in.co.thingsdata.gurukul.ui.dataUi.CommonAdapter;


public class FeesDetails extends AppCompatActivity implements GetStudentListInClassReq.GetStudentListInClassCallback ,
        GetPendingFeesStudentListReq.GetPendingFeesStudentListCallback{

    Spinner classSpinner, monthSpinner,yearSpinner;

    private RecyclerView mRecyclerView = null;
    private CommonAdapter mAdapter = null;

    Button findButton, upLoadButton;
    AutoCompleteTextView searchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fees_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        initRes();
        fillDropDownData();
        initAutoTextView();

        mAdapter = new CommonAdapter(FeesDetailsStaticData.dataList, CommonAdapter.FEES_DETAILS
                , new CommonAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                try {
                    Intent start;
                    if (FeesDetailsStaticData.clickedButton == FeesDetailsStaticData.buttonType.viewBtn) {
                        start = new Intent(FeesDetails.this, FeesProfile.class);
                    } else if(FeesDetailsStaticData.clickedButton == FeesDetailsStaticData.buttonType.pendingBtn){
                        start = new Intent(FeesDetails.this, FeesProfile.class);
                    }else{
                        start = new Intent(FeesDetails.this, FeesProfile.class);
                    }
                    start.putExtra(getResources().getString(R.string.intent_extra_Fees_studentposInList), position);
                    //start.putExtra(getResources().getString(R.string.intent_extra_rolnum),ReportCardStaticData.mStudentList.);
                    startActivity(start);
                } catch (Exception e) {
                    Toast.makeText(FeesDetails.this, "Error : Please restart Application", Toast.LENGTH_LONG).show();
                }

                ///list item was clicked
            }

        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

    }

    String postOfStudentListSaved = "postOfStudentListSaved";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(FeesDetailsStaticData.dataList != null) {
            outState.putInt(postOfStudentListSaved, FeesDetailsStaticData.dataList.size());
        }

    }
    int savedPos = -1;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        savedPos = savedInstanceState.getInt(postOfStudentListSaved);


    }

    //    String SAVED_RECYCLER_VIEW_STATUS_ID = "savedState";
//    String SAVED_RECYCLER_VIEW_DATASET_ID = "dataSetSaved";

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    void initRes() {
        mRecyclerView = (RecyclerView) findViewById(R.id.feesRecycler);
        searchList = (AutoCompleteTextView) findViewById(R.id.searchList);
        findButton = (Button) findViewById(R.id.findButton);
        upLoadButton = (Button) findViewById(R.id.uploadButton);

        classSpinner = (Spinner) findViewById(R.id.spinner_class);

        yearSpinner = (Spinner) findViewById(R.id.spinner_year);
        monthSpinner = (Spinner) findViewById(R.id.spinner_month);

        ArrayAdapter<String> spinnerAdapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(spinnerAdapterYear);


        spinnerAdapterYear.add("2017-18");
        spinnerAdapterYear.add("2018-19");
        spinnerAdapterYear.add("2019-20");
        spinnerAdapterYear.add("2020-21");
        spinnerAdapterYear.add("2021-22");
        spinnerAdapterYear.add("2022-23");
        spinnerAdapterYear.add("2023-24");

        spinnerAdapterYear.notifyDataSetChanged();

        ArrayAdapter<String> spinnerAdapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(spinnerAdapterMonth);

        spinnerAdapterMonth.add("All");
        spinnerAdapterMonth.add("1");
        spinnerAdapterMonth.add("2");
        spinnerAdapterMonth.add("3");
        spinnerAdapterMonth.add("4");
        spinnerAdapterMonth.add("5");
        spinnerAdapterMonth.add("6");
        spinnerAdapterMonth.add("7");
        spinnerAdapterMonth.add("8");
        spinnerAdapterMonth.add("9");
        spinnerAdapterMonth.add("10");
        spinnerAdapterMonth.add("11");
        spinnerAdapterMonth.add("12");

        spinnerAdapterMonth.notifyDataSetChanged();

    }

    void fillDropDownData() {

        ArrayAdapter<String> spinnerAdapterClass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(spinnerAdapterClass);
        FeesDetailsStaticData.mClassesInSchoolObj = CommonDetails.getAllClassesInSchool();
        try {
            for (ClassData obj : FeesDetailsStaticData.mClassesInSchoolObj) {
                String classsName = obj.getName();
                String section = obj.getSection();
                spinnerAdapterClass.add(classsName);

            }
        } catch (NullPointerException e) {

        }
        spinnerAdapterClass.notifyDataSetChanged();

    }

    void initAutoTextView() {

        try {
            searchList.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    if (mAdapter != null) {
                        mAdapter.getFilter().filter(cs);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {

                }

                @Override
                public void afterTextChanged(Editable arg0) {

                }

            });
        } catch (Exception e) {
            Log.v("TAG", "Error of searchList autotextview");
        }

    }

    void reqStudentList(){


        String token = UserData.getAccessToken();
        Integer indexValue = classSpinner.getSelectedItemPosition();

        String classN = FeesDetailsStaticData.mClassesInSchoolObj.get(indexValue).getClassCode();//UserData.getClassRoomId();//todo: uncomment once server data is right //ReportCardStaticData.getSelectedClass();
        FeesDetailsStaticData.setSelectedStudentClassRoomId(classN);

        String classSection = classSpinner.getSelectedItem().toString();

        String sectionStr = FeesDetailsStaticData.getSection(classSection);
        FeesDetailsStaticData.setSelectedStudentSection(sectionStr);

        GetStudentListInClassData data = new GetStudentListInClassData(token, classN, sectionStr);
        GetStudentListInClassReq req = new GetStudentListInClassReq(this, data, this);

        req.executeRequest();
    }

    void reqPendingFeesList(){

        String token = UserData.getAccessToken();
        Integer indexValue = classSpinner.getSelectedItemPosition();

        String classN = FeesDetailsStaticData.mClassesInSchoolObj.get(indexValue).getClassCode();//UserData.getClassRoomId();//todo: uncomment once server data is right //ReportCardStaticData.getSelectedClass();
        FeesDetailsStaticData.setSelectedStudentClassRoomId(classN);



        String sectionStr = FeesDetailsStaticData.getSection(classN);
        FeesDetailsStaticData.setSelectedStudentSection(sectionStr);

        indexValue = yearSpinner.getSelectedItemPosition();
        String year = yearSpinner.getItemAtPosition(indexValue).toString();

        int iend = year.indexOf("-");
        String strYearSel = year.substring(0, iend);
        int yearSel = Integer.parseInt(strYearSel);
        FeesDetailsStaticData.setYearSearched(yearSel);

        int month = indexValue = monthSpinner.getSelectedItemPosition();
        FeesDetailsStaticData.setMonthSearched(indexValue);

        GetPendingFeesStudentListData data = new GetPendingFeesStudentListData(token,classN,sectionStr,yearSel,month);
        GetPendingFeesStudentListReq req= new GetPendingFeesStudentListReq(FeesDetails.this,data,FeesDetails.this);

        req.executeRequest();


    }

    public void executePendingQuery(View view) {

        FeesDetailsStaticData.clickedButton = FeesDetailsStaticData.buttonType.pendingBtn;
        reqPendingFeesList();
    }

    public void executeViewFeesQuery(View view) {
        FeesDetailsStaticData.clickedButton = FeesDetailsStaticData.buttonType.viewBtn;

        reqStudentList();


    }

    @Override
    public void onGetStudentListResponse(CommonRequest.ResponseCode res, GetStudentListInClassData data) {

        FeesListModel dataStudentListForAdapter = null;

        switch (res) {

            case COMMON_RES_SUCCESS:

                if (FeesDetailsStaticData.mFeesStudentList != null) {
                    FeesDetailsStaticData.mFeesStudentList.clear();
                }

                FeesDetailsStaticData.mFeesStudentList = data.getStudentListInClass();

                for (Student obj : FeesDetailsStaticData.mFeesStudentList) {
                    String name = obj.getName();
                    int rollNumber = obj.getRollNumber();
                    String regId = obj.getRegistrationId();

                    dataStudentListForAdapter = new FeesListModel(name,rollNumber,regId);
                    FeesDetailsStaticData.dataList.add(dataStudentListForAdapter);
                }

                mAdapter.notifyDataSetChanged();
                Toast.makeText(FeesDetails.this, "Select Student from List", Toast.LENGTH_LONG).show();


                break;
            default:
                Toast.makeText(FeesDetails.this, "TRy AGain Error this time !", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void GetPendingFeesStudentListCallback(CommonRequest.ResponseCode res, GetPendingFeesStudentListData data) {

        switch (res) {
            case COMMON_RES_SUCCESS:
                Toast.makeText(FeesDetails.this, "These students have not paid fees as yet", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(FeesDetails.this, "Try Again Error this time !", Toast.LENGTH_LONG).show();
                break;
        }
    }

}
