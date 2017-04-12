package in.co.thingsdata.gurukul.ui.NoticeBoard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetNotificationData;
import in.co.thingsdata.gurukul.data.common.Notification;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetNotificationRequest;
import in.co.thingsdata.gurukul.ui.dataUi.CommonAdapter;
import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;
import in.co.thingsdata.gurukul.ui.dataUi.NoticeBoardModel;

public class NotesList extends AppCompatActivity implements GetNotificationRequest.GetNotificationCallback{

    private RecyclerView mRecyclerView;
    private CommonAdapter mAdapter;

    TextView mtvTitle,mtvDiscription;
    private List<DataOfUi> dataList = new ArrayList<>();
    private String TAG = "NotesList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nb_notes_list);

        initRes();

        getResultsOfQuery();
    }

    private void getResultsOfQuery(){
        String token = UserData.getAccessToken();
        try{
            GetNotificationData data = new GetNotificationData(token);
            GetNotificationRequest req = new GetNotificationRequest(NotesList.this,data,NotesList.this);

            req.executeRequest();

        }catch(Exception e){

        }

    }

    void initRes(){
        mRecyclerView = (RecyclerView)findViewById(R.id.statsRecyclerView);
    }

    @Override
    public void onGetNotificationResponse(CommonRequest.ResponseCode res, GetNotificationData data) {

        if(dataList != null) {
            dataList.clear();
        }

        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            try{
                int size = data.getNumberOfNotification();
                ArrayList<Notification> notifications = data.getNotificationList();

                for(Notification obj : notifications){
                    String title = obj.getTitle();
                    String dis = obj.getDescription();

                    NoticeBoardModel modelObj = new NoticeBoardModel(title,dis);
                    dataList.add(modelObj);
                }

                mAdapter.notifyDataSetChanged();

            }catch(Exception e){

            }
        }

    }

    public void addNewNote(View view) {

        Intent launchFeature = new Intent(this, selectClass.class);
        startActivity(launchFeature);


    }
}
