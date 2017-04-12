package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.ReplyNotificationData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ACCESS_TOKEN;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_REPLY;

/**
 * Created by Vikas on 2/24/2017.
 */

public class ReplyNotificationRequest extends CommonRequest {
    private ReplyNotificationData mData;

    public interface ReplyNotificationCallback {
        void onReplyNotificationResponse(ResponseCode res, ReplyNotificationData data);
    }
    private ReplyNotificationCallback mAppCallback;

    public ReplyNotificationRequest(Context context, ReplyNotificationData data, ReplyNotificationCallback cb) {
        super(context, RequestType.COMMON_REQUEST_REPLY_NOTIFICATION, CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);
        mAppCallback = cb; mData = data;

        Map<String, String> param = new HashMap<>();
        param.put(JSON_FIELD_ACCESS_TOKEN, data.getAccessToken());
        param.put(JSON_FIELD_NOTIFICATION_ID, data.getNotificationId());
        param.put(JSON_FIELD_NOTIFICATION_REPLY, data.getReply().toString());
        setParam(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        mAppCallback.onReplyNotificationResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onReplyNotificationResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
