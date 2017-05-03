package in.co.thingsdata.gurukul.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import in.co.thingsdata.gurukul.Models.Statsmodel;
import in.co.thingsdata.gurukul.R;


/**
 * Created by mouse on 1/9/16.
 */
public class StatNotificationAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Statsmodel> mModuleArrayList;
    TextView name, rolNum, sClass, sResponse;

    public StatNotificationAdapter(Context context, List<Statsmodel> moduleArrayList) {
        mContext = context;
        mModuleArrayList = moduleArrayList;
    }


    @Override
    public int getCount(){
        return mModuleArrayList.size();
    }

    @Override
    public Statsmodel getItem(int position){
        return mModuleArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mModuleArrayList.get(position).getUser1d().hashCode();
    }
//getaccesstoken
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.nb_stats_adapter, parent, false);
        }
        name = (TextView) convertView.findViewById(R.id.nbSName);
        rolNum = (TextView) convertView.findViewById(R.id.nbSRollN);
        sClass = (TextView) convertView.findViewById(R.id.nbSClass);
        sResponse = (TextView) convertView.findViewById(R.id.nbSResponse);

        name.setText(getItem(position).getName());
        rolNum.setText(getItem(position).getRolNum());
        sClass.setText(getItem(position).getClassName());
        sResponse.setText(getItem(position).getAnswer());



/*
          /* *//* TextView Name = (TextView) convertView.findViewById(R.id.solutiontext);

          *//*  if (getItem(position).getQuiz_Tiyle().equals("null")) {
                Name.setText("-----");
            } else {
                Name.setText("Solution");
            }*/


        return convertView;

    }


}


