package in.co.thingsdata.gurukul.Models;

import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;

/**
 * Created by Ritika on 5/21/2017.
 */
public class FeesPendingModel extends DataOfUi {

    String name;
    String rollNumber;
    String className;
    String balance;


    public String getName() {
        return name;
    }
    public void setName(String data) {
        this.name = data;
    }

    public String getRollNumber() {
        return rollNumber;
    }
    public void setRollNumber(String data) {
        this.rollNumber = data;
    }

    public String getClassName() {
        return className;
    }
    public void setClassName(String data) {
        this.className = data;
    }

    @Override
    public String getFilterableObject(int screenName) {
        return null;
    }
}
