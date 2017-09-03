package in.co.thingsdata.gurukul.data;

import java.io.File;

/**
 * Created by Vikas on 8/27/2016.
 */

public class UpdateImageRequestData {
    private File mImageData;

    private String mErrorMessage;

    public UpdateImageRequestData (File img){
        mImageData = img;
    }
    public void setErrorMessage (String msg) {mErrorMessage = msg;}
    public String getErrorMessage (){return mErrorMessage;}
    public File getImageData(){return mImageData;}
}
