package in.co.thingsdata.gurukul.Models;

import in.co.thingsdata.gurukul.ui.dataUi.DataOfUi;

/**
 * Created by Ritika on 5/21/2017.
 */
public class FeesListModel extends DataOfUi {

    String name;
    int rollNumber;
    String lastPaid;
    String balance;


    private static String classOfStudent ,typeOfExam,regId;
    public FeesListModel(String argname ,int rolnum ,String argregId ){

        name = argname;
        regId = argregId;
        rollNumber = rolnum;

    }



    public String getRegId() {
        return regId;
    }



    public String getName() {
        return name;
    }
    public void setName(String data) {
        this.name = data;
    }

    public int getRollNumber() {
        return rollNumber;
    }
    public void setRollNumber(int data) {
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

//    public static  void setSelectedClassRoomId(String data){
  //      mclassRoomId = data;
   // }

    @Override
    public String getFilterableObject(int screenName) {

        String retVal = name;

        return retVal;
    }
}
