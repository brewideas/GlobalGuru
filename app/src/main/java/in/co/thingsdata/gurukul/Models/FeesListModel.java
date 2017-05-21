package in.co.thingsdata.gurukul.Models;

import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;

/**
 * Created by Ritika on 5/21/2017.
 */
public class FeesListModel extends DataOfUi {

    String name;
    String rollNumber;
    String lastPaid;
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

    public String getLastFeesPaid() {
        return lastPaid;
    }
    public void setLastFeesPaid(String data) {
        this.lastPaid = data;
    }

    public String getBalanceFees() {
        return balance;
    }
    public void setBalanceFees(String data) {
        this.balance = data;
    }


    @Override
    public String getFilterableObject(int screenName) {
        return null;
    }
}
