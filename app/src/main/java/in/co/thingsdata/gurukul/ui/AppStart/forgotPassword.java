package in.co.thingsdata.gurukul.ui.AppStart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.ChangePasswordData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.ChangePasswordRequest;

public class forgotPassword extends AppCompatActivity implements ChangePasswordRequest.ChangePasswordCallback {

    String mobNum = null,otp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        Intent i = getIntent();
        mobNum = i.getStringExtra("mobileNum");
        otp = i.getStringExtra("otpForgot");

    }

    public void SumbmitNewPassword(View view) {

        EditText pas1,pas2;

        try {
            //otp = (EditText)findViewById(R.id.input_otp);
            pas1 = (EditText) findViewById(R.id.input_pswrd);
            pas2 = (EditText) findViewById(R.id.reenter_pswrd);

            //String otptext = otp.getText().toString();
            String pas1text = pas1.getText().toString();
            String pas2text = pas2.getText().toString();

            if (pas1text.equals(pas2text) == false) {

                Toast.makeText(forgotPassword.this, "Reentered password did not match", Toast.LENGTH_LONG);

            } else {
                ChangePasswordData data = new ChangePasswordData(otp,mobNum, pas2text);
                //make request to server to change password
                ChangePasswordRequest request = new ChangePasswordRequest(forgotPassword.this,data,forgotPassword.this);
                request.executeRequest();
            }
        }catch (NullPointerException e){
            Toast.makeText(forgotPassword.this, "Please enter correct data", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onChangePasswordResponse(CommonRequest.ResponseCode res, ChangePasswordData data) {

        if (res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS) {
            Toast.makeText(forgotPassword.this, "Now login with new password", Toast.LENGTH_LONG);

            Intent it = new Intent(this, LoginActivity.class);//Dashboard.class);
            startActivity(it);


        }else{
            Toast.makeText(forgotPassword.this, "Sorry Password change failure . Please try later", Toast.LENGTH_LONG);
        }
    }
}
