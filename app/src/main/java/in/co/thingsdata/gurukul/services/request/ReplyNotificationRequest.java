package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.ReplyNotificationData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ACCESS_TOKEN;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_REPLY;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_USER_ID;

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

        HashMap<String,String> param = new HashMap<>();
        param.put("notifySendUserUniqueId", mData.getNotificationId());
        param.put("status", "Replied by" + UserData.getFirstName());
        param.put("reponse", (mData.getReply() ==
                CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_YES)? "Y" : "N");
        param.put("comments", "Replied by" + UserData.getFirstName());
        setParam(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            int status = response.getInt("status");
            if (status == 1){
                mAppCallback.onReplyNotificationResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
            }
            else
            {
                mData.setErrorMessage(response.getString("message"));
                mAppCallback.onReplyNotificationResponse(ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE, mData);
            }
        } catch (JSONException e) {
            mAppCallback.onReplyNotificationResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, mData);
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onReplyNotificationResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
