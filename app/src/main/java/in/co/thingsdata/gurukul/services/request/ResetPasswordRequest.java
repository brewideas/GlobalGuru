package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import in.co.thingsdata.gurukul.data.ResetPasswordData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;

/**
 * Created by Vikas on 4/25/2017.
 */

public class ResetPasswordRequest extends CommonRequest {
    ResetPasswordData mData;

    public interface ResetPasswordCallback {
        void onResetPasswordResponse(ResponseCode res, ResetPasswordData data);
    }
    private ResetPasswordCallback mAppCallback;

    public ResetPasswordRequest(Context context, ResetPasswordData data, ResetPasswordCallback cb) {
        super(context, RequestType.COMMON_REQUEST_RESET_PASSWORD, CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);
        mData = data; mAppCallback = cb;

        //TODO: Set post data for OTP and new password
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        if (response == null){
            mAppCallback.onResetPasswordResponse(ResponseCode.COMMON_RES_NO_SUCH_REQUEST, mData);
        }
        else
        {
            try {
                if (response.getInt(JSON_FIELD_STATUS) == 1) {
                    mAppCallback.onResetPasswordResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
                }
                else
                {
                    mData.setErrorMessage(response.getString("message"));
                    mAppCallback.onResetPasswordResponse(ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE, mData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mAppCallback.onResetPasswordResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, mData);
            }
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onResetPasswordResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
