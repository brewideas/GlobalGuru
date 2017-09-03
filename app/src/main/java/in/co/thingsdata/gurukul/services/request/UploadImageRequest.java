package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.UpdateImageRequestData;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonFileUpload;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_INTERNAL_ERROR;

/**
 * Created by Vikas on 8/27/2016.
 */

public class UploadImageRequest {

    private Context mContext;
    private UpdateImageRequestData mImageData;
    private CommonFileUpload mFileUpload;

    public interface UpdateImageResponseCallback {
        void onUpdateImageResponse(CommonRequest.ResponseCode res, UpdateImageRequestData data);
    }
    private UpdateImageResponseCallback mUpdateImageResponseCallback;

    public UploadImageRequest(Context c, UpdateImageRequestData img_data, UpdateImageResponseCallback cb) {
        mContext = c; mUpdateImageResponseCallback = cb; mImageData = img_data;
    }


    public void executeRequest(){
        String url = "http://52.66.104.137:8080/school-data-service/api/utility/files/upload/gallery";

        Response.Listener<NetworkResponse> listner = new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                mUpdateImageResponseCallback.onUpdateImageResponse(CommonRequest.ResponseCode.COMMON_RES_SUCCESS, mImageData);
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onErrorHandler(error);
            }
        };


        mFileUpload = new CommonFileUpload(mContext,
                mImageData.getImageData(),
                CommonFileUpload.FileType.COMMON_UPLOAD_FILE_TYPE_IMAGE,
                "FileName",
                url,
                null,
                listner,
                errorListner);
        mFileUpload.setFileTag("upload-document");

        Map<String, String> params = new HashMap<>();
        params.put("x-org", Integer.toString(UserData.getSchoolCode()));
        params.put("x-file-group", "General");
        params.put("x-file-group", Boolean.toString(true));
        JSONObject j_param = new JSONObject();

        try {
            j_param.put("x-org", UserData.getSchoolCode());
            j_param.put("x-file-group", "General");
            j_param.put("x-file-group", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mFileUpload.setHeader(params);

        mFileUpload.setHeader(j_param);

        mFileUpload.uploadFile();
    }

    public void onErrorHandler(VolleyError error) {
        CommonRequest.ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
        mUpdateImageResponseCallback.onUpdateImageResponse (resCode, mImageData);
    }
}