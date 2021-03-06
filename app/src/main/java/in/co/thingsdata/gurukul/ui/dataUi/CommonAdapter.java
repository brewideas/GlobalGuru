package in.co.thingsdata.gurukul.ui.dataUi;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.co.thingsdata.gurukul.Models.FeesListModel;
import in.co.thingsdata.gurukul.Models.FeesPendingModel;
import in.co.thingsdata.gurukul.R;

/**
 * Created by Ritika on 2/10/2017.
 */
public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ReportCardViewHolder> implements Filterable {

    List<DataOfUi> mListOfData = null;
    List<DataOfUi> mListOfDataOrginal = null;
    private static final String TAG = "CommonAdapter";
    int mScrnNumber = -1;
    public static final int SINGLE_STUDENT_REPORTCARD_DETAIL = 0;
    public static final int TEACHER_VIEW_REPORTCARD = 1;
    public static final int CREATE_REPORT_CARD = 2;
    public static final int NB_STATICS = 3;
    public static final int NB_LIST = 4;
    public static final int SCHOOL_GALLERY = 5;
    public static final int FEES_DETAILS = 6;
    public static final int FEES_PENDING = 7;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);


    }


    @Override
    public ReportCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;

        if(mScrnNumber == SINGLE_STUDENT_REPORTCARD_DETAIL)
        {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.reportcarddetail, parent, false);
        }else if(mScrnNumber == TEACHER_VIEW_REPORTCARD){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rc_tv_studentlist, parent, false);
        }else if(mScrnNumber == NB_STATICS){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.nb_stats_adapter, parent, false);
        }else if(mScrnNumber == NB_LIST){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.nb_list_adapter, parent, false);
        }else if(mScrnNumber == SCHOOL_GALLERY){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.school_gallery_adapter, parent, false);
        }else if(mScrnNumber == FEES_DETAILS){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fees_list_adapter, parent, false);
        }else if(mScrnNumber == FEES_PENDING){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fees_pending_adapter, parent, false);
        }

        return new ReportCardViewHolder(itemView);
    }
int row_index = -1;

    @Override
    public void onBindViewHolder(ReportCardViewHolder holder,final int position) {

        holder.row_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;

                int absPost = 0;
                try {

                    int orgSize = mListOfDataOrginal.size();
                    int newSize = mListOfData.size();
                    if (orgSize != newSize){
                        for (DataOfUi item : mListOfDataOrginal) {
                            if (item.equals(mListOfData.get(position))) {
                                break;
                            }
                            absPost++;
                        }
                    }else{
                        absPost = position;
                    }
                }catch (Exception e){
                    absPost = position;
                    Log.d(TAG,"absPost error");
                }finally {
                    mOnItemClickListener.onItemClick(view,absPost);
                }
             //    notifyDataSetChanged();
            }
        });

