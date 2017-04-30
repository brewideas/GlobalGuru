package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import in.co.thingsdata.gurukul.data.GetNotificationSummaryData;
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
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            int status = response.getInt("status");
            if (status == 1){
                JSONArray data = response.getJSONArray("data");
                for (int i=0; i < data.length(); i++)
                {
                    JSONObject res = data.getJSONObject(0);
                    if (res.getString("responseMsg") == "Y")
                    {
                        mData.setTotalYesNotificationCount(res.getInt("total"));
                    }
                    if (res.getString("responseMsg") == "N")
                    {
                        mData.setTotalNoNotificationCount(res.getInt("total"));
                    }
                    //TODO: Get total number of notifications
                }
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
