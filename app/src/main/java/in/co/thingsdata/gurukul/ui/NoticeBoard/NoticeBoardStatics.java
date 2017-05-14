package in.co.thingsdata.gurukul.ui.NoticeBoard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetNotificationStatsData;
import in.co.thingsdata.gurukul.data.GetNotificationSummaryData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.NotificationReplyDetail;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetNotificationStatsRequest;
import in.co.thingsdata.gurukul.services.request.GetNotificationSummaryRequest;
import in.co.thingsdata.gurukul.ui.dataUi.CommonAdapter;
import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;
import in.co.thingsdata.gurukul.ui.dataUi.NoticeBoardModel;

public class NoticeBoardStatics extends AppCompatActivity implements GetNotificationStatsRequest.GetNotificationStatsCallback, GetNotificationSummaryRequest.GetNotificationSummaryCallback {
    private RecyclerView mRecyclerView;
    private CommonAdapter mAdapter;

    TextView mtvYes,mtvNo,mtvPending,mtvMsg;
    RelativeLayout showDetail;
    android.support.v7.widget.CardView cView;
    private List<DataOfUi> dataList = new ArrayList<>();
    private String TAG = "NoticeBoardStatics";
    String mSelNotification = null;
    Button detailsbutton;
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



       // getResultsOfQuery();

        getSummary();
   }


   private void  getSummary(){

       GetNotificationSummaryData data = new GetNotificationSummaryData(mSelNotification);
       GetNotificationSummaryRequest request = new GetNotificationSummaryRequest(this,data,this);

       request.executeRequest();
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

        mtvYes = (TextView)findViewById(R.id.yes);
        mtvNo = (TextView)findViewById(R.id.no);
        cView = (android.support.v7.widget.CardView)findViewById(R.id.InfoAboutVoteResults);
        mtvPending = (TextView)findViewById(R.id.pending);
        mtvMsg = (TextView)findViewById(R.id.msg);
        showDetail = (RelativeLayout)findViewById(R.id.showDetailRl);
        detailsbutton = (Button)findViewById(R.id.btn_statsdetail);
    }

    @Override
    public void onGetNotificationStatsResponse(CommonRequest.ResponseCode res, GetNotificationStatsData data) {

        if(dataList != null) {
            dataList.clear();
        }

        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){

            showDetail.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            detailsbutton.setVisibility(View.GONE);
            mtvMsg.setVisibility(View.GONE);
            cView.setVisibility(View.GONE);
            try{
                int size = data.getAllReplies().size();
                ArrayList<NotificationReplyDetail> notificsReply =  data.getAllReplies();

                for(NotificationReplyDetail objNotification : notificsReply){

                    String name = objNotification.getUserName();
                    String rolNum = Integer.toString(objNotification.getRollNumber());
                    String className = objNotification.getClassName();


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

        }else{
            mRecyclerView.setVisibility(View.GONE);
            showDetail.setVisibility(View.GONE);
            detailsbutton.setVisibility(View.VISIBLE);
            cView.setVisibility(View.VISIBLE);
            mtvMsg.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public void onGetNotificationSummaryResponse(CommonRequest.ResponseCode res, GetNotificationSummaryData data) {

        String toDisplay = "";
        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            int total = data.getTotalNotificationCount();
            int yesCount = data.getTotalYesNotificationCount();
            int noCount = data.getTotalNoNotificationCount();
            int pending = total - (yesCount + noCount);

            if(yesCount == 0 && noCount == 0){
                toDisplay = getResources().getString(R.string.vote_noVotes);
            }else if(yesCount  > noCount){
                toDisplay = getResources().getString(R.string.vote_success);
            }else if(noCount  > yesCount){
                toDisplay = getResources().getString(R.string.vote_fail);
            }else if(noCount == yesCount){
                toDisplay = getResources().getString(R.string.vote_equal);
            }
            String totalStr = String.valueOf(total);
            String pendingStr;
            if(pending > 0){
                pendingStr = String.valueOf(pending);
            }else{
                pendingStr = "0";
            }

            cView.setVisibility(View.VISIBLE);
            mtvMsg.setVisibility(View.VISIBLE);
            mtvMsg.setText(toDisplay);
            mtvNo.setText(String.valueOf(noCount) + "/" +totalStr);
            mtvYes.setText(String.valueOf(yesCount) + "/" +totalStr);
            mtvPending.setText(String.valueOf(pendingStr) + "/" +totalStr);
        }

    }

    public void Showdetails(View view) {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);



        getResultsOfQuery();

    }
}
