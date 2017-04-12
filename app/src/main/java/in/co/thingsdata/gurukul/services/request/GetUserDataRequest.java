package in.co.thingsdata.gurukul.services.request;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import in.co.thingsdata.gurukul.data.common.UserData;
import in.co.thingsdata.gurukul.services.helper.CommonRequest;

import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_GENDER_FEMALE;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_GENDER_MALE;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_TYPE_PARENT;
import static in.co.thingsdata.gurukul.data.common.CommonDetails.USER_TYPE_STUDENT;
import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_GET_STUDENT_DETAIL;
import static in.co.thingsdata.gurukul.services.helper.CommonRequest.RequestType.COMMON_REQUEST_GET_TEACHER_DETAIL;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DATA;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DEPARTMENT;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_DESIGNATION;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_EMAIL_ID;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_EMPLOYEE_CODE;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_FIRST_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_GENDER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_LAST_NAME;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_MOBILE_NUMBER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_REG_NUMBER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_ROLL_NUMBER;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_STATUS;
import static in.co.thingsdata.gurukul.services.helper.JSONParsingEnum.JSON_FIELD_UNIQUE_ID;

/**
 * Created by Vikas on 2/11/2017.
 */

public class GetUserDataRequest extends CommonRequest {
    private String mUserType;
    private String mUserId;
    private Context mContext;
    public GetUserDataRequest(Context context, String type, String id) {
        super(context, (type==USER_TYPE_STUDENT?COMMON_REQUEST_GET_STUDENT_DETAIL:COMMON_REQUEST_GET_TEACHER_DETAIL),
                CommonRequestMethod.COMMON_REQUEST_METHOD_GET, null);
        mUserType = type; mUserId = id; mContext = context;
        String url = getURL();
        if (mUserType == USER_TYPE_STUDENT || mUserType == USER_TYPE_PARENT) {
            url += "reg=" + mUserId;
        }
        else{
            url += "emp=" + mUserId;
        }
        setURL(url);
    }

    @Override
    public void onResponseHandler(JSONObject response) {
        try {
            if (response.getInt(JSON_FIELD_STATUS) == 1) {
                JSONObject data = response.getJSONObject(JSON_FIELD_DATA);

                String f_name = data.getString(JSON_FIELD_FIRST_NAME);
                String l_name = data.getString(JSON_FIELD_LAST_NAME);
                int school_code = data.getInt("schoolCode");
                String id = data.getString(JSON_FIELD_UNIQUE_ID);
                String gender = data.getString(JSON_FIELD_GENDER);

                UserData.setFirstName(f_name);
                UserData.setLastName(l_name);
                UserData.setSchoolCode(school_code);
                UserData.setUniqueId(id);
                UserData.setGender(gender=="M"?USER_GENDER_MALE:USER_GENDER_FEMALE);

                if (mUserType == USER_TYPE_STUDENT || mUserType == USER_TYPE_PARENT) {
                    String reg_num = data.getString(JSON_FIELD_REG_NUMBER);

                    int roll_num = data.getInt(JSON_FIELD_ROLL_NUMBER);
                    UserData.setRollNumber(roll_num);
                    UserData.setRegistrationId(reg_num);
                }
                else{
                    String mailId = data.getString(JSON_FIELD_EMAIL_ID);
                    String mobileNumber = data.getString(JSON_FIELD_MOBILE_NUMBER);
                    String empCode = data.getString(JSON_FIELD_EMPLOYEE_CODE);
                    String designation = data.getString(JSON_FIELD_DESIGNATION);
                    String dept = data.getString(JSON_FIELD_DEPARTMENT);
                    UserData.setEmailId(mailId);
                    UserData.setMobileNumber(mobileNumber);
                    UserData.setEmployeeId(empCode);
                    UserData.setDesignation(designation);
                    UserData.setDepartment(dept);
                }
                UserData.setUserDataReady(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorHandler(VolleyError error) {
        //TODO:throw user message to try again and exit the application
        Toast.makeText(mContext, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
    }
}
