package in.co.thingsdata.gurukul.ui.Gallery;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.NetworkImageView;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetGalleryImageData;
import in.co.thingsdata.gurukul.services.helper.VolleySingleton;

public class GalleryAdapter extends BaseAdapter {

    private Context mContext;
    private GetGalleryImageData mData;

    private boolean mIsDataReady = false, mIsDataRequested = false;


    public static interface dataLoadingCallback {
        public abstract void loadingCompleteCallback();
    }
    dataLoadingCallback mDataLoadingCallback;

    public GalleryAdapter(Context c, GetGalleryImageData data) { mContext =c; mData = data;}

    @Override
    public int getCount() {
        int num = mData.getTotalNumberOfImages();
        return num;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li;
        View v = convertView;


        li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (true) {
            v = li.inflate(R.layout.school_gallery_adapter, null);

            NetworkImageView image = (NetworkImageView) v.findViewById(R.id.img);
            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            image.setImageResource(R.drawable.gallery_default_thumb);
            image.setErrorImageResId(R.drawable.gallery_failed_thumb);

            String url =  mData.getImageUrlAtIndex(position);
            image.setVisibility(View.VISIBLE);
            image.setImageUrl(url, VolleySingleton.getInstance(mContext).getImageLoader());
        }
        return v;
    }
}