//        if(row_index==position){
//            holder.row_view.setBackgroundColor(Color.parseColor("#567845"));
//        } else
//        {
//            holder.row_view.setBackgroundColor(Color.parseColor("#ffffff"));
//        }


        if(mScrnNumber == SINGLE_STUDENT_REPORTCARD_DETAIL) {
            ReportCardModel data = (ReportCardModel) mListOfData.get(position);
            holder.subject.setText(data.getSubject());
            holder.marksObtained.setText(data.getMarksObtained());
            holder.total.setText(data.getTotal());
            holder.percentage.setText(data.getPercentage());
        }else if(mScrnNumber == TEACHER_VIEW_REPORTCARD) {
            ReportCardModel data = (ReportCardModel) mListOfData.get(position);
            holder.name.setText(data.getName());
            int rollNumber = data.getRollNumber();
            holder.rollnumber.setText(Integer.toString(rollNumber));
        }else if(mScrnNumber == CREATE_REPORT_CARD){
            ReportCardModel data = (ReportCardModel) mListOfData.get(position);
            holder.subject.setText(data.getSubject());
            holder.marksObtained.setText(data.getMarksObtained());
            holder.total.setText(data.getTotal());
        }else if(mScrnNumber == NB_STATICS){
            NoticeBoardModel data = (NoticeBoardModel) mListOfData.get(position);
            holder.nbName.setText(data.getStatsName());
            holder.nbRollNum.setText(data.getStatsRolNum());
            holder.nbClass.setText(data.getStatsClass());
            holder.nbResponse.setText(data.getStatsResponse());
        }else if(mScrnNumber == NB_LIST){
            NoticeBoardModel data = (NoticeBoardModel) mListOfData.get(position);
            holder.nbTitle.setText(data.getListTitle());
            holder.nbDiscriptioin.setText(data.getListDiscription());

        }else if(mScrnNumber == SCHOOL_GALLERY){
            schoolGalleryModel data = (schoolGalleryModel)mListOfData.get(position);
           // holder.sg_title.setText(data.getImage_title());
            holder.sg_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.sg_img.setImageResource((data.getImage_ID()));
        }else if(mScrnNumber == FEES_DETAILS){
            FeesListModel data = (FeesListModel)mListOfData.get(position);
            holder.feesName.setText(data.getName());
            holder.feesStudentRegId.setText(data.getRegId());


        }else if(mScrnNumber == FEES_PENDING){
            FeesPendingModel data = (FeesPendingModel)mListOfData.get(position);
            holder.feesPendingName.setText(data.getName());
            holder.feesPendingRollNum.setText(data.getRollNumber());
            holder.feesPendingClass.setText(data.getClassName());
        }


    }

    @Override
    public int getItemCount() {
        return mListOfData.size();
    }

    public CommonAdapter(List<DataOfUi> listOfData, int scrnNumber, OnItemClickListener onItemClickListener){
        mListOfData = listOfData;
        mListOfDataOrginal = listOfData;
        mScrnNumber = scrnNumber;
        mOnItemClickListener = onItemClickListener;

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<DataOfUi> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = mListOfDataOrginal;
                    }else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListOfData = (List<DataOfUi>) results.values;
                CommonAdapter.this.notifyDataSetChanged();
            }
        };

    }

    protected List<DataOfUi> getFilteredResults(String constraint) {
        List<DataOfUi> results = new ArrayList<>();
        for (DataOfUi item : mListOfDataOrginal) {
            if (item.getFilterableObject(mScrnNumber).toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        if(results.size() <=0){
            return mListOfDataOrginal;
        }

        return results;
    }


    public class ReportCardViewHolder extends RecyclerView.ViewHolder {// implements View.OnClickListener, View.OnLongClickListener {


        public TextView subject,marksObtained , total,percentage;
        public TextView name,rollnumber;
        public TextView nbName,nbRollNum,nbClass,nbResponse;
        public TextView nbTitle,nbDiscriptioin;
        private TextView sg_title;
        private ImageView sg_img;

        public TextView feesName,feesStudentRegId;

        public TextView feesPendingName,feesPendingRollNum,feesPendingClass;

        public View row_view;

        public ReportCardViewHolder(View view) {
            super(view);

           //view.setOnClickListener(this);
           //view.setOnLongClickListener(this);

            row_view = view;
            if(mScrnNumber == SINGLE_STUDENT_REPORTCARD_DETAIL) {
                subject = (TextView) view.findViewById(R.id.subject);
                marksObtained = (TextView) view.findViewById(R.id.marksObtained);
                total = (TextView) view.findViewById(R.id.total);
                percentage = (TextView) view.findViewById(R.id.percentage);
            }else if(mScrnNumber == TEACHER_VIEW_REPORTCARD){
                name = (TextView) view.findViewById(R.id.nameOfStudent);
                rollnumber = (TextView) view.findViewById(R.id.rollNumber);
            }else if(mScrnNumber == CREATE_REPORT_CARD){
                subject = (TextView) view.findViewById(R.id.subject);
                marksObtained = (TextView) view.findViewById(R.id.marksObtained);
                total = (TextView) view.findViewById(R.id.total);
            }else if(mScrnNumber == NB_STATICS){
                nbName = (TextView) view.findViewById(R.id.nbSName);
                nbRollNum = (TextView) view.findViewById(R.id.nbSRollN);
                nbClass = (TextView) view.findViewById(R.id.nbSClass);
                nbResponse = (TextView) view.findViewById(R.id.nbSResponse);
            }else if(mScrnNumber == NB_LIST){
                nbTitle = (TextView) view.findViewById(R.id.nbLTitle);
                nbDiscriptioin =  (TextView) view.findViewById(R.id.nbLDes);
            }else if(mScrnNumber == SCHOOL_GALLERY){
                sg_title = (TextView)view.findViewById(R.id.title);
                sg_img = (ImageView) view.findViewById(R.id.img);
            }else if(mScrnNumber == FEES_DETAILS){
                feesName = (TextView) view.findViewById(R.id.Nametitle);
                feesStudentRegId = (TextView) view.findViewById(R.id.regID);

            }else if(mScrnNumber == FEES_PENDING) {
                feesPendingName = (TextView) view.findViewById(R.id.Nametitle);
                feesPendingRollNum = (TextView) view.findViewById(R.id.RollNumebrTitle);
                feesPendingClass = (TextView) view.findViewById(R.id.classTv);
            }
        }

        /*
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }*/
    }

}
