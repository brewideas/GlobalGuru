package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.GetNotificationStatsData;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.NotificationReplyDetail;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
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
        url += data.getNotificationId();
        setURL(url);

        HashMap<String,String> p = new HashMap<>();
        p.put("Authorization", "bearer " + UserData.getAccessToken());
        setPostHeader(p);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            int status = response.getInt("status");
            if (status == 1){
                JSONArray data = response.getJSONArray(JSON_FIELD_DATA);
                for (int i = 0; i < data.length(); i++){
                    JSONObject reply = data.getJSONObject(i);
                    String uid = reply.getString("userId");
                    String answer = reply.getString("answer");
                    String reg_number = reply.getString("userMasterRefid");
                    NotificationReplyDetail detail = new NotificationReplyDetail(uid,
                            CommonDetails.NotificationTypeEnum.NOTIFICATION_TYPE_VOTE,
                            (answer == "Y")? CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_YES:
                            CommonDetails.NotificationReplyEnum.NOTIFICATION_REPLY_NO);
                    JSONObject userDetail = reply.getJSONObject("userDetail");
                    String name = userDetail.getString("name");
                    JSONArray roles = userDetail.getJSONArray("roles");
                    String userType = roles.getString(0);
                    String classCode, section;
                    if (userType.contentEquals(CommonDetails.USER_TYPE_STUDENT)) {
                        JSONObject domainData = userDetail.getJSONObject("domainData");
                        classCode = domainData.getString("CLASS");
                        section = domainData.getString("SECTION");
                        detail.setStudentDetails(name, classCode, section, -1, reg_number);
                    }
                    else{
                        classCode = null; section = null;
                        detail.setTeacherDetails(name, reg_number);
                    }
                    mData.addReply(detail);
                }
                mAppCallback.onGetNotificationStatsResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
            }
            else
            {
                mData.setErrorMessage(response.getString("message"));
                mAppCallback.onGetNotificationStatsResponse(ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE, mData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mAppCallback.onGetNotificationStatsResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, mData);
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onGetNotificationStatsResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
