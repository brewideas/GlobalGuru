package in.co.thingsdata.gurukul.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetClassListRequest;

public class MainActivity extends AppCompatActivity implements GetClassListRequest.GetClassListCallback {

    EditText email, password;
    private Handler handler;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        email = (EditText) findViewById(R.id.loginid);
//        password = (EditText) findViewById(R.id.password);
//        signup = (TextView) findViewById(R.id.signup);

        handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Intent it = new Intent(MainActivity.this, Dashboard.class);
                initializeUserData();
                startActivity(it);

            }
        };

        handler.postDelayed(r, 2000);


    }

    public void dashboard(View v) {
        if (email.getText().length() == 0) {
            email.setError("Please enter your email");
        }
        if (password.getText().length() == 0) {
            password.setError("Please enter password");
        } else {
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
            handler = new Handler();

            final Runnable r = new Runnable() {
                public void run() {
                    Intent it = new Intent(MainActivity.this, Dashboard.class);
                    initializeUserData();
                    startActivity(it);

                }
            };

            handler.postDelayed(r, 2000);

        }
    }


    public void initializeUserData() {
        GetClassListRequest req = new GetClassListRequest(this, this);
        req.executeRequest();
    }

    @Override
    public void onGetClassListResponse(CommonRequest.ResponseCode res, ArrayList<ClassData> classes) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            for (int i = 0; i < classes.size(); i++) {
                CommonDetails.addClass(classes.get(i));
            }
        }
    }
}
