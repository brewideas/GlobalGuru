package in.co.thingsdata.gurukul.ui.Gallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.co.thingsdata.gurukul.data.GetGalleryImageData;

public class ImageViewPagerAdapter extends FragmentStatePagerAdapter{

	public ImageViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return ImageViewFragment.InitNewFragment(position);
	}

	@Override
	public int getCount() {
		return schoolGallery.getImageData().getTotalNumberOfImages();
	}

}