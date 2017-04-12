package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.co.thingsdata.gurukul.data.MarkSheetData;
import in.co.thingsdata.gurukul.data.SubjectWiseMarks;
import in.co.thingsdata.gurukul.data.common.Subject;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_GET_RESULT;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_EXAM_TYPE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_MARKS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_RESULT_MARKS_OBTAINED;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_RESULT_TOTAL_MARKS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SUBJECT_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SUBJECT_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_YEAR;

/**
 * Created by Vikas on 2/10/2017.
 */

public class GetResultReq extends CommonRequest {
    MarkSheetData mData;

    public interface GetResultCallback {
        void onResultResponse(ResponseCode res, MarkSheetData data);
    }
    private GetResultCallback mAppCallback;

    public GetResultReq(Context context, MarkSheetData data, GetResultCallback cb) {
        super(context, COMMON_REQUEST_GET_RESULT, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mData = data; mAppCallback = cb;

        String url = getURL();
        url += JSON_FIELD_YEAR + "=" + mData.getExamYear();
        url += "&" + JSON_FIELD_EXAM_TYPE + "=" + mData.getExamType();
        url += "&" + "reg" + "=" + mData.getRegistrationId();
        setURL(url);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            if (response.getInt(JSON_FIELD_STATUS) == 1) {
                JSONObject data = response.getJSONObject(JSON_FIELD_DATA);
                JSONArray marks = data.getJSONArray(JSON_FIELD_MARKS);
                int total = marks.length();
                for (int i = 0; i < total; i++) {
                    JSONObject sub = marks.getJSONObject(i);
                    String subjectId = sub.getString(JSON_FIELD_SUBJECT_ID);

                    int marksObtained = sub.getInt(JSON_FIELD_RESULT_MARKS_OBTAINED);
                    int totalMarks = sub.getInt(JSON_FIELD_RESULT_TOTAL_MARKS);
                    String subjectName = null;
                    if (sub.has(JSON_FIELD_SUBJECT_NAME)){
                        subjectName = sub.getString(JSON_FIELD_SUBJECT_NAME);
                    }

                    Subject subject = new Subject(subjectId, subjectName);
                    SubjectWiseMarks subMarks = new SubjectWiseMarks(subject, totalMarks, marksObtained);
                    mData.addMarksInSubject(subMarks);
                }
                mAppCallback.onResultResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
            }
            else{
                mAppCallback.onResultResponse(ResponseCode.COMMON_RES_NO_DATA_FOUND, mData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onResultResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }
}
