package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.GetUserDetailsData;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_TYPE_STUDENT;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_USER_ID;

/**
 * Created by Vikas on 4/23/2017.
 */

public class GetUserDetailRequest extends CommonRequest {
    GetUserDetailsData mData;
    public interface GetUserDetailResponse {
        void onGetUserDetailResponse(ResponseCode res, GetUserDetailsData data);
    }
    private GetUserDetailResponse mAppCallback;

    public GetUserDetailRequest(Context context,  GetUserDetailsData data, GetUserDetailResponse cb) {
        super(context, RequestType.COMMON_REQUEST_GET_USER_DETAIL, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mData = data; mAppCallback = cb;

        HashMap<String,String> param = new HashMap<>();
        param.put("Authorization", "bearer " + UserData.getAccessToken());
        setPostHeader(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            if (response.getInt(JSON_FIELD_STATUS) == 1) {
                String class_id;
                JSONObject data = response.getJSONObject(JSON_FIELD_DATA);
                mData.setName(data.getString(JSON_FIELD_NAME));
                mData.setEmail(data.getString("mailId"));
                mData.setUserId(data.getString(JSON_FIELD_USER_ID));
                mData.setUserType(data.getString("primaryRole"));
                mData.setMobileNumber(data.getString("activeMobileNumber"));
                mData.setReferenceCode(data.getString("referenceId"));
                String ref_id = data.getString("referenceId");
                mData.setReferenceCode(ref_id);
                String s_code = ref_id.substring(0, 1);
                mData.setSchoolCode(Integer.parseInt(s_code));

                JSONObject domainData = data.getJSONObject("domainData");

                if (mData.getUserType() == USER_TYPE_STUDENT) {
                    class_id = domainData.getString("CLASS");
                    mData.setClassRoomId(class_id);
                    mData.setSection(domainData.getString("SECTION"));
                }
                mAppCallback.onGetUserDetailResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
            }
            else{
                mAppCallback.onGetUserDetailResponse(ResponseCode.COMMON_RES_NO_DATA_FOUND, null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mAppCallback.onGetUserDetailResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, null);
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onGetUserDetailResponse(ResponseCode.COMMON_RES_CONNECTION_TIMEOUT, null);
    }
}
