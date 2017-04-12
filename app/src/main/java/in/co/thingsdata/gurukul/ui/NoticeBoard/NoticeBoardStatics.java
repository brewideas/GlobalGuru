package in.co.thingsdata.gurukul.ui.NoticeBoard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetNotificationStatsData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.NotificationReplyDetail;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetNotificationStatsRequest;
import in.co.thingsdata.gurukul.ui.dataUi.CommonAdapter;
import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;
import in.co.thingsdata.gurukul.ui.dataUi.NoticeBoardModel;

public class NoticeBoardStatics extends AppCompatActivity implements GetNotificationStatsRequest.GetNotificationStatsCallback{
    private RecyclerView mRecyclerView;
    private CommonAdapter mAdapter;

    TextView mtvYes,mtvNo,mtvPending;
    private List<DataOfUi> dataList = new ArrayList<>();
    private String TAG = "NoticeBoardStatics";
    String mSelNotification = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board_statics);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        initRes();


        Intent intent = getIntent();
        mSelNotification = intent.getStringExtra(getResources().getString(R.string.intent_extra_id_notification_selected));


        mAdapter = new CommonAdapter(dataList, CommonAdapter.NB_STATICS , new CommonAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick"+position);
                ///list item was clicked
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        getResultsOfQuery();
   }


    private void getResultsOfQuery(){
        try{
            String token = UserData.getAccessToken();

            GetNotificationStatsData data = new GetNotificationStatsData(token,mSelNotification);
            GetNotificationStatsRequest req = new GetNotificationStatsRequest(NoticeBoardStatics.this,data,NoticeBoardStatics.this);

            req.executeRequest();

            //GetNotificationData data = new  GetNotificationData(token);
            //Request(this,data,this);

        }catch(Exception e){
            Log.d(TAG, "catch of getResultsOfQuery");
        }

    }



    void initRes(){
        mRecyclerView = (RecyclerView)findViewById(R.id.statsRecyclerView);

        mtvYes = (TextView)findViewById(R.id.yesCalTv);
        mtvNo = (TextView)findViewById(R.id.noCalTv);
        mtvPending = (TextView)findViewById(R.id.pendingCalTv);

    }

    @Override
    public void onGetNotificationStatsResponse(CommonRequest.ResponseCode res, GetNotificationStatsData data) {

        if(dataList != null) {
            dataList.clear();
        }

        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            try{
                int size = data.getNumberOfNoReplyByStudent();
                ArrayList<NotificationReplyDetail> notificsReply =  data.getAllReplies();

                for(NotificationReplyDetail objNotification : notificsReply){

                    String name = objNotification.getUserName();
                    String rolNum = Integer.toString(objNotification.getRollNumber());
                    String className = Integer.toString(objNotification.getClassId());
                    CommonDetails.NotificationReplyEnum resEnum = objNotification.getNotificationReply();
                    String resDisplay = null;

                    if(CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_YES == resEnum){
                            resDisplay = "Yes";
                    }else if (CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_NO == resEnum){
                            resDisplay = "No";
                    }else if (CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_PENDING == resEnum){
                        resDisplay = "Pending";
                    }

                    NoticeBoardModel obj = new NoticeBoardModel(name,rolNum,className,resDisplay);

                    dataList.add(obj);
                 }

                mAdapter.notifyDataSetChanged();


            }catch(Exception e){

            }

        }
    }


}
