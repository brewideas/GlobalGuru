package in.co.thingsdata.gurukul.ui.AppStart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetUserDetailsData;
import in.co.thingsdata.gurukul.data.LoginData;
import in.co.thingsdata.gurukul.data.SignUpData;
import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetClassListRequest;
import in.co.thingsdata.gurukul.services.request.GetUserDetailRequest;
import in.co.thingsdata.gurukul.services.request.LoginRequest;
import in.co.thingsdata.gurukul.services.request.SignUpRequest;
import in.co.thingsdata.gurukul.ui.common.ImageCropped;

import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_TYPE_STUDENT;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_TYPE_TEACHER;

public class SignUp extends AppCompatActivity implements SignUpRequest.SignUpCallback, GetClassListRequest.GetClassListCallback,
        LoginRequest.LoginResponseCallback, GetUserDetailRequest.GetUserDetailResponse {

    EditText mUniqueId, mEmailId, mPassword;
    RadioButton mTeacher, mStudent;
    String mUserType;
    RelativeLayout mUploadImageRl,mUploadImageR2;
    ImageButton profileImg;
    static SweetAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUploadImageRl = (RelativeLayout) findViewById(R.id.uploadImageRl);
        mUploadImageR2 = (RelativeLayout) findViewById(R.id.profile);

        mUniqueId = (EditText) findViewById(R.id.unique_id);
        mEmailId = (EditText) findViewById(R.id.login_id);
        mPassword = (EditText) findViewById(R.id.password);

        mStudent = (RadioButton) findViewById(R.id.rd_btn_parent);
        mTeacher = (RadioButton) findViewById(R.id.rd_btn_teacher);
    }

    public void signup(View v) {

        SignUpData sd = new SignUpData(mUniqueId.getText().toString(),
                mEmailId.getText().toString(), mPassword.getText().toString(), mUserType);
        SignUpRequest req = new SignUpRequest(this, sd, this);
        req.executeRequest();

        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("Loading");
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void onSignUpResponse(CommonRequest.ResponseCode res, SignUpData data) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){

            LoginData l_data = new LoginData(data.getLoginId(), data.getPassword());
            LoginRequest req = new LoginRequest(this, l_data, this);
            req.executeRequest();
        }
        else{
            mDialog.dismiss();
            if (res != CommonRequest.ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE){
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, data.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void teacherSelected (View v){
        mUserType = USER_TYPE_TEACHER;
        mStudent.setChecked(false);
    }

    public void parentSelected (View v){
        mUserType = USER_TYPE_STUDENT;
        mTeacher.setChecked(false);
    }

    public void uploadToCreateProfile(View view) {
        Intent inf = new Intent(this, ImageCropped.class);
        inf.putExtra(MainActivity.OPEN_GALLERY_FOR, MainActivity.STUDENT_PROFILE_CROPED_NAME);
        startActivity(inf);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        showImageForBackround();
    }

    private void showImageForBackround(){
        Log.v("SignUp", "showImageCampaign");
        try {
            FileInputStream in = openFileInput(MainActivity.STUDENT_PROFILE_CROPED_NAME);

            mUploadImageRl.setVisibility(View.GONE);
            mUploadImageR2.setVisibility(View.VISIBLE);

            profileImg = (ImageButton)findViewById(R.id.profileBtn);

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            profileImg.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("editCampaign","Blog_TemplateImage_IMAGE_CROPED_NAME file not found");
        }

    }

    @Override
    public void onGetClassListResponse(CommonRequest.ResponseCode res, ArrayList<ClassData> classes) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            for (int i = 0; i < classes.size(); i++) {
                CommonDetails.addClass(classes.get(i));

                UserData.setUserDataReady(true);
                Intent it = new Intent(this, Dashboard.class);
                startActivity(it);
                mDialog.dismiss();
                finish();
            }

        }
        else{
            mDialog.dismiss();
            Toast.makeText(this, "Some problem while accessing server for user data, please try later.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetUserDetailResponse(CommonRequest.ResponseCode res, GetUserDetailsData data) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            UserData.setClassRoomId(data.getClassRoomId());
            UserData.setUserType(data.getUserType());
            UserData.setMobileNumber(data.getMobileNumber());
            UserData.setSchoolCode(data.getSchoolCode());
            UserData.setEmailId(data.getEmail());
            UserData.setLoginId(data.getMobileNumber());
            UserData.setMobileNumber(data.getMobileNumber());
            UserData.setUserId(data.getUserId());
            UserData.setUniqueId(data.getReferenceCode());

            GetClassListRequest req = new GetClassListRequest(this, this);
            req.executeRequest();
        }
        else
        {
            Toast.makeText(this, "Some problem while accessing server for user data, please try later.", Toast.LENGTH_SHORT).show();
            mDialog.dismiss();
        }
    }

    @Override
    public void onLoginResponse(CommonRequest.ResponseCode res, LoginData data) {
        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){
            UserData.setAccessToken(data.getAccessToken());

            GetUserDetailsData d_data = new GetUserDetailsData(UserData.getAccessToken());
            GetUserDetailRequest req = new GetUserDetailRequest(this, d_data, this);
            req.executeRequest();
        }
        else
        {
            mDialog.dismiss();
            Toast.makeText(this, "Sign-up Failed, Invalid username.", Toast.LENGTH_SHORT).show();
        }
    }
}
