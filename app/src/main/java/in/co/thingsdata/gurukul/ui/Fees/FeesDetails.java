package in.co.thingsdata.gurukul.ui.Fees;

import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.ui.dataUi.CommonAdapter;
import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;
import in.co.thingsdata.gurukul.ui.dataUi.ReportCardStaticData;



public class FeesDetails extends AppCompatActivity {

    Spinner classSpinner , rollNumerSpinner;
    EditText nameEV , rolNumEV;
    private RecyclerView mRecyclerView = null;
    private CommonAdapter mAdapter = null;

    Button findButton ,upLoadButton;
    AutoCompleteTextView searchList;

    /*
    * dataList : This list is used to add data in adapter
    * Fill this list with data that is required to be passed in constructor of DataUi class
    * */
    public static List<DataOfUi> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fees_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        initRes();
        fillDropDownData();
        initAutoTextView();


        mAdapter = new CommonAdapter(dataList, CommonAdapter.FEES_DETAILS
                , new CommonAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                try {

//                    Intent start;
//                    if (FeesDetailsStaticData.clickedButton == FeesDetailsStaticData.buttonType.viewBtn) {
//                        start = new Intent(FeesDetails.this, .class);
//                    } else {
//                        start = new Intent(FeesDetails.this, .class);
//                    }
//                    start.putExtra(getResources().getString(R.string.intent_extra_posInList), position);
//
//                    String regId = FeesDetailsStaticData.mStudentList.get(position).getRegistrationId();
//                    FeesDetailsStaticData.setRegistrationId(regId);
//
//                    int rolNumber = FeesDetailsStaticData.mStudentList.get(position).getRollNumber();
//                    FeesDetailsStaticData.setRollNumber(rolNumber);
//                    //start.putExtra(getResources().getString(R.string.intent_extra_rolnum),ReportCardStaticData.mStudentList.);
//
//                    startActivity(start);
                }catch(Exception e){
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


    void initRes(){
        mRecyclerView = (RecyclerView)findViewById(R.id.feesRecycler);
        searchList = (AutoCompleteTextView)findViewById(R.id.searchList);
        findButton = (Button)findViewById(R.id.findButton);
        upLoadButton = (Button)findViewById(R.id.uploadButton);

        classSpinner =  (Spinner )findViewById(R.id.spinner_class);
        rollNumerSpinner =  (Spinner )findViewById(R.id.spinner_section);

        nameEV = (EditText)findViewById(R.id.nameEV);
        rolNumEV = (EditText)findViewById(R.id.rollnumberEV);

    }

    void fillDropDownData(){

        ArrayAdapter<String> spinnerAdapterClass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(spinnerAdapterClass);

        ArrayAdapter<String> spinnerAdapterSection = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterSection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rollNumerSpinner.setAdapter(spinnerAdapterSection);

        ReportCardStaticData.mClassesInSchoolObj = CommonDetails.getAllClassesInSchool();

        try {
            for (ClassData obj : ReportCardStaticData.mClassesInSchoolObj) {
                String classsName = obj.getName();
                String section = obj.getSection();

                spinnerAdapterClass.add(classsName);
                spinnerAdapterSection.add(section);
            }
        }catch(NullPointerException e){

        }

        spinnerAdapterClass.notifyDataSetChanged();
        spinnerAdapterSection.notifyDataSetChanged();

    }
    void initAutoTextView(){

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
        }catch(Exception e){
            Log.v("TAG", "Error of searchList autotextview");
        }

    }




}
