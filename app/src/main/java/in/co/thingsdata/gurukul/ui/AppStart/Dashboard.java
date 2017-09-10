package in.co.thingsdata.gurukul.ui.AppStart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import in.co.thingsdata.gurukul.NoticeficationPanel.ShowNotificationActivity;
import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.ui.Fees.FeesDetails;
import in.co.thingsdata.gurukul.ui.Fees.FeesProfileNonEditable;
import in.co.thingsdata.gurukul.ui.Gallery.schoolGallery;
import in.co.thingsdata.gurukul.ui.Homework.Showhomework;
import in.co.thingsdata.gurukul.ui.ReportCardUi.ReportCardTeacherView;

public class Dashboard extends AppCompatActivity {

    private AdView mAdView;

    void initRes(){
         mAdView = (AdView)findViewById(R.id.adViewBannerDAshBoard);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ScrollView rlayout = (ScrollView) findViewById(R.id.screenlayout);
        rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }

        });
        initRes();

        try{
        AdRequest adRequest = new   AdRequest.Builder().build();

        if((UserData.getUserType().equals(CommonDetails.USER_TYPE_PRINCIPAL)) ||
                (UserData.getUserType().equals(CommonDetails.USER_TYPE_TEACHER))) {
            mAdView.setVisibility(View.GONE);
        }else{
            mAdView.loadAd(adRequest);
        }
        }catch (NullPointerException e){
            Log.v("Dashboard","NullPointerException addview"+e);
        }
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    public void launchReportCard(View view) {

        Intent launchFeature = new Intent(this, ReportCardTeacherView.class);
        startActivity(launchFeature);

    }

    public void launchNoticeBoard(View view) {
       /* Intent launchFeature = new Intent(this, NotesList.class);
        startActivity(launchFeature);*/
        Intent launchFeature = new Intent(this, ShowNotificationActivity.class);
        startActivity(launchFeature);
    }

    public void launchSchool(View view) {
        Intent launchFeature = new Intent(this, BeforeLoginScreen.class);
        startActivity(launchFeature);
    }

    public void launchMyProfile(View view) {
        Intent launchFeature = new Intent(this, MyProfile.class);
        startActivity(launchFeature);


    }

    public void launchGallery(View view) {
        Intent launchFeature = new Intent(this, schoolGallery.class);
        startActivity(launchFeature);

    }

    public void launchFees(View view) {
        Intent launchFeature;
        if(UserData.getUserType() == CommonDetails.USER_TYPE_PRINCIPAL){
            launchFeature = new Intent(this, FeesDetails.class);

        }else{
            launchFeature = new Intent(Dashboard.this, FeesProfileNonEditable.class);
        }
        startActivity(launchFeature);
    }

    public void LaunchHomeWork(View view) {
        Intent launchFeature = new Intent(this, Showhomework.class);
        startActivity(launchFeature);
    }
}

