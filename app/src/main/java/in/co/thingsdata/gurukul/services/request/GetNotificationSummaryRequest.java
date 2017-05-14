package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.GetNotificationSummaryData;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

/**
 * Created by Vikas on 4/30/2017.
 */

public class GetNotificationSummaryRequest extends CommonRequest {
    private GetNotificationSummaryData mData;

    public interface GetNotificationSummaryCallback {
        void onGetNotificationSummaryResponse(ResponseCode res, GetNotificationSummaryData data);
    }
    private GetNotificationSummaryCallback mAppCallback;

    public GetNotificationSummaryRequest(Context context, GetNotificationSummaryData data, GetNotificationSummaryCallback cb) {
        super(context, RequestType.COMMON_REQUEST_GET_NOTIFICATION_SUMMARY, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);

        mData = data; mAppCallback = cb;

        String url = getURL();
        url += data.getNotificationId();
        setURL(url);

        HashMap<String,String> param = new HashMap<>();
        param.put("Authorization", "bearer " + UserData.getAccessToken());
        setPostHeader(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            int status = response.getInt("status");
            if (status == 1){
                JSONObject data = response.getJSONObject("data");
                JSONArray answer = data.getJSONArray("answers");
                int total_YES = 0, total_NO = 0; int grand_total = 0;
                for (int i=0; i < data.length(); i++)
                {
                    JSONObject res = answer.getJSONObject(i);
                    if (res.getString("responseMsg").contentEquals("Y"))
                    {
                        total_YES += res.getInt("total");
                    }
                    if (res.getString("responseMsg").contentEquals("N"))
                    {
                        total_NO += res.getInt("total");
                    }
                    grand_total = total_NO + total_YES;
                    //TODO: Get total number of notifications
                }
                mData.setTotalYesNotificationCount(total_YES);
                mData.setTotalNoNotificationCount(total_NO);
                mData.setTotalNotificationCount(grand_total);
                mAppCallback.onGetNotificationSummaryResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
            }
            else
            {
                mData.setErrorMessage(response.getString("message"));
                mAppCallback.onGetNotificationSummaryResponse(ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE, mData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mAppCallback.onGetNotificationSummaryResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, mData);
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onGetNotificationSummaryResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
