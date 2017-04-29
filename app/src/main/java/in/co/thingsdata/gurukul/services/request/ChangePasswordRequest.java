package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.ChangePasswordData;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;

/**
 * Created by Vikas on 4/25/2017.
 */

public class ChangePasswordRequest extends CommonRequest {
    ChangePasswordData mData;

    public interface ChangePasswordCallback {
        void onChangePasswordResponse(ResponseCode res, ChangePasswordData data);
    }
    private ChangePasswordCallback mAppCallback;

    public ChangePasswordRequest(Context context, ChangePasswordData data, ChangePasswordCallback cb) {
        super(context, RequestType.COMMON_REQUEST_CHANGE_PASSWORD, CommonRequestMethod.COMMON_REQUEST_METHOD_PUT, null);
        mData = data; mAppCallback = cb;

        Map<String, String> param = new HashMap<>();
        param.put("userId", mData.getMobileNumber());
        param.put("newPassword", mData.getNewPassword());
        param.put("oldPassword", mData.getOldPassword());
        setParam(param);

        HashMap<String,String> p = new HashMap<>();
        p.put("Authorization", "bearer " + UserData.getAccessToken());
        setPostHeader(p);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        if (response == null){
            mAppCallback.onChangePasswordResponse(ResponseCode.COMMON_RES_NO_SUCH_REQUEST, mData);
        }
        else
        {
            try {
                if (response.getInt(JSON_FIELD_STATUS) == 1) {
                    mAppCallback.onChangePasswordResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
                }
                else
                {
                    mData.setErrorMessage(response.getString("message"));
                    mAppCallback.onChangePasswordResponse(ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE, mData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mAppCallback.onChangePasswordResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, mData);
            }
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onChangePasswordResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
