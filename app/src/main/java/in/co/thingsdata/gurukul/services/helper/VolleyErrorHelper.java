package in.co.thingsdata.gurukul.services.helper;

/**
 * Created by Vikas on 1/30/2017.
 */



import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikas on 7/14/2016.
 */

public class VolleyErrorHelper {
    public static final String COMMON_NETWORK_ERROR_TIMEOUT = "timeout";
    public static final String COMMON_NETWORK_ERROR_NO_INTERNET = "no internet connection";
    public static final String COMMON_NETWORK_ERROR_UNKNOWN = "unknown";

    public static String getMessage (Object error , Context context){
        if(error instanceof TimeoutError){
            return COMMON_NETWORK_ERROR_TIMEOUT;
        }else if (isServerProblem(error)){
            return handleServerError(error ,context);

        }else if(isNetworkProblem(error)){
            return COMMON_NETWORK_ERROR_NO_INTERNET;
        }
        return COMMON_NETWORK_ERROR_UNKNOWN;

    }

    private static String handleServerError(Object error, Context context) {

        VolleyError er = (VolleyError)error;
        NetworkResponse response = er.networkResponse;
        if(response != null){
            switch (response.statusCode){

                case 404:
                case 422:
                case 401:
                    String trimmedString;
                    try {
                        String json;
                        json = new String(response.data);
                        JSONObject obj = new JSONObject(json);
                        trimmedString = obj.getString("message");
                        return trimmedString;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                default:
                    return COMMON_NETWORK_ERROR_TIMEOUT;
            }
        }

        return COMMON_NETWORK_ERROR_UNKNOWN;
    }

    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError || error instanceof AuthFailureError);
    }

    private static boolean isNetworkProblem (Object error){
        return (error instanceof NetworkError || error instanceof NoConnectionError);
    }
}
