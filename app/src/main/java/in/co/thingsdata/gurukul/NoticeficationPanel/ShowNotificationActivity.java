package in.co.thingsdata.gurukul.NoticeficationPanel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.co.thingsdata.gurukul.Adapters.NotificationAdapter;
import in.co.thingsdata.gurukul.Models.Studentnotificationmodel;
import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.ui.NoticeBoard.selectClass;


public class ShowNotificationActivity extends AppCompatActivity {
    Studentnotificationmodel notificationdata;
    ArrayList<Studentnotificationmodel> notifcationlist = new ArrayList<>();
    private String Data_URL = "http://ec2-35-154-121-61.ap-south-1.compute.amazonaws.com:8080/notification-service/api/notification/data/search";
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);
        Notification("2");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(ShowNotificationActivity.this,selectClass.class);
                startActivity(i);

            }
        });
    }

    private void Notification(String filter) {
        String urlSuffix = "?filter=" + filter;

        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog progressDialog = new ProgressDialog(ShowNotificationActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Getting Notification...");

                progressDialog.show();
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
                            String contentData = notify.optString("contentData");
                            String startDate = notify.optString("startDate");
                            String expireDate = notify.optString("expireDate");
                            String uniqueId = notify.optString("uniqueId");
                            String notifyTargetUser = notify.optString("notifyTargetUser");
                            String filter = notify.optString("filter");

                            notificationdata = new Studentnotificationmodel();
                            notificationdata.setContentType(contentType);
                            notificationdata.setContentTitle(contentTitle);
                            notificationdata.setContentMsg(contentMsg);
                            notificationdata.setContentData(contentData);
                            notificationdata.setStartDate(startDate);
                            notificationdata.setExpireDate(expireDate);
                            notificationdata.setUniqueId(uniqueId);
                            notificationdata.setNotifyTargetUser(notifyTargetUser);
                            notificationdata.setFilter(filter);
                            notifcationlist.add(notificationdata);

                        }


                        list = (ListView) findViewById(R.id.notificationlistView);
                        NotificationAdapter adap= new NotificationAdapter(ShowNotificationActivity.this,notifcationlist);
                        list.setAdapter(adap);

                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                               if(notifcationlist.get(position).getContentType().equals("QUESTION"))    {
                                   if(UserData.getUserType().equals("PRINCIPAL")){
                                       new SweetAlertDialog(ShowNotificationActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                               .setTitleText(notifcationlist.get(position).getContentTitle())
                                               .setContentText(notifcationlist.get(position).getContentMsg())

                                               .setConfirmText("Statistics")
                                               .showCancelButton(true)

                                               .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                   @Override
                                                   public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                       Intent i=new Intent(ShowNotificationActivity.this,StatsActivity.class);
                                                       i.putExtra("unique_id",notifcationlist.get(position).getUniqueId());
                                                       startActivity(i);

                                                       sweetAlertDialog.cancel();
                                                   }
                                               })
                                               .show();
                                   }else{
                                   new SweetAlertDialog(ShowNotificationActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                           .setTitleText(notifcationlist.get(position).getContentTitle())
                                           .setContentText(notifcationlist.get(position).getContentMsg())
                                           .setCancelText("No")
                                           .setConfirmText("Sure")
                                           .showCancelButton(true)
                                           .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                               @Override
                                               public void onClick(SweetAlertDialog sDialog) {
                                                   sDialog.cancel();
                                               }
                                           })
                                           .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                               @Override
                                               public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                   sweetAlertDialog.cancel();
                                               }
                                           })
                                           .show();}
                               }else{
                                   new SweetAlertDialog(ShowNotificationActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                           .setTitleText(notifcationlist.get(position).getContentTitle())
                                           .setContentText(notifcationlist.get(position).getContentMsg())
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
                    URL url = new URL(Data_URL + s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
}