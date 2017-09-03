package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import in.co.thingsdata.gurukul.data.GetGalleryImageData;
import in.co.thingsdata.gurukul.data.common.NotificationReplyDetail;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;

/**
 * Created by Vikas on 6/20/2017.
 */

public class GetGalleryImageRequest extends CommonRequest {
    GetGalleryImageData mData;

    public interface GetGalleryCallback {
        void onGalleryResponse(ResponseCode res, GetGalleryImageData data);
    }
    private GetGalleryCallback mAppCallback;
    public GetGalleryImageRequest(Context context, GetGalleryImageData data, GetGalleryCallback cb) {
        super(context, RequestType.COMMON_REQUEST_GET_SCHOOL_GALLERY, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mData = data; mAppCallback = cb;

        String url = getURL();
        url += UserData.getSchoolCode();
        setURL(url);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            int status = response.getInt("status");
            if (status == 1){
                JSONObject data = response.getJSONObject(JSON_FIELD_DATA);
                int deletableNumberOfImages = data.getInt("countDeletedImage");
                int nonDeletableNumberOfImages = data.getInt("countNonDeletedImage");
                JSONArray imageList = data.getJSONArray("images");
                for (int i=0; i < imageList.length(); i++){
                    JSONObject image = imageList.getJSONObject(i);
                    String id = image.getString("downloadId");
                    try {
                        boolean isPermanent = image.getBoolean("isParamenent");
                        mData.addImage(id, !isPermanent);
                    }
                    catch(JSONException e) {
                        e.printStackTrace();
                        mData.addImage(id, true);
                    }
                }
                mAppCallback.onGalleryResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
            }
            else
            {
                mData.setErrorMessage(response.getString("message"));
                mAppCallback.onGalleryResponse(ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE, mData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mAppCallback.onGalleryResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, mData);
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        if (error.networkResponse != null && error.networkResponse.statusCode == 404){
            mAppCallback.onGalleryResponse(ResponseCode.COMMON_RES_IMAGE_NOT_FOUND, mData);
        }
        else {
            mAppCallback.onGalleryResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
        }
    }
}
