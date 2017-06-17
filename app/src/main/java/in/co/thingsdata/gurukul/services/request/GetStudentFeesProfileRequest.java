package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.co.thingsdata.gurukul.data.common.FeesProfileData;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.helper.JSONParsingEnum;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_PROFILE_AUTHENTICATION_FAILED;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;

/**
 * Created by Ritika on 6/10/2017.
 */
public class GetStudentFeesProfileRequest extends CommonRequest {
//COMMON_REQUEST_GET_APP_VERSION


    private GetFeesProfileCallback mAppCallback;
    public GetStudentFeesProfileRequest(Context context, GetFeesProfileCallback cb ,
                                        FeesProfileData data) {
        super(context, RequestType.COMMON_REQUEST_GET_FEES_PROFILE,
                CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mAppCallback = cb;

        String url = getURL();
        url+= data.getRegistrationId();
        url += "&" + JSONParsingEnum.JSON_FIELD_FEES_YEAR + "=" + data.getYear();
        url += "&" + JSONParsingEnum.JSON_FIELD_FEES_MONTH + "=" + data.getMonth();

        setURL(url);
        HashMap<String,String> param = new HashMap<>();
        param.put("Authorization", "bearer " + UserData.getAccessToken());
        setPostHeader(param);
    }

    public interface GetFeesProfileCallback {
        void onGetStudentFeesProfileResponse(ResponseCode res, int status, int month , int year, int paidFees,
        int remainingFees , String lastPaidDate);
    }

    @Override
    public void onResponseHandler(JSONObject response) {

        try {
            if (response.getInt(JSON_FIELD_STATUS) == 1) {
                JSONObject version_detail = response.getJSONObject(JSON_FIELD_DATA);
                //int total = data.length();
                //for (int i = 0; i < total; i++)
                {
                  //  JSONObject version_detail = data.getJSONObject();
                    int status = version_detail.getInt(JSONParsingEnum.JSON_FIELD_FEES_STATUS);
                    int month = version_detail.getInt(JSONParsingEnum.JSON_FIELD_FEES_MONTH);
                    int year = version_detail.getInt(JSONParsingEnum.JSON_FIELD_FEES_YEAR);
                    int lastPaidAmount =  version_detail.getInt(JSONParsingEnum.JSON_FIELD_FEES_LAST_PAID);
                    int remaningfees = version_detail.getInt(JSONParsingEnum.JSON_FIELD_FEES_REMAINING);
                    String lastPaidDate =  version_detail.getString(JSONParsingEnum.JSON_FIELD_FEES_LAST_PAID_DATE);
                    mAppCallback.onGetStudentFeesProfileResponse(ResponseCode.COMMON_RES_SUCCESS,
                            status, month, year, lastPaidAmount,remaningfees,lastPaidDate);
                }
            }
        }catch (JSONException e) {
                    e.printStackTrace();
                    mAppCallback.onGetStudentFeesProfileResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR,
                            0, 0, 0, 0,0,"error");
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {

        if (error.networkResponse != null && (error.networkResponse.statusCode > 400 && error.networkResponse.statusCode < 500)) {
            mAppCallback.onGetStudentFeesProfileResponse(COMMON_RES_PROFILE_AUTHENTICATION_FAILED,
                    0, 0, 0, 0, 0, "error");
        }
        else {
            mAppCallback.onGetStudentFeesProfileResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT,
                    0, 0, 0, 0, 0, "error");
        }
    }
}
