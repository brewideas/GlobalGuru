package in.co.thingsdata.gurukul.ui.dataUi;

import java.io.Serializable;

/**
 * Created by Ritika on 3/20/2017.
 */
public class NoticeBoardModel extends DataOfUi implements Serializable  {

    private String mClassname;
    private boolean isSelected;
    String statsName = null;
    String statsRolNum = null;
    String statsClass = null;
    String statsResponse = null;

    String listTitle = null, listDiscription = null;

    public NoticeBoardModel(String argClassName, boolean isSelected){
        mClassname = argClassName;
        this.isSelected = isSelected;
    }

    public NoticeBoardModel(String arglistTitle, String argDiscription){
        listTitle = arglistTitle;
        listDiscription = argDiscription;
    }

    public NoticeBoardModel(String argName, String argRolNum, String argClass,String argResp){
        statsName = argName;
        statsRolNum = argRolNum;
        statsClass = argClass;
        statsResponse = argResp;
    }


    public String getListTitle() {
        return listTitle;
    }
    public String getListDiscription() {
        return listDiscription;
    }

    public String getClassName() {
        return mClassname;
    }

    public void setClassName(String name) {
        mClassname = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String getFilterableObject(int screenName) {
        return null;
    }


    public void setStatsName(int index ,String name){
        statsName = name;

    }
    public void setStatsRolNum(int index,String data)
    {
        statsRolNum = data;
    }
    public void setStatsClass(int index,String data){
        statsClass = data;

    }
    public void setStatsResponse(int index,String data){
      statsResponse = data;

    }

    public String getStatsName(){

        return statsName;
    }
    public String getStatsRolNum(){

        return statsRolNum;
    }
    public String getStatsClass(){

        return statsClass;
    }
    public String getStatsResponse(){

        return statsResponse;
    }

}
