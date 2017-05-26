package in.co.thingsdata.gurukul.ui.Advertisment;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.GetAdData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.request.GetAdRequest;

/**
 * Created by Ritika on 4/29/2017.
 */
public class adverUiReq implements GetAdRequest.GetAdCallback {

    Context mContext;
    public adverUiReq(Context appContext){

        mContext = appContext;
        GetAdData data = new GetAdData();
        GetAdRequest requestAd = new GetAdRequest(appContext,data,this);
        requestAd.executeRequest();


    }

    @Override
    public void onGetAdResponse(CommonRequest.ResponseCode res, GetAdData data) {

        if(res == CommonRequest.ResponseCode.COMMON_RES_SUCCESS){

            String ImgUri =  data.getContentUrl();
            String adId =    data.getAdId();
            downloadFile(ImgUri,adId);

        }

    }


    public void downloadFile(String uRl , String adId) {

       String saveName = mContext.getResources().getString(R.string.advertismentName);
       Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        DownloadManager mgr = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        if(isSDPresent) {
            File direct = new File(Environment.getExternalStorageDirectory()
                    + "/Gurukul");

            if (!direct.exists()) {
                direct.mkdirs();
            }
            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI
                            | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(true).setTitle(saveName)
                    .setDescription("Something useful")
                    .setDestinationInExternalPublicDir("/Gurukul", saveName);


        }else{

            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI
                            | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(true).setTitle(saveName)
                    .setDescription("Something useful")
                    .setDestinationInExternalPublicDir(DownloadManager.COLUMN_URI, saveName);
        }

        mgr.enqueue(request);
        }



}
