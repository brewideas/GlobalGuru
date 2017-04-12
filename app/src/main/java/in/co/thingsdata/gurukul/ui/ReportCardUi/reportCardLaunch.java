package in.co.thingsdata.gurukul.ui.ReportCardUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import in.co.thingsdata.gurukul.R;

public class reportCardLaunch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_launch);

/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void findView(View view) {

        Intent launchFeature = new Intent(this, ReportCardSingleStudent.class);
        startActivity(launchFeature);
    }

    public void uploadView(View view) {

        Intent launchFeature = new Intent(this, ReportCardTeacherView.class);
        startActivity(launchFeature);

    }
}
