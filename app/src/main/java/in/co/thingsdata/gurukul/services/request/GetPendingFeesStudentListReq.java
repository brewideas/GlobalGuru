package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.co.thingsdata.gurukul.data.GetPendingFeesStudentListData;
import in.co.thingsdata.gurukul.data.common.Student;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.helper.JSONParsingEnum;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_REG_NUMBER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_UNIQUE_ID;

/**
 * Created by Vikas on 2/10/2017.
 */

public class GetPendingFeesStudentListReq extends CommonRequest {
    private GetPendingFeesStudentListData mData;

    public interface GetPendingFeesStudentListCallback {
        void GetPendingFeesStudentListCallback(ResponseCode res, GetPendingFeesStudentListData data);
    }
    private GetPendingFeesStudentListCallback mAppCallback;

    public static enum Type_Pending_List{
        CLASSWISE_YEARLY,
        CLASSWISE_MONTHLY
    }
    public GetPendingFeesStudentListReq(Context context, GetPendingFeesStudentListData data, GetPendingFeesStudentListCallback cb
                                         ) {
        super(context, RequestType.COMMON_REQUEST_GET_PENDING_FEES_STUDENT_LIST, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
           mData = data; mAppCallback = cb;

        mAppCallback = cb;
        String url = getURL();
        url+= UserData.getSchoolCode();
        url += "&" + JSONParsingEnum.JSON_FIELD_FEES_YEAR + "=" + data.getYear();
        int month  = data.getMonth();;
        if(month != 0){
            url += "&" + JSONParsingEnum.JSON_FIELD_FEES_MONTH + "=" + month;
        }
        url += "&" + JSONParsingEnum.JSON_FIELD_CLASS + "=" + data.getClassRoomId();

        /*String section = data.getSectionId();
        if(section != null){
            url += "&" + JSONParsingEnum.JSON_FIELD_SECTION_ID + "=" + section;
        }*/

        setURL(url);
        HashMap<String,String> param = new HashMap<>();
        param.put("Authorization", "bearer " + UserData.getAccessToken());
        setPostHeader(param);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            if (response.getInt(JSON_FIELD_STATUS) == 1) {
                JSONArray data = response.getJSONArray(JSON_FIELD_DATA);
                int total = data.length();
                for (int i = 0; i < total; i++) {
                    JSONObject student = data.getJSONObject(i);
                    String name = student.getString(JSON_FIELD_NAME);
                   // int rollNum = student.getInt(JSON_FIELD_ROLL_NUMBER);
                    String id = student.getString(JSON_FIELD_UNIQUE_ID);
                    String reg_id = student.getString(JSON_FIELD_REG_NUMBER);
                    mData.addStudent(new Student(id, name, -1, reg_id));
                }
                mAppCallback.GetPendingFeesStudentListCallback(ResponseCode.COMMON_RES_SUCCESS, mData);
            }
            else{
                mAppCallback.GetPendingFeesStudentListCallback(ResponseCode.COMMON_RES_NO_DATA_FOUND, mData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.GetPendingFeesStudentListCallback(ResponseCode.COMMON_RES_INTERNAL_ERROR, mData);
    }
}
