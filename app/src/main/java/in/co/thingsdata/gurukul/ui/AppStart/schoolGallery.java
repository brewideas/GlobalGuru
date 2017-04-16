package in.co.thingsdata.gurukul.ui.AppStart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.ui.dataUi.CommonAdapter;
import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;
import in.co.thingsdata.gurukul.ui.dataUi.schoolGalleryModel;

public class schoolGallery extends AppCompatActivity {

    private RecyclerView mRecyclerView = null;
    private CommonAdapter mAdapter = null;
    static  final String TAG = "schoolGallery";

    private final Integer image_ids[] = {
            R.drawable.gal_1,
            R.drawable.gal_2,
            R.drawable.gal_3,
            R.drawable.gal_4,
            R.drawable.gal_5,
            R.drawable.gal_6,
            R.drawable.gal_7,
            R.drawable.gal_8,
            R.drawable.gal_9,
            R.drawable.gal_10,
            R.drawable.gal_11,
            R.drawable.gal_12,

    };

    private final String image_titles[] = {
            "Img1",
            "Img2",
            "Img3",
            "Img4",
            "Img5",
            "Img6",
            "Img7",
            "Img8",
            "Img9",
            "Img10",
            "Img11",
            "Img12"

    };

    public  List<DataOfUi> theimage = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_gallery);

        theimage = prepareData();
        mRecyclerView = (RecyclerView)findViewById(R.id.rcSchoolGallery);

        mAdapter = new CommonAdapter(theimage, CommonAdapter.SCHOOL_GALLERY
                , new CommonAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {



                ///list item was clicked
            }




        });





        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

   }

    private List<DataOfUi> prepareData(){

        for(int i = 0; i< image_titles.length; i++){
            schoolGalleryModel createList = new schoolGalleryModel();
            createList.setImage_title(image_titles[i]);
            createList.setImage_ID(image_ids[i]);
            theimage.add(createList);
        }
         return theimage;
    }
}


