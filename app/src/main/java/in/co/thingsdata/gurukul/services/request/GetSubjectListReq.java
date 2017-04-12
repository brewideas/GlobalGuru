package in.co.thingsdata.gurukul.services.request;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import in.co.thingsdata.gurukul.data.GetSubjectListData;
import in.co.thingsdata.gurukul.data.common.Subject;
import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;


import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_GET_SUBJECT_LIST;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ACCESS_TOKEN;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_CLASS_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_SCHOOL;

/**
 * Created by Vikas on 2/10/2017.
 */

public class GetSubjectListReq extends CommonRequest {
    private GetSubjectListData mData;
    private boolean mIsDummy = true;

    public interface GetSubjectListResponse {
        void onGetSubjectListResponse(ResponseCode res, GetSubjectListData data);
    }
    private GetSubjectListResponse mAppCallback;

    public GetSubjectListReq(Context context, GetSubjectListData data, GetSubjectListResponse cb) {
        super(context, COMMON_REQUEST_GET_SUBJECT_LIST, CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mData = data; mAppCallback = cb;
        String url = getURL();
        url += JSON_FIELD_ACCESS_TOKEN + "=" + data.getAccessToken();
        url += "&" + JSON_FIELD_SCHOOL + "=" + UserData.getSchoolCode();
        url += "&" + JSON_FIELD_CLASS_ID + "=" + data.getClassId();
        setURL(url);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        //TODO: Parse JSON and add subject list in mData.
        mAppCallback.onGetSubjectListResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        mAppCallback.onGetSubjectListResponse(ResponseCode.COMMON_RES_FAILED_TO_CONNECT, mData);
    }

    public void executeRequest (){
        if(mIsDummy == true){
            Subject s = new Subject("SC", "Science");
            mData.addSubject(s);
            Subject s1 = new Subject("MT", "Mathematics");
            mData.addSubject(s1);
            Subject s2 = new Subject("HN", "HINDI");
            mData.addSubject(s2);
            Subject s3 = new Subject("SSC", "Social Science");
            mData.addSubject(s3);
            Subject s4 = new Subject("EN", "English");
            mData.addSubject(s4);
            Subject s5 = new Subject("PH", "Physics");
            mData.addSubject(s5);
            Subject s6 = new Subject("CH", "Chemistry");
            mData.addSubject(s6);
            Subject s7 = new Subject("BIO", "Biology");
            mData.addSubject(s7);
            mAppCallback.onGetSubjectListResponse(ResponseCode.COMMON_RES_SUCCESS, mData);
        }
        else{
            super.executeRequest();
        }
    }
}
