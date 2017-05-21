package in.co.thingsdata.gurukul.ui.Fees;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import in.co.thingsdata.gurukul.R;
import in.co.thingsdata.gurukul.data.common.CommonDetails;
import in.co.thingsdata.gurukul.data.common.UserData;

public class FeesProfile extends AppCompatActivity {

    TextView nameTV,classTV,rolNumTV,LpaidAmountTV,LPaidDateTV;
    EditText balanceRemaingEt , amluntPaidEt;
    Spinner monthSv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fees_profile);

        initRes();
        fillDropDownData();
    }

    public void feesProfile(View view) {
        executeResultQuery();

    }

    void setViewGoneIfNotPrinciple(){

        if(UserData.getUserType() == CommonDetails.USER_TYPE_PRINCIPAL) {
            balanceRemaingEt.setVisibility(View.GONE);
            amluntPaidEt.setVisibility(View.GONE);
            monthSv.setVisibility(View.GONE);
        }

    }


    void initRes(){

        balanceRemaingEt = (EditText)findViewById(R.id.balanceEV);
        amluntPaidEt = (EditText)findViewById(R.id.amountpaidEV);

        nameTV = (TextView)findViewById(R.id.profileNameTV);
        classTV = (TextView)findViewById(R.id.profileClassTV);
        rolNumTV = (TextView)findViewById(R.id.profileRolNumTV);

        LpaidAmountTV = (TextView)findViewById(R.id.lastPaidAmountTV);
        LPaidDateTV = (TextView)findViewById(R.id.lastPaidDateTV);

        monthSv = (Spinner)findViewById(R.id.spinner_month);

        setViewGoneIfNotPrinciple();
    }

    void fillDropDownData(){

        ArrayAdapter<String> spinnerAdapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSv.setAdapter(spinnerAdapterMonth);

        spinnerAdapterMonth.add("January");
        spinnerAdapterMonth.add("Feb");
        spinnerAdapterMonth.add("March");
        spinnerAdapterMonth.add("April");
        spinnerAdapterMonth.add("May");
        spinnerAdapterMonth.add("June");
        spinnerAdapterMonth.add("July");
        spinnerAdapterMonth.add("August");
        spinnerAdapterMonth.add("September");
        spinnerAdapterMonth.add("October");
        spinnerAdapterMonth.add("November");
        spinnerAdapterMonth.add("December");

        spinnerAdapterMonth.notifyDataSetChanged();
    }


    void getDataInputWhileFeesUpdate(){

        if(UserData.getUserType() == CommonDetails.USER_TYPE_PRINCIPAL) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss.SSS");

            String addTime = df2.format(c.getTime());
            String currDate = df.format(c.getTime());

            String dateOfPayment = currDate + "T" + addTime;

            String monthStr = monthSv.getSelectedItem().toString();
            String amountPaid = amluntPaidEt.getText().toString();
            String balanceRemaing = balanceRemaingEt.getText().toString();
        }
    }


    public void executeResultQuery() {


    }

}