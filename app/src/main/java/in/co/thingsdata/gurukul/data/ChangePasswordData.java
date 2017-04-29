package in.co.thingsdata.gurukul.data;

/**
 * Created by Vikas on 4/25/2017.
 */

public class ChangePasswordData {
    private String mMobileNumber;
    private String mNewPassword;
    private String mOldPassword;
    private String mErrorMessage;

    public ChangePasswordData(String old_password, String mobile_number, String new_password){
        mMobileNumber = mobile_number; mNewPassword = new_password; mOldPassword = old_password;
    }

    public void setErrorMessage (String e_msg){mErrorMessage = e_msg;}

    public String getErrorMessage(){return mErrorMessage;}
    public String getMobileNumber(){return mMobileNumber;}
    public String getNewPassword(){return mNewPassword;}
    public String getOldPassword(){return mOldPassword;}
}
