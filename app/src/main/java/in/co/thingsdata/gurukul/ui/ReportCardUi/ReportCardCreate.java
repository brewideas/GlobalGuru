package in.co.thingsdata.gurukul.ui.ReportCardUi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetSubjectListData;
import in.co.thingsdata.gurukul.data.MarkSheetData;
import in.co.thingsdata.gurukul.data.SubjectWiseMarks;
import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.Student;
import in.co.thingsdata.gurukul.data.common.Subject;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetSubjectListReq;
import in.co.thingsdata.gurukul.services.request.SubmitMarkSheetReq;
import in.co.thingsdata.gurukul.ui.dataUi.CommonAdapter;
import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;
import in.co.thingsdata.gurukul.ui.dataUi.ReportCardModel;
import in.co.thingsdata.gurukul.ui.dataUi.ReportCardStaticData;

public class ReportCardCreate extends AppCompatActivity implements GetSubjectListReq.GetSubjectListResponse ,
        SubmitMarkSheetReq.SubmitMarkSheetCallback {

    private List<DataOfUi> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommonAdapter mAdapter;
    HorizontalScrollView addSubjectView;
    RelativeLayout layout;
    int editBOxId = 1;
    int indexOfStudentSel = 0;
    TextView className,rollNum,nameOfstudent;
    Student mSelStudent = null;
    String ID[] = new String[] {"Subject","MarksObt","Total"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_create);
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/

        Intent selKid = this.getIntent();
        indexOfStudentSel = selKid.getIntExtra(getResources().getString(R.string.intent_extra_posInList),0);
        initRes();
        fillSubjectListData();

    }

    void initRes(){

        className = (TextView)findViewById(R.id.classOfStudent);
        rollNum = (TextView)findViewById(R.id.rollnumOfStudent);
        nameOfstudent = (TextView)findViewById(R.id.nameOfStudent);

        try {
            className.setText(ReportCardStaticData.getSelectedClass() + " " + ReportCardStaticData.getSelectedSection());
            mSelStudent = ReportCardStaticData.mStudentList.get(indexOfStudentSel);

            String stdName = mSelStudent.getName();
            nameOfstudent.setText(stdName);

            int stdRol  = mSelStudent.getRollNumber();
            rollNum.setText(Integer.toString(stdRol));


        }catch(Exception e){
            finish();

        }




    }


    GetSubjectListData data;
     ArrayList<Subject> mSublist;
    void fillSubjectListData(){
        //String token = UserData.getAccessToken();
        //String classOfSt =  ReportCardStaticData.getSelectedClassRoomId();

       // data = new GetSubjectListData(token,Integer.parseInt(classOfSt));
      //  GetSubjectListReq req = new GetSubjectListReq(ReportCardCreate.this,data,ReportCardCreate.this);

        for(ClassData obj : ReportCardStaticData.mClassesInSchoolObj){
            if(obj.getClassRoomId().equals(ReportCardStaticData.getSelectedClassRoomId())){
                receivedSubjMakrs = obj.getSubjectList();
                break;
            }
        }

        createDynamicLayout();


        //req.executeRequest();


    }

    int getNumberOfSubjects(){
      return receivedSubjMakrs.size();
    }

    void createDynamicLayout(){
        final ScrollView addDynamicLayoutToScrollView =  new ScrollView(ReportCardCreate.this);
        addDynamicLayoutToScrollView.setLayoutParams
                (new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT));
        addDynamicLayoutToScrollView.removeAllViews();

        layout = (RelativeLayout) findViewById(R.id.addSubjectRl);
        layout.removeAllViews();

        final RelativeLayout layoutTest = new RelativeLayout(this);
        layoutTest.setLayoutParams(new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        layoutTest.removeAllViews();

        {
            int rows = getNumberOfSubjects();
            int columns = 3;
            float x =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
            float y =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
            float marginall =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            float rowHt =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
            //@Override
            //public void run()
            {
                int rowIterator = 0;
                int lastRowId = 0;
                boolean repeatLoop = true;
                int  rowId = 1011;

                for(;rowIterator<rows;) {

                    final RelativeLayout row = new RelativeLayout(ReportCardCreate.this);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        rowId =  ReportCardStaticData.generateViewId();
                    } else {
                        rowId = View.generateViewId();
                    }

                    row.removeAllViews();

                    RelativeLayout.LayoutParams rowLp = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            (int)rowHt));


                   //   row.setLayoutParams
                   //         (new RelativeLayout.LayoutParams(300, RelativeLayout.LayoutParams.MATCH_PARENT));
                   //         rowId = rowIterator;



                    row.setId(rowId);

                 //   if(lastRowId!=0)
                    {
                        rowLp.addRule(RelativeLayout.BELOW, lastRowId);
                    }
//                    else {
//                        rowLp.addRule(RelativeLayout.ALIGN_PARENT_TOP, lastRowId);
//                    }
                    lastRowId = rowId;
                    rowLp.setMargins((int)marginall, (int)marginall, (int)marginall, (int)marginall);
                    row.setLayoutParams(rowLp);
                    //row.setOrientation(LinearLayout.HORIZONTAL);


                    int indexOfId = 0;
                    int lastEditBoxId = 0;
                    for(int j = 0;j<columns;j++){
                        EditText et = new EditText(ReportCardCreate.this);
                        if(j == 0) {
                            et.setEnabled(false);
                        }

                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(
                                (int)x,
                                (int)y));

                        if(indexOfId > 2){
                            indexOfId = 0;
                        }


                        if (indexOfId == 0){
                            et.setText(receivedSubjMakrs.get(rowIterator).getSubjectName());
                            et.setTag(R.id.subject_id , receivedSubjMakrs.get(rowIterator).getSubjectId());

                        }else if (indexOfId == 1){
                            et.setHint("0");
                        }else if (indexOfId == 2){
                            et.setText("100");
                        }

                        int editBoxIdGen = 0;
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            editBoxIdGen =  ReportCardStaticData.generateViewId();
                        } else {
                            editBoxIdGen = View.generateViewId();
                        }
                        editBOxId++;
                        et.setId(editBoxIdGen);
                        et.setTag(ID[indexOfId]);

                        indexOfId++;

                        if(indexOfId > 0) {
                            lp.addRule(RelativeLayout.RIGHT_OF
                                    , lastEditBoxId);
                        }
                        lastEditBoxId = editBoxIdGen;
                        lp.setMargins(10, 0, 10, 0);
                        et.setLayoutParams(lp);
                        row.addView(et);

                    }
                    layoutTest.addView(row);

                    if(repeatLoop == true) {
//                        row.setId(rowId);
//                        rowLp.setMargins((int) marginall, (int) marginall, (int) marginall, (int)marginall);
//                        row.setLayoutParams(rowLp);
//                        layoutTest.addView(row);
//                        lastRowId = rowId;
                    //                        continue;
                        repeatLoop = false;
                    }else{
                        rowIterator++;
                    }

                }

                //setContentView(layout);
            }

            addDynamicLayoutToScrollView.addView(layoutTest);
            layout.addView(addDynamicLayoutToScrollView);
        }//, 500);


    }

    MarkSheetData subMitMarkdata = null;
    SubjectWiseMarks subWiseMarks = null;
    boolean mCheckIfMarksNotObt = false;
    public void addSubject() {

        int count = layout.getChildCount();
        ScrollView viewScrol = (ScrollView)layout.getChildAt(0);
        count = viewScrol.getChildCount();

        RelativeLayout row = (RelativeLayout)viewScrol.getChildAt(0);
        count = row.getChildCount();

        ReportCardModel rcData = (ReportCardModel)ReportCardStaticData.dataList.get(indexOfStudentSel);
        int rolnum = ReportCardStaticData.getSelectedRollNumbber();
        String regNum = ReportCardStaticData.getRegistrationId();


        subMitMarkdata = new MarkSheetData(UserData.getAccessToken(), UserData.getClassRoomId(),rolnum,ReportCardStaticData.getSelectedYear(),
                ReportCardStaticData.getSelectedTypeOfExam(),regNum);

        for (int i = 0; i < count; i++) {

            if(i ==  0){
                continue;
            }

            RelativeLayout oneRow = (RelativeLayout) row.getChildAt(i);
            int innerChildCount = oneRow.getChildCount();
            String sub = null; int total = 0; int marks = -1;

            //for (int y = 0; y < innerChildCount; y++)
            {
                View edtText = oneRow.getChildAt(0);
                String subId = null;
                //if (edtText instanceof EditText)
                {//"Subject","MarksObt","Total"

                    if(edtText.getTag().equals("Subject")) {//total

                        sub = ((EditText) edtText).getText().toString();
                        subId = (String)edtText.getTag(R.id.subject_id);

                        Log.d("testasa", "sub " + sub);
                        Log.d("testasa", "sub-id " + subId);


                    }//else

                    edtText = oneRow.getChildAt(1);
                    if(edtText.getTag().equals("MarksObt")) {//these will be marks obtained
                        String strMarks = null;
                        strMarks =  ((EditText) edtText).getText().toString();
                        if(strMarks!=null && strMarks.length() > 0) {
                            marks = Integer.parseInt(strMarks);
                        }else{
                            mCheckIfMarksNotObt = true;
                        }
                        Log.d("testasa", "marks " + marks);
                    }

                    edtText = oneRow.getChildAt(2);
                    //else
                    if(edtText.getTag().equals("Total")) {//these will be subject
                        String strTotal = ((EditText) edtText).getText().toString();
                        total = Integer.parseInt(strTotal);
                        Log.d("test", "String " + data);
                    }
                }

                if(marks > -1) {
                    Subject subObj = new Subject(subId, sub);
                    SubjectWiseMarks addSub = new SubjectWiseMarks(subObj, total, marks);
                    subMitMarkdata.addMarksInSubject(addSub);
                }

            }
        }
        submitMarkSheetCreated();
     }
    final static String TAG = "ReportCardCreate";
    ArrayList<Subject> receivedSubjMakrs;
    @Override
    public void onGetSubjectListResponse(CommonRequest.ResponseCode res, GetSubjectListData data) {

        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){

            try {
                receivedSubjMakrs = data.getSubjectList();
                createDynamicLayout();
            }catch(Exception e){
                Log.d(TAG,"Error in parsing of received data");
            }
        }

    }

    void executeSubmitMarkSheetCreated(){
        SubmitMarkSheetReq req = new SubmitMarkSheetReq(ReportCardCreate.this, subMitMarkdata, this);
        req.executeRequest();

       ReportCardStaticData.showProgressBar(this);
    }

    void submitMarkSheetCreated(){

        if(mCheckIfMarksNotObt == true){

            new AlertDialog.Builder(ReportCardCreate.this)
                    .setTitle("Half Filled ReportCard")
                    .setMessage("Are you sure want to upload ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            executeSubmitMarkSheetCreated();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }else{
            executeSubmitMarkSheetCreated();
        }
        mCheckIfMarksNotObt = false;
    }

    @Override
    public void onSubmitMarksResponse(CommonRequest.ResponseCode res, MarkSheetData data) {
        Log.d(TAG, "response :" + res);
        ReportCardStaticData.dismissProgressBar();
        switch (res) {
            case COMMON_RES_SUCCESS:
                Toast.makeText(ReportCardCreate.this,"Report Card Successfully Uploaded",Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(ReportCardCreate.this,"Report Card Error in Upload , Please try again",Toast.LENGTH_LONG).show();
                break;
        }

        finish();

    }

    public void executeSubjectQuery(View view) {
        addSubject();
    }

    public void cancelSubjectQuery(View view) {

        new AlertDialog.Builder(ReportCardCreate.this)
                .setTitle("Cancel Create ReportCard")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
