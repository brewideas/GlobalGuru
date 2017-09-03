package in.co.thingsdata.gurukul.ui.Gallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.services.helper.VolleySingleton;

/**
 * Created by Vikas on 7/1/2017.
 */

public class ImageViewFragment extends Fragment {

    private static final String POSITION = "position";

    public ImageViewFragment(){

    }

    public static ImageViewFragment InitNewFragment (int pos)
    {
        ImageViewFragment frag = new ImageViewFragment();
        Bundle bundle= new Bundle();
        bundle.putInt(POSITION, pos);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_view, container, false);

        NetworkImageView Iv = (NetworkImageView)rootView.findViewById(R.id.galleryNetworkImageViewe);
        int pos = getArguments().getInt(POSITION);
        Iv.setImageResource(R.drawable.gallery_default_thumb);
        Iv.setErrorImageResId(R.drawable.gallery_failed_thumb);
        Iv.setImageUrl(schoolGallery.getImageData().getImageUrlAtIndex(pos),
                VolleySingleton.getInstance(getActivity()).getImageLoader());

        return rootView;
    }
}
