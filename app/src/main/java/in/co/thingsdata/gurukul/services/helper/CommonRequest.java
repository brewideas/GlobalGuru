package in.co.thingsdata.gurukul.services.helper;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Vikas on 1/30/2017.
 */

public abstract class CommonRequest {
    /*------------------------- Constant Fields Definition ----------------------------*/
    private static final String DOMAIN = "http://52.66.104.137:8080/";
    private static final String LOGIN_REQUEST_URL = "http://sclerp:gurukul@52.66.104.137:8080/" +
                                                    "user-auth-service/oauth/token?grant_type=password";
    private static final String CLASS_LIST_URL = DOMAIN + "school-data-service/api/school/class/room/search?";
    private static final String CLASS_DETAIL_URL = DOMAIN + "school-data-service/api/school/class/room/search/";
    private static final String SIGN_UP_REQUEST_URL = DOMAIN + "school-data-service/api/school/signup/enduser";
    private static final String GET_RESULT_URL = DOMAIN + "school-module-service/api/school/exam/result/search?";
    private static final String SUBMIT_MARKSHEET_URL = DOMAIN + "school-module-service/api/school/exam/result";
    private static final String GET_STUDENT_DETAIL_URL = DOMAIN + "school-data-service/api/school/profile/student/search/detail/regNo?";
    private static final String GET_TEACHER_DETAIL_URL = DOMAIN + "school-data-service/school/profile/teacher/search?";
    private static final String GET_STUDENT_LIST_URL = DOMAIN + "school-data-service/api/school/profile/student/search/summary?";
    private static final String GET_SUBJECT_LIST_URL = DOMAIN + "Subject list"; //TODO: Get proper URL
    private static final String CREATE_NOTIFICATION_URL = DOMAIN + "school-data-service" + "/api/notification/data";
    private static final String NOTIFICATION_STAT_URL = DOMAIN + "school-data-service" + "/api/notification/result/detail?id=";
    private static final String PULL_NOTIFICATION_URL = DOMAIN + "school-data-service" + "/api/notification/data/search/pull?";
    private static final String REPLY_NOTIFICATION_URL = DOMAIN + "school-data-service" + "/api/notification/do/response";
    private static final String NOTIFICATION_SUMMARY_URL = DOMAIN + "school-data-service" + "/api/notification/result/summary?id=";

    private static final String GET_USER_DETAIL_URL = DOMAIN + "school-data-service/api/school/search/detail/user";
    private static final String GET_USERS_DETAIL_BY_ID_URL = DOMAIN + "school-data-service/api/school/search/summary/users?";
    private static final String FORGET_PASSWORD_URL = DOMAIN + "school-data-service/api/school/password/forgot/";
    private static final String CHANGE_PASSWORD_URL = DOMAIN + "school-data-service/api/school/password/change/";
private static final String GET_SCHOOL_GALLERY_URL = DOMAIN + "school-data-service/api/utility/files/search?org=";
    private static final String HOMEWORK_AS_NOTIFICATION_STAT_URL = DOMAIN + "school-data-service" + "/api/notification/data/search/pull?";

    private static final String GET_APPLICATION_VERSION_URL = DOMAIN +
                                                "school-data-service/api/apps/android/version?org=1";
    private static final String GET_APPLICATION_GET_FEES_PROFILE = DOMAIN +
                                     "school-module-service/api/finance/flat/fees/search/by/regNo?reg=";
    private static final String PUSH_APPLICATION_SUBMIT_FEES = DOMAIN +
                                                      "school-module-service/api/finance/flat/fees";
    private static final String GET_APPLICATION_PENDING_FEES_STUDENT_LIST =  DOMAIN +
                             "school-module-service/api/finance/flat/fees/search/fee/pending?school=";
    //2&amp;year=2016&amp;class=12 //for year wise
    //2&amp;year=2016&amp;month=4&amp;class=12//for month wise


    public enum RequestType  {
        COMMON_REQUEST_LOGIN,
        COMMON_REQUEST_GET_CLASS_LIST,
        COMMON_REQUEST_GET_CLASS_DETAIL,
        COMMON_REQUEST_FORGET_PASSWORD,
        COMMON_REQUEST_SIGNUP,
        COMMON_REQUEST_GET_STUDENT_DETAIL,
        COMMON_REQUEST_GET_TEACHER_DETAIL,
        COMMON_REQUEST_GET_USER_DETAIL,

