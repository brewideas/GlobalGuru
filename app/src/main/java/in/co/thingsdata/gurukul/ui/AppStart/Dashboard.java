package in.co.thingsdata.gurukul.ui.AppStart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import in.co.thingsdata.gurukul.NoticeficationPanel.ShowNotificationActivity;
import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.ui.Fees.FeesDetails;
import in.co.thingsdata.gurukul.ui.Fees.FeesProfile;
import in.co.thingsdata.gurukul.ui.ReportCardUi.ReportCardTeacherView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
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

        if(UserData.getUserType() == CommonDetails.USER_TYPE_PRINCIPAL) {
            Intent launchFeature = new Intent(this, FeesDetails.class);
            startActivity(launchFeature);
        }else{
            //Need to make request for profile
            Intent launchFeature = new Intent(this, FeesProfile.class);
            startActivity(launchFeature);
        }


    }
}

