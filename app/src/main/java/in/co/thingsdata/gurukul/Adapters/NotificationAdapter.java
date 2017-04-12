package in.co.thingsdata.gurukul.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import in.co.thingsdata.gurukul.Models.Studentnotificationmodel;
import in.co.thingsdata.gurukul.R;


/**
 * Created by mouse on 1/9/16.
 */
public class NotificationAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Studentnotificationmodel> mModuleArrayList;
    TextView title, discreption, startdate, enddate;

    public NotificationAdapter(Context context, List<Studentnotificationmodel> moduleArrayList) {
        mContext = context;
        mModuleArrayList = moduleArrayList;
    }


    @Override
    public int getCount() {
        return mModuleArrayList.size();
    }

    @Override
    public Studentnotificationmodel getItem(int position) {
        return mModuleArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mModuleArrayList.get(position).getContentData().hashCode();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.shownotificationlayout, parent, false);
        }
        title= (TextView) convertView.findViewById(R.id.title);
        discreption= (TextView) convertView.findViewById(R.id.notificationdis);
        startdate= (TextView) convertView.findViewById(R.id.startdate);
        enddate= (TextView) convertView.findViewById(R.id.expirydate);
        title.setText(getItem(position).getContentTitle());
        discreption.setText(getItem(position).getContentMsg());
        startdate.setText(getItem(position).getStartDate());
        enddate.setText(getItem(position).getExpireDate());

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


