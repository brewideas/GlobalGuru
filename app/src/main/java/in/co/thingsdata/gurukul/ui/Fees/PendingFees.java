package in.co.thingsdata.gurukul.ui.Fees;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.ui.dataUi.CommonAdapter;
import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;

public class PendingFees extends AppCompatActivity {

    private RecyclerView mRecyclerView = null;
    private CommonAdapter mAdapter = null;

    private static List<DataOfUi> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_fees);

        initRes();


        mAdapter = new CommonAdapter(dataList, CommonAdapter.FEES_PENDING
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
                    Toast.makeText(PendingFees.this, "Error : Please restart Application", Toast.LENGTH_LONG).show();
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
    }

}
