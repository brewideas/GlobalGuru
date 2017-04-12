package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.Subject;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_GET_CLASS_DETAIL;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_CLASS_CODE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_CLASS_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_CLASS_ROOM_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_CLASS_TEACHER_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_CLASS_TEACHER_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SECTION_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SUBJECTS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SUBJECT_ID;

/**
 * Created by Vikas on 3/2/2017.
 */

public class GetClassDetailRequest extends CommonRequest {
    private ClassData mClassData;

    public interface GetClassDetailCallback {
        void onGetClassDetailResponse(ResponseCode res, ClassData data);
    }
    private GetClassDetailCallback mAppCallback;

    public GetClassDetailRequest(Context context, ClassData data, GetClassDetailCallback cb) {
        super(context, COMMON_REQUEST_GET_CLASS_DETAIL, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mAppCallback = cb; mClassData = data;

        String url = getURL();
        url += mClassData.getUniqueId();
        setURL(url);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            if (response.getInt(JSON_FIELD_STATUS) == 1) {
                JSONObject class_detail = response.getJSONObject(JSON_FIELD_DATA);

                mClassData.setClassCode(class_detail.getString(JSON_FIELD_CLASS_CODE));
                mClassData.setClassName(class_detail.getString(JSON_FIELD_CLASS_NAME));
                mClassData.setClassTeacherName(class_detail.getString(JSON_FIELD_CLASS_TEACHER_NAME));
                mClassData.setClassRoomId(class_detail.getString(JSON_FIELD_CLASS_ROOM_ID));
                mClassData.setClassTeacherId(class_detail.getString(JSON_FIELD_CLASS_TEACHER_ID));
                mClassData.setSection(class_detail.getString(JSON_FIELD_SECTION_ID));

                JSONArray subjectList = class_detail.getJSONArray(JSON_FIELD_SUBJECTS);
                int size = subjectList.length();
                for (int i = 0; i < size; i++){
                    Subject s = new Subject(subjectList.getString(i), subjectList.getString(i));
                    mClassData.addSubjectInClass(s);
                }


                mAppCallback.onGetClassDetailResponse(ResponseCode.COMMON_RES_SUCCESS, mClassData);
            }
            else{
                mAppCallback.onGetClassDetailResponse(ResponseCode.COMMON_RES_NO_DATA_FOUND, mClassData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorHandler(VolleyError error) {

    }
}
