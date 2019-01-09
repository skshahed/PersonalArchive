package com.example.shahed.yararch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class TotalDonationFragment extends Fragment {

    private View inflatedView;
    private TextView toDateTV, totalExpenseTV;
    private Calendar calendar;
    private String day, month, year, userName;
    //private Date currentDate;
    private YarDatabaseSource yarDatabaseSource;
    public TotalDonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (inflatedView==null) {
            inflatedView = inflater.inflate(R.layout.fragment_total_donation, container, false);
        }
        toDateTV = (TextView) inflatedView.findViewById(R.id.deToDate);
        totalExpenseTV = (TextView) inflatedView.findViewById(R.id.totalCostExpense);
        yarDatabaseSource = new YarDatabaseSource(getContext());
        userName = "yousuf";
        calendar = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(calendar.getTime());
        String currentDate = (formattedDate);
        //currentDate = calendar.getTime("dd-mm-yyyy");
        toDateTV.setText(currentDate);
        String getTotalExpense = yarDatabaseSource.getTotalExpense(userName);
        totalExpenseTV.setText(getTotalExpense+".00 TK.");
        /*year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);*/

        return inflatedView;
    }

}
