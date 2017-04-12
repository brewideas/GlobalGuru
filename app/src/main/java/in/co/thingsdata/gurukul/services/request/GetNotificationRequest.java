package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

import in.co.thingsdata.gurukul.data.GetNotificationData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

/**
 * Created by Vikas on 2/24/2017.
 */

public class GetNotificationRequest extends CommonRequest {
    private GetNotificationData mData;
    public interface GetNotificationCallback {
        void onGetNotificationResponse(ResponseCode res, GetNotificationData data);
    }
    private GetNotificationCallback mAppCallback;
    public GetNotificationRequest(Context context, GetNotificationData data, GetNotificationCallback cb) {
        super(context, RequestType.COMMON_REQUEST_GET_NOTIFICATION, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mAppCallback = cb; mData = data;
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        //TODO: Parse notification in Notification class format and save in mData notification list.
        mAppCallback.onGetNotificationResponse(ResponseCode.COMMON_RES_SUCCESS, mData);

    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onGetNotificationResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
