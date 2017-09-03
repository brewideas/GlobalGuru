package in.co.thingsdata.gurukul.NoticeficationPanel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.CreateNotificationData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.CreateNotificationRequest;

import static in.co.thingsdata.gurukul.R.id.Submit;
import static in.co.thingsdata.gurukul.R.id.exdate;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.NotificationTypeEnum.NOTIFICATION_TYPE_NORMAL;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.NotificationTypeEnum.NOTIFICATION_TYPE_VOTE;


public class CreatenotificationActivity extends AppCompatActivity implements CreateNotificationRequest.CreateNotificationCallback {
    EditText title,  enddate, mDetails;
    //EditText startdate;
    Button submit;
    Calendar myCalendar;
    String radio;
    public static final int CONNECTION_TIMEOUT = 10000000;
    public static final int READ_TIMEOUT = 15000000;
    DatePickerDialog.OnDateSetListener date;
    RadioButton normal, optional;
    ArrayList<String> selClassesList;
    //ArrayList<String> selClassesSectionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnotification);

        try {
            String keyClassName = "selClass";
         //   String keyClassSection = "selSection";
            Bundle b = this.getIntent().getExtras();
            selClassesList = b.getStringArrayList(keyClassName);
         //   selClassesSectionList = b.getStringArrayList(keyClassSection);
        }catch(NullPointerException e){
            selClassesList = null;
            //selClassesSectionList = null;
        }
        mDetails= (EditText)findViewById(R.id.detail);
        title = (EditText) findViewById(R.id.addtitle);
        //startdate = (EditText) findViewById(R.id.daate);
        enddate = (EditText) findViewById(exdate);
        normal = (RadioButton) findViewById(R.id.radioButtonnormal);
        optional = (RadioButton) findViewById(R.id.radioButtonwithoption);
        myCalendar = Calendar.getInstance();
        submit = (Button) findViewById(Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNotification(v,false);
            }
        });
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel1();

            }

        };
//        startdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(CreatenotificationActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                updateLabel();
//            }
//        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(CreatenotificationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                optional.setChecked(false);

                radio = (String) normal.getText();
            }
        });
        optional.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                normal.setChecked(false);

                radio = (String) optional.getText();
            }
        });
    }




    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here//MM/dd/yy
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    //    startdate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        enddate.setText(sdf.format(myCalendar.getTime()));
    }

    private void CreateNotification (View v,boolean isSMS){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss.SSS");

        String addTime = df2.format(c.getTime());
        String currDate = df.format(c.getTime());
//        String createDate = startdate.getText().toString();// + "T" + currTime;
        String expiryDate = enddate.getText().toString() + "T" + "23:59:04.830";
        String createDate =currDate + "T" + addTime;
        CommonDetails.NotificationTypeEnum type = normal.isChecked()? NOTIFICATION_TYPE_NORMAL:NOTIFICATION_TYPE_VOTE;

        CreateNotificationData nd = null;
        String des = mDetails.getText().toString();
        if(selClassesList != null && selClassesList.size()>0) {
             nd = new CreateNotificationData(
                    UserData.getAccessToken(),
                    createDate,
                    expiryDate,
                    des,
                    title.getText().toString(),
                    selClassesList,
                    type,isSMS);
        }else{//To all
             nd = new CreateNotificationData(
                    UserData.getAccessToken(),
                    createDate,
                    expiryDate,
                    des,
                    title.getText().toString(),
                    "", type,isSMS);
        }
        CreateNotificationRequest request = new CreateNotificationRequest(this, nd, this);
        request.executeRequest();
    }

    @Override
    public void onCreateNotificationResponse(CommonRequest.ResponseCode res, CreateNotificationData data) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            Toast.makeText(this, "Notification created successfully", Toast.LENGTH_SHORT).show();
            Intent launchFeature = new Intent(this, ShowNotificationActivity.class);
            startActivity(launchFeature);
        }
        else
        {
            Toast.makeText(this, "Failed to create notification", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendViaSMS(View view) {

        CreateNotification(view,true);
    }

    public void cancelCreate(View view) {
        finish();
    }

  /*  private class AsyncLogin1 extends AsyncTask<String, Integer, String> {
        ProgressDialog pdLoading = new ProgressDialog(CreatenotificationActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://ec2-35-154-121-61.ap-south-1.compute.amazonaws.com:8080/notification-service/api/notification/data");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                try {
                    conn = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("name", "Abhinav")
                        .appendQueryParameter("filter", "2")
                        .appendQueryParameter("contentData", "ublic holiday on 13 March")
                        .appendQueryParameter("contentTitle", "Holi Holiday")
                        .appendQueryParameter("contentMsg", "Notification: Holiday")
                        .appendQueryParameter("respondToBack", "true")
                        .appendQueryParameter("contentType", "QUESTION")
                        .appendQueryParameter("questText", "Is School is going to close on monday ?")
                        .appendQueryParameter("options", "y")
                        .appendQueryParameter("notifyTargetUser", "ALL")
                        .appendQueryParameter("startDate", "2017-02-28T08:48:35.479")
                        .appendQueryParameter("expireDate", "2017-02-28T08:48:35.479")
                        .appendQueryParameter("notifyUserSpecificData", "ALL");


                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            Toast.makeText(CreatenotificationActivity.this, result, Toast.LENGTH_SHORT).show();
            System.out.println("errorrrr" + result);
            Object o = result;


            pdLoading.dismiss();


            if (result.equalsIgnoreCase("true")) {
                *//* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 *//*


            } else if (result.equalsIgnoreCase("false")) {

                // If username and password does not match display a error message


            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {


            }
        }


    }*/

}