        COMMON_REQUEST_GET_ATTENDANCE,
        COMMON_REQUEST_SUBMIT_ATTENDANCE,
        COMMON_REQUEST_SUBMIT_MULTI_ATTENDANCE,

        COMMON_REQUEST_GET_SUBJECT_LIST,
        COMMON_REQUEST_GET_STUDENT_LIST_IN_CLASS,
        COMMON_REQUEST_GET_RESULT,
        COMMON_REQUEST_SUBMIT_RESULT,

        COMMON_REQUEST_CREATE_NOTIFICATION,
        COMMON_REQUEST_GET_NOTIFICATION,
        COMMON_REQUEST_PULL_NOTIFICATION,
        COMMON_REQUEST_REPLY_NOTIFICATION,
        COMMON_REQUEST_GET_NOTIFICATION_STATS,
        COMMON_REQUEST_GET_SCHOOL_GALLERY,

        COMMON_REQUEST_GET_PROFILE, COMMON_REQUEST_GET_USER_PROFILE_LIST, COMMON_REQUEST_GET_AD,
        COMMON_REQUEST_CHANGE_PASSWORD, COMMON_REQUEST_GET_USERS_DETAIL_BY_ID,
        COMMON_REQUEST_GET_NOTIFICATION_SUMMARY,COMMON_REQUEST_GET_APP_VERSION,
        COMMON_REQUEST_GET_FEES_PROFILE,COMMON_REQUEST_PUSH_SUBMIT_FEES,COMMON_REQUEST_GET_PENDING_FEES_STUDENT_LIST,
        COMMON_REQUEST_PULL_HOMEWORK_AS_NOTIFICATION,
        COMMON_REQUEST_END // WARNING: Add all request types above this line only
    }

    public enum ResponseCode  {
        COMMON_RES_SUCCESS,
        COMMON_RES_NO_DATA_FOUND,
        COMMON_RES_INTERNAL_ERROR,
        COMMON_RES_CONNECTION_TIMEOUT,
        COMMON_RES_FAILED_TO_CONNECT,
        COMMON_RES_IMAGE_NOT_FOUND,
        COMMON_RES_SERVER_ERROR_WITH_MESSAGE,
        COMMON_RES_INVALID_INPUT,
        COMMON_RES_PROFILE_DATA_NO_CONTENT,
        COMMON_RES_FAILED_TO_UPLOAD,

        COMMON_RES_PROFILE_AUTHENTICATION_FAILED, COMMON_RES_NO_SUCH_REQUEST, COMMON_REQUEST_END // WARNING: Add all request types above this line only
    }

    public enum CommonRequestMethod {
        COMMON_REQUEST_METHOD_GET,
        COMMON_REQUEST_METHOD_POST,

        COMMON_REQUEST_METHOD_PUT, COMMON_REQUEST_METHOD_END
    }

    /*---------------------------- Member variables -----------------------------------*/
    private String mURL;
    private CommonRequestMethod mMethod;
    private Map<String, String> mParams;
    private Map<String, String> mPostHeader;
    private JSONObject mJSONParams;
    private RequestType mRequestType;
    private Context mContext;


    public CommonRequest (Context context,RequestType type,
                          CommonRequestMethod reqMethod, Map<String, String> param){
        mContext = context; mRequestType = type; mMethod = reqMethod; mParams = param;
        mURL = getRequestTypeURL (mRequestType);
    }

    public void setURL (String url){
        mURL = url;
    }

    public void setHttpRequestMethod (CommonRequestMethod method){
        mMethod = method;
    }

    public void setParam (Map<String, String> params){
        mParams = params;
    }
    public void setPostHeader (Map<String, String> h){mPostHeader = h;}

    public void setParam (JSONObject params){
        mJSONParams = params;
    }

    public abstract void onResponseHandler (JSONObject response);
    public abstract void onErrorHandler (VolleyError error);

