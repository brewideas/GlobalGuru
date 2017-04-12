package in.co.thingsdata.gurukul.data;

/**
 * Created by Vikas on 3/30/2017.
 */

public class SignUpData {
    private String mUserUniqueId;
    private String mLoginId;
    private String mPassword;
    private String mUserType;
    private String mErrorMessage;

    public SignUpData (String unique_id, String login_id, String pwd, String user_type){
        mUserUniqueId = unique_id;
        mLoginId = login_id;
        mPassword = pwd;
        mUserType = user_type;
    }

    public String getUniqueId(){return mUserUniqueId;}
    public String getLoginId(){return mLoginId;}
    public String getPassword(){return mPassword;}
    public String getUserType(){return mUserType;}
    public String getErrorMessage(){return mErrorMessage;}

    public void setErrorMessage(String m) {mErrorMessage = m;}
}
