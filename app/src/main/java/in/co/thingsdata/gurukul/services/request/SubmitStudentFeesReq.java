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

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_REG_NUMBER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SCHOOL_CODE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_YEAR;

/**
 * Created by Vikas on 2/11/2017.
 */

public class SubmitStudentFeesReq extends CommonRequest{
    FeesProfileData mData;
    public interface SubmitStudenFeesCallback {
        void onSubmitMarksResponse(ResponseCode res, FeesProfileData data);
    }
    private SubmitStudenFeesCallback mAppCallback;

    public SubmitStudentFeesReq(Context context, FeesProfileData data, SubmitStudenFeesCallback cb) {
        super(context, RequestType.COMMON_REQUEST_PUSH_SUBMIT_FEES, CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);
        mData = data; mAppCallback = cb;
        JSONObject param = new JSONObject();
        try {
            param.put(JSON_FIELD_REG_NUMBER, data.getRegistrationId());
            param.put(JSONParsingEnum.JSON_FIELD_MONTH,data.getMonth());
            param.put(JSON_FIELD_YEAR, data.getYear());
            param.put(JSON_FIELD_SCHOOL_CODE,UserData.getSchoolCode());
            param.put(JSONParsingEnum.JSON_FIELD_CLASS_CODE, data.getClassRoomId());
            param.put(JSONParsingEnum.JSON_FIELD_SECTION_ID, data.getSectionId());
            param.put(JSONParsingEnum.JSON_FIELD_FEES_RECEIPT_NUM, data.getReceiptNumber());
            param.put(JSONParsingEnum.JSON_FIELD_FEES_PAID_AMOUNT, data.getFeesPaid());
            param.put(JSONParsingEnum.JSON_FIELD_FEES_REMAINING_AMOUNT, data.getFeesRemaining());
            param.put(JSONParsingEnum.JSON_FIELD_FEES_SUBMITTED_BY, data.getFeesSubmittedBy());
        } catch (JSONException e) {
            e.printStackTrace();
        }

       try{
           setParam(param);

           HashMap<String,String> p = new HashMap<>();
           p.put("Authorization", "bearer " + UserData.getAccessToken());
           setPostHeader(p);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        mAppCallback.onSubmitMarksResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onSubmitMarksResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
