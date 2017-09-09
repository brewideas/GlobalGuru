package in.co.thingsdata.gurukul.ui.Homework;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import in.co.thingsdata.gurukul.Models.Studentnotificationmodel;
import in.co.thingsdata.gurukul.R;

/**
 * Created by Ritika on 8/27/2017.
 */
public class HomeworkAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Studentnotificationmodel> mModuleArrayList;
    TextView title, discreption, startdate;

    public HomeworkAdapter(Context context, List<Studentnotificationmodel> moduleArrayList) {
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
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.showhomeworklayout, parent, false);
        }
        title= (TextView) convertView.findViewById(R.id.title);//subject
        discreption= (TextView) convertView.findViewById(R.id.notificationdis); //description
        startdate= (TextView) convertView.findViewById(R.id.startdate);

        title.setText(getItem(position).getContentTitle());
        discreption.setText(getItem(position).getContentData());
        startdate.setText(getItem(position).getStartDate());


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

