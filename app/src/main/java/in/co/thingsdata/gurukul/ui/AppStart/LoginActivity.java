package in.co.thingsdata.gurukul.ui.AppStart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.LoginData;
import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetClassListRequest;
import in.co.thingsdata.gurukul.services.request.LoginRequest;

public class LoginActivity extends AppCompatActivity implements GetClassListRequest.GetClassListCallback, LoginRequest.LoginResponseCallback {
    EditText mLoginId;
    EditText mPassword;
    private Handler mHandler;
    static SweetAlertDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginId = (EditText) findViewById(R.id.input_username);
        mPassword = (EditText) findViewById(R.id.input_password);
    }

    public void login(View v) {
/*        if (mLoginId.getText().length() == 0) {
            mLoginId.setError("Please enter your email");
        }
        if (mPassword.getText().length() == 0) {
            mPassword.setError("Please enter password");
        } else {
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
            mHandler = new Handler();

            final Runnable r = new Runnable() {
                public void run() {
                    Intent it = new Intent(LoginActivity.this, Dashboard.class);
                    initializeUserData();
                    startActivity(it);

                }
            };

            mHandler.postDelayed(r, 2000);

        }*/
        LoginData data = new LoginData(mLoginId.getText().toString(), mPassword.getText().toString());
        LoginRequest req = new LoginRequest(this, data, this);
        req.executeRequest();

        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("Loading");
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void openSignUp(View v) {
        Intent it = new Intent(LoginActivity.this, SignUp.class);
        startActivity(it);
    }

    public void forgetPassword(View v) {
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
            Intent it = new Intent(LoginActivity.this, Dashboard.class);
            startActivity(it);
            mDialog.dismiss();
            finish();
        }
        else{
            mDialog.dismiss();
            Toast.makeText(this, "Request failed. Please try later", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoginResponse(CommonRequest.ResponseCode res, LoginData data) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            initializeUserData();
        }
        else
        {
            Toast.makeText(this, "Login Failed, Invalid username or password.", Toast.LENGTH_SHORT).show();
            mDialog.dismiss();
        }
    }
}
