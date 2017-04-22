package in.co.thingsdata.gurukul.ui.NoticeBoard;

import android.content.Intent;
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

import in.co.thingsdata.gurukul.NoticeficationPanel.CreatenotificationActivity;
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

    private Button btnSelection,btnSelAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nb_select_class);

        btnSelection = (Button) findViewById(R.id.btnShow);
        btnSelAll = (Button) findViewById(R.id.btnSelectall);
        classList = new ArrayList<NoticeBoardModel>();
        ArrayList<ClassData> clsData = CommonDetails.getAllClassesInSchool();
        for(ClassData obj: clsData){
            NoticeBoardModel st = new NoticeBoardModel(obj.getName(),obj.getSection(), false);
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

        btnSelAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(selectClass.this, CreatenotificationActivity.class);
                startActivity(i);
            }
        });

        btnSelection.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = "";
                ArrayList<String> classesSel = new ArrayList<String>();
                ArrayList<String> classesSection = new ArrayList<String>();

                List<NoticeBoardModel> clsList = ((NoticeBoardDataAdapter) mAdapter)
                        .getClassList();

                for (int i = 0; i < clsList.size(); i++) {
                    NoticeBoardModel singleClass = clsList.get(i);
                    if (singleClass.isSelected() == true) {
                        String cls = singleClass.getClassName().toString();
                        String sec = singleClass.getClassSection().toString();
                        data = data + "\n" + cls;
                        NoticeBoardStaticData.setSelectedClassList(singleClass);
                        classesSel.add(cls);
                        classesSection.add(sec);
                    }

                }

                if(classesSel.size() <=0){
                    Toast.makeText(selectClass.this,
                            "Please select atLeast 1 class", Toast.LENGTH_SHORT)
                            .show();
                }else {

                    Toast.makeText(selectClass.this,
                            "Selected Class: \n" + data, Toast.LENGTH_SHORT)
                            .show();

                    Bundle bnd = new Bundle();
                    bnd.putStringArrayList("selClass", classesSel);
                    bnd.putStringArrayList("selSection", classesSection);
                    Intent i = new Intent(selectClass.this, CreatenotificationActivity.class);
                    i.putExtras(bnd);
                    startActivity(i);
                }
            }
        });




    }

}
