package in.co.thingsdata.gurukul.ui.NoticeBoard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.ui.dataUi.NoticeBoardModel;
import in.co.thingsdata.gurukul.ui.dataUi.NoticeBoardStaticData;

public class selectClass extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<NoticeBoardModel> classList;

    private Button btnSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nb_select_class);

        btnSelection = (Button) findViewById(R.id.btnShow);
        classList = new ArrayList<NoticeBoardModel>();
        ArrayList<ClassData> clsData = CommonDetails.getAllClassesInSchool();
        for(ClassData obj: clsData){
            NoticeBoardModel st = new NoticeBoardModel(obj.getClassRoomId(), false);
            classList.add(st);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an Object for Adapter
        mAdapter = new NoticeBoardDataAdapter(classList);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        btnSelection.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = "";
                List<NoticeBoardModel> clsList = ((NoticeBoardDataAdapter) mAdapter)
                        .getClassList();

                for (int i = 0; i < clsList.size(); i++) {
                    NoticeBoardModel singleClass = clsList.get(i);
                    if (singleClass.isSelected() == true) {

                        data = data + "\n" + singleClass.getClassName().toString();
                        NoticeBoardStaticData.setSelectedClassList(singleClass);
                    }

                }

                Toast.makeText(selectClass.this,
                        "Selected Class: \n" + data, Toast.LENGTH_LONG)
                        .show();
            }
        });




    }

}
