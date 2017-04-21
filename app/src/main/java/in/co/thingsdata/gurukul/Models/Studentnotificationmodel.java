package in.co.thingsdata.gurukul.Models;

/**
 * Created by mouse on 26/3/17.
 */
public class Studentnotificationmodel {
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentMsg() {
        return contentMsg;
    }

    public void setContentMsg(String contentMsg) {
        this.contentMsg = contentMsg;
    }

    public String getContentData() {
        return contentData;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
    }

    public String getStartDate() {
        String returnDate = "NoDate";
        try {
            returnDate = startDate.substring(0,10);
        }catch(Exception e){

        }
       return returnDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpireDate()
    {
        String returnDate = "NoDate";
        try {
            returnDate = expireDate.substring(0,10);
        }catch(Exception e){

        }
        return returnDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getNotifyTargetUser() {
        return notifyTargetUser;
    }

    public void setNotifyTargetUser(String notifyTargetUser) {
        this.notifyTargetUser = notifyTargetUser;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    String contentType;
    String contentTitle;
    String contentMsg;
    String contentData;
    String startDate;
    String expireDate;
    String uniqueId;
    String notifyTargetUser;
    String filter;

}
