package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.SubmitAttendanceData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_SUBMIT_ATTENDANCE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ACCESS_TOKEN;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ATTENDANCE_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DAY;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_MONTH;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_YEAR;

/**
 * Created by Vikas on 2/1/2017.
 */

public class SubmitAttendanceRequest extends CommonRequest {
    public interface SubmitAttendanceCallback {
        void onSubmitAttendanceResponse(ResponseCode res, SubmitAttendanceData data);
    }
    private SubmitAttendanceCallback mAppCallback;


    private SubmitAttendanceData mAttendance;
    public SubmitAttendanceRequest(Context context, SubmitAttendanceData data, SubmitAttendanceCallback cb) {
        super(context, COMMON_REQUEST_SUBMIT_ATTENDANCE, CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);

        mAttendance = data; mAppCallback = cb;
        Map<String, String> param = new HashMap<>();
        param.put(JSON_FIELD_ACCESS_TOKEN, data.getAccessToken());
        param.put(JSON_FIELD_DAY, Integer.toString(data.getAttendance().getDay()));
        param.put(JSON_FIELD_MONTH, Integer.toString(data.getAttendance().getMonth()));
        param.put(JSON_FIELD_YEAR, Integer.toString(data.getAttendance().getYear()));
        param.put(JSON_FIELD_ATTENDANCE_STATUS, data.getAttendance().getAttendance().toString());
        setParam(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        mAppCallback.onSubmitAttendanceResponse(ResponseCode.COMMON_RES_SUCCESS, mAttendance);
    }

    @Override
    public void onErrorHandler(VolleyError error) {

    }
}
