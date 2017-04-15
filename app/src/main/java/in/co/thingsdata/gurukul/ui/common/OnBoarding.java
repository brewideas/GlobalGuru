package in.co.thingsdata.gurukul.ui.common;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import in.co.thingsdata.gurukul.R;

/*OnBoarding is fragment that is expected to be used in various activities to provide onBoarding experience*/

public class OnBoarding extends Fragment implements ViewPager.OnPageChangeListener , View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match

    Handler mHandler;
    Runnable mUpdate;
    int currentPage = 0, NUM_PAGES = 4;
    ViewPager mViewPager;
    PagerAdapter mAdapter;
    //DrawerLayout drawer;

     private int mImgResources[] ={
             R.drawable.principle,
             R.drawable.director,
             R.drawable.login_reg_bg
     };




    private OnFragmentInteractionListener mListener;

//    public OnBoarding() {
//        // Required empty public constructor
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private int dotsCount;
    private ImageView[] dots;
    private LinearLayout pager_indicator;

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];


        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity().getApplicationContext());
            dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.nonselecteditem_dot, null));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 75, 4, 0);
            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selecteditem_dot, null));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.onboard_fragment,container,false);

        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        mAdapter = new ScreenPagerAdapter(getActivity().getApplicationContext());
        mViewPager.setAdapter(mAdapter);

        pager_indicator = (LinearLayout) rootView.findViewById(R.id.viewPagerCountDots);
        mViewPager.setOnPageChangeListener(this);
       // drawer = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);

        startSlideShow();
        setUiPageViewController();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.nonselecteditem_dot, null));
        }

        dots[position].setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.selecteditem_dot, null));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        //switch (v.getId()) {
          //  case R.id.btn_next:
          //            intro_images.setCurrentItem((intro_images.getCurrentItem() < dotsCount)?
            //          intro_images.getCurrentItem() + 1 : 0);
            //    break;

            //case R.id.btn_finish:
                //finish();
              //  break;
            //}
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    void startSlideShow() {
        mHandler = new Handler();
        mUpdate = new Runnable() {
            boolean revers= false;
            public void run() {

//                if (currentPage == NUM_PAGES - 2 && !revers) {
////                    currentPage = 0;
//                    revers = true;
//                } else if (currentPage == 0){
//                    revers = false;
//
//                }

                if (currentPage == NUM_PAGES){
                    revers = false;
                    currentPage = 0;
                }

                if (!revers) {
                    mViewPager.setCurrentItem(currentPage++, true);
                }else {
                    mViewPager.setCurrentItem(currentPage--, true);
                }
            }
        };
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                mHandler.post(mUpdate);
            }
        }, 500, 3000);
    }

}
