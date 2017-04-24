package in.co.thingsdata.gurukul.ui.AppStart;

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
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetClassListRequest;

public class MainActivity extends AppCompatActivity implements GetClassListRequest.GetClassListCallback {

    EditText email, password;
    private Handler handler;
    TextView signup;

    public static final String OPEN_GALLERY_FOR = "Open_gallery _for_which_act";
    public static final String STUDENT_PROFILE_CROPED_NAME = "profileImg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserData.setContext(getApplicationContext());
        if (UserData.isUserAlreadyLoggedIn()){
            UserData.init();
            GetClassListRequest req = new GetClassListRequest(this, this);
            req.executeRequest();
        }

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
                    startActivity(it);

                }
            };

            handler.postDelayed(r, 2000);

        }
    }

    @Override
    public void onGetClassListResponse(CommonRequest.ResponseCode res, ArrayList<ClassData> classes) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            for (int i = 0; i < classes.size(); i++) {
                CommonDetails.addClass(classes.get(i));
            }
        }
        else {
            if (res == CommonRequest.ResponseCode.COMMON_RES_PROFILE_AUTHENTICATION_FAILED)
            UserData.clearAll();
        }
        handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                if (UserData.isUserAlreadyLoggedIn()) {
                    Intent it = new Intent(MainActivity.this, Dashboard.class);//Dashboard.class);
                    startActivity(it);
                }else {
                    Intent it = new Intent(MainActivity.this, LoginActivity.class);//Dashboard.class);
                    startActivity(it);
                }

            }
        };
        handler.postDelayed(r, 500);
    }
}
