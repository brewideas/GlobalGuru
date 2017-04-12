package in.co.thingsdata.gurukul.ui.ReportCardUi;

import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetStudentListInClassData;
import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.Student;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetClassListRequest;
import in.co.thingsdata.gurukul.services.request.GetStudentListInClassReq;
import in.co.thingsdata.gurukul.ui.dataUi.CommonAdapter;
import in.co.thingsdata.gurukul.ui.dataUi.ReportCardModel;
import in.co.thingsdata.gurukul.ui.dataUi.ReportCardStaticData;

public class ReportCardTeacherView extends AppCompatActivity
        implements GetStudentListInClassReq.GetStudentListInClassCallback ,
        GetClassListRequest.GetClassListCallback
        {

    private RecyclerView mRecyclerView = null;
    private CommonAdapter mAdapter = null;

    Button findButton ,upLoadButton;
    AutoCompleteTextView searchList;
    android.support.v7.widget.CardView headerOfList;
    static  final String TAG = "ReportCardTeacherView";
   // ArrayList<Student>  mStudentList = null;
    String classEntered,sectionEntered,yearEntered,typeOfExamcEntered;
    Spinner classTv , sectionTv, yearTv, typeOfExamTv;


            int rollNum = 0;

            void fillDropDownData(){

                ArrayAdapter<String> spinnerAdapterClass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
                spinnerAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                classTv.setAdapter(spinnerAdapterClass);

                ArrayAdapter<String> spinnerAdapterSection = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
                spinnerAdapterSection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sectionTv.setAdapter(spinnerAdapterSection);

                ArrayAdapter<String> spinnerAdapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
                spinnerAdapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                yearTv.setAdapter(spinnerAdapterYear);

                ArrayAdapter<String> spinnerAdapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
                spinnerAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeOfExamTv.setAdapter(spinnerAdapterType);

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

                spinnerAdapterYear.add("2016");
                spinnerAdapterYear.add("2017");
                spinnerAdapterYear.add("2018");
                spinnerAdapterYear.add("2019");
                spinnerAdapterYear.add("2020");

                spinnerAdapterType.add(CommonDetails.EXAM_TYPE_YEARLY);
                spinnerAdapterType.add(CommonDetails.EXAM_TYPE_HALF_YEARLY);


                spinnerAdapterClass.notifyDataSetChanged();
                spinnerAdapterSection.notifyDataSetChanged();
                spinnerAdapterYear.notifyDataSetChanged();
                spinnerAdapterType.notifyDataSetChanged();


            }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_teacher_view);
        initRes();

        fillDropDownData();

        //GetClassListRequest request = new GetClassListRequest(ReportCardTeacherView.this,ReportCardTeacherView.this);
        //request.executeRequest();

 /*
        fillPrevEnteredadat();
        */
        initAutoTextView();


        mAdapter = new CommonAdapter(ReportCardStaticData.dataList, CommonAdapter.TEACHER_VIEW_REPORTCARD
                , new CommonAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                try {
                    Intent start;
                    if (ReportCardStaticData.clickedButton == ReportCardStaticData.buttonType.viewBtn) {
                        start = new Intent(ReportCardTeacherView.this, ReportCardSingleStudent.class);
                    } else {
                        start = new Intent(ReportCardTeacherView.this, ReportCardCreate.class);
                    }
                    start.putExtra(getResources().getString(R.string.intent_extra_posInList), position);

                    String regId = ReportCardStaticData.mStudentList.get(position).getRegistrationId();
                    ReportCardStaticData.setRegistrationId(regId);

                    int rolNumber = ReportCardStaticData.mStudentList.get(position).getRollNumber();
                    ReportCardStaticData.setRollNumber(rolNumber);
                    //start.putExtra(getResources().getString(R.string.intent_extra_rolnum),ReportCardStaticData.mStudentList.);

                    startActivity(start);
                }catch(Exception e){
                    Toast.makeText(ReportCardTeacherView.this, "Error : Please restart Application", Toast.LENGTH_LONG).show();
                }

               ///list item was clicked
            }




        });

        mRecyclerView = (RecyclerView)findViewById(R.id.singleStudentMarks);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);




    }



    void onClickImplimentation(){





        if(mRecyclerView != null) {

          /*  ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                    RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                    holder.itemView.setBackgroundColor(Color.parseColor("#76767"));
                    Log.d(TAG, "item clicked:" + position);

                }

            });*/
        }

    }


    void initRecyclerView(){
        if(mAdapter == null) {
            mAdapter = new CommonAdapter(ReportCardStaticData.dataList, CommonAdapter.TEACHER_VIEW_REPORTCARD , new CommonAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View view, int position) {
                     Log.d("asas","asasa");
                    ///list item was clicked
                }
            });
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onGetStudentListResponse(CommonRequest.ResponseCode res, GetStudentListInClassData data) {
        ReportCardModel dataStudentListForAdapter = null;
        ReportCardStaticData.dismissProgressBar();
        switch (res){

            case COMMON_RES_SUCCESS:

                if(ReportCardStaticData.mStudentList != null) {
                    ReportCardStaticData.mStudentList.clear();
                }
                ReportCardStaticData.dataList.clear();

                ReportCardStaticData.mStudentList = data.getStudentListInClass();
                for(Student obj: ReportCardStaticData.mStudentList){
                     String name = obj.getName();
                     int rollNumber = obj.getRollNumber();
                     String regId = obj.getRegistrationId();
                     dataStudentListForAdapter = new ReportCardModel(name,rollNumber,regId);
                     ReportCardStaticData.dataList.add(dataStudentListForAdapter);

                }
              mAdapter.notifyDataSetChanged();
              Toast.makeText(ReportCardTeacherView.this, "Select Student from List", Toast.LENGTH_LONG).show();
                break;
            case COMMON_RES_INTERNAL_ERROR:
            case COMMON_RES_CONNECTION_TIMEOUT:
            case COMMON_RES_FAILED_TO_CONNECT:
            case COMMON_RES_IMAGE_NOT_FOUND:
            case COMMON_RES_SERVER_ERROR_WITH_MESSAGE:

                Toast.makeText(ReportCardTeacherView.this, "Error in receiving data ,Please try some other time", Toast.LENGTH_LONG).show();
              break;
            default:
                Toast.makeText(ReportCardTeacherView.this, "Error : Please try some other time", Toast.LENGTH_LONG).show();
                Log.d(TAG,"NOt known exception :" + res);
                break;
        }
  //      prepareMovieData();

    }

    public void executeResultQuery() {

        String token = UserData.getAccessToken();
        String classTvStr = classTv.getSelectedItem().toString();
        String sectionTvStr = sectionTv.getSelectedItem().toString();
        int yearTvStr = Integer.parseInt(yearTv.getSelectedItem().toString());
        String typeOfExamTvStr = typeOfExamTv.getSelectedItem().toString();

        ReportCardStaticData.setSelectedTypeOfExam(typeOfExamTvStr);
        ReportCardStaticData.setSelectedSection(sectionTvStr);
        ReportCardStaticData.setSelectedClass(classTvStr);
        ReportCardStaticData.setSelectedYear(yearTvStr);


        Integer indexValue = classTv.getSelectedItemPosition();

        String classN = ReportCardStaticData.mClassesInSchoolObj.get(indexValue).getClassRoomId();//UserData.getClassRoomId();//todo: uncomment once server data is right //ReportCardStaticData.getSelectedClass();
        ReportCardStaticData.setSelectedClassRoomId(classN);

        String classSec = sectionTvStr; //todo: uncomment once server data is right //ReportCardStaticData.getSelectedSection();

        GetStudentListInClassData data = new GetStudentListInClassData(token,classN,classSec);
        GetStudentListInClassReq req = new GetStudentListInClassReq(this,data,this);

        req.executeRequest();

        //prepareMovieData();

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
            Log.v("TAG","Error of searchList autotextview");
        }

    }


    void initRes(){

        headerOfList = (android.support.v7.widget.CardView)findViewById(R.id.headerOfList);
        mRecyclerView = (RecyclerView)findViewById(R.id.singleStudentMarks);
        searchList = (AutoCompleteTextView)findViewById(R.id.searchList);
        findButton = (Button)findViewById(R.id.findButton);
        upLoadButton = (Button)findViewById(R.id.uploadButton);


        classTv =  (Spinner )findViewById(R.id.spinner_class);
        sectionTv =  (Spinner )findViewById(R.id.spinner_section);
        yearTv =  (Spinner )findViewById(R.id.spinner_year);
        typeOfExamTv =  (Spinner )findViewById(R.id.spinner_type);
    }

    void fillPrevEnteredadat(){

       /* try{
            classTv.setText(ReportCardStaticData.getSelectedClass());
            sectionTv.setText(ReportCardStaticData.getSelectedSection());
            yearTv.setText(ReportCardStaticData.getSelectedYear());
            typeOfExamTv.setText(ReportCardStaticData.getSelectedTypeOfExam());
        }catch(Resources.NotFoundException | NullPointerException ed){
            Log.d(TAG,"fillPrevEnteredadat ERROR");
        }*/
    }


    void setVisibilityOfComponents(int visibility){
        mRecyclerView.setVisibility(visibility);
        headerOfList.setVisibility(visibility);
        searchList.setVisibility(visibility);


    }

    void getTextEnteredByUser(){

/*        try {
            classEntered = classTv.getText().toString();
            ReportCardStaticData.setSelectedClass(classEntered);

            sectionEntered = sectionTv.getText().toString();
            ReportCardStaticData.setSelectedSection(sectionEntered);

            yearEntered = yearTv.getText().toString();
            ReportCardStaticData.setSelectedYear(Integer.parseInt(yearEntered));

            typeOfExamcEntered = typeOfExamTv.getText().toString();
            ReportCardStaticData.setSelectedTypeOfExam(typeOfExamcEntered);
        }catch (Exception e){
            Log.d(TAG,"AutoComplete resources null");
        }
*/

    }



    public void executeFindQuery(View view) {
        setVisibilityOfComponents(View.VISIBLE);
        getTextEnteredByUser();
        initRecyclerView();

        ReportCardStaticData.clickedButton = ReportCardStaticData.buttonType.viewBtn;

        if(searchList != null) {
            //searchList.clearListSelection();
           // searchList.clearComposingText();
            searchList.setText("");
        }

        executeResultQuery(); //we need to show student list . when teacher clicks particular student show his results

    }

    public void executeUploadQuery(View view) {
        setVisibilityOfComponents(View.VISIBLE);
        getTextEnteredByUser();
        initRecyclerView();
        ReportCardStaticData.showProgressBar(ReportCardTeacherView.this);
        ReportCardStaticData.clickedButton = ReportCardStaticData.buttonType.uploadBtn;

        if(searchList != null) {
            searchList.setText("");
            //searchList.clearComposingText();
         //   searchList.clearListSelection();
        }

        executeResultQuery(); //we need to show student list . when teacher clicks particular student show his results
//        prepareMovieData();

    }


            @Override
            public void onGetClassListResponse(CommonRequest.ResponseCode res, ArrayList<ClassData> class_id_list) {

                ArrayAdapter<String> spinnerAdapterClass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
                spinnerAdapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                classTv.setAdapter(spinnerAdapterClass);

                ArrayAdapter<String> spinnerAdapterSection = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
                spinnerAdapterSection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sectionTv.setAdapter(spinnerAdapterSection);

                ArrayAdapter<String> spinnerAdapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
                spinnerAdapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                yearTv.setAdapter(spinnerAdapterYear);

                ArrayAdapter<String> spinnerAdapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
                spinnerAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeOfExamTv.setAdapter(spinnerAdapterType);

                ArrayList<ClassData> dataObj = CommonDetails.getAllClassesInSchool();

                try {
                    for (ClassData obj : dataObj) {
                        String classsName = obj.getName();
                        String section = obj.getSection();

                        spinnerAdapterClass.add(classsName);
                        spinnerAdapterSection.add(section);
                    }
                }catch(NullPointerException e){
                    Toast.makeText(ReportCardTeacherView.this, "Error: Please restart application", Toast.LENGTH_LONG).show();
                }

                spinnerAdapterYear.add("2016");
                spinnerAdapterYear.add("2017");
                spinnerAdapterYear.add("2018");
                spinnerAdapterYear.add("2019");
                spinnerAdapterYear.add("2020");




                spinnerAdapterType.add("value1");
                spinnerAdapterType.add("value2");
                spinnerAdapterType.add("value3");
                spinnerAdapterType.add("value4");

                spinnerAdapterClass.notifyDataSetChanged();
                spinnerAdapterSection.notifyDataSetChanged();
                spinnerAdapterYear.notifyDataSetChanged();
                spinnerAdapterType.notifyDataSetChanged();



            }
        }
