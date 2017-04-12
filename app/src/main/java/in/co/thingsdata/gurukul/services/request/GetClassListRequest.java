package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.data.common.ClassData;
import in.co.thingsdata.gurukul.data.common.Subject;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_GET_CLASS_LIST;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_CLASS_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_CLASS_ROOM_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SCHOOL;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SECTION_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SUBJECTS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_UNIQUE_ID;

/**
 * Created by Vikas on 3/2/2017.
 */

public class GetClassListRequest extends CommonRequest {
    private ArrayList<ClassData> mClassList = new ArrayList<>();

    public interface GetClassListCallback {
        void onGetClassListResponse(ResponseCode res, ArrayList<ClassData> classes);
    }
    private GetClassListCallback mAppCallback;
    public GetClassListRequest(Context context, GetClassListCallback cb) {
        super(context, COMMON_REQUEST_GET_CLASS_LIST, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mAppCallback = cb;
        String url = getURL();
        url+= JSON_FIELD_SCHOOL + "=" + UserData.getSchoolCode();
        setURL(url);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            if (response.getInt(JSON_FIELD_STATUS) == 1) {
                JSONArray data = response.getJSONArray(JSON_FIELD_DATA);
                int total = data.length();
                for (int i = 0; i < total; i++) {
                    JSONObject class_detail = data.getJSONObject(i);
                    if (!class_detail.has(JSON_FIELD_SUBJECTS)){
                        continue;
                    }
                    String class_unique_id = class_detail.getString(JSON_FIELD_UNIQUE_ID);
                    String name = class_detail.getString(JSON_FIELD_CLASS_NAME);
                    ClassData _class = new ClassData(class_unique_id,name );
                    _class.setSection(class_detail.getString(JSON_FIELD_SECTION_ID));
                    _class.setClassRoomId(class_detail.getString(JSON_FIELD_CLASS_ROOM_ID));
                    JSONArray subjectList = class_detail.getJSONArray(JSON_FIELD_SUBJECTS);
                    for (int j=0; j<subjectList.length(); j++){
                        Subject s = new Subject(subjectList.get(j).toString(), subjectList.get(j).toString());
                        _class.addSubjectInClass(s);
                    }
                    mClassList.add(_class);
                }
                mAppCallback.onGetClassListResponse(ResponseCode.COMMON_RES_SUCCESS, mClassList);
            }
            else{
                mAppCallback.onGetClassListResponse(ResponseCode.COMMON_RES_NO_DATA_FOUND, null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mAppCallback.onGetClassListResponse(ResponseCode.COMMON_RES_INTERNAL_ERROR, null);
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {

    }
}
