package in.co.thingsdata.gurukul.ui.AppStart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetAppVersionRequest;
import in.co.thingsdata.gurukul.services.request.GetClassListRequest;
import in.co.thingsdata.gurukul.ui.Fees.FeesDetails;

public class MainActivity extends AppCompatActivity implements GetClassListRequest.GetClassListCallback , GetAppVersionRequest.GetAppVersionCallback{

    EditText email, password;
    private Handler handler;
    boolean isUserLoggedIn = false;

    public static final String OPEN_GALLERY_FOR = "Open_gallery _for_which_act";
    public static final String STUDENT_PROFILE_CROPED_NAME = "profileImg";

    public static int mMajorVersion = -1;
    public static int mMinorVersion = -1;
    public static String mVersionMsg = null;
    final String VersionOfApplicationKey = "in.co.thingsdata.gurukul.MajorVersion";
    public static final String MAJOR_VERSION = "Major Version";
    public static final String MINOR_VERSION = "Minor Version";

    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserData.setContext(getApplicationContext());
        setContentView(R.layout.activity_main);

        getSavedVersionOfApplication();

        boolean temp = false;
        if(temp == true) {
            Intent start = new Intent(this, FeesDetails.class);
            startActivity(start);
        }else {
            if (UserData.isUserAlreadyLoggedIn()) {
                isUserLoggedIn = true;
                UserData.init();

//          Runnable rn = new Runnable() {
//                @Override
//                public void run() {
//                    GetClassListRequest req = new GetClassListRequest(MainActivity.this, MainActivity.this);
//                    req.executeRequest();
//                }
//            };
//            Thread thCLreqm = new Thread(rn);
//            thCLreqm.run();
//
//        }

            } else {
                isUserLoggedIn = false;
            }
            GetClassListRequest req = new GetClassListRequest(MainActivity.this, MainActivity.this);
            req.executeRequest();

            GetAppVersionRequest resVersion = new GetAppVersionRequest(this, this);
            resVersion.executeRequest();
        }
  }

  void launchApplication(){

      if (isUserLoggedIn == true){

                  Intent it = new Intent(MainActivity.this, Dashboard.class);//Dashboard.class);
                  startActivity(it);
      }else {
                  Intent it = new Intent(MainActivity.this, LoginActivity.class);//Dashboard.class);
                  startActivity(it);
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
            isUserLoggedIn = false;
            //if (res == CommonRequest.ResponseCode.COMMON_RES_PROFILE_AUTHENTICATION_FAILED)
                UserData.clearAll();
            Toast.makeText(this, "Session expired, please login again", Toast.LENGTH_LONG).show();
        }
//        handler = new Handler();
//        final Runnable r = new Runnable() {
//            public void run() {
//                if (UserData.isUserAlreadyLoggedIn()) {
//                    Intent it = new Intent(MainActivity.this, Dashboard.class);//Dashboard.class);
//                    startActivity(it);
//                }else {
//                    Intent it = new Intent(MainActivity.this, LoginActivity.class);//Dashboard.class);
//                    startActivity(it);
//                }
//
//            }
//        };
//        handler.postDelayed(r, 500);
    }


    @Override
    public void onGetAppVersionResponse(CommonRequest.ResponseCode res,String url, String msg, int majorVersion, int minorVersion) {

        if(CommonRequest.ResponseCode.COMMON_RES_SUCCESS == res){

            mVersionMsg = msg;
            Log.d("MainActivity","Major Version" + majorVersion);

            if(mMajorVersion == -1){

                mMajorVersion = majorVersion;
                mMinorVersion = minorVersion;
                setSavedVersionOfApplication(MAJOR_VERSION,majorVersion);
                setSavedVersionOfApplication(MINOR_VERSION,minorVersion);

                launchApplication();
            }else {
                updateApplication(msg, url, majorVersion, minorVersion);
            }
        }else{
            Log.d("MainActivity", "Error Version");
            launchApplication();//if there is version request issue . we can stop user from using app
        }
    }

    public void openWebPage(String url){
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    public void updateApplication(String msg, String updateUrlarg,final int majorVersion, int minorVersion){

        final String updateUrl = updateUrlarg;
        final String updateMsg = msg;

        if(majorVersion != mMajorVersion || minorVersion != mMinorVersion) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);


            alert.setMessage(updateMsg + "\nPress Ok to proceed");
            alert.setTitle("Update Application");


            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                      openWebPage(updateUrl);
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    int presentVersion = 0;//get major version from shared preference
                    if (majorVersion > presentVersion) {
                        Toast.makeText(MainActivity.this, "Update to new Version is must .", Toast.LENGTH_LONG).show();
                        finish();
                        System.exit(0);
                    } else { //if it's only minor version difference it is OK to launch application
                        launchApplication();
                    }

                }
            });

            alert.show();
        }else{
            launchApplication();
        }
    }

    void setSavedVersionOfApplication(String key,int int_value){
            SharedPreferences sharedPref = this.getSharedPreferences(VersionOfApplicationKey, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(key, int_value);
            editor.commit();
    }

    public void getSavedVersionOfApplication(){
        SharedPreferences sharedPref = getSharedPreferences(VersionOfApplicationKey, this.MODE_PRIVATE);
        mMajorVersion = sharedPref.getInt(MAJOR_VERSION, -1);
        mMinorVersion = sharedPref.getInt(MINOR_VERSION, -1);


    }
}
