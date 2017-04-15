package in.co.thingsdata.gurukul.ui.AppStart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import in.co.thingsdata.gurukul.NoticeficationPanel.ShowNotificationActivity;
import in.co.thingsdata.gurukul.R;
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
}

