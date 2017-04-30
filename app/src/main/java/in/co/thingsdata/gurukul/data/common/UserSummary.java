package in.co.thingsdata.gurukul.data.common;

/**
 * Created by Vikas on 4/30/2017.
 */

public final class UserSummary {
    private String mName;
    private String mUserId;
    private String mEmail;
    private String mRole;
    private String mRefId;
    private String mClassId;
    private String mClassName;
    private String mSection;

    public void setName(String s){mName = s;}
    public void setUserId(String s){mUserId = s;}
    public void setEmail(String s){mEmail = s;}
    public void setRole(String s){mRole = s;}
    public void setRefId(String s){mRefId = s;}
    public void setClassId(String s){mClassId = s;}
    public void setClassName(String s){mClassName = s;}
    public void setSection(String s){mSection = s;}

    public String getName(){return mName;}
    public String getUserId(){return mUserId;}
    public String getEmail(){return mEmail;}
    public String getRole(){return mRole;}
    public String getRefId(){return mRefId;}
    public String getClassId(){return mClassId;}
    public String getClassName(){return mClassName;}
    public String getSection(){return mSection;}
}
