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
import in.co.thingsdata.gurukul.Models.Studentnotificationmodel;
import in.co.thingsdata.gurukul.R;


/**
 * Created by mouse on 1/9/16.
 */
public class StatNotificationAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Statsmodel> mModuleArrayList;
    TextView title, discreption, startdate, enddate;

    public StatNotificationAdapter(Context context, List<Statsmodel> moduleArrayList) {
        mContext = context;
        mModuleArrayList = moduleArrayList;
    }


    @Override
    public int getCount() {
        return mModuleArrayList.size();
    }

    @Override
    public Statsmodel getItem(int position) {
        return mModuleArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mModuleArrayList.get(position).getUser1d().hashCode();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.statslayout, parent, false);
        }
        title= (TextView) convertView.findViewById(R.id.userid);
        discreption= (TextView) convertView.findViewById(R.id.response);
        title.setText(getItem(position).getUser1d());
        discreption.setText(getItem(position).getAnswer());



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


