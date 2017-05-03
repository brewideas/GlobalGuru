package in.co.thingsdata.gurukul.ui.AppStart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.common.UserData;

public class MyProfile extends AppCompatActivity {

    ImageButton proImg ;
    TextView tvprofileName,tvprofileClass,tvprofileRolNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        initRes();


        proImg = (ImageButton)findViewById(R.id.profileBtn);
        showImageForBackround();
      }

    void initRes(){

        try {
            tvprofileName = (TextView) findViewById(R.id.profileName);
            String userName = UserData.getFirstName();
            String userClass = Integer.toString(UserData.getClassId());
            String userRolNum = Integer.toString(UserData.getRollNumber());
            tvprofileName.setText(userName);
            tvprofileClass = (TextView) findViewById(R.id.profileClass);
            tvprofileClass.setText(userClass);
            tvprofileRolNum = (TextView) findViewById(R.id.profileRolNum);
            tvprofileRolNum.setText(userRolNum);
        }catch(Exception e){
            Toast.makeText(MyProfile.this, "Profile data not available as this moment , Please check again", Toast.LENGTH_SHORT).show();
        }
    }

    private void showImageForBackround(){
        Log.v("editCampaign", "showImageCampaign");
        try {
            FileInputStream in = openFileInput(MainActivity.STUDENT_PROFILE_CROPED_NAME);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            proImg.setImageDrawable(new BitmapDrawable(getResources(), bitmap));


        }catch (FileNotFoundException e){
            Log.v("MyProfile","Profile file not found");
        }

    }


    public void enterTemPassword(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(MyProfile.this);
        alert.setMessage("Please enter old Password");
        alert.setTitle("Enter Temporary Password");

        alert.setView(edittext);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String otp = edittext.getText().toString();
                //send mobileNum
                String mobileNum = UserData.getMobileNumber();
                Intent i = new Intent(MyProfile.this,forgotPassword.class);
                i.putExtra("mobileNum",mobileNum);
                i.putExtra("otpForgot",otp);
                startActivity(i);

            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(MyProfile.this, "Pasword not changed", Toast.LENGTH_SHORT).show();

            }
        });

        alert.show();


    }



    public void finishProfile(View view) {
        finish();
    }

    public void changePassword(View view) {
        enterTemPassword();
    }
}
