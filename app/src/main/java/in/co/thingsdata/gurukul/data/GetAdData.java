package in.co.thingsdata.gurukul.data;

/**
 * Created by Vikas on 4/24/2017.
 */

public class GetAdData {
    private String mAdId;
    private String mContentUrl;
    private String mRedirectUrl;

    public GetAdData(){}
    public void setAdId(String id) {mAdId = id;}
    public void setContentUrl(String c_url) {mContentUrl = c_url;}
    public void setRedirectUrl(String r_url){mRedirectUrl = r_url;}

    public String getAdId(){return mAdId;}
    public String getContentUrl(){return mContentUrl;}
    public String getRedirectUrl(){return mRedirectUrl;}
}
