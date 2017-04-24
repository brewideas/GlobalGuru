package in.co.thingsdata.gurukul.data;

/**
 * Created by Vikas on 4/25/2017.
 */

public class ResetPasswordData {
    private String mOTP;
    private String mMobileNumber;
    private String mNewPassword;
    private String mErrorMessage;

    public ResetPasswordData (String otp, String mobile_number, String new_password){
        mOTP = otp; mMobileNumber = mobile_number; mNewPassword = new_password;
    }

    public void setErrorMessage (String e_msg){mErrorMessage = e_msg;}

    public String getErrorMessage(){return mErrorMessage;}
    public String getOTP(){return mOTP;}
    public String getMobileNumber(){return mMobileNumber;}
    public String getNewPassword(){return mNewPassword;}
}
