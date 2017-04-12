package in.co.thingsdata.gurukul.ui.dataUi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ritika on 3/20/2017.
 */
public class NoticeBoardStaticData{

    static List<NoticeBoardModel> selectedClassList = new ArrayList<NoticeBoardModel>();

    public static  void setSelectedClassList(NoticeBoardModel add){
        selectedClassList.add(add);
    }

    public static List<NoticeBoardModel> getSelectedClassList(NoticeBoardModel add){
        return selectedClassList;
    }

}
