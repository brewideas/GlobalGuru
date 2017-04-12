package in.co.thingsdata.gurukul.services.request;






import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.thingsdata.gurukul.data.LoginData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;
import in.co.thingsdata.gurukul.services.helper.VolleyErrorHelper;

import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_CONNECTION_TIMEOUT;
import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_FAILED_TO_CONNECT;
import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_INTERNAL_ERROR;
import static in.co.thingsdata.gurukul.services.helper.CommonRequest.ResponseCode.COMMON_RES_SERVER_ERROR_WITH_MESSAGE;
import static in.co.thingsdata.gurukul.services.helper.VolleyErrorHelper.COMMON_NETWORK_ERROR_TIMEOUT;

/**
 * Created by Vikas on 1/30/2017.
 */

public class LoginRequest extends CommonRequest {
    private static final String JSON_FIELD_USERNAME = "username";
    private static final String JSON_FIELD_PASSWORD = "password";
    private Map<String, String> mParams;
    private LoginData mLoginData;
    Context mContext;


    public interface LoginResponseCallback {
        void onLoginResponse(ResponseCode res, LoginData data);
    }
    private LoginResponseCallback mLoginResponseCallback;

    public LoginRequest (Context context, LoginData data, LoginResponseCallback cb)
    {
        super(context, RequestType.COMMON_REQUEST_LOGIN, CommonRequestMethod.COMMON_REQUEST_METHOD_POST, null);
        mContext = context;
        mLoginData = data;

        String url = getRequestTypeURL(RequestType.COMMON_REQUEST_LOGIN);
        url += "&username=" + data.getUserName();
        url += "&password=" + data.getPassword();
        super.setURL(url);

        mLoginResponseCallback = cb;
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            //TODO: Need to change parsing as per response from server
            mLoginData.setAccessToken(response.getString("access_token"));
            mLoginData.setRefreshToken(response.getString("refresh_token"));
            mLoginData.setAccessTokenExpiry(response.getInt("expires_in"));
            mLoginResponseCallback.onLoginResponse (ResponseCode.COMMON_RES_SUCCESS, mLoginData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        String errorMsg = VolleyErrorHelper.getMessage(error, mContext);
        ResponseCode resCode = COMMON_RES_INTERNAL_ERROR;
        if (errorMsg == COMMON_NETWORK_ERROR_TIMEOUT)
        {
            resCode = COMMON_RES_CONNECTION_TIMEOUT;
        }
        else if (errorMsg == VolleyErrorHelper.COMMON_NETWORK_ERROR_UNKNOWN){
            resCode = COMMON_RES_INTERNAL_ERROR;
        }
        else if (errorMsg == VolleyErrorHelper.COMMON_NETWORK_ERROR_NO_INTERNET){
            resCode = COMMON_RES_FAILED_TO_CONNECT;
        }
        else
        {
            resCode = COMMON_RES_SERVER_ERROR_WITH_MESSAGE;
            mLoginData.setErrorMessage(errorMsg);
        }
        mLoginResponseCallback.onLoginResponse (resCode, mLoginData);
    }

    public void executeRequest (){
        String url = getURL();

        Response.Listener<String> listner = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onResponseHandler(obj);
            }
        };

        Response.ErrorListener errorListner = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onErrorHandler(error);
            }
        };


        StringRequest jsonReq = new StringRequest(Request.Method.POST, url, listner, errorListner){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                String tempUrl = getURL();

                String credentials = tempUrl.substring((tempUrl.indexOf("http://") + "http://".length()), tempUrl.indexOf("@"));
                String credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT).replace("\n", "");

                params.put("authorization", "Basic "+ credBase64);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonReq);
    }
}
