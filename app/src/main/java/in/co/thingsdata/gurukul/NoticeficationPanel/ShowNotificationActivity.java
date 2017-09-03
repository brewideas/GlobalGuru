package in.co.thingsdata.gurukul.NoticeficationPanel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.co.thingsdata.gurukul.Adapters.NotificationAdapter;
import in.co.thingsdata.gurukul.Models.Studentnotificationmodel;
import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetNotificationStatsData;
import in.co.thingsdata.gurukul.data.ReplyNotificationData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetNotificationStatsRequest;
import in.co.thingsdata.gurukul.services.request.ReplyNotificationRequest;
import in.co.thingsdata.gurukul.ui.AppStart.Dashboard;
import in.co.thingsdata.gurukul.ui.NoticeBoard.NoticeBoardStatics;
import in.co.thingsdata.gurukul.ui.NoticeBoard.selectClass;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_USER_ID;

//Appid : 1:714710807475:android:1ee963c0e5cdb9d0
//PAckage Name : com.GlobalGurukul.android
//PAcakge id : globalgurukul-e3b64
//Web API Key  AIzaSyA9haqp94N4XO197P3U-RC8CduPtIxeXTQ


public class ShowNotificationActivity extends AppCompatActivity
        implements  GetNotificationStatsRequest.GetNotificationStatsCallback, ReplyNotificationRequest.ReplyNotificationCallback{
    Studentnotificationmodel notificationdata;
    ArrayList<Studentnotificationmodel> notifcationlist = new ArrayList<>();
    private String Data_URL = "http://52.66.104.137:8080" +
            "/school-data-service/api/notification/data/search/pull?";

    private String Data_URL_Principal = "http://52.66.104.137:8080" +
            "/school-data-service/api/notification/data/search?";
    ListView list;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);

        userName = (TextView)findViewById(R.id.username);
        userName.setText(UserData.getFirstName());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        /*if((UserData.getUserType().equals(CommonDetails.USER_TYPE_STUDENT)) ||
                 (UserData.getUserType().equals(CommonDetails.USER_TYPE_PARENT))){
            fab.setVisibility(View.GONE);
        }else */{
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(ShowNotificationActivity.this, selectClass.class);
                    startActivity(i);

                }
            });
        }
        Notification("2");
    }

    private void Notification(String filter) {
        String urlSuffix = "?filter=" + filter;

        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog progressDialog = new ProgressDialog(ShowNotificationActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                if (!UserData.getUserType().contentEquals(CommonDetails.USER_TYPE_PRINCIPAL)) {
                    Data_URL += JSON_FIELD_USER_ID + "=" + UserData.getUserId();
                    Data_URL += "&role=" + UserData.getUserType();
                    Data_URL += "&filter=" + UserData.getSchoolCode();
                    Data_URL += "&pullNew=false";

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss.SSS");
                    String addTime = df2.format(c.getTime());
                    String currDate = df.format(c.getTime());
                    String createDate = currDate + "T" + addTime;
                    Data_URL += "&lastFetchTime=" + createDate;
                }
                else
                {
                    Data_URL_Principal += "org="+ UserData.getSchoolCode();
                }
                progressDialog.setMessage("Getting Notification...");

                progressDialog.show();
            }

            private boolean isExpire(String date){
                if(date.isEmpty() || date.trim().equals("")){
                    return false;
                }else{
                    SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd"); // Jan-20-2015 1:30:55 PM
                    Date d=null;
                    Date d1=null;
                    String today=   getToday("yyyy-MM-dd");
                    try {
                        //System.out.println("expdate>> "+date);
                        //System.out.println("today>> "+today+"\n\n");
                        d = sdf.parse(date);
                        d1 = sdf.parse(today);
                        if(d1.compareTo(d) <=0){// not expired
                            return false;
                       }
// else if(d.compareTo(d1)==0){// both date are same
//                            if(d.getTime() < d1.getTime()){// not expired
//                                return false;
//                            }else if(d.getTime() == d1.getTime()){//expired
//                                return true;
//                            }else{//expired
//                                return true;
//                            }
//                        }
                    else{//expired
                            return true;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return false;//though exception but better to show notification rather ignore
                    }
                }

            }


            public  String getToday(String format){
                Date date = new Date();
                return new SimpleDateFormat(format).format(date);
            }

            @Override
            protected void onPostExecute(String s) {

                if (s == null) {
                    progressDialog.dismiss();

                } else {
                    Object o = s;
                    JSONObject notificationobject = null;

                    try {
                        notificationobject = new JSONObject(o.toString());
                        final JSONArray notificationarray = notificationobject.getJSONArray("data");
                        for (int i = 0; i < notificationarray.length(); i++) {
                            JSONObject notify = notificationarray.getJSONObject(i);
                            String contentType = notify.optString("contentType");
                            String contentTitle = notify.optString("contentTitle");
                            String contentMsg = notify.optString("contentMsg");
                            String contentData;
                            if (!UserData.getUserType().contentEquals(CommonDetails.USER_TYPE_PRINCIPAL) &&
                                    contentType.contentEquals("QUESTION")) {
                                JSONObject data = notify.getJSONObject("contentData");
                                contentData = data.optString("questText");
                            }
                            else
                            {
                                contentData = notify.optString("contentData");
                            }
                            String startDate;
                            String expireDate;
                            String uniqueId;
                            notificationdata = new Studentnotificationmodel();
                            if (!UserData.getUserType().contentEquals(CommonDetails.USER_TYPE_PRINCIPAL)) {
                                uniqueId = notify.optString("userNotifyId");
                            }
                            else {
                                uniqueId = notify.optString("uniqueId");
                                startDate = notify.optString("startDate");
                                expireDate = notify.optString("expireDate");
                                notificationdata.setStartDate(startDate);
                                notificationdata.setExpireDate(expireDate);
                            }
                            //String notifyTargetUser = notify.optString("notifyTargetUser");
                            //String filter = notify.optString("filter");


                            notificationdata.setContentType(contentType);
                            notificationdata.setContentTitle(contentTitle);
                            notificationdata.setContentMsg(contentMsg);
                            notificationdata.setContentData(contentData);
                            notificationdata.setUniqueId(uniqueId);
                            //notificationdata.setNotifyTargetUser(notifyTargetUser);
                            //notificationdata.setFilter(filter);

                            //if(!isExpire(expireDate)) {
                                //notifcationlist.add(notificationdata);
                            //}
                            notifcationlist.add(notificationdata);

                        }


                        list = (ListView) findViewById(R.id.notificationlistView);
                        Collections.reverse(notifcationlist);
                        NotificationAdapter adap= new NotificationAdapter(ShowNotificationActivity.this,notifcationlist);
                        list.setAdapter(adap);

                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                               if(notifcationlist.get(position).getContentType().equals("QUESTION"))    {
                                   if(UserData.getUserType().equals("PRINCIPAL")){
                                       new SweetAlertDialog(ShowNotificationActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                               .setTitleText(notifcationlist.get(position).getContentTitle())
                                               .setContentText(notifcationlist.get(position).getContentData())

                                               .setConfirmText("Statistics")
                                               .showCancelButton(true)

                                               .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                   @Override
                                                   public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                       //Intent i=new Intent(ShowNotificationActivity.this,StatsActivity.class);
                                                       //i.putExtra("unique_id",notifcationlist.get(position).getUniqueId());
                                                       //startActivity(i);

                                                       GetNotificationStatsData data = new GetNotificationStatsData(null, notifcationlist.get(position).getUniqueId());
                                                       GetNotificationStatsRequest request = new GetNotificationStatsRequest
                                                               (ShowNotificationActivity.this, data, ShowNotificationActivity.this);
                                                       request.executeRequest();

                                                       sweetAlertDialog.cancel();
                                                   }
                                               })
                                               .show();
                                   }else if((UserData.getUserType().equals(CommonDetails.USER_TYPE_STUDENT))
                                           || (UserData.getUserType().equals(CommonDetails.USER_TYPE_PARENT))){
                                   new SweetAlertDialog(ShowNotificationActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                           .setTitleText("This will change your earlier vote \n"+notifcationlist.get(position).getContentTitle())
                                           .setContentText(notifcationlist.get(position).getContentData())
                                           .setCancelText("No")
                                           .setConfirmText("Sure")
                                           .showCancelButton(true)
                                           .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                               @Override
                                               public void onClick(SweetAlertDialog sDialog) {
                                                   sDialog.cancel();
                                                   ReplyNotificationData data = new ReplyNotificationData(
                                                           UserData.getAccessToken(),
                                                           notifcationlist.get(position).getUniqueId(),
                                                           CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_NO);
                                                   ReplyNotificationRequest req = new ReplyNotificationRequest(
                                                           ShowNotificationActivity.this, data,
                                                           ShowNotificationActivity.this);
                                                   req.executeRequest();
                                               }
                                           })
                                           .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                               @Override
                                               public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                   sweetAlertDialog.cancel();
                                                   ReplyNotificationData data = new ReplyNotificationData(
                                                           UserData.getAccessToken(),
                                                           notifcationlist.get(position).getUniqueId(),
                                                           CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_YES);
                                                   ReplyNotificationRequest req = new ReplyNotificationRequest(
                                                           ShowNotificationActivity.this, data,
                                                           ShowNotificationActivity.this);
                                                   req.executeRequest();
                                               }
                                           })
                                           .show();}
                               }else{
                                   new SweetAlertDialog(ShowNotificationActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                           .setTitleText(notifcationlist.get(position).getContentTitle())
                                           .setContentText(notifcationlist.get(position).getContentData())
                                           .show();

                               }
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    super.onPostExecute(s);

                    progressDialog.dismiss();


                }
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url;
                    if (UserData.getUserType().contentEquals(CommonDetails.USER_TYPE_PRINCIPAL)){
                        url = new URL(Data_URL_Principal);
                    }
                    else {
                        url = new URL(Data_URL);
                    }
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    String authorization = "bearer " + UserData.getAccessToken();
                    con.setRequestProperty("Authorization", authorization);
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                } catch (Exception e) {
                    Log.e("MYAPPPPPPP", "exception", e);

                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);


    }


    @Override
    public void onGetNotificationStatsResponse(CommonRequest.ResponseCode res, GetNotificationStatsData data) {
        //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this ,NoticeBoardStatics.class);
        String notificationId = data.getNotificationId();
        i.putExtra(getResources().getString(R.string.intent_extra_id_notification_selected),notificationId);
        startActivity(i);


    }

    @Override
    public void onReplyNotificationResponse(CommonRequest.ResponseCode res, ReplyNotificationData data) {
        if (res != CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            Toast.makeText(this, "Reply failed, please try later", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent it = new Intent(this, Dashboard.class);//Dashboard.class);
            startActivity(it);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}