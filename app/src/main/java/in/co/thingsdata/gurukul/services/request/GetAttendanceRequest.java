package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.GetAttendanceData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.CommonRequestMethod.COMMON_REQUEST_METHOD_POST;
import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_GET_ATTENDANCE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ACCESS_TOKEN;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_MONTH;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ROLL_NUMBER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_YEAR;

/**
 * Created by Vikas on 1/31/2017.
 */

public class GetAttendanceRequest extends CommonRequest {

    private GetAttendanceData mAttendanceData;

    public interface GetAttendanceCallback {
        void onGetAttendanceResponse(ResponseCode res, GetAttendanceData data);
    }
    private GetAttendanceCallback mAppCallback;

    public GetAttendanceRequest(Context context, GetAttendanceData data, GetAttendanceCallback cb) {
        super(context, COMMON_REQUEST_GET_ATTENDANCE, COMMON_REQUEST_METHOD_POST, null);

        mAppCallback = cb; mAttendanceData = data;
        Map<String, String> param = new HashMap<>();
        param.put(JSON_FIELD_ACCESS_TOKEN, data.getAccessToken());
        param.put(JSON_FIELD_ROLL_NUMBER, data.getRollNumber());
        param.put(JSON_FIELD_MONTH, Integer.toString(data.getMonth()));
        param.put(JSON_FIELD_YEAR, Integer.toString(data.getYear()));
        setParam(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        //TODO: Parse object and return to mAppCallback.
    }

    @Override
    public void onErrorHandler(VolleyError error) {

    }
}
