package in.co.thingsdata.gurukul.NoticeficationPanel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import in.co.thingsdata.gurukul.Adapters.NotificationAdapter;
import in.co.thingsdata.gurukul.Adapters.StatNotificationAdapter;
import in.co.thingsdata.gurukul.Models.Statsmodel;
import in.co.thingsdata.gurukul.Models.Studentnotificationmodel;
import in.co.thingsdata.gurukul.R;

public class StatsActivity extends AppCompatActivity {
    Statsmodel stats;
    ArrayList<Statsmodel> notifcationlist = new ArrayList<>();

    private Intent intent;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        intent = getIntent();
        final String uid = intent.getStringExtra("unique_id");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, uid, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Notification(uid);
            }
        });
    }

    private void Notification(String filter) {
        String urlSuffix = "?id=" + filter;

        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog progressDialog = new ProgressDialog(StatsActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Getting Notification...");

                progressDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                s = " {\"message\":\"data for  Notification Response \",\"status\":1," +
                        "\"data\":[{\"answer\":\"Y\",\"userId\":\"1212\",\"remark\":\"true\"," +
                        "\"statusNotify\":\"STATUS_NOTIFY_RECEIVED_BY_DEVICE\"," +
                        "\"sendDateTime\":\"2017-02-26T09:42:20.501\",\"respondTime\":" +
                        "\"2017-02-27T16:40:10.081\"}]}";

                System.out.println("eeeeeeeeeeeeeeeeee" + s);
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
                            String contentType = notify.optString("answer");
                            String id = notify.optString("userId");
                            stats = new Statsmodel();
                            stats.setAnswer(contentType);
                            stats.setUser1d(id);
                            notifcationlist.add(stats);

                        }

                        list = (ListView) findViewById(R.id.statslistView);
                        StatNotificationAdapter adap= new StatNotificationAdapter(StatsActivity.this,notifcationlist);
                        list.setAdapter(adap);

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
                    URL url = new URL("" + s);
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
