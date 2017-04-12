package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.AttendanceData;
import in.co.thingsdata.gurukul.data.SubmitMultiAttendanceData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_SUBMIT_MULTI_ATTENDANCE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ACCESS_TOKEN;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ATTENDANCE_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DAY;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_MONTH;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_YEAR;

/**
 * Created by Vikas on 1/31/2017.
 */

public class SubmitMultiAttendanceRequest extends CommonRequest {

    public interface SubmitMultiAttendanceCallback {
        void onSubmitMultiAttendanceResponse(ResponseCode res, SubmitMultiAttendanceData data);
    }
    private SubmitMultiAttendanceCallback mAppCallback;

    private SubmitMultiAttendanceData mMultiAttendanceData;
    private ArrayList<AttendanceData> mAttendanceList;

    public SubmitMultiAttendanceRequest(Context context, SubmitMultiAttendanceData data, SubmitMultiAttendanceCallback cb) {
        super(context, COMMON_REQUEST_SUBMIT_MULTI_ATTENDANCE, CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);

        mMultiAttendanceData = data; mAppCallback = cb;
        Map<String, String> param = new HashMap<>();
        param.put(JSON_FIELD_ACCESS_TOKEN, data.getAccessToken());
        setPostHeader(param);

        mAttendanceList = data.getAttendanceList();

        JSONArray jsonArray = new JSONArray();
        int size = mAttendanceList.size();

        for (int i = 0; i < size; i++){
            AttendanceData attn = mAttendanceList.get(i);
            JSONObject js = new JSONObject();
            try
            {
                js.put(JSON_FIELD_DAY, attn.getDay());
                js.put(JSON_FIELD_MONTH, attn.getMonth());
                js.put(JSON_FIELD_YEAR, attn.getYear());
                js.put(JSON_FIELD_ATTENDANCE_STATUS, attn.getAttendance());
                jsonArray.put(i, js);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try
        {
            JSONObject jsObj = jsonArray.toJSONObject(jsonArray);
            setParam(jsObj);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        mAppCallback.onSubmitMultiAttendanceResponse(ResponseCode.COMMON_RES_SUCCESS, mMultiAttendanceData);
    }

    @Override
    public void onErrorHandler(VolleyError error) {

    }
}
