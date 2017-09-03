package in.co.thingsdata.gurukul.ui.Gallery;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.PopupWindow;

import in.co.thingsdata.gurukul.R;

/**
 * Created by Vikas on 6/22/2017.
 */

public class GalleryImageViewActivity extends FragmentActivity {

    ViewPager mPager;
    int mCurrPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageViewPagerAdapter mAdapter;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery_image_viewer);

        Bundle extras = getIntent().getExtras();
        mCurrPosition = extras.getInt("Index");

        mPager = (ViewPager) findViewById(R.id.learnPager);
        mAdapter = new ImageViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPager.setCurrentItem(mCurrPosition);
            }
        }, 100);
    }
}
