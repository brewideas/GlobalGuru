package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.co.thingsdata.gurukul.data.GetStudentListInClassData;
import in.co.thingsdata.gurukul.data.common.Student;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_CLASS_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_REG_NUMBER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ROLL_NUMBER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SCHOOL;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SECTION_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_UNIQUE_ID;

/**
 * Created by Vikas on 2/10/2017.
 */

public class GetStudentListInClassReq extends CommonRequest {
    private GetStudentListInClassData mData;

    public interface GetStudentListInClassCallback {
        void onGetStudentListResponse(ResponseCode res, GetStudentListInClassData data);
    }
    private GetStudentListInClassCallback mAppCallback;

    public GetStudentListInClassReq(Context context, GetStudentListInClassData data, GetStudentListInClassCallback cb) {
        super(context, RequestType.COMMON_REQUEST_GET_STUDENT_LIST_IN_CLASS,
                CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mData = data; mAppCallback = cb;

        String url = getURL();
        url+= JSON_FIELD_SCHOOL + "=" + UserData.getSchoolCode();
        url+= "&" + JSON_FIELD_CLASS_ID + "=" + mData.getClassId();
        url+= "&" + JSON_FIELD_SECTION_ID + "=" + mData.getSectionId();

        setURL(url);
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
                    int rollNum = student.getInt(JSON_FIELD_ROLL_NUMBER);
                    String id = student.getString(JSON_FIELD_UNIQUE_ID);
                    String reg_id = student.getString(JSON_FIELD_REG_NUMBER);
                    mData.addStudent(new Student(id, name, rollNum, reg_id));
                }
                mAppCallback.onGetStudentListResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
            }
            else{
                mAppCallback.onGetStudentListResponse(ResponseCode.COMMON_RES_NO_DATA_FOUND, mData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onGetStudentListResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, mData);
    }
}
