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
import in.co.thingsdata.gurukul.data.MarkSheetData;
import in.co.thingsdata.gurukul.data.SubjectWiseMarks;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_SUBMIT_RESULT;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ACCESS_TOKEN;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ATTENDANCE_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_CLASS_ROOM_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DAY;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_EXAM_TYPE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_MARKS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_MONTH;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_REG_NUMBER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_RESULT_MARKS_OBTAINED;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_RESULT_TOTAL_MARKS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ROLL_NUMBER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SCHOOL_CODE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SUBJECT_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SUBJECT_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_YEAR;

/**
 * Created by Vikas on 2/11/2017.
 */

public class SubmitMarkSheetReq extends CommonRequest{
    MarkSheetData mData;
    public interface SubmitMarkSheetCallback {
        void onSubmitMarksResponse(ResponseCode res, MarkSheetData data);
    }
    private SubmitMarkSheetCallback mAppCallback;

    public SubmitMarkSheetReq(Context context, MarkSheetData data, SubmitMarkSheetCallback cb) {
        super(context, COMMON_REQUEST_SUBMIT_RESULT, CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);
        mData = data; mAppCallback = cb;


        JSONObject param = new JSONObject();
        try {
            param.put(JSON_FIELD_YEAR, data.getExamYear());
            param.put(JSON_FIELD_EXAM_TYPE, data.getExamType());
            param.put(JSON_FIELD_REG_NUMBER, "2-ST-8800722771" /*data.getRegistrationId()*/);
            param.put(JSON_FIELD_CLASS_ROOM_ID, data.getClassRoomId());
            param.put(JSON_FIELD_SCHOOL_CODE, Integer.toString(UserData.getSchoolCode()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        ArrayList<SubjectWiseMarks> markSheet = mData.getMarkSheet();
        int size = markSheet.size();

        for (int i = 0; i < size; i++){
            SubjectWiseMarks marks = markSheet.get(i);
            JSONObject js = new JSONObject();
            try
            {
                js.put(JSON_FIELD_RESULT_TOTAL_MARKS, marks.getTotalMarks());
                js.put(JSON_FIELD_RESULT_MARKS_OBTAINED, marks.getMarksObtained());
                js.put(JSON_FIELD_SUBJECT_ID, marks.getSubject().getSubjectId());
                js.put(JSON_FIELD_SUBJECT_NAME, marks.getSubject().getSubjectName());
                js.put("delete", false);
                jsonArray.put(i, js);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try
        {
            param.put(JSON_FIELD_MARKS, jsonArray);
            setParam(param);
        }catch (JSONException e) {
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
