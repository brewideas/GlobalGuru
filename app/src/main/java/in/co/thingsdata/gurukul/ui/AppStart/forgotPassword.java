package in.co.thingsdata.gurukul.ui.AppStart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import in.co.thingsdata.gurukul.R;

public class forgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

    }

    public void SumbmitNewPassword(View view) {

        EditText otp,pas1,pas2;

        otp = (EditText)findViewById(R.id.input_otp);
        pas1 = (EditText)findViewById(R.id.input_pswrd);
        pas2 = (EditText)findViewById(R.id.reenter_pswrd);

        String otptext = otp.getText().toString();
        String pas1text = pas1.getText().toString();
        String pas2text = pas2.getText().toString();

        if(pas1text.equals(pas2text) == false){

            Toast.makeText(forgotPassword.this,"Reentered password did not matched",Toast.LENGTH_LONG);

        }else{

            //make request to server to change password
        }




    }
}
