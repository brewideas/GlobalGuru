package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import in.co.thingsdata.gurukul.data.GetUsersDetailsByIdData;
import in.co.thingsdata.gurukul.data.common.UserSummary;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_EMAIL_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SIGN_UP_REF_CODE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_USER_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_USER_TYPE;

/**
 * Created by Vikas on 4/30/2017.
 */

public class GetUsersDetailByIdRequest extends CommonRequest {
    private GetUsersDetailsByIdData mData;
    public interface GetUsersDetailByIdCallback {
        void onGetUsersDetailByIdResponse(ResponseCode res, GetUsersDetailsByIdData data);
    }
    private GetUsersDetailByIdCallback mAppCallback;
    public GetUsersDetailByIdRequest(Context context, GetUsersDetailsByIdData data, GetUsersDetailByIdCallback cb) {
        super(context, RequestType.COMMON_REQUEST_GET_USERS_DETAIL_BY_ID, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mData = data; mAppCallback = cb;

        String url = getURL();
        url += "users=" + mData.getUserIdList().get(0);
        for (int i=1; i < mData.getUserIdList().size(); i++){
            url += ","+ mData.getUserIdList().get(i);
        }
        setURL(url);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            int status = response.getInt("status");
            if (status == 1){
                JSONObject data = response.getJSONObject(JSON_FIELD_DATA);
                for (int i = 0; i < mData.getUserIdList().size(); i++){
                    JSONObject user = response.getJSONObject(mData.getUserIdList().get(i));
                    UserSummary userSummary = new UserSummary();
                    userSummary.setName(user.getString(JSON_FIELD_NAME));
                    userSummary.setUserId(user.getString(JSON_FIELD_USER_ID));
                    userSummary.setEmail(user.getString(JSON_FIELD_EMAIL_ID));
                    userSummary.setRefId(user.getString("referenceId"));
                    userSummary.setRole(user.getString("primaryRole"));
                    if (userSummary.getRole() == "STUDENT"){
                        JSONObject domainData = user.getJSONObject("domainData");
                        String classCode = domainData.getString("CLASS");
                        userSummary.setClassId(classCode);
                        String c_name = classCode.substring(classCode.indexOf("-"), 1);
                        userSummary.setClassName(c_name);
                        userSummary.setSection(domainData.getString("SECTION"));
                    }
                    mData.addUserSummary(userSummary);
                }
            }
            else
            {
                mData.setErrorMessage(response.getString("message"));
                mAppCallback.onGetUsersDetailByIdResponse(ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE, mData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mAppCallback.onGetUsersDetailByIdResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, mData);
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onGetUsersDetailByIdResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
