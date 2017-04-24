package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.GetAdData;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

/**
 * Created by Vikas on 4/24/2017.
 */

public class GetAdRequest extends CommonRequest {
    private GetAdData mData;

    public interface GetAdCallback {
        void onGetAdResponse(ResponseCode res, GetAdData data);
    }
    private GetAdCallback mAppCallback;

    public GetAdRequest(Context context, GetAdData data, GetAdCallback cb) {
        super(context, RequestType.COMMON_REQUEST_GET_AD, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mData = data; mAppCallback = cb;

        HashMap<String,String> param = new HashMap<>();
        param.put("Authorization", "bearer " + UserData.getAccessToken());
        setPostHeader(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        if (response == null)
        {
            mAppCallback.onGetAdResponse(ResponseCode.COMMON_RES_NO_SUCH_REQUEST, mData);
        }
        else
        {
            //TODO: parse and add data to mData.
            mAppCallback.onGetAdResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onGetAdResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
