package in.co.thingsdata.gurukul.ui.NoticeBoard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.ui.dataUi.NoticeBoardModel;

/**
 * Created by Ritika on 3/20/2017.
 */
public class NoticeBoardDataAdapter extends RecyclerView.Adapter<NoticeBoardDataAdapter.ViewHolder>  {

    private List<NoticeBoardModel> stList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvClassName;


        public CheckBox chkSelected;

        public NoticeBoardModel singlestudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvClassName = (TextView) itemLayoutView.findViewById(R.id.tvName);
            chkSelected = (CheckBox) itemLayoutView
                    .findViewById(R.id.chkSelected);

        }

    }

    // method to access in activity after updating selection
    public List<NoticeBoardModel> getClassList() {
        return stList;
    }
    public NoticeBoardDataAdapter(List<NoticeBoardModel> students) {
        this.stList = students;

    }

    @Override
    public NoticeBoardDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.nb_selectclass_adapter, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoticeBoardDataAdapter.ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.tvClassName.setText(stList.get(position).getClassName());

        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

        viewHolder.chkSelected.setTag(stList.get(position));
        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                NoticeBoardModel contact = (NoticeBoardModel) cb.getTag();

                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());

                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return stList.size();
    }

}
