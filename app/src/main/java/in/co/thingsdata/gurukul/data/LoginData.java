package in.co.thingsdata.gurukul.data;

/**
 * Created by Vikas on 1/30/2017.
 */

public class LoginData {
    private String mUsername;
    private String mPassword;

    private String mAccessToken;
    private String mRefreshToken;
    private Integer mAccessTokenExpiryTime;
    private String mErrorMessage;

    public LoginData(String u_name, String u_password){
        mUsername = u_name; mPassword = u_password;
    }

    public String getUserName (){
        return mUsername;
    }

    public String getPassword (){
        return mPassword;
    }

    public String getAccessToken (){
        return mAccessToken;
    }

    public String getRefreshToken (){
        return mRefreshToken;
    }

    public void setAccessToken (String token){
        mAccessToken = token;
    }

    public void setRefreshToken (String token){
        mRefreshToken = token;
    }

    public void setAccessTokenExpiry (int seconds){
        mAccessTokenExpiryTime = seconds;
    }

    public Integer getAccessTokenExpiry (){
        return mAccessTokenExpiryTime;
    }

    public void setErrorMessage (String msg){
        mErrorMessage = msg;
    }

    public String getErrorMessage (){
        return mErrorMessage;
    }
}
