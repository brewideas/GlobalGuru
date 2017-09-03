package in.co.thingsdata.gurukul.data;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;

/**
 * Created by Vikas on 6/20/2017.
 */

public class GetGalleryImageData extends DataOfUi{
    private String mUrlPath = "http://52.66.104.137:8080/school-data-service/api/utility/files/download/";
    private String mErrorMessage;
    private int mTotalNumOfNonDeletableImages;
    private int mTotalNumOfDeletableImages;
    private int mTotalNumOfImages;
    private ArrayList<ImageData> mImageList = new ArrayList<>();

    @Override
    public String getFilterableObject(int screenName) {
        return null;
    }

    private class ImageData{
        String mImageId;
        boolean mIsDeletable;

        public ImageData(String id, boolean isDeletable){
            mImageId = id; mIsDeletable = isDeletable;
        }
    }

    //public void setTotalNumOfNonDeletableImages (int num){mTotalNumOfNonDeletableImages = num;}
    //public void setTotalNumOfDeletableImages (int num){mTotalNumOfDeletableImages = num;}

    public void addImage(String id, boolean isDeletable){
        ImageData data = new ImageData(id, isDeletable);
        mImageList.add(data);
        mTotalNumOfImages++;
        if(isDeletable)
        {
            mTotalNumOfDeletableImages++;
        }
        else
        {
            mTotalNumOfNonDeletableImages++;
        }
    }

    public String getImageUrlAtIndex(int index){
        String url = mUrlPath;
        url+= mImageList.get(index).mImageId;
        return url;
    }
    public int getTotalNumberOfImages() {return mTotalNumOfImages; }
    public void setErrorMessage(String s){mErrorMessage = s;}
    public String getErrorMessage(){return mErrorMessage;}
}
