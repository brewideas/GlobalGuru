package in.co.thingsdata.gurukul.ui.AppStart;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.ui.common.OnBoarding;

public class BeforeLoginScreen extends AppCompatActivity {

    Fragment onBoardingObj = new OnBoarding();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_before_login_screen);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
  //      setSupportActionBar(toolbar);

        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.mainRLayout, onBoardingObj);
        transaction.commit();
    }


    public void showSignUpScreen(View view) {

        Intent launchFeature = new Intent(this, SignUp.class);
        startActivity(launchFeature);

    }

    public void showLoginScreen(View view) {
        Intent launchFeature = new Intent(this, LoginActivity.class);
        startActivity(launchFeature);
    }
}