    public String getRequestTypeURL (RequestType type){
        String url = null;
        switch (type){
            case COMMON_REQUEST_LOGIN:
                url = LOGIN_REQUEST_URL;
                break;
            case COMMON_REQUEST_SIGNUP:
                url = SIGN_UP_REQUEST_URL;
                break;
            case COMMON_REQUEST_GET_STUDENT_DETAIL:
                url = GET_STUDENT_DETAIL_URL;
                break;
            case COMMON_REQUEST_GET_TEACHER_DETAIL:
                url = GET_TEACHER_DETAIL_URL;
                break;
            case COMMON_REQUEST_GET_RESULT:
                url = GET_RESULT_URL;
                break;
            case COMMON_REQUEST_SUBMIT_RESULT:
                url = SUBMIT_MARKSHEET_URL;
                break;
            case COMMON_REQUEST_GET_STUDENT_LIST_IN_CLASS:
                url = GET_STUDENT_LIST_URL;
                break;
            case COMMON_REQUEST_GET_SUBJECT_LIST:
                url = GET_SUBJECT_LIST_URL;
                break;
            case COMMON_REQUEST_GET_CLASS_LIST:
                url = CLASS_LIST_URL;
                break;
            case COMMON_REQUEST_GET_CLASS_DETAIL:
                url = CLASS_DETAIL_URL;
                break;
            case COMMON_REQUEST_CREATE_NOTIFICATION:
                url = CREATE_NOTIFICATION_URL;
                break;
            case COMMON_REQUEST_GET_USER_DETAIL:
                url = GET_USER_DETAIL_URL;
                break;
            case COMMON_REQUEST_FORGET_PASSWORD:
                url = FORGET_PASSWORD_URL;
                break;
            case COMMON_REQUEST_CHANGE_PASSWORD:
                url = CHANGE_PASSWORD_URL;
                break;
            case COMMON_REQUEST_GET_NOTIFICATION_STATS:
                url = NOTIFICATION_STAT_URL;
                break;
            case COMMON_REQUEST_PULL_NOTIFICATION:
                url = PULL_NOTIFICATION_URL;
                break;
            case COMMON_REQUEST_REPLY_NOTIFICATION:
                url = REPLY_NOTIFICATION_URL;
                break;
            case COMMON_REQUEST_GET_USER_PROFILE_LIST:
                url = GET_USERS_DETAIL_BY_ID_URL;
                break;
            case COMMON_REQUEST_GET_NOTIFICATION_SUMMARY:
                url = NOTIFICATION_SUMMARY_URL;
                break;
            case COMMON_REQUEST_GET_APP_VERSION:
                url = GET_APPLICATION_VERSION_URL;
                break;
            case COMMON_REQUEST_GET_FEES_PROFILE:
                url= GET_APPLICATION_GET_FEES_PROFILE;
                break;
            case COMMON_REQUEST_PUSH_SUBMIT_FEES:
                url= PUSH_APPLICATION_SUBMIT_FEES;
                break;
            case COMMON_REQUEST_GET_PENDING_FEES_STUDENT_LIST:
                url= GET_APPLICATION_PENDING_FEES_STUDENT_LIST;
                break;
            case COMMON_REQUEST_PULL_HOMEWORK_AS_NOTIFICATION:
                url= GET_APPLICATION_PENDING_FEES_STUDENT_LIST;
                break;
			case COMMON_REQUEST_GET_SCHOOL_GALLERY:
                url = GET_SCHOOL_GALLERY_URL;
                break;	
            default:
                url = null;
        }
        return url;
    }

    public String getURL (){
        return mURL;
    }

    public void executeRequest (){


        if ((mURL == null)|| (mURL.isEmpty())) {
            onResponseHandler(null);
        }
        Response.Listener<JSONObject> listner = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onResponseHandler(response);
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onErrorHandler(error);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        CustomRequest jsObjRequest;
        if (mMethod == CommonRequestMethod.COMMON_REQUEST_METHOD_GET){
            jsObjRequest = new CustomRequest(mURL, null, listner, errorListner){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return ((mPostHeader != null)? mPostHeader : super.getHeaders());
                }
            };
            requestQueue.add(jsObjRequest);
        }
        else
        {
            int method;
            method = (mMethod == CommonRequestMethod.COMMON_REQUEST_METHOD_PUT) ? Request.Method.PUT : Request.Method.POST;
            jsObjRequest = new CustomRequest(method, mURL, mParams, listner, errorListner) {
                  public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return ((mPostHeader != null)? mPostHeader : super.getHeaders());
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    if (mJSONParams == null) {
                        return super.getBody();
                    }
                    else
                    {
                        return mJSONParams.toString().getBytes();
                    }
                }
            };
            requestQueue.add(jsObjRequest);
        }
    }
}