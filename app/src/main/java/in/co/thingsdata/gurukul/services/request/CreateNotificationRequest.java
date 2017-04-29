package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.CreateNotificationData;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.data.common.CommonDetails.NOTIFICATION_TARGET_ALL_STRING;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.NOTIFICATION_TYPE_JSON_STRING_QUESTION;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.NOTIFICATION_TYPE_JSON_STRING_TEXT;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.NotificationTypeEnum.NOTIFICATION_TYPE_VOTE;
import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_CREATE_NOTIFICATION;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_CONTENT_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_CONTENT_MESSAGE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_CONTENT_TITLE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_CREATE_DATE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_EXPIRY_DATE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_FILTER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_QUESTION_TEXT;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_RESPONSE_BACK_FLAG;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_TARGET_USER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_TARGET_USER_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NOTIFICATION_TYPE;

/**
 * Created by Vikas on 2/24/2017.
 */

public class CreateNotificationRequest extends CommonRequest {
    private CreateNotificationData mData;

    public interface CreateNotificationCallback {
        void onCreateNotificationResponse(ResponseCode res, CreateNotificationData data);
    }
    private CreateNotificationCallback mAppCallback;

    public CreateNotificationRequest(Context context, CreateNotificationData data, CreateNotificationCallback cb) {
        super(context, COMMON_REQUEST_CREATE_NOTIFICATION, CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);

        mAppCallback = cb; mData = data;
        JSONObject param = new JSONObject();
        try {
            param.put(JSON_FIELD_NOTIFICATION_FILTER, Integer.toString(UserData.getSchoolCode()));
            param.put(JSON_FIELD_NOTIFICATION_CREATE_DATE, data.getCreateDate());
            param.put(JSON_FIELD_NOTIFICATION_EXPIRY_DATE, data.getExpiryDate());
            param.put(JSON_FIELD_NAME, data.getTitle());
            param.put(JSON_FIELD_NOTIFICATION_CONTENT_TITLE, data.getTitle());
            param.put(JSON_FIELD_NOTIFICATION_CONTENT_MESSAGE, data.getTitle());
            param.put(JSON_FIELD_NOTIFICATION_CONTENT_DATA, data.getDescription());
            if (data.getNotificationType() ==  NOTIFICATION_TYPE_VOTE){
                param.put(JSON_FIELD_NOTIFICATION_RESPONSE_BACK_FLAG, "true");
                param.put(JSON_FIELD_NOTIFICATION_TYPE, NOTIFICATION_TYPE_JSON_STRING_QUESTION);

                JSONObject questionContent = new JSONObject();
                questionContent.put("questType", "YES_NO");
                questionContent.put(JSON_FIELD_NOTIFICATION_QUESTION_TEXT, data.getTitle());
                JSONObject opt = new JSONObject();
                opt.put("Y", "YES");
                opt.put("N", "NO");
                questionContent.put("options", opt);
                param.put("questionContents",questionContent);
            }
            else{
                param.put(JSON_FIELD_NOTIFICATION_RESPONSE_BACK_FLAG, "false");
                param.put(JSON_FIELD_NOTIFICATION_TYPE, NOTIFICATION_TYPE_JSON_STRING_TEXT);
            }

            if (mData.getClassName() != null){
                JSONObject target = new JSONObject();
                target.put("targetUserType", "QUERY_SPECIFIC_PRE_FETCH");

                JSONArray queryId = new JSONArray();
                queryId.put("CLASS_SECTION_DATA_IN");
                target.put("uniqueQueryIds", queryId);

                JSONArray values = new JSONArray();
                values.put("STUDENT");
                values.put("TEACHER");
                target.put("values", values);

                JSONObject targetParam = new JSONObject();
                targetParam.put ("CLASS_CODE", mData.getClassName());
                targetParam.put ("SECTION", mData.getSection());
                target.put("params", targetParam);

                param.put(JSON_FIELD_NOTIFICATION_TARGET_USER_DATA, target);
            }
            else
            {
                JSONObject allData = new JSONObject();
                allData.put("targetUserType", "ALL");
                param.put(JSON_FIELD_NOTIFICATION_TARGET_USER_DATA, allData);
            }

            if (mData.IsSMS()){
                param.put("typeId", "SMS");
                param.put("sendInstant", true);
            }
            else
            {
                param.put("typeId", "PULL");
                param.put("sendInstant", false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setParam(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        mAppCallback.onCreateNotificationResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onCreateNotificationResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
