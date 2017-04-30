package in.co.thingsdata.gurukul.data;

import java.util.ArrayList;

import in.co.thingsdata.gurukul.data.common.UserSummary;

/**
 * Created by Vikas on 4/30/2017.
 */

public class GetUsersDetailsByIdData {
    private String mErrorMessage;
    private ArrayList<String> mUserIdList;
    private ArrayList<UserSummary> mUserSummaryList = new ArrayList<>();

    public GetUsersDetailsByIdData(ArrayList<String> uid_list){
        mUserIdList = uid_list;
    }



    public void setErrorMessage(String s){mErrorMessage = s;}
    public String getErrorMessage(){return mErrorMessage;}

    public ArrayList<String> getUserIdList(){return mUserIdList;}

    public void addUserSummary(UserSummary s){mUserSummaryList.add(s);}
    public ArrayList<UserSummary> getUserSummaryList(){return mUserSummaryList;}
}
