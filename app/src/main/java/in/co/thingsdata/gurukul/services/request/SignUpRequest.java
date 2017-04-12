package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.SignUpData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_PASSWORD;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SIGN_UP_REF_CODE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SIGN_UP_USER_TYPE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_USER_ID;

/**
 * Created by Vikas on 3/30/2017.
 */

public class SignUpRequest extends CommonRequest {
    private SignUpData mData;

    public interface SignUpCallback {
        void onSignUpResponse(ResponseCode res, SignUpData data);
    }
    private SignUpCallback mAppCallback;

    public SignUpRequest(Context context, SignUpData data, SignUpCallback cb) {
        super(context, RequestType.COMMON_REQUEST_SIGNUP, CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);
        mData = data; mAppCallback = cb;

        Map<String, String> param = new HashMap<>();

        param.put(JSON_FIELD_USER_ID, mData.getLoginId());
        param.put(JSON_FIELD_PASSWORD, mData.getPassword());
        param.put(JSON_FIELD_SIGN_UP_USER_TYPE, mData.getUserType());
        param.put(JSON_FIELD_SIGN_UP_REF_CODE, mData.getUniqueId());
        setParam(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            if (response.getInt(JSON_FIELD_STATUS) != 0) {
                mAppCallback.onSignUpResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
            }
            else
            {
                String message = response.getString("message");
                mData.setErrorMessage(message);
                mAppCallback.onSignUpResponse(ResponseCode.COMMON_RES_INVALID_INPUT, mData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onSignUpResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
