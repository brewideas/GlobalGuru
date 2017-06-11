package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.helper.JSONParsingEnum;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_PROFILE_AUTHENTICATION_FAILED;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SCHOOL;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;

/**
 * Created by Ritika on 6/10/2017.
 */
public class GetAppVersionRequest  extends CommonRequest {
//COMMON_REQUEST_GET_APP_VERSION

    private GetAppVersionCallback mAppCallback;
    public GetAppVersionRequest(Context context, GetAppVersionCallback cb) {
        super(context, RequestType.COMMON_REQUEST_GET_APP_VERSION, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mAppCallback = cb;

        String url = getURL();
        url+= JSON_FIELD_SCHOOL + "=" + UserData.getSchoolCode();
        setURL(url);

        HashMap<String,String> param = new HashMap<>();
        param.put("Authorization", "bearer " + UserData.getAccessToken());
        setPostHeader(param);
    }

    public interface GetAppVersionCallback {
        void onGetAppVersionResponse(ResponseCode res,String url ,String msg ,int majorVersion , int minorVersion);
    }

    @Override
    public void onResponseHandler(JSONObject response) {

        try {
            if (response.getInt(JSON_FIELD_STATUS) == 1) {
                JSONObject version_detail = response.getJSONObject(JSON_FIELD_DATA);
                //int total = data.length();
                //for (int i = 0; i < total; i++)
                {
                  //  JSONObject version_detail = data.getJSONObject();
                    int majorVersion = version_detail.getInt(JSONParsingEnum.JSON_FIELD_MAJOR_VERSION);
                    int minorVersion = version_detail.getInt(JSONParsingEnum.JSON_FIELD_MINOR_VERSION);
                    String msgVersion = version_detail.getString(JSONParsingEnum.JSON_FIELD_VERSION_MESSAGE);
                    String url =  version_detail.getString(JSONParsingEnum.JSON_FIELD_DOWNLOAD_URL);
                    mAppCallback.onGetAppVersionResponse(ResponseCode.COMMON_RES_SUCCESS,url,msgVersion,majorVersion , minorVersion);
                }
            }
        }catch (JSONException e) {
                    e.printStackTrace();
                    mAppCallback.onGetAppVersionResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR,null,"Can't Update now , Try Later",0,0);
                }
    }

    @Override
    public void onErrorHandler(VolleyError error) {

        if (error.networkResponse != null && (error.networkResponse.statusCode > 400 && error.networkResponse.statusCode < 500)) {
            mAppCallback.onGetAppVersionResponse(COMMON_RES_PROFILE_AUTHENTICATION_FAILED,"null","Can't Update now , Try Later",0,0);
        }
        else {
            mAppCallback.onGetAppVersionResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT,"null","Can't Update now , Try Later",0,0);
        }
    }
}
