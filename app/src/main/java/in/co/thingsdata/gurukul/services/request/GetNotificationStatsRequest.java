package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

import in.co.thingsdata.gurukul.data.GetNotificationStatsData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_ID;

/**
 * Created by Vikas on 2/24/2017.
 */

public class GetNotificationStatsRequest extends CommonRequest {
    private GetNotificationStatsData mData;

    public interface GetNotificationStatsCallback {
        void onGetNotificationStatsResponse(ResponseCode res, GetNotificationStatsData data);
    }
    private GetNotificationStatsCallback mAppCallback;

    public GetNotificationStatsRequest(Context context, GetNotificationStatsData data, GetNotificationStatsCallback cb) {
        super(context, RequestType.COMMON_REQUEST_GET_NOTIFICATION_STATS, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mData = data; mAppCallback = cb;

        String url = getURL();
        url += JSON_FIELD_NOTIFICATION_ID + "=" + data.getNotificationId();
        setURL(url);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        //TODO: Parse response into NotificationReplyDetail and add in mData.
        mAppCallback.onGetNotificationStatsResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onGetNotificationStatsResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
