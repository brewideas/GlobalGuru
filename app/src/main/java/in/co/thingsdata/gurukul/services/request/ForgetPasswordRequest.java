package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SCHOOL;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;

/**
 * Created by Vikas on 4/24/2017.
 */

public class ForgetPasswordRequest extends CommonRequest {
    private String mMobileNumber;

    public interface ForgetPasswordCallback {
        void onForgetPasswordResponse(ResponseCode res, String mobile_number);
    }
    private ForgetPasswordCallback mAppCallback;

    public ForgetPasswordRequest(Context context, String mobile_number, ForgetPasswordCallback cb) {
        super(context, RequestType.COMMON_REQUEST_FORGET_PASSWORD, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mMobileNumber = mobile_number; mAppCallback = cb;

        String url = getURL();
        url+= mobile_number;
        setURL(url);
    }

    @Override
    public void onResponseHandler(JSONObject response) {

        if (response == null){
            mAppCallback.onForgetPasswordResponse(ResponseCode.COMMON_RES_NO_SUCH_REQUEST, mMobileNumber);
        }
        else
        {
            try {
                if (response.getInt(JSON_FIELD_STATUS) == 1) {
                    mAppCallback.onForgetPasswordResponse(ResponseCode.COMMON_RES_SUCCESS, mMobileNumber);
                }
                else
                {
                    mAppCallback.onForgetPasswordResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, mMobileNumber);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onForgetPasswordResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mMobileNumber);
    }
}
